
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
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
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
    static String ADRESSE_HOTE;
    static String NOM_HOTE;
    private ChatManager chatManager;
    private MessageListener messageListener;
    private boolean provider;
    protected boolean travail_terminer=false;
    private int retour_Providing=0;
    private int envoyer =0;
    private int recu =0;
    private boolean[] WorkerIncapacite;
    private ConnectionListener ConnectionListener;
    protected static String name_provider ;
    protected  static boolean job_enCours ;
    protected static int  rand ;
    protected  static String ID;
    protected  static String strcmd;
    protected  static Process p_cmd;
    protected static Thread t;
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
	public boolean TousIncapacite(boolean[] WorkerIncapacite)
	{
		boolean res= false;
		int i =0;
		
		while(i<WorkerIncapacite.length && WorkerIncapacite[i]==false)
		{
			i++;
		}
		
		if(i==WorkerIncapacite.length && WorkerIncapacite[i]==false )
		{
			res=true;
		}
		return res;
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
        ConnectionListener = new MyConnectionListener();
        connection.addConnectionListener(ConnectionListener);
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
       // System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));
        Chat chat = chatManager.createChat(buddyJID, messageListener);
        chat.sendMessage(message);
    }
    
    public void createEntry(String user, String name) throws Exception {
        System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));
        Roster roster = connection.getRoster();
        roster.createEntry(user, name, null);
    }
    
    public boolean appartient(boolean[] WorkerIncapaciteB,int id_choisi)
    {
    	return WorkerIncapacite[id_choisi];
    }
    
    class MyConnectionListener implements ConnectionListener {

		@Override
		public void connectionClosed() {
			// TODO Auto-generated method stub
			System.out.println("Quelqun c'est deconnecter faut savoir qui !!!");
		}

		@Override
		public void connectionClosedOnError(Exception arg0) {
			// TODO Auto-generated method stub
			System.out.println("Quelqun c'est deconnecter avec une erreur faut savoir qui !!!");
		}

		@Override
		public void reconnectingIn(int arg0) {
			// TODO Auto-generated method stub
			System.out.println("Reconnection !!!");
		}

		@Override
		public void reconnectionFailed(Exception arg0) {
			// TODO Auto-generated method stub
			System.out.println("Reconnection echouer !!!");
			
		}

		@Override
		public void reconnectionSuccessful() {
			// TODO Auto-generated method stub
			System.out.println("Reconnection reussite !!!");
			
		}
    }
    class MyMessageListener implements MessageListener {

        @Override
        public void processMessage(Chat chat, Message message) {
        	 ID ="";
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

	            if(Integer.parseInt(en_tete[0])!=-1){
	            // indice 0 = en tete indice 1 = res
	            	System.out.println("On passe Provider Integer.parseInt(en_tete[0])!=-1 vrai ");
		            if(Integer.parseInt(en_tete[0])==1){
		            	System.out.println("chaine recu :en_tete[1] "+en_tete[1]);
		            	System.out.println("en_tete[1].length() "+en_tete[1].length());
		            	//on enleve le retour chariot
		            	en_tete[1].replaceAll("\n"," ");
		            	System.out.println("chaine recu :en_tete[1] 2 "+en_tete[1]);
		            	int retour=Integer.parseInt(en_tete[1].substring(0,en_tete[1].length()));
		            	retour_Providing= retour_Providing + retour;
		            	System.out.println("retour_Providing ="+retour_Providing);
		            	recu++;
		            	System.out.println("recu = "+recu);
		            	System.out.println("envoyer = "+envoyer);
		            	/*try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
		            	if(recu==envoyer)
		            	{
		            		travail_terminer=true;
		            	}
		            }	
		            else
		            {
		            	System.out.println("On passe Provider Integer.parseInt(en_tete[0])==1 faux ");
		            	// On choisi le worker qui va s'occuper de sa 
		            	WorkerIncapacite[Integer.parseInt(en_tete[2])]=true;
		            	String buddyName ="";
		            	String buddyID="";
		            	int id_choisi =(int) (Math.random()*ButtonLaunch.Liste_user.size());
		            	
		            	while(appartient(WorkerIncapacite,id_choisi))
		            	{
		            		id_choisi =(int) (Math.random()*ButtonLaunch.Liste_user.size());
		            	}
		            	if(TousIncapacite(WorkerIncapacite))
		            	{
		            		travail_terminer=true;
		            		retour_Providing= -1;
		            		MessageBox.show("Erreur", "Tous les Workers on ete incapacite");
		            	}
		            	else{
			            	//On recupere ces informations
			            	buddyID =ButtonLaunch.Liste_user.get(id_choisi).getId();
			            	buddyName=ButtonLaunch.Liste_user.get(id_choisi).getName();
			            	
			            	
			            	System.out.println("L'execution n'as pas ete possible on redistribue aleatoirement la tache");
			            	
			            	//On doit parser le xml recu qui est en en_tete[3] et le renvoyer a l'id choisi  
			            	String Probleme_individuel="";
			            	
			            	
			            	try {
				            	//On va recuperer les information interessantes dans le fichier xml recu 	
				            	System.out.println("Ecriture du XML dans un fichier xml receive_FAILURE");
								File fileXML = new File("JOB_REC/xml_receive_failure_pour.xml"+id_choisi);
								fileXML.createNewFile();
								PrintWriter writer = new PrintWriter(fileXML);
								writer.write(body);
								writer.close();
							
				            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								DocumentBuilder builder = factory.newDocumentBuilder();
								
								Document xml = builder.parse(fileXML);
						        Element root = xml.getDocumentElement();
						        
						        
						        XPathFactory xpf = XPathFactory.newInstance();
						        XPath path = xpf.newXPath();
						        String expression_C = "/JOB/contraintes";
						        String expression_E = "/JOB/exec";
						        String expression_CMD = "/JOB/cmd";
								
						        String strexec = (String)path.evaluate(expression_E, root);
						        String strcmd = (String)path.evaluate(expression_CMD, root);    
						        String strperl = (String)path.evaluate(expression_C, root);    
						        
						        
						        
						        
								//On crer le nouveau XML a expedie 
								final Document document= builder.newDocument();
			
								final Element racine = document.createElement("JOB");
								document.appendChild(racine);	
								final Element exec = document.createElement("exec");
								exec.appendChild(document.createTextNode(strexec));
								
								final Element contraintes = document.createElement("contraintes");
								contraintes.appendChild(document.createTextNode(strperl));
								
								
								final Element cmd = document.createElement("cmd");
								cmd.appendChild(document.createTextNode(strcmd));
								final Element id = document.createElement("id");
								id.appendChild(document.createTextNode(""+id_choisi));
								racine.appendChild(id);
								racine.appendChild(contraintes);
								racine.appendChild(exec);
								racine.appendChild(cmd);
								
							
								
								final TransformerFactory transformerFactory = TransformerFactory.newInstance();
							    final Transformer transformer = transformerFactory.newTransformer();
							    final DOMSource source = new DOMSource(document);
							    final StreamResult sortie = new StreamResult(new File("JOB_SEND/xml_send_Cfailure_pour.xml"+id_choisi));
		
							    transformer.transform(source, sortie);	
							    sendMessage(ButtonLaunch.FileToString("JOB_SEND/xml_send_Cfailure_pour.xml"+id_choisi), buddyName+"@"+NOM_HOTE);
								
			            	} catch (XMPPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (XPathExpressionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParserConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SAXException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}	
		            }
	            }
        	}
        	else
        	{
        		
        	   String from = message.getFrom();
        	   name_provider=from;
 	           String body = message.getBody();
 	           System.out.println(String.format("Received message '%1$s' from %2$s", body, from));
 	           System.out.println("c'est partie on va executer ce qu'il faut");
 	           // creation de l'architecture necessaire 
 	           File dir = new File ("JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName());
 	           dir.mkdirs();
 	           try{
 	        	    job_enCours=true;
 	        	    rand =(int) (Math.random()*100000);
					System.out.println("Ecriture du XML dans un fichier xml receive");
					File file = new File("JOB_REC/xml_receive_"+ManagementFactory.getRuntimeMXBean().getName()+"_"+rand+".xml");
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
			        
			        String expressionID = "/JOB/id";
			        ID = (String)path.evaluate(expressionID, root);
			        expression = "/JOB/contraintes";

			        String strperl = (String)path.evaluate(expression, root);
		            
		            /* On récupère tous les noeuds répondant au chemin //patient */

					
					// On ajouter a sa la bonne ligne de commande a executer
			        expression = "/JOB/cmd";
			         strcmd = (String)path.evaluate(expression, root);    
			        //Creation du fichier de contrainte en PERL
			        
					System.out.println("Ecriture du XML dans un fichier contrainte");
					File file_con = new File("JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName()+"/contraintes.pl");
					file.createNewFile();
					writer = new PrintWriter(file_con);
					writer.write(strperl);
					writer.close();
					
					//Creation du fichier de exec en PERL et son execution
					
					expression = "/JOB/nom_fic";

					String nom_fic_exec = (String)path.evaluate(expression, root); 
					
					System.out.println("Ecriture du XML dans un fichier de calcul");
					File file_exec = new File("JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName()+"/"+nom_fic_exec);
					file_exec.createNewFile();
					writer = new PrintWriter(file_exec);
					writer.write(strexec);
					writer.close();
					// execution
					Runtime runtime = Runtime.getRuntime();
					System.out.println("execution du fichier de contrainte ");
					// on execute le fichier de contrainte 3 = GOOD / different = NOGOOD
					
					Process p_cunt =runtime.exec("perl JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName()+"/contraintes.pl");
					int resultat_con=p_cunt.waitFor();
					System.out.println("execution termine du fichier de contraintes ");
					
					System.out.println("On a recupere la valeur de sortie de contraintes");
					System.out.println("Resultat contraintes = "+resultat_con);
					
					if(resultat_con==3){
						//execution
						System.out.println("La contrainte a valider,on va commencer l'execution strcmd = "+strcmd);
						// On traville la chaine strcmd pour comprendre ce qu'il faut
						
						String delims = "[/]";
						String[] tokens =strcmd.split(delims);
						System.out.print("affichage des tokens");
						for(int j=0;j<tokens.length;j++)
							System.out.println(j+" :"+tokens[j]);
						
						strcmd =tokens[0]+"JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName()+"/"+tokens[1];
						System.out.println("MAJ DE strcmd = "+strcmd);
									
						p_cmd = runtime.exec(strcmd);
									
									
						int resultat=p_cmd.waitFor();
						System.out.println("Retour  du calcul = "+resultat);
						// on n'as plus que a lire le resultats dans un ficheir resultat.txt tout le fichier ne doit contenir que la valeur souhaites ici des entiers
						String resultatF= ButtonLaunch.FileToString2("resultat.txt");
						getCurrent().sendMessage("1,"+resultatF, "provider@"+NOM_HOTE);
						System.out.println("message envoyer = 1,"+resultatF);
						job_enCours=false;
						
						 
					}else
					{
						//Si on peut pas lexecuter on le renvoie aux provider
						sendMessage("0,NO,"+ID+","+ButtonLaunch.FileToString("JOB_REC/xml_receive_"+ManagementFactory.getRuntimeMXBean().getName()+"_"+rand+".xml"), "provider@"+NOM_HOTE);
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

	public static String getNOM_HOTE() {
		return NOM_HOTE;
	}

	public static void setNOM_HOTE(String nOM_HOTE) {
		NOM_HOTE = nOM_HOTE;
	}

	public boolean[] getWorkerIncapacite() {
		return WorkerIncapacite;
	}

	public void setWorkerIncapacite(boolean[] workerIncapacite) {
		WorkerIncapacite = workerIncapacite;
	}

	public static String getADRESSE_HOTE() {
		return ADRESSE_HOTE;
	}

	public static void setADRESSE_HOTE(String aDRESSE_HOTE) {
		ADRESSE_HOTE = aDRESSE_HOTE;
	}
    
}
