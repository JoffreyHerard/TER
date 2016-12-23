import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPath;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.Occupant;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.packet.DiscoverItems;

@SuppressWarnings({ "unused", "serial" })
public class ButtonLaunch extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private JButton bouton_ok;
	 private JFrame fenetre;
	 private JComboBox<String> comboPrb; 
	 private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};
	 private File repertoire;
	 private File[] files;
	 private File fichier_Choisi;
	 private XmppManager xmppManager = new XmppManager("apocalypzer-lg-gram", 5222);
	 private String ProblemeCourant;
	 private boolean isRunning ;
	 private ArrayList<identity> Liste_user;
	 
	 public ButtonLaunch(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    fenetre = new JFrame();
	    comboPrb = new JComboBox<String>();
	    
	    bouton_ok = new JButton("OK");
	    Liste_user = new ArrayList<identity>();
	  }
	 public String FileToString(String PathFile)
		{
			String fic ="";
			//lecture du fichier texte	
			try
			{
				InputStream ips=new FileInputStream(PathFile); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null)
				{
					System.out.println(ligne);
					fic+=ligne+"\n";
				}
				br.close(); 
				ipsr.close();
				ips.close();
			}		
			catch (Exception e)
			{
				System.out.println(e.toString());
			}
			return fic;
		} 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		fenetre.setTitle("Lancement d'une tache");
		fenetre.setSize(800, 400);
		fenetre.setLocationRelativeTo(null);
		
		fenetre.setBackground(Color.white);
		fenetre.setLayout(new FlowLayout());
		repertoire = new File("DB_JOBS");
		files =repertoire.listFiles(xmlFileFilter);
		
		comboPrb.setPreferredSize(new Dimension(150, 40));
		for(int i = 0;i<files.length;i++)
		{
			int taille_nom= files[i].getName().length();
			comboPrb.addItem((files[i].getName()).substring(0, taille_nom-4));
		}
		

	    fenetre.add(comboPrb);

	    fenetre.add(bouton_ok);
		fenetre.setVisible(true); 
   	    
		bouton_ok.addMouseListener(new MouseListener(){

		    public void mouseClicked(MouseEvent e) {
		    	/*Lancement du JOB*/
		    	// on recupere ce qui a ete choisi 
		    	String choix =comboPrb.getSelectedItem().toString();
		    
		    	String username = "provider";
		    	String password = "toto";
		    	choix= choix+".xml"; 
		    	ProblemeCourant= FileToString("DB_JOBS/"+choix);
		    	//ProblemeCourant="CECI EST UN TEST";
		    	try { 
					/*On initialise la connection */
					xmppManager.init();
					xmppManager.performLogin(username, password);
					xmppManager.setStatus(true, "YOLO");  
					xmppManager.setProvider(true);
					
					/*On crer la chatroom Multiuser */
					  //Get the MultiUserChatManager
					
					  //Create a MultiUserChat using an XMPPConnection for a room
					  String Name_room = "providing_room_"+comboPrb.getSelectedItem().toString()+"@conference.apocalypzer-lg-gram";
					  MultiUserChat muc = new MultiUserChat(xmppManager.getConnection(), Name_room);
					
					  // Create the room identity
					  muc.create("BOT_Providing");
					  
					  // Send an empty room configuration form which indicates that we want
					  // an instant room
					  muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
					  /*On essaye d'utiliser le XML selectioner*/
					  
					  //On va essayer d'avoir la liste des utilisateurs selectionner et de leur envoyer chacun une tache a faire 
					  

				      System.out.println("On enregistre le nombre de personne");
				      int  Nombre_Participants=muc.getOccupantsCount();
				      System.out.println("Number of occupants et affichage de la liste:"+Nombre_Participants);
				      
				      Iterator it = muc.getOccupants();
				      
				      while(it.hasNext())
				      {
				    	  System.out.print("Membre : ");
				    	  System.out.println("Valeur "+it.next());	  
				      }
				      
				      /*On attend un nombre d'utilisateur requis par le split ici on le simule seulement avec 1 utilisateur de type worker */
				      
				      //On cherche a savoir combien d'utilisateurs par rapport au fichier xml enregistrer
				      
				      System.out.println("On attend un nombre dutilisateur precis");
				      SAXBuilder sxb = new SAXBuilder();
				      Document document = sxb.build(new File(ProblemeCourant));
				      //On initialise un nouvel élément racine avec l'élément racine du
				      // document.
				      Element racine = document.getRootElement();
 
				      XPath xpa = XPath.newInstance("/JOB/rang");
				      Element monNoeud = (Element) xpa.selectSingleNode(racine);
				      String retour = xpa.valueOf(monNoeud);
				      
				      int Nombre_requis =Integer.parseInt(retour);
				      
				      while(Nombre_Participants<Nombre_requis){
				    	  Nombre_Participants=muc.getOccupantsCount();
					      System.out.println("Number of occupants et affichage de la liste:"+Nombre_Participants);
					      
					      it = muc.getOccupants();
					      
					      while(it.hasNext())
					      {
					    	  System.out.println("Affichage tableau : ");
					    	  System.out.println("Valeur "+it.next());	  
					      }

				      }
				      
				      //Faire liste utilisateurs de la room
				      
				   
				      ArrayList<Occupant> Liste=(ArrayList<Occupant>)muc.getParticipants();
				      
				      
				      for(int i = 0;i<Liste.size();i++)
				      {
				    	  Liste_user.add(new identity(Liste.get(i).getJid(),Liste.get(i).getRole(),Liste.get(i).getNick()));
				    	  System.out.println("Nickname "+i+Liste.get(i).getNick());
				      }
				    	  			      
				      
				      //maintenant que on a la liste des utilisateur connecte a la chatRoom on va leur envoyer chacun un #Split method un job 
				      //On a attendu que lon est assez de participant ou pas en fonction du split 
				      //Voir pour filtrer son propre nom a savoir is on est tjr le premier ou pas 
				      System.out.println("Debut split ");
				      
				      split(Liste_user,Nombre_Participants,ProblemeCourant,xmppManager);
				      
				      System.out.println("Fin du split ");
					  muc.sendMessage("Lancement du probleme du"+comboPrb.getSelectedItem().toString());
					  
					  /*Maintenant que l'on a envoyer plusieurs problemes on va essayer davoir leur reponses*/
					  
					  isRunning = true;
					  while (isRunning){
						  Thread.sleep(50);
					  }
					  xmppManager.destroy();	
					  
					  
		    	}
		    	 catch (XMPPException ex) {
		    		// TODO Auto-generated catch block
		    		  ex.printStackTrace();
		    	}
		    	catch (Exception ex) {
		    		 // TODO Auto-generated catch block
		    		  ex.printStackTrace();
		    	}
		    	
		    }

		    public void mousePressed(MouseEvent e) {

		    }

		    public void mouseReleased(MouseEvent e) {

		    }

		    public void mouseEntered(MouseEvent e) {

		    }

		    public void mouseExited(MouseEvent e) {

			}
		});
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	public int split(ArrayList<identity> Liste_user,int Nombre_Participants,String ProblemeCourant,XmppManager xmppManager)
	{
		for(int i=0;i<Nombre_Participants-1;i++)
		{
			String buddyJID = Liste_user.get(i).getId();
			String buddyName = Liste_user.get(i).getName();
			      
			try {
				xmppManager.createEntry(buddyJID, buddyName);
				
				//On va crer le message XML approprier puis l'envoyer 
				Element JOB = new Element("DEBUT_JOB");
				SAXBuilder sxb = new SAXBuilder();
				Document doc = sxb.build(ProblemeCourant);
				
				//On construit le fichier XML avec le code a executer 
				
				 Element racine = doc.getRootElement();
		            
	            /* On va dans un premier temps rechercher l'ensemble des noms des patients de notre hôpital. */
	            
	            /* Recherche de la liste des patients*/
	            XPath xpa = XPath.newInstance("/JOB/exec/");   
	            
	            /* On récupère tous les noeuds répondant au chemin //patient */
	            List<String> results = (List<String>) xpa.selectNodes(racine) ;
		            
		            
				JOB.addContent(new Element("code_exec").setText(results.get(0)));
				
				// On ajouter a sa la bonne ligne de commande a executer
				xpa = XPath.newInstance("/JOB/cmd/");   
		            
		        /* On récupère tous les noeuds répondant au chemin //patient */
				results = (List<String>) xpa.selectNodes(racine) ;
				//Faut paerser la liste 
				JOB.addContent(new Element("cmd").setText("toto execute des patates"));
				JOB.addContent(new Element("id").setText(String.valueOf(i)));
				
				// new XMLOutputter().output(doc, System.out);
				XMLOutputter xmlOutput = new XMLOutputter();
				
				
				
				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput.output(doc, new FileWriter("JOB_SEND/Fichier_envoie_"+i));
				String Probleme_individuel=FileToString("JOB_SEND/Fichier_envoie_"+i);
				
				xmppManager.sendMessage(Probleme_individuel, buddyName+"@apocalypzer-lg-gram");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Ici on fait apelle a la fonction split 
			  
			  
			
		
		}
		 
		return 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public JButton getBouton_ok() {
		return bouton_ok;
	}
	public void setBouton_ok(JButton bouton_ok) {
		this.bouton_ok = bouton_ok;
	}
	public JFrame getFenetre() {
		return fenetre;
	}
	public void setFenetre(JFrame fenetre) {
		this.fenetre = fenetre;
	}
	public JComboBox<String> getComboPrb() {
		return comboPrb;
	}
	public void setComboPrb(JComboBox<String> comboPrb) {
		this.comboPrb = comboPrb;
	}
	
	public static FilenameFilter getXmlFileFilter() {
		return xmlFileFilter;
	}
	public static void setXmlFileFilter(FilenameFilter xmlFileFilter) {
		ButtonLaunch.xmlFileFilter = xmlFileFilter;
	}
	public File getRepertoire() {
		return repertoire;
	}
	public void setRepertoire(File repertoire) {
		this.repertoire = repertoire;
	}
	public File[] getFiles() {
		return files;
	}
	public void setFiles(File[] files) {
		this.files = files;
	}
	public File getFichier_Choisi() {
		return fichier_Choisi;
	}
	public void setFichier_Choisi(File fichier_Choisi) {
		this.fichier_Choisi = fichier_Choisi;
	}
	public XmppManager getXmppManager() {
		return xmppManager;
	}
	public void setXmppManager(XmppManager xmppManager) {
		this.xmppManager = xmppManager;
	}
	public String getProblemeCourant() {
		return ProblemeCourant;
	}
	public void setProblemeCourant(String problemeCourant) {
		ProblemeCourant = problemeCourant;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public ArrayList<identity> getListe_user() {
		return Liste_user;
	}
	public void setListe_user(ArrayList<identity> liste_user) {
		Liste_user = liste_user;
	}
	
}
