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
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.jivesoftware.smack.*;

@SuppressWarnings({ "unused", "serial" })
public class ButtonLaunch extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private ButtonOKLa bouton_ok;
	 private JFrame fenetre;
	 private JComboBox<String> comboPrb; 
	 private JComboBox<String> combo ;
	 private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};
	 private File repertoire;
	 private File[] files;
	 private File fichier_Choisi;
	 public ButtonLaunch(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    fenetre = new JFrame();
	    comboPrb = new JComboBox<String>();
	    combo = new JComboBox<String>();
	    bouton_ok = new ButtonOKLa("OK");
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
		
	    combo.setPreferredSize(new Dimension(150, 40));
	    combo.addItem("0");
	    combo.addItem("1");
	    combo.addItem("2");
	    combo.addItem("3");
	    combo.addItem("4");
	    combo.addItem("6");
	    combo.addItem("7");
	    combo.addItem("8");
	    combo.addItem("9");
	    combo.addItem("10");
	    fenetre.add(comboPrb);
	    fenetre.add(combo);
	    fenetre.add(bouton_ok);
		fenetre.setVisible(true); 
   	    
	    /*
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
		
		xmppManager.destroy();*/
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
