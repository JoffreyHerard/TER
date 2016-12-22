import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;


@SuppressWarnings({ "serial", "unused" })
public class ButtonWorker extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private JFrame fenetre ;
	 private JButton bouton_ok ;
	 private ButtonDoW bouton_ok_channel ;
	 private JTextField pseudo ;
	 private JLabel label_pseudal ;
		
	 private JTextField pass ;
	 private JLabel label_pass ;
	 private JLabel affichage ;	
	 private String username ;
	 private String password ;
	 
	 private XmppManager xmppManager;
	 private String ProblemeCourant;
	 private boolean isRunning ;
	 private ArrayList<HostedRoom> ListeRoom;
	 private JComboBox<String> comboPrb; 
	 
	 public ButtonWorker(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.fenetre = new JFrame();
	    this.bouton_ok = new JButton("OK");
	    this.bouton_ok_channel = new ButtonDoW("OK");
	    this.pseudo = new JTextField("pseudo");
	    this.label_pseudal = new JLabel("Pseudo");	
	    this.pass = new JTextField("Password");
	    this.label_pass = new JLabel("Mot de passe");
	    this.affichage = new JLabel("Selection du salon:");	
	    this.comboPrb = new JComboBox<String>();
	    this.ListeRoom = new ArrayList<HostedRoom>();
	    this.xmppManager = new XmppManager("apocalypzer-lg-gram", 5222);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		GridLayout gl;
		//fenetre.setLayout(gl=new GridLayout(3, 2));
		fenetre.setLayout(new FlowLayout());
		fenetre.add(label_pseudal);
		fenetre.add(pseudo);
		
	    fenetre.add(label_pass);
	    fenetre.add(pass);
		fenetre.setTitle("Travail sur des taches");
		fenetre.add(bouton_ok);
		
		fenetre.setSize(400, 500);
		//gl.setColumns(2);
		//gl.setRows(3);
		
		fenetre.setLocationRelativeTo(null);
		
		fenetre.setVisible(true); 
		
		

		bouton_ok.addMouseListener(new MouseListener(){
	
		    @SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
		    	
		    	try {
		    		
					xmppManager.init();
			    	String username = "worker1"; //pseudo.getText();
			    	String password = "toto";//pass.getText();
					xmppManager.performLogin(username, password);
					xmppManager.setStatus(true, "YOLO");  
					xmppManager.setProvider(false);
					
					
					System.out.println("Etablir une liste de room");
					
					ListeRoom =(ArrayList<HostedRoom>)MultiUserChat.getHostedRooms(xmppManager.getConnection(), "conference.apocalypzer-lg-gram");
					System.out.println("Fin de etablir une liste de room");
					Iterator it = ListeRoom.iterator();
					
					while(it.hasNext())
					{
						HostedRoom tmp = (HostedRoom) it.next();
						System.out.println("ChatRoom : "+tmp.getName());
						comboPrb.addItem(tmp.getName());
					}
					
					fenetre.remove(label_pseudal);
					fenetre.remove(pseudo);
					fenetre.remove(label_pass);
					fenetre.remove(pass);
					fenetre.remove(bouton_ok);
					fenetre.setVisible(false); 
					
					//On rafraichit 
					fenetre.add(affichage);
					fenetre.add(comboPrb);
					fenetre.add(bouton_ok_channel);
					
					
					bouton_ok_channel.setProblemeCourant(comboPrb.getSelectedItem().toString());
					bouton_ok_channel.setUsername(username);
					bouton_ok_channel.setPassword(password);
					
					fenetre.setVisible(true); 
					xmppManager.destroy();					
				} catch (XMPPException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
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

	public JFrame getFenetre() {
		return fenetre;
	}

	public void setFenetre(JFrame fenetre) {
		this.fenetre = fenetre;
	}

	public JButton getBouton_ok() {
		return bouton_ok;
	}

	public void setBouton_ok(JButton bouton_ok) {
		this.bouton_ok = bouton_ok;
	}

	public ButtonDoW getBouton_ok_channel() {
		return bouton_ok_channel;
	}

	public void setBouton_ok_channel(ButtonDoW bouton_ok_channel) {
		this.bouton_ok_channel = bouton_ok_channel;
	}

	public JTextField getPseudo() {
		return pseudo;
	}

	public void setPseudo(JTextField pseudo) {
		this.pseudo = pseudo;
	}

	public JLabel getLabel_pseudal() {
		return label_pseudal;
	}

	public void setLabel_pseudal(JLabel label_pseudal) {
		this.label_pseudal = label_pseudal;
	}

	public JTextField getPass() {
		return pass;
	}

	public void setPass(JTextField pass) {
		this.pass = pass;
	}

	public JLabel getLabel_pass() {
		return label_pass;
	}

	public void setLabel_pass(JLabel label_pass) {
		this.label_pass = label_pass;
	}

	public JLabel getAffichage() {
		return affichage;
	}

	public void setAffichage(JLabel affichage) {
		this.affichage = affichage;
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

	public ArrayList<HostedRoom> getListeRoom() {
		return ListeRoom;
	}

	public void setListeRoom(ArrayList<HostedRoom> listeRoom) {
		ListeRoom = listeRoom;
	}

	public JComboBox<String> getComboPrb() {
		return comboPrb;
	}

	public void setComboPrb(JComboBox<String> comboPrb) {
		this.comboPrb = comboPrb;
	}
	
}
