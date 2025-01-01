package application;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ProgressBarController {
	  @FXML
     public ProgressBar IdProgressBar;
	  @FXML
    Text IdText;
    @FXML
    private StackPane SP;
    
    public void Progress(ProgressBar p) {
		double in =p.getProgress();
		System.out.println("value progressbar="+p.getProgress());
			in=in+0.1;
			p.setProgress(in);
		
		
	}
   
}
