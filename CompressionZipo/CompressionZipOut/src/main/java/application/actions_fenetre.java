package application;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import compression.decompressionV1;
import compression.coder;

public class actions_fenetre implements Initializable {
	/**************************************
	 * les composants de la fenetre.
	 ********************************************/
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

	/*********************************************
	 * les actions sur la fenetre
	 *********************************************/
	FileChooser filechooser = new FileChooser();
	lzw compres = new lzw();
	DirectoryChooser directo=new DirectoryChooser();
	double taille=0;
	DecimalFormat dtaille = new DecimalFormat("##.##");
	public File selectedFile() {
		File selected=null;
	
		try {
		Stage win = new Stage();
		File selectedFile = filechooser.showOpenDialog(win);	
		selected=	selectedFile;
	
		
		 }
		catch (Exception e) {System.out.print("No File Selected !!");}
		return selected;
	}
	public File selectedDirect() {
		File selected=null;
		try {
		Stage win = new Stage();
		File selectedDirectory = directo.showDialog(win);		
		selected=selectedDirectory;
		}
		catch (Exception e) {System.out.print("No Directory Selected !!");}
		return selected;
	}

	@FXML
	void compresser(ActionEvent event) {
	
		try {
			CompressionV1 comp = new CompressionV1();
			coder co =new coder();
			comp.CompressedFile(recuptext1.getText(),recuptext11.getText());
			taille=coder.taille_file(recuptext1.getText(), "mo");//fichier initiale
			double taille_ap_com=co.taille_file_com(recuptext1.getText(), recuptext11.getText());
			int taux_com=co.tauxComp("Comp", recuptext1.getText(), recuptext11.getText());
			idtextresultat1.setText("Compression termniée ..."+"\n"+"\n"+"-Chemin de votre Fichier : "+
			recuptext1.getText()+ "\n"+"-Compresser dans le Répertoire : "+recuptext11.getText()+"\n"+
			"-La taille de fichier avant l'opération en MégaOct est : "+dtaille.format(taille)+
			" Méga-oct"+"\n"+"-le nouveau taille du fichier est: "+
			taille_ap_com +	"\n"+"-le Taux de Compression est :"+taux_com+"%");
			idtextresultat1.setWrapText(true);			
		} catch (Exception e) {
			e.printStackTrace();
		
		}

	
	}

	@FXML
	void Ectracter(ActionEvent event) throws IOException {
		try {
			decompressionV1 comp = new decompressionV1();
			coder co =new coder();
			comp.DecompressionFile(recuptext2.getText(),recuptext12.getText());
			taille=coder.taille_file(recuptext2.getText(), "mo");	/* le fichier zip*/
			double tailleap=co.tailleavantcomp(recuptext2.getText());	/* le fichier apres la compression zip*/
			int taux_ext=co.tauxComp("Ext", recuptext2.getText(), recuptext12.getText());
			idtextresultat2.setText("-Chemin de votre Fichier : "+recuptext2.getText()+ "\n"+
			"-Extracter dans le Répertoire : "+recuptext12.getText() 
			+ "\n"+"-La taille de fichier mzip en MégaOct est : "+dtaille.format(taille)+" Mégaoct"+
			"\n"+"- La taille de fichier apres extraction est :"+tailleap+"mo"+
			"\n"+"- Le Taux de l'extration est de :"+taux_ext+"%");
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
		recuptext1.setText(selectedFile().getAbsolutePath());
		idtextresultat1.setWrapText(true);
	
		}catch (Exception e) {System.out.print("No File Selected !!");}
	}
	@FXML
	void choix2(ActionEvent event) {
		
	try {
		recuptext2.setText(selectedFile().getAbsolutePath());
		
		idtextresultat2.setWrapText(true);
	}	catch (Exception e) {System.out.print("No File Selected !!");}
		
	}

	@FXML
	void ousortie1(ActionEvent event) {
		try {
			
			recuptext11.setText(selectedDirect().getAbsolutePath());
		}	catch (Exception e) {System.out.print("No Directory Selected !!");}
		
	}

	@FXML
	void ousortie2(ActionEvent event) {
		try {
			recuptext12.setText(selectedDirect().getAbsolutePath());
			
		}	catch (Exception e) {System.out.print("No Directory Selected !!");}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filechooser.getExtensionFilters().addAll(
				new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Text Files", "*.txt"), 
				new ExtensionFilter("Mzip Files", "*.mzip"));
	}
}
