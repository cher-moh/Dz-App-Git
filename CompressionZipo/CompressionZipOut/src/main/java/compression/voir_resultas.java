package compression;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class voir_resultas extends Thread {
	static List<String> selectedF=new ArrayList<String>();
	public voir_resultas(List  fichiers) {
		this.selectedF=fichiers;
	}
	public void run() {
		for (int i=0;i<selectedF.size();i++) {
		File fichierS=new File(selectedF.get(i));
		String name=fichierS.getAbsolutePath();
		System.out.println(name);
		}	
	}

}