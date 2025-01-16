package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import application.Controller_Main;
import javafx.scene.control.ProgressBar;

public class ThreadProgressBar extends Controller_Main implements Runnable{
	ProgressBar pr;
	File Folders=null;
	boolean endd=false;
	double tailles=0;
	public ThreadProgressBar(ProgressBar p,double t) {
		this.pr = p;
		this.tailles=t;	
	
	}
	@Override
	public void run() {	
	
		System.out.println("pb="+tailles);
		double taille_f= tailles;
		double div=(double)1/(double)taille_f;
		double i = pr.getProgress();
		DecimalFormat dtaille = new DecimalFormat("##.##");
		i = Double.valueOf(dtaille.format(i));
			while (i <= 1 ) {
				pr.setProgress(i);
				i = i + div;
			}	
		}

	 public  double folderSize(File folder){
		    double size = 0;
		   
		    File[] fileList = folder.listFiles();	
		    for(File file : fileList){
		        if(!file.isFile()){ 
		            folderSize(file);
		        }
		        size += file.length();
		    }
		   // System.out.println("taile=="+size);
		    return size;
		}
	 
	
}
