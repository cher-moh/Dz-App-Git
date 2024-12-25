package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressionV1 {
	
	public void CompressedFile(String selectedF) throws IOException {
		File fichierS=new File(selectedF);
		/*********************************get the file and create new zip*******************************************************/
		try {
		String Dist ="/home/mohamed-lunix/Bureau/";/*******get distenation when the file zip will be created******/
		FileOutputStream fileout = new FileOutputStream(Dist+"comp.zip");/*************create new file zip with the name comp.zip**********************************/
		CheckedOutputStream checksum = new CheckedOutputStream(fileout, new Adler32());/********************crypté le stream ******************************/
		ZipOutputStream zipout = new ZipOutputStream(checksum);/**********************create new ZIP stream qui va contenir les fichiers a compressé******************************/
		FileInputStream fin = new FileInputStream(fichierS);/****************get the file will be compressed**********************/
		ZipEntry zipEntry = new ZipEntry(fichierS.getName());/*******get the name of the file************/
		zipout.putNextEntry(zipEntry);/************get others file if we have others will be compressed*********************/
		int length;
		byte[] buffer = new byte[1024];
		while ((length = fin.read(buffer)) > 0) {/************read file*****************/
			zipout.write(buffer, 0, length);
		}
		zipout.closeEntry();/****************close all streaming opned ***************/
		zipout.finish();
		fin.close();
		zipout.close();
		}catch (IOException ioex) {
			
		}
		

		/*****************************************************************************************/

		ZipInputStream zipin = new ZipInputStream(new FileInputStream(fichierS.getPath()));
		ZipEntry entry;
		try {
		while ((entry = zipin.getNextEntry()) != null) {
			System.out.printf("File: %s Modified on %TD %n", entry.getName(), new Date(entry.getTime()));
			zipin.closeEntry();
		}
		zipin.close();
		}catch (IOException ioex) {
			
		}
		System.out.print("ok 2 eme partie");


	}
}
