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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import org.jivesoftware.smackx.muc.MultiUserChat;

@SuppressWarnings("unused")
public class ButtonDoW extends JButton implements MouseListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Image img;
	private JComboBox<String> comboPrb; 
	private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};
	private File repertoire;
	private File[] files;
	private JButton bouton_ok;
	private File fichier_Choisi;
	private String choix;

	private String username ;
	private String password ;
	 
	private XmppManager xmppManager;
	private String ProblemeCourant;
	private boolean isRunning ;
	
	public ButtonDoW(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.xmppManager = new XmppManager(xmppManager.NOM_HOTE, 5222);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
    	try{
          xmppManager.init();
		  xmppManager.performLogin(username, password);
		  xmppManager.setStatus(true, "YOLO");  
		  xmppManager.setProvider(false);
		  
		  // Create a MultiUserChat using an XMPPConnection for a room
		  MultiUserChat muc2 = new MultiUserChat(xmppManager.getConnection(), ProblemeCourant+"@conference."+xmppManager.NOM_HOTE);
		
		  // User2 joins the new room
		  // The room service will decide the amount of history to send
	      muc2.join(username);
	      xmppManager.createEntry("provider","BOT_Providing");
	      xmppManager.sendMessage("-1", "provider@"+xmppManager.NOM_HOTE);
	      isRunning = true;
	      
		  while (isRunning){
			  Thread.sleep(50);
			 
		  }

		  xmppManager.destroy();	
		  
		} catch (Exception exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public JComboBox<String> getComboPrb() {
		return comboPrb;
	}

	public void setComboPrb(JComboBox<String> comboPrb) {
		this.comboPrb = comboPrb;
	}

	public static FilenameFilter getXmlFileFilter() {
		return xmlFileFilter;
	}

	public static void setXmlFileFilter(FilenameFilter xmlFileFilter) {
		ButtonDoW.xmlFileFilter = xmlFileFilter;
	}

	public File getRepertoire() {
		return repertoire;
	}

	public void setRepertoire(File repertoire) {
		this.repertoire = repertoire;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public JButton getBouton_ok() {
		return bouton_ok;
	}

	public void setBouton_ok(JButton bouton_ok) {
		this.bouton_ok = bouton_ok;
	}

	public File getFichier_Choisi() {
		return fichier_Choisi;
	}

	public void setFichier_Choisi(File fichier_Choisi) {
		this.fichier_Choisi = fichier_Choisi;
	}

	public String getChoix() {
		return choix;
	}

	public void setChoix(String choix) {
		this.choix = choix;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public XmppManager getXmppManager() {
		return xmppManager;
	}

	public void setXmppManager(XmppManager xmppManager) {
		this.xmppManager = xmppManager;
	}

	public String getProblemeCourant() {
		return ProblemeCourant;
	}

	public void setProblemeCourant(String problemeCourant) {
		ProblemeCourant = problemeCourant;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
