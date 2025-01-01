package compression;

import java.util.ArrayList;
import java.util.List;

import application.actions_fenetre;
import javafx.scene.control.ProgressBar;

public class CompressionV1 {

	public List<String>  CompressedFile(String Files, String Directory) {
		char[] str = new char[Files.length()];
		String[] chemin = new String[20];
		List<String> selectedF = new ArrayList<String>();
		int j = 0;
		String s = "";
		for (int i = 0; i < Files.length(); i++) {
			str[i] += Files.charAt(i);
		}
		for (int i = 0; i < Files.length(); i++) {
			if (str[i] != ';') {
				s += str[i];
			}else {
				selectedF.add(s);
				s="";
			}				
		}
		return selectedF;
	}
	 void progress(ProgressBar p) {
		double i=p.getProgress();
		if (i<0) {
			i=0.1;
		}else
		{
			i=i+0.1;
			if (i>1.0) {
				i=1.0;
			}
		}
		p.setProgress(i);	
	}
}