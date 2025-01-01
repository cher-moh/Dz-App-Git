package compression;

import java.util.ArrayList;
import java.util.List;

public class mainresultats {

	public static void main(String[] args) {
		List<String> selectedF=new ArrayList<String>();
		selectedF.add("/home/mohamed-lunix/Images/1.png");
		selectedF.add("/home/mohamed-lunix/Images/2.png");
		
		ThreadsCompression vr 	= new ThreadsCompression(selectedF);
		vr.start();

	}

}
