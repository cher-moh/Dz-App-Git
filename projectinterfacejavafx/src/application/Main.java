package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage Fenetre) {
		try {
			StackPane parent = FXMLLoader.load(getClass().getResource("/application/fenetre.fxml"));
			Fenetre.setScene(new Scene(parent));
			Fenetre.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
