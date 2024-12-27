package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
public class MainMavenZIP extends Application {
	public void start(Stage Fenetre) {
		try {
			StackPane parent = FXMLLoader.load(getClass().getResource("/application/fenetre.fxml"));
			Fenetre.setScene(new Scene(parent));
			Fenetre.setTitle("Compression Mega-Zip");
			Fenetre.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
