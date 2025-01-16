package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import compression.FilesStringToListFiles;
import compression.ThreadsCompression;
import compression.ThreadExtraction;

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
	public TextArea idtextresultat1;
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
	@FXML
	private CheckBox Idcheck;
	@FXML
	private Text IdText;
	File Parent;
    @FXML
    public TextField idtache;
    @FXML
    private Button idbuttoncomp;
    int i=0;
	
	
	/*********************************************
	 **********les actions sur la Fenetre*********
	 *********************************************/
	FileChooser filechooser = new FileChooser();
	DirectoryChooser directo = new DirectoryChooser();
	double taille;
	DecimalFormat dtaille = new DecimalFormat("##.##");
	public String selectedFile() {
		String Files_names = "";
		int i = 0;
		List<File> selected = new ArrayList<File>();
		try {
			Stage win = new Stage();
			List<File> selectedFile = filechooser.showOpenMultipleDialog(win);
			Iterator<File> it = selectedFile.iterator();
			Parent = ParentselectedFile(selectedFile);
			while (it.hasNext()) {
				Files_names += it.next().getAbsolutePath() + ";";
			}
		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}
		return Files_names;
	}

	public List<File> listfiles() {
		Stage win = new Stage();
		List<File> selectedFiles = filechooser.showOpenMultipleDialog(win);
		return selectedFiles;
	}

	public File ParentselectedFile(List<File> selectedFiles) {
		String Files_names = "";
		int i = 0;
		File parent = null;
		try {
			parent = new File(selectedFiles.getLast().getParent());

		} catch (Exception e) {
			System.out.print("No File Selected !!");
		}
		return parent;
	}

	@FXML
	void Ectracter(ActionEvent event) throws IOException {
		try {
			String chemin1 = recuptext2.getText();
			String chemin2 = recuptext12.getText();
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
			filechooser.getExtensionFilters().addAll(new ExtensionFilter("Mzip Files", "*.mzip"));
			File selectedFile = filechooser.showOpenDialog(win);
			recuptext2.setText(selectedFile.getAbsolutePath());
			idtextresultat2.setWrapText(true);
		} catch (Exception e) {
			System.out.print("No File Selected to Extract !!");
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
			if (Idcheck.isSelected()) {
				File selectedDirectory = directo.showDialog(new Stage());
				recuptext1.setText(selectedDirectory.getAbsolutePath());
				idtache.setText("0");i=0;	idprogress1.setProgress(0);	text("");//i variable global //initialisation 
			} else {
				recuptext1.setText(selectedFile());
				idtache.setText("0");i=0;	idprogress1.setProgress(0);	text("");//i variable global//initialisation
			}
		} catch (Exception e) {
			System.out.print("No Directory Selected !!");
		}
	}
	public String foldertozip() {
		String foldertozip = recuptext1.getText();
		return foldertozip;
	}

	@FXML
	void CompresserFolder(ActionEvent event) throws Throwable {
		if (i!=0) {	idprogress1.setProgress(0);	}
		if (i==0) {  runcompression(recuptext11.getText(),foldertozip());	i++;idtache.setText(String.valueOf(i));}
	}	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		idprogress1.setProgress(0);
		idprogress2.setProgress(0);
		idtache.setText("0");
		Idcheck.setIndeterminate(false);
	}
	@FXML
	void ActionCheck(ActionEvent event) {
		if (Idcheck.isSelected()) {
			IdText.setText("Pleaze set Your Folder To compress");
		} else {
			IdText.setText("Pleaze set Your Files To compress");
		}
	}
	public void audio_end() {
		Media sound = new Media(getClass().getResource("/1.wav").toExternalForm());
		MediaPlayer player = new MediaPlayer(sound);
		player.setAutoPlay(true);
		player.setVolume(0.9);
		player.play();
	}
	public void runPB11(double taille) {	
		double div=(double)1/(double)taille;
		double i = idprogress1.getProgress();
		DecimalFormat dtaille = new DecimalFormat("##.##");
		i = Double.valueOf(dtaille.format(i));
			while (i <= 1 ) {
				idprogress1.setProgress(i);
				i = i + div;
			}	
		}
	public void text(String textt) {	
		idtextresultat1.setText(textt);
	
		}
	public void runcompression( String chemin, String cheminFolder) throws Throwable {
		boolean running=true;
		boolean run=true;
		long duree;
		double j=0;
		text("");
		ThreadsCompression vr=	new ThreadsCompression(null, recuptext11.getText(), foldertozip());
		try {					
				if(!(Idcheck.isSelected())) {					
						try {
							FilesStringToListFiles lf = new FilesStringToListFiles();
							List<String> selectedF = lf.CompressedFile(recuptext1.getText());
							for (int i = 0; i < selectedF.size(); i++) {
								File fichierS = new File(selectedF.get(i));
								int point = 0;
								String name = null;
								point = fichierS.getName().indexOf(".");
								if (point != 0) {
									name = fichierS.getName().substring(0, fichierS.getName().indexOf("."));
								} else {
									name = fichierS.getName().substring(0, fichierS.getName().length());
								}				
								FileOutputStream fileout = new FileOutputStream(chemin +"/"+ name + ".mzip"); 
								CheckedOutputStream checksum = new CheckedOutputStream(fileout,new Adler32());
								ZipOutputStream zipout = new ZipOutputStream(checksum);
								FileInputStream fin = new FileInputStream(fichierS);
								ZipEntry zipEntry = new ZipEntry(fichierS.getName());
								zipout.putNextEntry(zipEntry);
								int length;
								byte[] buffer = new byte[2024];									
								while ((length = fin.read(buffer)) > 0) {									
									zipout.write(buffer, 0, length);
									double k=0;
									k=(double)1/(double)i;							
									idprogress1.setProgress(j);
									j=j+k;							
								}
								zipout.closeEntry();
								zipout.finish();
								fin.close();
								zipout.close();							
							}
							
						} catch (IOException ioex) {
							System.out.println("Exception Compressed files ..!!");
						}				
				} else {
					if (cheminFolder != null) {
						long startTime = System.currentTimeMillis() / 1000;
						File Folder = new File(cheminFolder);
						String name = Folder.getName();
						FileOutputStream FolderOut;
						FolderOut = new FileOutputStream(chemin + "/" + name + ".mzip");
						File[] listfiles = Folder.listFiles();
						CheckedOutputStream checksum = new CheckedOutputStream(FolderOut, new Adler32());
						ZipOutputStream zipout = new ZipOutputStream(checksum);						
						for (int i = 0; i < listfiles.length; i++) {
							if (listfiles[i].isDirectory()) {
								vr.CompressionFolder(zipout, listfiles[i].getAbsoluteFile());
							} else {
								vr.compression_file(zipout, listfiles[i].getAbsoluteFile());
							}
							double k=0;
							k=(double)1/(double)i;							
							idprogress1.setProgress(j);
							j=j+k;
						}
						long endTime = System.currentTimeMillis() / 1000;
						long time_comp = endTime - startTime;
						duree = time_comp;
						zipout.closeEntry();
						zipout.finish();
						zipout.close();
					}
				}				
				running = false;			
	} catch (IOException e) {
		e.printStackTrace();
	}
		audio_end();
		text("Compression Terminée avec sucée ..");
		
	}
	
	
	
}
