package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class voir_resultas_write extends Thread {
	static List<String> selectedF=new ArrayList<String>();
	public voir_resultas_write(List  fichiers) {
		this.selectedF=fichiers;
	}
	public void run() {
		for (int i=0;i<selectedF.size();i++) {
		try {
			File fichierS=new File(selectedF.get(i));		
			String name=fichierS.getName().substring(0, fichierS.getName().indexOf("."));
			String Dist ="/home/mohamed-lunix/Bureau";/*******get destination when the file zip will be created******/
			FileOutputStream fileout = new FileOutputStream(Dist+"/"+name+".mzip");/*************create new file zip with the name comp.zip**********************************/
			CheckedOutputStream checksum = new CheckedOutputStream(fileout, new Adler32());/********************crypté le stream ******************************/
			ZipOutputStream zipout = new ZipOutputStream(checksum);/**********************create new ZIP stream qui va contenir les fichiers a compressé******************************/
			FileInputStream fin = new FileInputStream(fichierS);/****************get the file will be compressed**********************/
			ZipEntry zipEntry = new ZipEntry(fichierS.getName());/*******get the name of the file************/
			zipout.putNextEntry(zipEntry);/************get others file if we have others will be compressed*********************/
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
			}	
		}
	}

}