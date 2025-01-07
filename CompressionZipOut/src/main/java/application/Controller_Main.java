package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import compression.FilesStringToListFiles;
import compression.ThreadsCompression;
import compression.ThreadExtraction;
import compression.coder;

public class Controller_Main implements Initializable {

	Parent root;
	Stage stage;
	Scene scene;
	AnchorPane ArchorP;

	/**************************************
	 * les composants de la fenetre.
	 ********************************************/
	@FXML
	private Button IdFolder;
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
	private ProgressBar idprogress2;
	/*********************************************
	 * les actions sur la Fenetre*****************
	 *********************************************/
	FileChooser filechooser = new FileChooser();
	DirectoryChooser directo = new DirectoryChooser();
	double taille ;
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

	@FXML
	void Ectracter(ActionEvent event) throws IOException {
		try {
			String chemin1=recuptext2.getText();
			String chemin2=recuptext12.getText();
			ThreadExtraction comp = new ThreadExtraction(chemin1, chemin2);
			comp.DecompressionFile(true);
			idtextresultat2.setText("Extraction Terminée ..");
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
	void choix2(ActionEvent event) {

		try {
			Stage win = new Stage();
			File selectedFile = filechooser.showOpenDialog(win);
			recuptext2.setText(selectedFile.getAbsolutePath());

			idtextresultat2.setWrapText(true);
		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}
	}

	@FXML
	void ousortie1(ActionEvent event) {
		try {
			File selectedDirectory = directo.showDialog(new Stage());	
			recuptext11.setText(selectedDirectory.getAbsolutePath());
		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}

	}

	@FXML
	void ousortie2(ActionEvent event) {
		try {
			File selectedDirectory = directo.showDialog(new Stage());	
			recuptext12.setText(selectedDirectory.getAbsolutePath());

		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}
	}
	   @FXML
	    void choixDirec(ActionEvent event) {
			try {
				File selectedDirectory = directo.showDialog(new Stage());			
				recuptext1.setText(selectedDirectory.getAbsolutePath());
			} catch (Exception e) {
				System.out.print("No Directory Selected !!");
			}

	    }
	   public String foldertozip() {
		   String foldertozip=recuptext1.getText();
		   return foldertozip;
	   }
	    @FXML
	    void CompresserFolder(ActionEvent event) throws Throwable {			
				ThreadsCompression vr = new ThreadsCompression(null,recuptext11.getText(),foldertozip() );
				vr.compression();
				double i=0.1;
				while (vr.EtatG(true)&& i<=1.0) {
					idprogress1.setProgress(i);
					i=i+0.1;
				}
				idtextresultat1.setText("Compression Folder Termniée ..." );
				idtextresultat1.setWrapText(true);
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("Mzip Files", "*.mzip"));
		idprogress1.setProgress(0);
		idprogress2.setProgress(0);
	

	}

}
