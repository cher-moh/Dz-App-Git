package compression;

import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class coder {
	
	public static byte[] createChecksum(String filename) throws Exception {
		InputStream fis = new FileInputStream(filename);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}
	public static String getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";

		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	public static double taille_file(String fichier, String mesure) {

		File file = new File(fichier);
		String[] mes_rest = { "bit", "oct", "ko", "mo", "go", "to" };
		double resultats = 0;
		if (file.exists()) {
			double bytes = file.length();
			double bits = bytes * 8;
			double kilobytes = bytes / 1000;
			double megabytes = kilobytes / 1000;
			double gigabytes = megabytes / 1000;
			double terabytes = gigabytes / 1000;
			switch (mesure) {
			case "bit":
				resultats = bytes;
				break;
			case "oct":
				resultats = bits;
				break;
			case "ko":
				resultats = kilobytes;
				break;
			case "mo":
				resultats = megabytes;
				break;
			case "go":
				resultats = gigabytes;
				break;
			case "to":
				resultats = terabytes;
				break;
			}

			/*
			 * System.out.println("bits : " + bits+" bits"); System.out.println("bytes : " +
			 * bytes+ " octets"); System.out.println("kilooctet : " + kilobytes+ "Ko");
			 * System.out.println("megaoctet : " + megabytes + "Mo");
			 * System.out.println("gigaoctet : " + gigabytes + "Go");
			 * System.out.println("teraoctet : " + terabytes + "To");
			 * System.out.println("resultats : " + resultats + "mo"); }else{
			 * System.out.println("Fichier innexistant");
			 */
		}
		return resultats;

	}

	public double taille_file_com(String compfile, String directory) {
		double taille_compression = 0;

		File fichiersS = new File(compfile);
		if (fichiersS.exists()) {
			String name = fichiersS.getName().substring(0, fichiersS.getName().indexOf("."));
			String fileandchemin = directory + "/" + name + ".mzip";
			double taill = 0;
			taill = coder.taille_file(fileandchemin, "mo");
			taille_compression = taill;
		} else {
			System.out.println("Fichier innexistant");
		}
		return taille_compression;

	}

	public double tailleavantcomp(String selectedFile) {
		double reultats = 0;
		try {
			ZipFile zf = new ZipFile(selectedFile);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				String name = ze.getName();
				Date lastModified = new Date(ze.getTime());
				long uncompressedSize = ze.getSize();
				long compressedSize = ze.getCompressedSize();
				double tailleAV = (double) uncompressedSize;
				reultats = (tailleAV / 1000) / 1000;
				//System.out.println(name);
				//System.out.println(lastModified);
				//System.out.println(uncompressedSize);
				//System.out.println(compressedSize);

			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
		return reultats;
	}
	public static List <Object>  contenuzip(String selectedFile) {
		List<Object> contenu = new ArrayList <Object>();
		try {
			ZipFile zf = new ZipFile(selectedFile);
			Enumeration e = zf.entries();
			while (e.hasMoreElements()) {
				ZipEntry ze = (ZipEntry) e.nextElement();
				String name = ze.getName();
				Date lastModified = new Date(ze.getTime());
				long uncompressedSize = ze.getSize();
				long compressedSize = ze.getCompressedSize();
				contenu.add(name);
				contenu.add(lastModified);
				contenu.add((double)compressedSize);
				contenu.add((double)uncompressedSize);

			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
		return contenu;
		
		
	}
	public int tauxComp(String Tcom, String selectedfile, String Directory) {
		int r_taux=0;
			
		switch (Tcom) {
		case "Comp":{
			double taille_file_av_com = taille_file(selectedfile, "mo");
			double taille_file_ap_com = taille_file_com(selectedfile, Directory);
			int taux = (int) ((1 - (taille_file_ap_com / taille_file_av_com)) * 100);
			r_taux=taux;
			break;
			}
		case "Ext":{
			try {
			String nomzip=(String) contenuzip(selectedfile).get(0);			
			String chemin_ext=Directory+"/"+nomzip;
			double taille_file_ap_extraction = tailleavantcomp(selectedfile);
			double taille_file_av_com = taille_file(chemin_ext, "mo");
			int taux = (int) (( ( taille_file_av_com / taille_file_ap_extraction)) * 100);
			r_taux=taux;
			break;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		}
		return r_taux;
	}

}
