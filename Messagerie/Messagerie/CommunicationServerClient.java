package Messagerie;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.control.TextArea;
public class CommunicationServerClient    extends Thread  {///réponce with server.
	
	static Socket s;
	static int nbr ;
    private InputStream in = null;
    BufferedReader bf;
    InputStreamReader isr;
    PrintWriter pw;
    String end=".";
    DataInputStream input;
    DataInputStream input1;
    private String line="";
	interfaceMain im = new interfaceMain();
	InterfaceClients ic = new InterfaceClients(); 
	public CommunicationServerClient(InputStream inn) {
		this.in=inn;
	}
	
	@Override
	public void run() {
			messages();
	}
	public void messages () {
		try {
			input=new DataInputStream(new BufferedInputStream(in));		
			while (!line.equals(end)){
			line=input.readUTF();
			ic.Sneded.setText("From Server :"+line);
			System.out.println(line);	
			}
			} catch (IOException e) {		
				e.printStackTrace();
			}
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

public InputStream getIn() {
	return in;
}
public void setIn(InputStream in) {
	
	this.in = in;
}
public String getline() {
	return line;
}
public void setline(String line) {
	
	this.line = line;
}


}
