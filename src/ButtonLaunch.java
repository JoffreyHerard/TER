import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.jivesoftware.smack.*;

public class ButtonLaunch extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 
	 public ButtonLaunch(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Lancement d'une tache");
		fenetre.setSize(250, 275);
		fenetre.setLocationRelativeTo(null);

		fenetre.setVisible(true); 
		String username = "testuser1";
		String password = "testuser1pass";
		
		XmppManager xmppManager = new XmppManager("myserver", 5222);
		
		try {
			xmppManager.init();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			xmppManager.performLogin(username, password);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xmppManager.setStatus(true, "Hello everyone");
		
		String buddyJID = "testuser2";
		String buddyName = "testuser2";
		try {
			xmppManager.createEntry(buddyJID, buddyName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			xmppManager.sendMessage("Hello mate", "testuser2@myserver");
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean isRunning = true;
		
		while (isRunning) {
		    try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		xmppManager.destroy();
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

}
