package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javafx.fxml.FXML;
 

public class CompressionV1 {
	
	public void CompressedFile(String selectedF,String directory) throws IOException {
		File fichierS=new File(selectedF);
		String name=fichierS.getName().substring(0, fichierS.getName().indexOf("."));
	   
		/*********************************get the file and create new zip***********************/
		try {
		String Dist ="/home/mohamed-lunix/Bureau/";/*******get destination when the file zip will be created******/
		FileOutputStream fileout = new FileOutputStream(directory+"/"+name+".mzip");/*************create new file zip with the name comp.zip**********************************/
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