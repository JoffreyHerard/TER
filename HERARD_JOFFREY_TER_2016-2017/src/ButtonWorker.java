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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

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
		
	 private JPasswordField pass ;
	 private JLabel label_pass ;
	 private JLabel affichage ;	
	 private String username ;
	 private String password ;
	 
	 private XmppManager xmppManager;
	 private String ProblemeCourant;
	 private boolean isRunning ;
	 private ArrayList<HostedRoom> ListeRoom;
	 private JComboBox<String> comboPrb; 
	 
	 
	 @SuppressWarnings("static-access")
	public ButtonWorker(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.fenetre = new JFrame();
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	    this.bouton_ok = new JButton("OK");
	    this.bouton_ok_channel = new ButtonDoW("OK");
	    this.pseudo = new JTextField("pseudo");
	    this.label_pseudal = new JLabel("Pseudo");	
	    this.pass = new JPasswordField("Password");
	    this.label_pass = new JLabel("Mot de passe");
	    this.affichage = new JLabel("Selection du salon:");	
	    this.comboPrb = new JComboBox<String>();
	    this.ListeRoom = new ArrayList<HostedRoom>();
	    this.xmppManager = new XmppManager(xmppManager.ADRESSE_HOTE, 5222);
	    fenetre.addWindowListener(new WindowAdapter()//du coup non et je vais miam x) cest le menu en haut pour lancer si tu veux et le carre rou
	    	    {
	    			// l;e nom du serv c apocalypzer-lg-gram tu es bien lhost et lip c 192.168.1.23 
	    			@Override
	    			public void windowClosing(WindowEvent arg0) {
	    				// TODO Auto-generated method stub
	    				// on doit signaler que l'on s'en va si un job est en cours 
	    				System.out.println("On va nettoyer tout ca");
	    				try{
	    					if(xmppManager.job_enCours)
	    					{
	    						
	    						// On signale au provider de rediriger le job
	    						//Si on peut pas lexecuter on le renvoie aux provider
	    						try {
	    							
	    							bouton_ok_channel.getXmppManager().sendMessage("0,NO,"+xmppManager.ID+","+ButtonLaunch.FileToString("JOB_REC/xml_receive_"+ManagementFactory.getRuntimeMXBean().getName()+"_"+xmppManager.rand+".xml"), "provider@"+xmppManager.NOM_HOTE);
	    							
	    						} catch (XMPPException e) {
	    							// TODO Auto-generated catch block
	    							e.printStackTrace();
	    						}
	    						//on supprime la connexion 
	    						bouton_ok_channel.getXmppManager().destroy();
	    						
	    					}
	    					else
	    					{
	    						// il ne travaille pas alors on s'en fiche ou il a deja finit 
	    						   
	    						
	    						bouton_ok_channel.getXmppManager().destroy();
	    					    
	    					}
	    					fenetre.dispose();
	    				}catch(NullPointerException e){
	    					fenetre.dispose();
	    				}//faudra que tu gere cette erreur ^^ 
	    				
	    			}
	    	    	
	    	    });
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
		
		
		File d = new File ("JOB_SEND/");
    	d.mkdir();
    	d = new File ("JOB_REC/");
    	d.mkdir();

		bouton_ok.addMouseListener(new MouseListener(){
	
		    @SuppressWarnings("static-access")
			public void mouseClicked(MouseEvent e) {
		    	
		    	try {
		    		
					xmppManager.init();
			    	String username = pseudo.getText();
			    	String password = new String(pass.getPassword());
					xmppManager.performLogin(username, password);
					xmppManager.setStatus(true, "YOLO");  
					xmppManager.setProvider(false);
					
					
					System.out.println("Etablir une liste de room");
					
					ListeRoom =(ArrayList<HostedRoom>)MultiUserChat.getHostedRooms(xmppManager.getConnection(), "conference."+xmppManager.NOM_HOTE);
					System.out.println("Fin de etablir une liste de room");
					@SuppressWarnings("rawtypes")
					Iterator it = ListeRoom.iterator();
					
					while(it.hasNext())
					{
						HostedRoom tmp = (HostedRoom) it.next();
						System.out.println("ChatRoom : "+tmp.getName());
						comboPrb.addItem(tmp.getName());
					}
					xmppManager.destroy();
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

	public void setPass(JPasswordField pass) {
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
