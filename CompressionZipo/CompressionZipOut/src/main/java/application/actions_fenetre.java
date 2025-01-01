package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import compression.lzw;
import compression.CompressionV1;
import compression.ThreadsCompression;
import compression.decompressionV1;
import compression.coder;

public class actions_fenetre implements Initializable {
	/**************************************
	 * les composants de la fenetre.
	 ********************************************/
	Stage stage;
	Scene scene;
	StackPane SP;

	@FXML
	private Pane a1;
	@FXML
	private StackPane all;
	@FXML
	private Button charger1;
	@FXML
	private Button charger11;
	@FXML
	private Button charger111;
	@FXML
	private Button charger2;
	@FXML
	private TextArea idtextresultat1;
	@FXML
	private TextArea idtextresultat2;
	@FXML
	private TextField recuptext1;
	@FXML
	private TextField recuptext11;
	@FXML
	private TextField recuptext12;
	@FXML
	private TextField recuptext2;
	@FXML
	public ProgressBar idprogress1;
	@FXML
	public ProgressBar idprogress2;
	/*********************************************
	 * les actions sur la fenetre
	 *********************************************/
	FileChooser filechooser = new FileChooser();

	lzw compres = new lzw();
	DirectoryChooser directo = new DirectoryChooser();
	double taille = 0;
	DecimalFormat dtaille = new DecimalFormat("##.##");

	public String selectedFile() {
		String Files_names = "";
		int i = 0;
		List<File> selected = new ArrayList<File>();
		try {
			Stage win = new Stage();
			List<File> selectedFile = filechooser.showOpenMultipleDialog(win);
			Iterator<File> it = selectedFile.iterator();
			while (it.hasNext()) {
				Files_names += it.next().getAbsolutePath() + ";";
			}
		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}
		return Files_names;
	}

	public File selectedDirect() {
		File selected = null;
		try {
			Stage win = new Stage();
			File selectedDirectory = directo.showDialog(win);
			selected = selectedDirectory;
		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}
		return selected;
	}

	@FXML
	void compresser(ActionEvent event) {
		String Etat = "false";
		double i = 0.1;
		try {
			CompressionV1 comp = new CompressionV1();
			/****************************************************************/
			ThreadProgressBar tb=new ThreadProgressBar((comp.CompressedFile(recuptext1.getText(), recuptext11.getText())));
			
			
			/****************************************************************/
			
			ThreadsCompression vr = new ThreadsCompression((comp.CompressedFile(recuptext1.getText(), recuptext11.getText())), recuptext11.getText());
		//	vr.start();tb.start();
			vr.run(true);tb.run(true);
			double nbr = 1 / (comp.CompressedFile(recuptext1.getText(), recuptext11.getText()).size());
			System.out.println("nbr="+nbr);
			idprogress1.setProgress(nbr);

			/*
			 * System.out.println(Thread.currentThread().getState());
			 * System.out.println("vr.etat()="+vr.etat()); if (vr.etat()==true) { double
			 * debut=vr.debutcomp(); debut=debut+0.5;
			 * 
			 * idprogress1.setProgress(debut);
			 * 
			 * 
			 * } else { vr.interrupt(); vr.run(false);
			 * System.out.println(Thread.currentThread().getState()); }
			 */

			/****************************************************************/

			coder co = new coder();
			taille = coder.taille_file(recuptext1.getText(), "mo");

			comp.CompressedFile(recuptext1.getText(), recuptext11.getText());

			double taille_ap_com = co.taille_file_com(recuptext1.getText(), recuptext11.getText());
			int taux_com = co.tauxComp("Comp", recuptext1.getText(), recuptext11.getText());
			idtextresultat1.setText("Compression termniée ..." + "\n" + "\n" + "-Chemin de votre Fichier : "
					+ recuptext1.getText() + "\n" + "-Compresser dans le Répertoire : " + recuptext11.getText() + "\n"
					+ "-La taille de fichier avant l'opération en MégaOct est : " + dtaille.format(taille) + " Méga-oct"
					+ "\n" + "-le nouveau taille du fichier est: " + taille_ap_com + "\n"
					+ "-le Taux de Compression est :" + taux_com + "%" + "\n"
					+ "le temps écouler dans la compression est : " + vr.duree() + " seconde");
			idtextresultat1.setWrapText(true);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@FXML
	void Ectracter(ActionEvent event) throws IOException {
		try {
			decompressionV1 comp = new decompressionV1();
			coder co = new coder();
			long startTime = System.currentTimeMillis() / 1000;
			comp.DecompressionFile(recuptext2.getText(), recuptext12.getText());
			long endTime = System.currentTimeMillis() / 1000;
			long time_comp = endTime - startTime;
			taille = coder.taille_file(recuptext2.getText(), "mo"); /* le fichier zip */
			double tailleap = co.tailleavantcomp(recuptext2.getText()); /* le fichier apres la compression zip */
			int taux_ext = co.tauxComp("Ext", recuptext2.getText(), recuptext12.getText());
			idtextresultat2.setText("-Chemin de votre Fichier : " + recuptext2.getText() + "\n"
					+ "-Extracter dans le Répertoire : " + recuptext12.getText() + "\n"
					+ "-La taille de fichier mzip en MégaOct est : " + dtaille.format(taille) + " Mégaoct" + "\n"
					+ "-La taille de fichier apres extraction est :" + tailleap + "mo" + "\n"
					+ "-Le Taux de l'extration est de :" + taux_ext + "%" + "\n"
					+ "-le Temps Ecouler dans l'extraction est : " + time_comp + " seconde");
			idtextresultat2.setWrapText(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void close(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void choix1(ActionEvent event) {

		try {

			recuptext1.setText(selectedFile());
			idtextresultat1.setWrapText(true);

		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}
	}

	@FXML
	void choix2(ActionEvent event) {

		try {
			recuptext2.setText(selectedFile());

			idtextresultat2.setWrapText(true);
		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}

	}

	@FXML
	void ousortie1(ActionEvent event) {
		try {

			recuptext11.setText(selectedDirect().getAbsolutePath());
		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}

	}

	@FXML
	void ousortie2(ActionEvent event) {
		try {
			recuptext12.setText(selectedDirect().getAbsolutePath());

		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("Mzip Files", "*.mzip"));
	}
}
