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
 //  private static String SOURCE_FILE = "/home/mo_na_ro_ra/Bureau/text_compfression/abc.jpg";
   private static String TARGET_FILE = "/home/mo_na_ro_ra/Bureau/mo.zip";
public void CompressedFile(String selectedF) throws IOException
{
   /*****************************************************************************/
      FileOutputStream fout = new FileOutputStream(TARGET_FILE);
      CheckedOutputStream checksum = new CheckedOutputStream(fout, new Adler32());
      ZipOutputStream zout = new ZipOutputStream(checksum);
      FileInputStream fin = new FileInputStream(selectedF);
      ZipEntry zipEntry = new ZipEntry(selectedF);
      zout.putNextEntry(zipEntry);
      int length;
      byte[] buffer = new byte[1024];
      while((length = fin.read(buffer)) > 0) {
         zout.write(buffer, 0, length);
      }
      zout.closeEntry();
      zout.finish();
      fin.close();
      zout.close();
   
/*****************************************************************************************/
  
      ZipInputStream zin = new ZipInputStream(new FileInputStream(TARGET_FILE)); 
      ZipEntry entry;
      while((entry = zin.getNextEntry())!=null){
         System.out.printf("File: %s Modified on %TD %n", 
         entry.getName(), new Date(entry.getTime()));
     
       
         zin.closeEntry();
      }
      zin.close();
  /****************************************************************************************/ 
   
      FileOutputStream fos = null; 
      try { 
         fos = new FileOutputStream(entry.getName()); 
         while(zin.available() != 0){
            fos.write(zin.read()); 
         }
      } catch (IOException ioex) { 
         fos.close(); 
      }
 /***********************************************************************************/
	
   

}
}
