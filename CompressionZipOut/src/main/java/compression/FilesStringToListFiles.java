package compression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.ControllerProgress;
import application.Controller_Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public class FilesStringToListFiles {

	public List<String> CompressedFile(String Files) throws Throwable {
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
	
	public double[] taillefichier(List<String> selectedF ) {
		coder co=new coder();
		double[] taille=new double[1024];
		for (int i=0;i<selectedF.size();i++) {
		File fichierS=new File(selectedF.get(i));	
		taille[i]=co.taille_file(fichierS.getAbsolutePath(), "mo");
		}
		return taille;
	}

	public List<String> FolderFiles(String Files) throws Throwable {
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
	
	
}