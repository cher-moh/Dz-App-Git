package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import application.ControllerProgress;
import application.Controller_Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ThreadsCompression extends Controller_Main {// implements Runnable{
	static List<String> selectedF = new ArrayList<String>();
	static String chemin;
	static String cheminFolder;
	static Boolean running = true;
	public  double taille_folder;
	long duree;
	boolean etatG = false;

	public ThreadsCompression(List<String> fichiers, String chem, String chemFolder) {
		this.selectedF = fichiers;
		this.chemin = chem;
		this.cheminFolder = chemFolder;
	}

	/**************************************************************************************/
	public void compression_files() {
	
		try {
			
			for (int i = 0; i < selectedF.size(); i++) {
				File fichierS = new File(selectedF.get(i));
				int point = 0;
				String name = null;
				point = fichierS.getName().indexOf(".");
				if (point != 0) {
					name = fichierS.getName().substring(0, fichierS.getName().indexOf("."));
				} else {
					name = fichierS.getName().substring(0, fichierS.getName().length());
				}				
				FileOutputStream fileout = new FileOutputStream(chemin +"/"+ name + ".mzip"); 
				CheckedOutputStream checksum = new CheckedOutputStream(fileout,new Adler32());
				ZipOutputStream zipout = new ZipOutputStream(checksum);
				FileInputStream fin = new FileInputStream(fichierS);
				ZipEntry zipEntry = new ZipEntry(fichierS.getName());
				zipout.putNextEntry(zipEntry);
				int length;
				byte[] buffer = new byte[2024];			
				while ((length = fin.read(buffer)) > 0) {
					zipout.write(buffer, 0, length);
				}
				zipout.closeEntry();
				zipout.finish();
				fin.close();
				zipout.close();
			}
			
		} catch (IOException ioex) {
			System.out.println("Exception compressed files ..!!");
		}
	

	}

	public void compression_file(ZipOutputStream zipout, File file_to_zip) {
		File newzipfile = file_to_zip;
		String chemin_parent_file = newzipfile.getParent();
		String name_file = newzipfile.getName();
		try {
			FileInputStream fin = new FileInputStream(newzipfile);
			ZipEntry zipEntry = new ZipEntry(chemin_parent_file + "/" + name_file);
			zipout.putNextEntry(zipEntry);
			int length;
			byte[] buffer = new byte[2024];
			/******************************* read file *************************/
			while ((length = fin.read(buffer)) > 0) {
				zipout.write(buffer, 0, length);
			}
			/****************** close all streaming opened *********************/
			fin.close();
		} catch (IOException ioex) {

			System.out.println("Exception compressed files!!");
		}
	}

	/**********************************************************************************************/

	public void CompressionFolder(ZipOutputStream zipout, File zip_director) {
		try {
			File[] listfiles = zip_director.listFiles();
			String chemin = zip_director.getAbsolutePath();
			String name_direct = zip_director.getName();
			for (int i = 0; i < listfiles.length; i++) {
				if (listfiles[i].isFile()) {
					String namefile = listfiles[i].getName();
					FileInputStream fin = new FileInputStream(listfiles[i]);
					ZipEntry zipEntry = new ZipEntry(chemin + "/" + listfiles[i].getName());
					zipout.putNextEntry(zipEntry);			
					byte[] buffer = new byte[2024];
					int length;				
					while ((length = fin.read(buffer)) > 0) {
						double k=(double) 1/(double) length;
						zipout.write(buffer, 0, length);						
					}
					fin.close();
				} else {
					CompressionFolder(zipout, listfiles[i]);
				}
			}
		} catch (IOException ioex) {
			System.out.println("Exception Compressed Folder !!");
		}

	}	
	/*********************************************************************************/
	public void run() {
		try {			
			if (running = true) {	
				
				if (selectedF != null) {				
					compression_files() ;			
				} else {
					if (cheminFolder != null) {
						long startTime = System.currentTimeMillis() / 1000;
						File Folder = new File(cheminFolder);
						String name = Folder.getName();
						FileOutputStream FolderOut;
						FolderOut = new FileOutputStream(chemin + "/" + name + ".mzip");
						File[] listfiles = Folder.listFiles();
						CheckedOutputStream checksum = new CheckedOutputStream(FolderOut, new Adler32());
						ZipOutputStream zipout = new ZipOutputStream(checksum);					
						for (int i = 0; i < listfiles.length; i++) {
							if (listfiles[i].isDirectory()) {
								CompressionFolder(zipout, listfiles[i].getAbsoluteFile());
							} else {
								compression_file(zipout, listfiles[i].getAbsoluteFile());
							}
						}
						long endTime = System.currentTimeMillis() / 1000;
						long time_comp = endTime - startTime;
						duree = time_comp;
						zipout.closeEntry();
						zipout.finish();
						zipout.close();
					}
				}
			}	
			
			System.out.println(taille_folder);
			audio_end();
			running = false;
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//text("Compression Terminee avec succée ..");
	}
	public long dureecompression() {
		return duree;
	}
public double t() {
	return taille_folder;
}
	public boolean EtatG(boolean etat) {
		return running;
	}
	/*****************************/
	
	 public double taille_CompressionFolder() {
		 double tailles = 0;
			try {
				File Folder = new File(cheminFolder);
				String name = Folder.getName();
				FileOutputStream FolderOut;
				FolderOut = new FileOutputStream(chemin + "/" + name + ".mzip");
				File[] listfiles = Folder.listFiles();
				CheckedOutputStream checksum = new CheckedOutputStream(FolderOut, new Adler32());
				ZipOutputStream zipout = new ZipOutputStream(checksum);					
				for (int i = 0; i < listfiles.length; i++) {
					if (listfiles[i].isFile()) {
						String namefile = listfiles[i].getName();
						FileInputStream fin = new FileInputStream(listfiles[i]);
						ZipEntry zipEntry = new ZipEntry(chemin + "/" + listfiles[i].getName());
						zipout.putNextEntry(zipEntry);			
						byte[] buffer = new byte[2024];
						int length;				
						while ((length = fin.read(buffer)) > 0) {
							zipout.write(buffer, 0, length);
							tailles++;
						}
						
						fin.close();
					} else {
						CompressionFolder(zipout, listfiles[i]);
					}
				}
				zipout.closeEntry();
				zipout.finish();
				zipout.close();
			} catch (IOException ioex) {
				System.out.println("Exception taille de Compressed Folder !!");
			}
	
			System.out.println("taille"+tailles);
			 return tailles;
		}

}




