package Messagerie;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class InterfaceAdmin extends JFrame {
	Socket s = null;
	ServerSocket ss = null;
	int nbr;
	int Port = 4000;
	JFrame f = new JFrame("Administrateur");
	JButton Lancer = new JButton("Lancer Server");
	JButton btdesconect = new JButton("Déconecté Server");
	JButton btExit = new JButton("Sortie");
	public JTextArea Sneded = new JTextArea();
	InterfaceClients ic = new InterfaceClients();
	interfaceMain im = new interfaceMain();
	List<String> listusers = new ArrayList();
	File file;
	File file1;
	final JFrame parent = new JFrame();
	JLabel userAdmin=new JLabel();
	public void Fenetre() {

		f.setLocationRelativeTo(null);
		// Spécifier la couleur d'arrière-plan du JPanel
		f.setBackground(Color.lightGray);

		// Créer le bouton

		// Définir la position du bouton
		userAdmin.setBounds(105, 170, 150, 50);
		Lancer.setBounds(5, 170, 150, 50);
		btdesconect.setBounds(390, 170, 150, 50);
		btExit.setBounds(450, 240, 90, 20);
		// textarea

		// Définir la position textarea

		Sneded.setBounds(25, 5, 500, 137);
		/*********************************************/
		f.add(userAdmin);
		f.add(Sneded);
		f.add(btdesconect);
		f.add(btExit);
		f.add(Lancer);
		/************************/
		// f.add(pass);
		f.setSize(550, 300);
		f.setLayout(null);
		f.setVisible(true);
		//// Evenements
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				String nomFichier = "src/main/resources/UsersDeconnecte.txt";
				String nomFichier1 = "src/main/resources/UsersConnecte.txt";
				file = new File(nomFichier);
				file1=new File(nomFichier1);
				file.deleteOnExit();
				file1.deleteOnExit();
				String Exit = JOptionPane.showInputDialog(parent, "Merci de Confirmer votre Sortie(oui/non) ...!!", null);				
				if (Exit.equals("oui")) {
					ic.charge_list_users_Deconnecte();
				System.exit(0);
				}
				
			}
		});
		Lancer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Sneded.setText("Server started");
				Sneded.setText("Waiting for a client ...");
				Thread lanceServer = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							ss = new ServerSocket(Port);
							while (true) {
								s = ss.accept();
								nbr++;
								CommunicationServerClient ccs = new CommunicationServerClient(s.getInputStream());
								ccs.start();
								Sneded.setText("Server is started.." + "\n" + "client N° == " + nbr
										+ "            Connecté ..");
								System.out.println("Server is started.." + "\n" + "client N° == " + nbr
										+ "            Connecté ..");
								Sneded.setText("\n"+"\n"+ccs.getline());

							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				lanceServer.start();

			}
		});

		btdesconect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ss.close();
					Sneded.setText("Server est Déconnecté ..");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}

}
