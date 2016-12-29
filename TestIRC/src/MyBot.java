import java.util.ArrayList;

import org.jibble.pircbot.*;

public class MyBot extends PircBot {
    ArrayList<String> model ;
    
    public MyBot() {
        this.setName("MyBot");
        model = new ArrayList<String>();
        
    }
    
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if (message.equalsIgnoreCase("time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is now " + time);
        }
        if (message.equalsIgnoreCase("Tu t'appelles comment ?")) {
            
            sendMessage(channel, sender + ": Joffrey HERARD");
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
    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message)
    {
    	System.out.println("Appelle de la methode onPrivateMessage");
    	System.out.println("On affiche ");
    	System.out.println("Sender : "+sender+ " login of the sender : "+login+" Hostname of sender : "+hostname+" and message : "+message);
    }
    @Override
    protected void onJoin(String channel, String sender, String login, String hostname)
    {
    	System.out.println("Appelle de la methode On Join");
    	 User[] user=this.getUsers("#TEST_TER_GRID_JH");
         System.out.println("On affiche ");
         for(int i =0; i<user.length;i++){
             System.out.println(user[i]);
             sendMessage(channel,"Utilisateur "+i+" : "+user[i]);
         }    
         System.out.println("On affiche plus ");
         
    }
    
}