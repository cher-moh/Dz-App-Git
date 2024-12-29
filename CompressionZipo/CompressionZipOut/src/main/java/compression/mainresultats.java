package compression;

import java.util.ArrayList;
import java.util.List;

public class mainresultats {

	public static void main(String[] args) {
		List<String> selectedF=new ArrayList<String>();
		selectedF.add("/home/mohamed-lunix/Images/1.png");
		selectedF.add("/home/mohamed-lunix/Images/2.png");
		
		voir_resultas_write vr 	= new voir_resultas_write(selectedF);
		vr.start();

	}

}
