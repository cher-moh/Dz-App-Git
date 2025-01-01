package application;

import java.lang.Thread.State;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ThreadsMains extends Thread {
	static Stage Fenetre;
	static Scene scene;
	static StackPane parent;
	static boolean etat = true;

	public void run() {
		try {
			new FXMLLoader();
			FXMLLoader loader = FXMLLoader.load(getClass().getResource("/application/fenetre.fxml"));
			parent = FXMLLoader.load(getClass().getResource("/application/fenetre.fxml"));
			actions_fenetre af = loader.getController();
			etat = false;
			// Fenetre.setScene(new Scene(parent));
			// Fenetre.setTitle("Compression Mega-Zip");
			// Fenetre.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		etat = false;
	}

	public State temined() {
		State st = Thread.currentThread().getState();
		if (etat = true) {
			st = Thread.State.RUNNABLE;
		} else {
			st = Thread.State.TERMINATED;
		}
		return st;
	}

}
