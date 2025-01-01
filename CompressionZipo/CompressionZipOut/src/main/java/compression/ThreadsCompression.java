package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import application.actions_fenetre;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class ThreadsCompression extends Thread {
	static List<String> selectedF=new ArrayList<String>();
	static String ch;
	 boolean etatG;
	 double dureecomp;
	 double dcomp;
	public ThreadsCompression(List<String>  fichiers,String chemin) {
		this.selectedF=fichiers;
		this.ch=chemin;
	}
	public void run(boolean etat) throws IOException {
	/*
		FXMLLoader loader =new FXMLLoader((getClass().getResource("/application/fenetre.fxml")));
		StackPane sp=loader.load();
		actions_fenetre af = loader.getController();
		
		double icomp=1/selectedF.size();	
		af.idprogress1.setProgress(icomp);
	
		*/
		long startTime = System.currentTimeMillis() / 1000;
		if (etat=true) {
		for (int i=0;i<selectedF.size();i++) {
		try {
			
			
			File fichierS=new File(selectedF.get(i));		
			String name=fichierS.getName().substring(0, fichierS.getName().indexOf("."));
			String Dist ="/home/mohamed-lunix/Bureau";/*******get destination when the file zip will be created******/
			FileOutputStream fileout = new FileOutputStream(ch+"/"+name+".mzip");/*************create new file zip with the name comp.zip**********************************/
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
	//	icomp+=icomp;
	//	af.idprogress1.setProgress(icomp);
		}
		etat=false;Thread.currentThread().interrupt();
		long endTime = System.currentTimeMillis() / 1000;
		long time_comp = endTime - startTime;
		dureecomp=(double)time_comp;
		
		}else
		{
			Thread.currentThread().interrupt(); System.out.println("etat else thread="+etat);
		}	
		etatG=etat;

	}
/*	public State temined() {
		State st=Thread.currentThread().getState();
		if (etat=true) {
			st=Thread.State.RUNNABLE;
		}
		else {
			st=Thread.State.TERMINATED;
			Thread.currentThread().interrupt();
		}
		return st;
	}*/
	public boolean etat() {
		return etatG;
	}
	public double duree() {
		return dureecomp;
	}
	public void Progress(ProgressBar p) {
		double in =p.getProgress();
		System.out.println("value progressbar="+p.getProgress());
			in=in+(1/selectedF.size());
			p.setProgress(in);
		
		
	}
	

}