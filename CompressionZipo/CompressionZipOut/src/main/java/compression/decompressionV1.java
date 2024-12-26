package compression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javafx.event.ActionEvent;

public class decompressionV1 {
	public void DecompressionFile(String selectedfile,String directory) throws IOException {
		File fichierS = new File(selectedfile);
		try {			
			ZipInputStream zipin = new ZipInputStream(new FileInputStream(fichierS.getAbsolutePath()));
			ZipEntry ze = new ZipEntry(zipin.getNextEntry());
			String namezip = ze.getName();
			String chemainextract=fichierS.getParent();
			FileOutputStream fos = new FileOutputStream(directory+"/"+namezip);
			int length;
			byte[] buffer = new byte[1024];
			while ((length = zipin.read(buffer)) > 0) {	
				fos.write(buffer, 0, length);
			}
			zipin.close();
			zipin.closeEntry();
			fos.close();
		} catch (IOException ioex) {

		}
	}
}
