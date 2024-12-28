package compression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
public class  decompressionV1{
	public File DecompressionFile(File selectedfile) throws IOException {
	try {
	System.out.print("ok");
	String name = selectedfile.getName().substring(0,selectedfile.getName().indexOf("."));
			String str = "";
			String i;
			FileReader freader = new FileReader(selectedfile);
			BufferedReader bfreader = new BufferedReader(freader);		
			while ((i = bfreader.readLine()) != null) {
				str += i;
			}
			String j;
			String extra = "";
			FileReader freaderstr = new FileReader(selectedfile);
			BufferedReader bfreaderstr = new BufferedReader(freaderstr);
			FileWriter fwriterstr = new FileWriter("/home/mohamed-lunix/Bureau/" + name);
			BufferedWriter bfwriterstr = new BufferedWriter(fwriterstr);
			while ((j = bfreaderstr.readLine()) != null) {
				extra += j;
			}
			extra = extra.substring(0, str.indexOf("/"));
			bfwriterstr.write(extra);
			System.out.print(extra);
			//bfwriterstr.write(extraction.lzw_extract(extra));
			bfreader.close();
			bfreaderstr.close();
			bfwriterstr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedfile;/*a voir*/
	}
}


