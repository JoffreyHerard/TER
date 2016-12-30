import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jibble.pircbot.*;
import org.jivesoftware.smack.XMPPException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class BotProvider extends PircBot {
    private ArrayList<String> model ;
    private boolean Provider;
    protected boolean travail_terminer=false;
    private int retour_Providing=0;
    private int envoyer =0;
    private int recu =0;
    private boolean[] WorkerIncapacite;
	private File repertoire;
	private File[] files;
	private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};

	public BotProvider() {
        this.setName("BOT_Providing");
        model = new ArrayList<String>();
        
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
    	// Donner la liste des salles possible a un utilisateur 
        if (message.equalsIgnoreCase("problem?")) {
            
            repertoire = new File("DB_JOBS");
    		files =repertoire.listFiles(xmlFileFilter);
    		
            File fichier_resume = new File("resume.txt");
            try {
				fichier_resume.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            PrintWriter writer = null;
			try {
				writer = new PrintWriter(fichier_resume);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            for(int i = 0;i<files.length;i++)
    		{
            	int taille_nom= files[i].getName().length();
    			
				writer.write("#providing_room_"+((files[i].getName()).substring(0, taille_nom-4))+"\n");
				
				
    			
    		}
            writer.close();
            this.dccSendFile(fichier_resume, sender, 500);
			
        }
        	
    }
    /*@Override
    protected void onUserList(String channel, User[] users)
    {
    	System.out.println("Appelle de la methode OnUserList");
        for (User user1 : users) {
            System.out.println(user1);
            model.add(user1.getNick());
        }
        super.onUserList(channel, users);

    }*/
    public boolean appartient(boolean[] WorkerIncapaciteB,int id_choisi)
    {
    	return WorkerIncapacite[id_choisi];
    }
    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message)
    {
    	
    	System.out.println("Appelle de la methode onPrivateMessage");
    	
    	
        String regex="[,]";
        String[] en_tete = message.split(regex);

        if(Integer.parseInt(en_tete[0])!=-1){
        // indice 0 = en tete indice 1 = res
        	System.out.println("On passe Provider Integer.parseInt(en_tete[0])!=-1 vrai ");
            if(Integer.parseInt(en_tete[0])==1){
            	System.out.println("On passe Provider Integer.parseInt(en_tete[0])==1 vrai ");
            	
            	int retour=Integer.parseInt(en_tete[1].substring(0,1));
            	retour_Providing= retour_Providing + retour;
            	System.out.println("On passe retour_Providing+=Integer.parseInt(en_tete[1]);");
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
            	
            	int id_choisi =(int) (Math.random()*ButtonLaunch.Liste_user.size());
            	while(appartient(WorkerIncapacite,id_choisi))
            	{
            		id_choisi =(int) (Math.random()*ButtonLaunch.Liste_user.size());
            	}
            	//On recupere ces informations
            	
            	buddyName=ButtonLaunch.Liste_user.get(id_choisi).getName();
            	
            	
            	System.out.println("Lexecution n'as pas ete possible on redistribue aleatoirement la tache");
            	
            	//On doit parser le xml recu qui est en en_tete[3] et le renvoyer a l'id choisi  
            	String Probleme_individuel="";
            	
            	
            	try {
	            	//On va recuperer les information interessantes dans le fichier xml recu 	
	            	System.out.println("Ecriture du XML dans un fichier xml receive_FAILURE");
					File fileXML = new File("JOB_REC/xml_receive_failure_pour.xml"+id_choisi);
					fileXML.createNewFile();
					PrintWriter writer = new PrintWriter(fileXML);
					writer.write(message);
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
					this.dccSendFile(new File("JOB_SEND/xml_send_Cfailure_pour.xml"+id_choisi), buddyName, 500);
            	
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
    
    @Override
    protected void onJoin(String channel, String sender, String login, String hostname)
    {
    	
         
    }

	public ArrayList<String> getModel() {
		return model;
	}

	public void setModel(ArrayList<String> model) {
		this.model = model;
	}

	public boolean isProvider() {
		return Provider;
	}

	public void setProvider(boolean provider) {
		Provider = provider;
	}

	public boolean isTravail_terminer() {
		return travail_terminer;
	}

	public void setTravail_terminer(boolean travail_terminer) {
		this.travail_terminer = travail_terminer;
	}

	public int getRetour_Providing() {
		return retour_Providing;
	}

	public void setRetour_Providing(int retour_Providing) {
		this.retour_Providing = retour_Providing;
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

	public boolean[] getWorkerIncapacite() {
		return WorkerIncapacite;
	}

	public void setWorkerIncapacite(boolean[] workerIncapacite) {
		WorkerIncapacite = workerIncapacite;
	}
    
}