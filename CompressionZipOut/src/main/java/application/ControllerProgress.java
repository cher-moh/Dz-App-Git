package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerProgress {
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	@FXML
	public
	 ProgressBar IdProgressBar;
	@FXML
	 Text IdText;
	@FXML
	public void progress(double k) {
		double i=+0.1;
			IdProgressBar.setProgress(k);
			System.out.print("k:"+k);	
	}



}
