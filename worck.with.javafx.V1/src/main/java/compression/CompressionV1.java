package compression;

public class CompressionV1 {
	
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.nio.file.DirectoryStream;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.nio.file.attribute.FileTime;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import java.util.zip.ZipEntry;
	import java.util.zip.ZipOutputStream;

	public File Zipper(String SelectedFile) {
	
	    private void createZip(String dirName) {
	      
	     
	    	File fichier=new File(SelectedFile);
	        // open the zip stream in a try resource block, no finally needed
	        try( ZipOutputStream zipStream = new ZipOutputStream(
	                        new FileOutputStream(fichier)) ) {

	            // traverse every file in the selected directory and add them
	            // to the zip file by calling addToZipFile(..)
	            DirectoryStream<Path> dirStream = Files.newDirectoryStream(fichier.);
	            dirStream.forEach(path -> addToZipFile(path, zipStream));

	            LOG.info("Zip file created in " + directory.toFile().getPath());
	        }
	        catch(IOException|ZipParsingException e) {
	            LOG.log(Level.SEVERE, "Error while zipping.", e);
	        }
	    }
	    private void addToZipFile(Path file, ZipOutputStream zipStream) {
	        String inputFileName = file.toFile().getPath();
	        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {

	            // create a new ZipEntry, which is basically another file
	            // within the archive. We omit the path from the filename
	            ZipEntry entry = new ZipEntry(file.toFile().getName());
	            entry.setCreationTime(FileTime.fromMillis(file.toFile().lastModified()));
	            entry.setComment("Created by TheCodersCorner");
	            zipStream.putNextEntry(entry);

	            LOG.info("Generated new entry for: " + inputFileName);

	            byte[] readBuffer = new byte[2048];
	            int amountRead;
	            int written = 0;

	            while ((amountRead = inputStream.read(readBuffer)) > 0) {
	                zipStream.write(readBuffer, 0, amountRead);
	                written += amountRead;
	            }

	            LOG.info("Stored " + written + " bytes to " + inputFileName);


	        }
	        catch(IOException e) {
	            throw new ZipParsingException("Unable to process " + inputFileName, e);
	        }
	    }

	    /**
	     * Instantiate a new ZipWriter and provide the directory we want to compress.
	     * @param args command line args not used
	     */
	    public static void main(String[] args) {
	        ZipWriter zw = new ZipWriter();
	        zw.createZip(ZIP_DIR);
	    }

	    /**
	     * We want to let a checked exception escape from a lambda that does not
	     * allow exceptions. The only way I can see of doing this is to wrap the
	     * exception in a RuntimeException. This is a somewhat unfortunate side
	     * effect of lambda's being based off of interfaces.
	     */
	    private class ZipParsingException extends RuntimeException {
	        public ZipParsingException(String reason, Exception inner) {
	            super(reason, inner);
	        }
	    }
	}
}
