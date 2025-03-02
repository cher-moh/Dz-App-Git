package Messagerie;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class interfaceMain extends JFrame {
	JFrame f = new JFrame("Messagerie");
	JButton btConnecte = new JButton("Connecté");
	JButton btClose = new JButton("Sortie");
	public JTextField users = new JTextField();
	public JPasswordField pwds = new JPasswordField();
	JPanel pan = new JPanel();
	JButton b = new JButton("Connecté");
	JButton btu = new JButton("+ Utilistaeur");
	DefaultListModel<String> Modeluser = new DefaultListModel<>();
	public JList<String> listUser = new JList(Modeluser);
	final JFrame parent = new JFrame();
	JButton loadusers = new JButton("Charger Utls");
	JLabel Etat_creation = new JLabel("");
	List<String> liste_user = new ArrayList<>();
	List<String> liste_pwds = new ArrayList<>();
	List<String> liste_connect = new ArrayList<>();
	Boolean a = true;
	File file;
	static String current_user="";
	static String lignecurrent_user="";
	public void Fenetre() {
	charge_list();
		file=createfile();
		btu.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String name = JOptionPane.showInputDialog(parent, "What is your Identifiant?", null);
				String pw = JOptionPane.showInputDialog(parent, "What is your PasseWord?", null);
				String nomFichier = "USERS.txt";
				File file;
				file = new File(nomFichier);
				if ((file.exists()) & (!name.isEmpty()) & (!pw.isEmpty())) {
					Path fichier = Paths.get("USERS.text");
					try {
						String str = "; id:" + name + " pw:" + pw;
						String chaine = "";
						FileWriter fileWriter = new FileWriter("USERS.text", true);
						BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			            //Insérer un saut de ligne
			            bufWriter.newLine();
			            bufWriter.write(str);
			            bufWriter.close();

						//chaine = Files.readString(fichier);
						//Files.writeString(fichier, str, StandardOpenOption.APPEND);
						Etat_creation.setText("l'utilsateur : " + name + "  est Ajouté avec Succée.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						File f = createfile();
						String str = "; id:" + name + " pw:" + pw;
						FileWriter fileWriter = new FileWriter("USERS.text", true);
						BufferedWriter bufWriter = new BufferedWriter(fileWriter);
			            //Insérer un saut de ligne
			            bufWriter.newLine();
			            bufWriter.write(str);
			            bufWriter.close();
						Etat_creation.setText("l'utilsateur : " + name + "  est Ajouté avec Succée.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		loadusers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Modeluser.clear();
				String chaine = "";
				Path fichier = Paths.get("USERS.text");
				try {
					chaine = Files.readString(fichier);
					// System.out.println(chaine);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String idd = "; id:";
				String pww = "pw:";
				String getpw = "";
				String getuser = "";

				int i = 0;

				String regex = idd;
				String[] myArray = chaine.split(regex);
				for (String s : myArray) {
					// System.out.println("s="+s);
					String regex1 = pww;
					String[] myArray1 = s.split(regex1, 2);
					for (String s1 : myArray1) {
						if (i == 0) {
							// System.out.println("pw=="+ s1);
							i = 1;
							liste_pwds.add(s1);
						} else {
							Modeluser.addElement(s1);
							i = 0;

						}

					}
				}

			}
		});

		listUser.setSelectedIndex(0);
		listUser.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		/*
		 * Modeluser.addElement("user1"); Modeluser.addElement("user2");
		 * Modeluser.addElement("user3");
		 */
		f.setLocationRelativeTo(null);
		JPanel sp = new JPanel();
		sp.setBounds(150, 40, 375, 150);

		// Spécifier la couleur d'arrière-plan du JPanel
		sp.setBackground(Color.lightGray);

		// Créer le bouton

		// textarea
		JLabel info = new JLabel("Veuillez Introduire les Coordonnées de Connexion .");
		info.setBounds(5, 0, 400, 20);
		JLabel id = new JLabel("Identifiant");
		JLabel pass = new JLabel("Mots de Passe");
		JLabel liste_users = new JLabel("Liste Utilistaeurs");
		pwds.setBounds(200, 60, 150, 20);
		pass.setBounds(230, 50, 150, 10);
		id.setBounds(80, 50, 150, 10);
		users.setBounds(40, 60, 150, 20);
		/////////////// chexbox
		JRadioButton check1 = new JRadioButton("Administrateur");
		JRadioButton check2 = new JRadioButton("Utilisateur");
		ButtonGroup group = new ButtonGroup();
		group.add(check1);
		group.add(check2);
		check2.setSelected(true);
		check1.setBounds(40, 90, 150, 20);
		check2.setBounds(40, 110, 150, 20);
		// Définir la position du bouton
		btConnecte.setBounds(260, 120, 90, 20);
		btClose.setBounds(450, 240, 90, 20);
		listUser.setBounds(5, 40, 125, 150);
		liste_users.setBounds(5, 1, 125, 50);
		btu.setBounds(5, 200, 125, 25);
		loadusers.setBounds(5, 225, 125, 25);
		Etat_creation.setBounds(150, 185, 400, 50);
		// Ajouter le bouton au frame
		sp.add(pass);
		sp.add(id);
		sp.add(btConnecte);
		f.add(btClose);
		sp.add(pwds);
		sp.add(users);
		sp.add(info);
		sp.add(check1);
		sp.add(check2);
		f.add(sp);
		f.add(listUser);
		f.add(loadusers);
		f.add(liste_users);
		f.add(btu);
		f.add(Etat_creation);
		sp.setLayout(null);
		// f.add(pass);
		f.setSize(550, 300);
		f.setLayout(null);
		f.setVisible(true);
		//// Evenements
		btClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btConnecte.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				current_user=users.getText();
				if (!users.getText().isEmpty()) {
					if (check1.isSelected()) {
						InterfaceAdmin ina = new InterfaceAdmin();
						ina.Fenetre();
						f.setVisible(false);
						list_user_connecte() ;
					} else {
						InterfaceClients inc = new InterfaceClients();
						inc.Fenetre(users.getText(),true);
						f.setVisible(false);list_user_connecte() ;	
						
					}} else {
					info.setText("Attention Tu doit saisir votre Identifiant et le mots de passe !");

				}
			}
		});
		listUser.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				users.setText(listUser.getSelectedValue());
				pwds.setText("123");
				a = false;
			}
		});
	}

	public File createfile() {
		File f = new File("USERS.text");
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

	public File createfile1() {
		File f = new File("UsersConnecte.txt");
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

	public void charge_list() {
		Modeluser.clear();
		String chaine = "";
		Path fichier = Paths.get("USERS.text");
		try {
			chaine = Files.readString(fichier);
			// System.out.println(chaine);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String idd = "; id:";
		String pww = "pw:";
		int i = 0;
		String regex = idd;
		String[] myArray = chaine.split(regex);
		for (String s : myArray) {
			// System.out.println("s="+s);
			String regex1 = pww;
			String[] myArray1 = s.split(regex1, 2);
			for (String s1 : myArray1) {
				if (i == 0) {
					// System.out.println("pw=="+ s1);
					i = 1;
					liste_pwds.add(s1);
				} else {
					Modeluser.addElement(s1);
					i = 0;

				}

			}
		}
		
	}
	public void list_user_connecte() {
		String nomFichier = "UsersConnecte.txt";
		file = new File(nomFichier);
		int count_user=0;
		if ((file.exists())) {
			try {
				boolean existe_deja=false;
				 String str =";"+count_user+" : "+users.getText() + " Online";
				 String recup="";
				FileWriter fileWriter = new FileWriter(file, true);
				FileReader fr=new FileReader(file);
				BufferedWriter    bufWriter = new BufferedWriter(fileWriter);
				BufferedReader befreader=new BufferedReader(fr);
				while (befreader.readLine()!=null) {				
					count_user=count_user+1;
				}
				if (existe_deja==false) {			
				 count_user++;
				 str =";"+count_user+" : "+users.getText() + " Online";
				 bufWriter.newLine();setligneuserconnecte(str);
		         bufWriter.write(str);		           
		         bufWriter.close();
				}
		
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		} else {
			try {
				file = createfile1();
				FileWriter fileWriter = new FileWriter("UsersConnecte.txt", true);
				BufferedWriter    bufWriter = new BufferedWriter(fileWriter);


			//	count_user++;
				String str =";"+1+" : "+users.getText() + " Online";setligneuserconnecte(str);
				bufWriter.write(str);
		        bufWriter.close();
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
		}	
	}
	
	public void setligneuserconnecte(String s) {
		lignecurrent_user=s;
	}
	public String getligneuserconnecte() {
		return lignecurrent_user;
	}
	

}
