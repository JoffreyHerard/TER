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
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	 private XmppManager xmppManager ;
	 private String ProblemeCourant;
	 private boolean isRunning ;
	 private ArrayList<identity> Liste_user;
	 private JLabel res ;
	 
	 public ButtonLaunch(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    fenetre = new JFrame();
	    comboPrb = new JComboBox<String>();
	    
	    bouton_ok = new JButton("OK");
	    Liste_user = new ArrayList<identity>();
	    res=new JLabel("Resultat");
	    xmppManager = new XmppManager(XmppManager.NOM_HOTE, 5222);
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
		    	
		    	try { 
					/*On initialise la connection */
					xmppManager.init();
					xmppManager.performLogin(username, password);
					xmppManager.setStatus(true, "YOLO");  
					xmppManager.setProvider(true);
					
					/*On crer la chatroom Multiuser */
					  //Get the MultiUserChatManager
					
					  //Create a MultiUserChat using an XMPPConnection for a room
					  String Name_room = "providing_room_"+comboPrb.getSelectedItem().toString()+"@conference."+xmppManager.NOM_HOTE;
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
				      int Nombre_requis=3;
				      
				      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


				      try {

				         DocumentBuilder builder = factory.newDocumentBuilder();

				         File fileXML = new File("DB_JOBS/"+choix);

				         Document xml = builder.parse(fileXML);

				         Element root = xml.getDocumentElement();

				         XPathFactory xpf = XPathFactory.newInstance();

				         XPath path = xpf.newXPath();

				                   

				        String expression = "/JOB/rang";

				        String str = (String)path.evaluate(expression, root);

				        Nombre_requis=Integer.parseInt(str);

				        System.out.println("-------------------------------------");
				      } catch (ParserConfigurationException xe) {
				          xe.printStackTrace();
				       } catch (SAXException xe) {
				          xe.printStackTrace();
				       } catch (IOException xe) {
				          xe.printStackTrace();
				       } catch (XPathExpressionException xe) {
				          xe.printStackTrace();
				       }
				      System.out.println("NBR : "+Nombre_requis);
				      
				    //modifier ici attention nombre requis = 2 ,valable uniquement pour les tests 
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
				      //modifier ici attention on a pas le bon nombre de participant
				      split(Liste_user,Nombre_Participants,ProblemeCourant,xmppManager,choix);
				      
				      System.out.println("Fin du split ");
					  muc.sendMessage("Lancement du probleme du"+comboPrb.getSelectedItem().toString());
					  
					  /*Maintenant que l'on a envoyer plusieurs problemes on va essayer davoir leur reponses*/
					  
					  isRunning = true;
					  fenetre.add(res);
					  while (xmppManager.isTravail_terminer()){
						  Thread.sleep(50);
					  }
					  //on a eu le resultat
					  res.setText("Resultat : "+xmppManager.getRetour_Providing());
					  fenetre.add(res);
					  Thread.sleep(5000);
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
	public int split(ArrayList<identity> Liste_user,int Nombre_Participants,String ProblemeCourant,XmppManager xmppManager,String choix)
	{
		//modifier ici attention
		for(int i=0;i<Nombre_Participants-1;i++)
		{
			String buddyJID = Liste_user.get(i).getId();
			String buddyName = Liste_user.get(i).getName();
			      
			try {
				xmppManager.createEntry(buddyJID, buddyName);
				
				//On va crer le message XML approprier puis l'envoyer 
				
				
				
				//On construit le fichier XML avec le code a executer 
		
		            
	            /* On va dans un premier temps rechercher l'ensemble des noms des patients de notre hôpital. */
	            
	            /* Recherche de la liste des exec*/
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();

		        File fileXML = new File("DB_JOBS/"+choix);

		        Document xml = builder.parse(fileXML);

		        Element root = xml.getDocumentElement();

		        XPathFactory xpf = XPathFactory.newInstance();

		        XPath path = xpf.newXPath();

		                   

		        String expression = "/JOB/code_exec";

		        String strexec = (String)path.evaluate(expression, root);
		        System.out.print("DEBUT STR EXEC");
		        System.out.print(strexec);
		        System.out.print("FIN STR EXEC");

		        expression = "/JOB/code_Perl";

		        String strperl = (String)path.evaluate(expression, root);
	            
	            /* On récupère tous les noeuds répondant au chemin //patient */

				
				// On ajouter a sa la bonne ligne de commande a executer
		        expression = "/JOB/cmd";

		        String strcmd = (String)path.evaluate(expression, root);
		            
		        /* On récupère tous les noeuds répondant au chemin //patient */
				
				String delims = "[,]";
				String[] tokens =strcmd.split(delims);
				System.out.print("affichage des tokens");
				tokens[tokens.length-1]=tokens[tokens.length-1].substring(0, tokens[tokens.length-1].length()-1);
				for(int j=0;j<tokens.length;j++)
					System.out.println(j+" : "+tokens[j]);
				
				//Faut parser la liste 
				
				
				  
				final Document document= builder.newDocument();
					
				final Element racine = document.createElement("JOB");
				document.appendChild(racine);	
				final Element exec = document.createElement("exec");
				exec.appendChild(document.createTextNode(strexec));
				
				final Element contraintes = document.createElement("contraintes");
				contraintes.appendChild(document.createTextNode(strperl));
				
				
				final Element cmd = document.createElement("cmd");
				cmd.appendChild(document.createTextNode(tokens[i]));
				final Element id = document.createElement("id");
				id.appendChild(document.createTextNode(""+i));
				racine.appendChild(id);
				racine.appendChild(contraintes);
				racine.appendChild(exec);
				racine.appendChild(cmd);
				
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
			    final Transformer transformer = transformerFactory.newTransformer();
			    final DOMSource source = new DOMSource(document);
			    final StreamResult sortie = new StreamResult(new File("JOB_SEND/XML_send_"+i));
			    transformer.transform(source, sortie);	
				
				String Probleme_individuel=FileToString("JOB_SEND/XML_send_"+i);
				
				xmppManager.sendMessage(Probleme_individuel, buddyName+"@"+xmppManager.NOM_HOTE);
				
				
				
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
