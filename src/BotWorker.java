import java.io.File;
import java.util.ArrayList;

import org.jibble.pircbot.*;

public class BotWorker extends PircBot {
    private ArrayList<String> model ;
    private boolean Provider;
    protected boolean travail_terminer=false;
    private int retour_Providing=0;
    private int envoyer =0;
    private int recu =0;
    private boolean[] WorkerIncapacite;
    
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
        // Use the suggested file name.
        File file = transfer.getFile();
        // Receive the transfer and save it to the file, allowing resuming.
        transfer.receive(file, true);
        
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