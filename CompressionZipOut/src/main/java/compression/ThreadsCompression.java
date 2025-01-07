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
			
	
	
	public void compression_file(ZipOutputStream zipout,File file_to_zip) {
			long startTime = System.currentTimeMillis() / 1000;
			File newzipfile=file_to_zip;
			String chemin_parent_file=newzipfile.getParent();
			String name_file=newzipfile.getName();
		try {				
			FileInputStream fin = new FileInputStream(newzipfile);
			ZipEntry zipEntry = new ZipEntry(chemin_parent_file+"/"+name_file);
			zipout.putNextEntry(zipEntry);
			int length;	
			byte[] buffer = new byte[2024];
			/*******************************read file*************************/
			while ((length = fin.read(buffer)) > 0) {
				zipout.write(buffer, 0, length);
			}
			/******************close all streaming opened *********************/
		
			fin.close();

			
			}catch (IOException ioex) {
				Thread.currentThread().interrupt();	
				System.out.println("Exception !!");
				
			} 
		
		long endTime = System.currentTimeMillis() / 1000;
		long time_comp = endTime - startTime;
		duree=time_comp;
		}
	
		
	
	
	public void CompressionFolder(ZipOutputStream zipout,File zip_director)  {

			long startTime = System.currentTimeMillis() / 1000;
			try {	
				File[] listfiles=	zip_director.listFiles();
				String chemin=zip_director.getAbsolutePath();
				String name_direct=zip_director.getName();		
			for (int i=0;i<listfiles.length;i++) {	
				if (listfiles[i].isFile()) {
				String namefile=listfiles[i].getName();	
				FileInputStream fin = new FileInputStream(listfiles[i]);
				ZipEntry zipEntry = new ZipEntry(chemin+"/"+listfiles[i].getName());
				zipout.putNextEntry(zipEntry);
				byte[] buffer = new byte[2024];
				int length;	
				while ((length = fin.read(buffer)) > 0) {
					zipout.write(buffer, 0, length);
				}
				/******************close all streaming opened *********************/
				fin.close();			
		     	}
				else {
					CompressionFolder( zipout,listfiles[i]);
				}
				}			
			}catch (IOException ioex) {			
				System.out.println("Exception Compressed Folder !!");				
			} 
			long endTime = System.currentTimeMillis() / 1000;
			long time_comp = endTime - startTime;
			duree=time_comp;	
	}
	
	public void compression() throws IOException {
		try {
		File Folder=new File(cheminFolder);
		String name=Folder.getName();			
		FileOutputStream FolderOut;
		FolderOut = new FileOutputStream(chemin+"/"+name+".mzip");	
		File[] listfiles=	Folder.listFiles();
		CheckedOutputStream checksum = new CheckedOutputStream(FolderOut, new Adler32());
		ZipOutputStream zipout = new ZipOutputStream(checksum);
		for (int i=0;i<listfiles.length;i++) {		
			if (listfiles[i].isDirectory()){	
				 CompressionFolder(zipout,listfiles[i].getAbsoluteFile()) ;			
			}else {
				compression_file(zipout,listfiles[i].getAbsoluteFile());
			}
		}
		zipout.closeEntry();
		zipout.finish();
		zipout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
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

