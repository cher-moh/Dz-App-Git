package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import application.ControllerProgress;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ThreadsCompression  {
	static List<String> selectedF=new ArrayList<String>();
	static String chemin;
	static String cheminFolder;
	static Boolean running=true;
	
long duree;
boolean etatG=false;
	public ThreadsCompression(List <String> fichiers,String chem,String chemFolder) {
		this.selectedF=fichiers;
		this.chemin=chem;
		this.cheminFolder=chemFolder;
	}
	
	
		/**************************************************************************************/
			
	
	
	public void compression(boolean etat) {
		if (etat=true) {
			long startTime = System.currentTimeMillis() / 1000;
		for (int i=0;i<selectedF.size();i++) {
		try {	
			File fichierS=new File(selectedF.get(i));		
			String name=fichierS.getName().substring(0, fichierS.getName().indexOf("."));		
			FileOutputStream fileout = new FileOutputStream(chemin+"/"+name+".mzip");
			CheckedOutputStream checksum = new CheckedOutputStream(fileout, new Adler32());
			ZipOutputStream zipout = new ZipOutputStream(checksum);
			FileInputStream fin = new FileInputStream(fichierS);
			ZipEntry zipEntry = new ZipEntry(fichierS.getName());
			zipout.putNextEntry(zipEntry);
			int length;	
			byte[] buffer = new byte[2024];
			/*******************************read file*************************/
			while ((length = fin.read(buffer)) > 0) {
				zipout.write(buffer, 0, length);
			}
			/******************close all streaming opened *********************/
			zipout.closeEntry();
			zipout.finish();
			fin.close();
			zipout.close();
			
			}catch (IOException ioex) {
				Thread.currentThread().interrupt();	
				System.out.println("Exception !!");
				
			} 
		}
		long endTime = System.currentTimeMillis() / 1000;
		long time_comp = endTime - startTime;
		duree=time_comp;
		}
		EtatG(false);	
	}
	
	public void CompressionFolder(boolean etat)  {
		if (etat=true) {
			long startTime = System.currentTimeMillis() / 1000;
			try {	
				File Folder=new File(cheminFolder);
				String name=Folder.getName();			
				FileOutputStream FolderOut = new FileOutputStream(chemin+"/"+name+".mzip");
				File[] listfiles=	Folder.listFiles();
				CheckedOutputStream checksum = new CheckedOutputStream(FolderOut, new Adler32());
				ZipOutputStream zipout = new ZipOutputStream(checksum);
			for (int i=0;i<listfiles.length;i++) {		
				File file=new File(listfiles[i].getAbsolutePath());
				String namefile=file.getName();
				FileInputStream fin = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zipout.putNextEntry(zipEntry);
				byte[] buffer = new byte[2024];
				int length;	
				while ((length = fin.read(buffer)) > 0) {
					zipout.write(buffer, 0, length);
				}
				/******************close all streaming opened *********************/
				
				fin.close();
				
			}
			zipout.closeEntry();
			zipout.finish();
			zipout.close();
			}catch (IOException ioex) {
			
				System.out.println("Exception Compressed Folder !!");
				
			} 
			long endTime = System.currentTimeMillis() / 1000;
			long time_comp = endTime - startTime;
			duree=time_comp;
		}	
		EtatG(false);	
	}
	

	
	public long dureecompression() {
		return duree;
	}

	public boolean EtatG(boolean etat) {
		etatG=etat;
		return etatG;
	}
	/*****************************/
	}

