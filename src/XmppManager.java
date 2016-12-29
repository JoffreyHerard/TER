
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

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

@SuppressWarnings("unused")
public class XmppManager {
    
    private static final int packetReplyTimeout = 500; // millis
    
    private String server;
    private int port;
    
    private ConnectionConfiguration config;
    private XMPPConnection connection;
    static String NOM_HOTE="apocalypzer-lg-gram";
    private ChatManager chatManager;
    private MessageListener messageListener;
    private boolean provider;
    private boolean travail_terminer=false;
    private int retour_Providing=0;
    private int envoyer =0;
    private int recu =0;
    
    public XmppManager(String server, int port) {
        this.server = server;
        this.port = port;
        this.provider=true;
    }
    
    public boolean isProvider() {
		return provider;
	}

	public void setProvider(boolean provider) {
		this.provider = provider;
	}

	public void init() throws XMPPException {
        
        System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));

        SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);
        
        config = new ConnectionConfiguration(server, port);
        config.setSASLAuthenticationEnabled(false);
        config.setSecurityMode(SecurityMode.disabled);
        
        connection = new XMPPConnection(config);
        connection.connect();
        
        System.out.println("Connected: " + connection.isConnected());
        
        chatManager = connection.getChatManager();
        messageListener = new MyMessageListener();
        
    }
    
    public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}

	public boolean performLogin(String username, String password) throws XMPPException {
        if (connection!=null && connection.isConnected()) {
            connection.login(username, password);
        }
        return true;
    }

    public void setStatus(boolean available, String status) {
        
        Presence.Type type = available? Type.available: Type.unavailable;
        Presence presence = new Presence(type);
        
        presence.setStatus(status);
        connection.sendPacket(presence);
        
    }
    
    public void destroy() {
        if (connection!=null && connection.isConnected()) {
            connection.disconnect();
        }
    }
    
    public void sendMessage(String message, String buddyJID) throws XMPPException {
        System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
        Chat chat = chatManager.createChat(buddyJID, messageListener);
        chat.sendMessage(message);
    }
    
    public void createEntry(String user, String name) throws Exception {
        System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));
        Roster roster = connection.getRoster();
        roster.createEntry(user, name, null);
    }
    
    class MyMessageListener implements MessageListener {

        @Override
        public void processMessage(Chat chat, Message message) {
        	
        	if(provider){ 
	        	//Modification de reaction si provider ou non 
	            String from = message.getFrom();
	            String body = message.getBody();
	            System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
	            // on regarde l'en tete du message apparente a un message xml reponse tel que lid est 1 si reponse 2
	            //si imposible a executer dans le cas echeant un troisieme champ correspond a lid du job non reussi 
	            //et 0 si envoie travail 
	            
	            // on procede donc au build un script ici un peu fictif mais pour prolonger pour voir si ca marche bien
	            // ici on va juste sommer les results choses assez simple
	            
	            String regex="[,]";
	            String[] en_tete = body.split(regex);
	            
	            // indice 0 = en tete indice 1 = res
	            if(Integer.parseInt(en_tete[0])==1){
	            	retour_Providing+=Integer.parseInt(en_tete[1]);
	            	recu++;
	            	if(recu==envoyer)
	            	{
	            		travail_terminer=true;
	            	}
	            }	
	            else
	            {
	            	System.out.println("Lexecution n'as pas ete posible on redistribue aleatoirement la tache");
	            }
        	}
        	else
        	{
        		
        	   String from = message.getFrom();
 	           String body = message.getBody();
 	           System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
 	           System.out.println("c'est partie on va executer ce qu'il faut");
 	           // creation de l'architecture necessaire 
 	           File dir = new File ("JOB_REC/DATA_EXTRACT");
 	           dir.mkdirs();
 	           try{
 	        	   
					System.out.println("Ecriture du XML dans un fichier xml receive");
					File file = new File("JOB_REC/xml_receive.xml");
					file.createNewFile();
					
				
				    
					PrintWriter writer = new PrintWriter(file);
					writer.write(body);
					writer.close();
					
					//On a maintenant cerer le fichier on va le parser pour recuperer # fichier precis
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document xml = builder.parse(file);
			        Element root = xml.getDocumentElement();
			        XPathFactory xpf = XPathFactory.newInstance();
			        XPath path = xpf.newXPath();
			        String expression = "/JOB/exec";
			        String strexec = (String)path.evaluate(expression, root);
			        
			        
			        expression = "/JOB/contraintes";

			        String strperl = (String)path.evaluate(expression, root);
		            
		            /* On récupère tous les noeuds répondant au chemin //patient */

					
					// On ajouter a sa la bonne ligne de commande a executer
			        expression = "/JOB/cmd";
			        String strcmd = (String)path.evaluate(expression, root);    
			        //Creation du fichier de contrainte en PERL
			        
					System.out.println("Ecriture du XML dans un fichier contrainte");
					File file_con = new File("JOB_REC/DATA_EXTRACT/contraintes.pl");
					file.createNewFile();
					writer = new PrintWriter(file_con);
					writer.write(strperl);
					writer.close();
					
					//Creation du fichier de exec en PERL et son execution
					
					System.out.println("Ecriture du XML dans un fichier calcul.pl");
					File file_exec = new File("JOB_REC/DATA_EXTRACT/calcul.pl");
					file.createNewFile();
					writer = new PrintWriter(file_exec);
					writer.write(strexec);
					writer.close();
					// execution
					Runtime runtime = Runtime.getRuntime();
					System.out.println("execution du fichier de contrainte ");
					// on execute le fichier de contrainte 3 = GOOD different = NOGOOD
					
					Process p_cunt =runtime.exec("perl JOB_REC/DATA_EXTRACT/contraintes.pl");
					int resultat_con=p_cunt.waitFor();
					System.out.println("execution termine du fichier de contrainte ");
					
					System.out.println("On a recupere la valeur de sortie de contraintes");
					System.out.println("Resultat contraintes = "+resultat_con);
					
					if(resultat_con==3){
						//execution
						System.out.println("La contrainte a valider,on va commencer lexecution");
						
						Process p_cmd =runtime.exec(strcmd);
						int resultat=p_cmd.waitFor();
						System.out.println("Resultats du calcul = "+resultat);
						// on n'as plus que a renvoyer le resultats
						
						getCurrent().sendMessage("1,"+resultat, "provider@"+NOM_HOTE);
						System.out.println("message envoyer = 1,"+resultat);
					}else
					{
						//on a pas pu lexecuter on renvoi un message 
					}	
        	   } 
 	           catch(IOException ioe){
        		    System.out.println("Erreur IO");
				ioe.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	}
        }
        
    }
    
    public boolean isTravail_terminer() {
		return travail_terminer;
	}

	public void setTravail_terminer(boolean travail_terminer) {
		this.travail_terminer = travail_terminer;
	}

	public int getEnvoyer() {
		return envoyer;
	}

	public void setEnvoyer(int envoyer) {
		this.envoyer = envoyer;
	}

	public int getRecu() {
		return recu;
	}

	public void setRecu(int recu) {
		this.recu = recu;
	}

	public XmppManager getCurrent() {
		return this;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ConnectionConfiguration getConfig() {
		return config;
	}

	public void setConfig(ConnectionConfiguration config) {
		this.config = config;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public int getRetour_Providing() {
		return retour_Providing;
	}

	public void setRetour_Providing(int retour_Providing) {
		this.retour_Providing = retour_Providing;
	}

	public static int getPacketreplytimeout() {
		return packetReplyTimeout;
	}
    
}
