package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import application.actions_fenetre;


	public class voir_resultas  extends Thread {
	private static String fichier;
	private static String chemin;
		 public voir_resultas(String selectedF,String directory) {
			 this.chemin=directory;
			 this.fichier=selectedF;
		 }
	
		  public void run(){							
					File fichierS=new File(fichier);
					String name=fichierS.getName().substring(0, fichierS.getName().indexOf("."));
					
					/*********************************get the file and create new zip***********************/
					try {
					
					FileOutputStream fileout = new FileOutputStream(chemin+"/"+name+".mzip");/*************create new file zip with the name comp.zip**********************************/
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
		  public static void main(String[] args) throws IOException {
			 voir_resultas th1 = new voir_resultas("/home/mo_na_ro_ra/Bureau/text_compfression/file.mp4", "/home/mo_na_ro_ra/Bureau");
			 voir_resultas th2 = new voir_resultas("/home/mo_na_ro_ra/Bureau/text_compfression/abc.jpg", "/home/mo_na_ro_ra/Bureau");
		    th1.start();
		    th2.start();	
		    while(th1.isAlive()) {
		        System.out.println("compression mp4 file...");
		      }
		   while(th2.isAlive()) {
		        System.out.println("compression image file...");
		      }
		  
	
		  }}


