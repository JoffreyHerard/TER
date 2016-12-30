import java.io.IOException;

import org.jibble.pircbot.*;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
        // Now start our bot up.
        MyBot bot = new MyBot();
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        try {
			bot.connect("irc.freenode.net");
		} catch (IOException | IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Join the  channel.
        bot.joinChannel("#TEST_TER_GRID_JH");
       // on essayer unDCC manager 
        
        
	}

}
