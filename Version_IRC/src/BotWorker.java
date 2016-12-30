import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jibble.pircbot.*;
import org.jivesoftware.smack.XMPPException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class BotWorker extends PircBot {
    private ArrayList<String> model ;
    private boolean Provider;
    protected boolean travail_terminer=false;
    private int retour_Providing=0;
    private int envoyer =0;
    private int recu =0;
    private boolean[] WorkerIncapacite;
    private String ID;
    public BotWorker() {
    	int nbr=(int) (Math.random()*666);
        this.setName("Bot_Worker"+nbr);
        model = new ArrayList<String>();
        
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        
         	
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
    public void onIncomingFileTransfer(DccFileTransfer transfer) {
        // Use the suggested file name ici cest le fichir xml de job .
        File fileXML = transfer.getFile();
        // Receive the transfer and save it to the file, allowing resuming.
        transfer.receive(fileXML, true);
        
        System.out.println(String.format("Received message '%1$s' from %2$s", transfer.getNick(),"Fichier XML"));
        System.out.println("c'est partie on va executer ce qu'il faut");
         // creation de l'architecture necessaire 
         File dir = new File ("JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName());
         dir.mkdirs();
         try{
        	 	int rand =(int) (Math.random()*100000);
				System.out.println("Ecriture du XML dans un fichier xml receive");
				
			
				File file = new File("JOB_REC/xml_receive_"+ManagementFactory.getRuntimeMXBean().getName()+"_"+rand+".xml");

				boolean resultat = fileXML.renameTo(file);
				
				
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
		        String strcmd = (String)path.evaluate(expression, root);    
		        //Creation du fichier de contrainte en PERL
		        
				System.out.println("Ecriture du XML dans un fichier contrainte");
				File file_con = new File("JOB_REC/DATA_EXTRACT_"+ManagementFactory.getRuntimeMXBean().getName()+"/contraintes.pl");
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file_con);
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

				
					
					Process p_cmd =runtime.exec(strcmd);
					int resultat_c=p_cmd.waitFor();
					System.out.println("Retour  du calcul = "+resultat_c);
					// on n'as plus que a lire le resultats dans un ficheir resultat.txt tout le fichier ne doit contenir que la valeur souhaites ici des entiers
					String resultatF= ButtonLaunch.FileToString("resultat.txt");
					
					this.sendMessage("BOT_providing","1,"+resultatF);
					System.out.println("message envoyer = 1,"+resultatF);
				}else
				{
					//Si on peut pas lexecuter on le renvoie aux provider
					//sendMessage("0,NO,"+ID+","+ButtonLaunch.FileToString("JOB_REC/xml_receive_"+ManagementFactory.getRuntimeMXBean().getName()+"_"+rand+".xml"), "provider@"+NOM_HOTE);
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
		}
    }
    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message)
    {
    	
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