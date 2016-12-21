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
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jivesoftware.smack.*;

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
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

@SuppressWarnings({ "unused", "serial" })
public class ButtonLaunch extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private JButton bouton_ok;
	 private JFrame fenetre;
	 private JComboBox<String> comboPrb; 
	 private JComboBox<String> combo ;
	 private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};
	 private File repertoire;
	 private File[] files;
	 private File fichier_Choisi;
	 private XmppManager xmppManager;
	 
	 public ButtonLaunch(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    fenetre = new JFrame();
	    comboPrb = new JComboBox<String>();
	    combo = new JComboBox<String>();
	    bouton_ok = new JButton("OK");
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
   	    
		bouton_ok.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	/*Lancement du JOB*/
		    	// on recupere ce qui a ete choisi 
		    	String choix =comboPrb.getSelectedItem().toString();
		    	int taille = combo.getSelectedIndex();
		    	String username = "provider";
		    	String password = "toto";
		    	choix= choix+".xml"; 
		    	
		    	try { 
		    		/*On initialise la connection */
		    		xmppManager.init();
		    		xmppManager.performLogin(username, password);
		    		xmppManager.setStatus(true, "");  
		    		XmppManager xmppManager = new XmppManager("apocalypzer-lg-gram", 5222);
		    		
		    		/*On crer la chatroom Multiuser */
		    		
		    		/*On essaye d'utiliser le XML selectioner*/
		    	}
		    	catch (XMPPException ex) {
		    		  // TODO Auto-generated catch block
		    		  ex.printStackTrace();
		    	}
		    	catch (Exception ex) {
		    		  // TODO Auto-generated catch block
		    		  ex.printStackTrace();
		    	}
		    	
		    }

		    public void mousePressed(MouseEvent e) {

		    }

		    public void mouseReleased(MouseEvent e) {

		    }

		    public void mouseEntered(MouseEvent e) {

		    }

		    public void mouseExited(MouseEvent e) {

			}
		});
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
