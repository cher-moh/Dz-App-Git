package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ThreadExtraction {
	static List<String> selectedF = new ArrayList<String>();
	static String chemin;
	static String cheminFolder;
	static Boolean running = true;
	long duree=0;
	boolean etatG = false;

	public ThreadExtraction(String chem, String chemFolder) {
		this.chemin = chem;
		this.cheminFolder = chemFolder;
	}

	public void DecompressionFile(boolean etat)  {
		if (etat = true) {
			long startTime = System.currentTimeMillis() / 1000;		
			try {
				File fichierS = new File(chemin);
				String name = fichierS.getName().substring(0, fichierS.getName().indexOf("."));
				File fosfolderfile = new File(cheminFolder + "/" + name);
				fosfolderfile.mkdir();
				ZipInputStream zipin = new ZipInputStream(new FileInputStream(fichierS.getAbsolutePath()));
				ZipEntry ze;
				while ((ze = new ZipEntry(zipin.getNextEntry())) != null) {				
					File f=new File(ze.getName());
					String namezip = f.getName();		
						FileOutputStream fosfile = new FileOutputStream(cheminFolder + "/" + name + "/" + namezip);
					int length;
					byte[] buffer = new byte[2024];
					while ((length = zipin.read(buffer)) > 0) {
						fosfile.write(buffer, 0, length);
					}					
					fosfile.close();
				}	
				
				
				zipin.closeEntry();
				zipin.close();			
			} catch (IOException ioex) {
				System.out.println("Error in Extraction...");
			}
			long endTime = System.currentTimeMillis() / 1000;
			long time_comp = endTime - startTime;
			duree = time_comp;
		}
		EtatG(false);
	}

	public long dureeextraction() {
		return duree;
	}

	public boolean EtatG(boolean etat) {
		etatG = etat;
		return etatG;
	}

}
