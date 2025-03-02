package Messagerie;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InterfaceClients extends JFrame {
	JFrame f = new JFrame("Utilisateur");
	JButton btEnv = new JButton("Envoyer");
	JButton btExit = new JButton("Sortie");
	JTextArea toSned = new JTextArea();
	JTextArea Sneded = new JTextArea();
	Socket s = null;
	FileInputStream input;
	DataOutputStream out;
	String line = "";
	interfaceMain im = new interfaceMain();

	DefaultListModel<String> Modeluser = new DefaultListModel<>();
	public JList<String> listUser = new JList(Modeluser);
	public JTextField userTOsend = new JTextField();
	public JLabel userConnecte = new JLabel();
	clients cl;
	File file;

	public void Fenetre(String user, boolean etat) {
		if (etat==true) {
		userConnecte.setText(im.current_user);
		// im.list_user_connecte();
		charge_list_users_connecte();
		try {
			s = new Socket("127.0.0.1", 4000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listUser.setSelectedIndex(0);
		listUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		f.setLocationRelativeTo(null);
		// Spécifier la couleur d'arrière-plan du JPanel
		f.setBackground(Color.lightGray);
		// Créer le bouton

		// Définir la position du bouton
		btEnv.setBounds(450, 215, 90, 20);
		btExit.setBounds(450, 240, 90, 20);
		// textarea
		toSned.setCaretColor(Color.BLUE);
		// Définir la position textarea
		toSned.setBounds(135, 175, 400, 30);
		Sneded.setBounds(135, 5, 400, 137);
		Sneded.setSelectedTextColor(Color.BLACK);
		Sneded.setBackground(Color.white);
		Sneded.setEnabled(false);
		userConnecte.setBounds(135, 145, 125, 25);
		userTOsend.setBounds(5, 175, 125, 30);
		userTOsend.setEnabled(false);
		/*****************************************/
		JLabel liste_users = new JLabel("Liste Utilistaeurs");
		JLabel dist_users = new JLabel("A/Envoyé");
		liste_users.setBounds(5, 5, 125, 10);
		listUser.setBounds(5, 20, 125, 123);
		dist_users.setBounds(5, 100, 125, 123);
		/*******************************************/
		f.add(toSned);
		f.add(Sneded);
		f.add(btEnv);
		f.add(btExit);
		f.add(listUser);
		f.add(liste_users);
		f.add(userTOsend);
		f.add(userConnecte);
		f.add(dist_users);
		// f.add(pass);
		f.setSize(550, 300);
		f.setLayout(null);
		f.setVisible(true);
		//// Evenements
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charge_list_users_Deconnecte();
			//	Fenetre(im.current_user,false);			
				System.exit(0);
			}
		});
		btEnv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File f;
			if (!userTOsend.getText().isEmpty()) {
				try {
					f = File.createTempFile("messages", ".tmp");
					FileWriter fw = new FileWriter("messages.tmp");
					fw.write(toSned.getText());
					fw.close();
					f.delete();
					input = new FileInputStream("messages.tmp");
					Scanner in = new Scanner(input);
					out = new DataOutputStream(s.getOutputStream());
					String dist=userTOsend.getText();
			
					while (in.hasNext()) {
						line = im.current_user + "=> " + in.nextLine()+":"+dist.substring(dist.indexOf(" "), dist.length());
						out.writeUTF(line.strip());
						Sneded.append("\n" + line);
					}

					toSned.setText(null);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}else {
				System.out.println("pas de distinataire !!");
				
			}
			}
			
		});

		listUser.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String str=listUser.getSelectedValue();
				userTOsend.setText(	str.substring(0, str.length()-7));

			}
		});
		}
		else {
			System.exit(0);
		}
	}

	public void charge_list_users_connecte() {

		Modeluser.clear();
		String chaine = "";
		Path fichier = Paths.get("UsersConnecte.txt");
		try {
			chaine = Files.readString(fichier);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String idd = ";";
		int i = 0;
		String regex = idd;
		String[] myArray = chaine.split(regex);
		for (String s : myArray) {
			String regex1 = ":";
			String[] myArray1 = s.split(regex1, 2);
			for (String s1 : myArray1) {
				if (i == 0) {
					i = 1;
					Modeluser.addElement(s1);
				} else {
					i = 0;
				}
			}
		}
	}

	public void charge_list_users_Deconnecte() {
		String nomFichier = "UsersDeconnecte.txt";
		file = new File(nomFichier);
			try {
			int count=0;
			if (file.exists()) {
				String str =im.getligneuserconnecte();			
				FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter    bufWriter = new BufferedWriter(fileWriter);
                  String NbrStr=str.substring(1, str.indexOf(":"));
                  int nbr=Integer.valueOf(NbrStr.trim());
				 str =";"+nbr+" : "+im.current_user + " OFFline";
				 bufWriter.newLine();
		         bufWriter.write(str);		           
		         bufWriter.close();	
			}
			else {
				file=createfiledeconnecte();	
				String str =im.getligneuserconnecte();			
				FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter    bufWriter = new BufferedWriter(fileWriter);
                  String NbrStr=str.substring(1, str.indexOf(":"));
                  int nbr=Integer.valueOf(NbrStr.trim());
				 str =";"+nbr+" : "+im.current_user + " OFFline";
		         bufWriter.write(str);		           
		         bufWriter.close();	
			}
			} catch (IOException ee) {
				// TODO Auto-generated catch block
			ee.printStackTrace();
			}
		
		} 
	
	public File createfiledeconnecte() {
		File f = new File("UsersDeconnecte.txt");
		try {
			if (f.createNewFile()) {
				System.out.println("File created: " + f.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return f;
	}
	

}
