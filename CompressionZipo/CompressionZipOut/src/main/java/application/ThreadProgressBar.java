package application;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import compression.CompressionV1;
import compression.ThreadsCompression;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ThreadProgressBar extends Thread {

	static boolean etat = false;
	Stage stage;
	Scene scene;
	StackPane SP;

	static List<String> selectedF = new ArrayList<String>();
	static String ch;
	boolean etatG;
	double dureecomp;
	double dcomp;
	static double comp = 0;

	public ThreadProgressBar(List<String> fichiers) {
		this.selectedF = fichiers;

	}

	public void run(boolean etat) throws IOException {

		if (etat = true) {
			try {
				FXMLLoader loader = new FXMLLoader((getClass().getResource("/application/ProgressBar.fxml")));
				StackPane SP = loader.load();
				ProgressBarController pc = loader.getController();
				scene = new Scene(SP);
				stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("ProgresseBar");
				stage.show();
				for (int i = 0; i < selectedF.size(); i++) {
					while (comp < 1.0) {
						pc.IdProgressBar.setProgress(comp);
						comp = comp + 0.1;
					//	System.out.println("value progressbar=" + pc.IdProgressBar.getProgress());
					}
					/*
					 * double z=vr.CatchTimeComp(); pc.progress(z); if
					 * (Thread.State.RUNNABLE!=vr.temined()) { stage.close() ;
					 * System.out.println(vr.temined()); }
					 */

				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		} //// if

		etat = false;
		Thread.currentThread().interrupt();
	//	stage.close();
	}

	public State temined() {
		State st = Thread.currentThread().getState();
		if (etat = true) {
			st = Thread.State.RUNNABLE;
		} else {
			st = Thread.State.TERMINATED;
			// stage.close();
		}
		return st;
	}

}
