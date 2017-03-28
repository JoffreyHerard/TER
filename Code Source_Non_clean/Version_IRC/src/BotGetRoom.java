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

public class BotGetRoom extends PircBot {
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

	public BotGetRoom() {
        this.setName("BOT_getter");
        model = new ArrayList<String>();
        
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
    	
        	
    }
    public void onIncomingFileTransfer(DccFileTransfer transfer) {
        // Use the suggested file name.
        File file = transfer.getFile();
        // Receive the transfer and save it to the file, allowing resuming.
        transfer.receive(file, true);
        
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
    	// ici on va se servir de cela pour recevoir les resultats
    	
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
