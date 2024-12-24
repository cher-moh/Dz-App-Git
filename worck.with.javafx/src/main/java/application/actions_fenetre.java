package application;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import compression.lzw;


public class actions_fenetre implements Initializable {
	FileChooser filechooser = new FileChooser();
	lzw compres = new lzw();

	public File selectedFile() {
		Stage win = new Stage();
		File selectedFile = filechooser.showOpenDialog(win);
		return selectedFile;
	}
	@FXML
	private TextField recuptext;
	@FXML
	private Pane a1;
	@FXML
	private StackPane all;
	@FXML
	private Button charger;
	@FXML
	private ToggleGroup level;
	@FXML
	private TextArea idtextresultat;

	@FXML
	void choix(ActionEvent event) {
		recuptext.setText(selectedFile().getAbsolutePath());
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
	void compresser(ActionEvent event) {
		try {
			File monfichier = new File(recuptext.getText());/// le fichier à écrire
			String str = "";
			String i;
			FileReader freader = new FileReader(monfichier);
			BufferedReader bfreader = new BufferedReader(freader);
			FileWriter fwriter = new FileWriter("/home/mohamed-lunix/Bureau/Compression.izap");
			BufferedWriter bfwriter = new BufferedWriter(fwriter);
			while ((i = bfreader.readLine()) != null) {
				str += i;
			}
			String comp = compres.lzw_compress(str);
			String AFF = "/" + monfichier.getName();
			bfwriter.write(comp);
			bfwriter.newLine();
			bfwriter.write(AFF);
			bfreader.close();
			bfwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		idtextresultat.setText("Compression LZW Ended");
	}

	@FXML
	void Ectracter(ActionEvent event) throws IOException {
		try {
			lzw extraction = new lzw();
			File monfichier = new File(recuptext.getText());/// le fichier à écrire
			String str = "";
			String i;
			FileReader freader = new FileReader(monfichier);
			BufferedReader bfreader = new BufferedReader(freader);
			while ((i = bfreader.readLine()) != null) {
				str += i;
			}
			String j;
			String extra = "";
			String name = str.substring(str.indexOf("/") + 1, str.length());
			FileReader freaderstr = new FileReader(monfichier);
			BufferedReader bfreaderstr = new BufferedReader(freaderstr);
			FileWriter fwriterstr = new FileWriter("/home/mohamed-lunix/Bureau/" + name);
			BufferedWriter bfwriterstr = new BufferedWriter(fwriterstr);
			while ((j = bfreaderstr.readLine()) != null) {
				extra += j;
			}
			extra = extra.substring(0, str.indexOf("/"));
			//bfwriterstr.write(extra);
			//System.out.print(extraction.lzw_extract(extra));
			bfwriterstr.write(extraction.lzw_extract(extra));
			bfreader.close();
			bfreaderstr.close();
			bfwriterstr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		idtextresultat.setText("Extraction izap Ended");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("IZAP Files", "*.izap"),
				new ExtensionFilter("Text Files", "*.txt"), new ExtensionFilter("All Files", "*.*"));
	}
}
