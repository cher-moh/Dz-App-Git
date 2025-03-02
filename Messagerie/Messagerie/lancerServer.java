package Messagerie;


import java.net.*;


import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


import java.io.*;

public class lancerServer extends Thread  {
	    AnchorPane IdAnchorPaneAdmin;
	    private InputStream in = null;
	    private Socket s = null;
	    private ServerSocket ss = null;	   
	    int p;
	    int nbr;
	public  String value;
	  
	    public lancerServer(int port) {
	      this.p=port;
	    
	    }
	      
	    public void lanceServerSS() throws Throwable {
	    	   ss = new ServerSocket(p);	
	    	
		  }
		  public void closeServerSS() throws Throwable {
			  s.close(); ss.close();
			 
		  }
		@Override
		public void run() {	
			        try
			        {
			        	try {
							lanceServerSS();
						} catch (Throwable e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}            
			            
			            while (true) {
			            s = ss.accept();
			            nbr++;		                  			                
			            CommunicationServerClient    ccs= new CommunicationServerClient(s.getInputStream());
			            ccs.start();  		       
			            System.out.println("Server is started.."+"\n"+"client N° == "+nbr+"            Connecté .."); 
			            }      
			        }
			        catch(IOException i)
			        {
			            System.out.println(i);
			        }
			      }	
		public int nbr() {
			return nbr;
		}

		public InputStream getIn() {
			return in;
		}
	
		public void setIn(InputStream in) {
			this.in = in;
		}
		public void msgServer(TextArea tt) {
			tt.setText("Server started"+'\n'+"Waiting for a client ...");
			if (nbr>0) {
				tt.setText("Server is started.."+"\n"+"client N° == "+nbr+"            Connecté .."); 
			}
		}

	}

	
	
	


