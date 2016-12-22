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

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;


@SuppressWarnings({ "serial", "unused" })
public class ButtonWorker extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private JFrame fenetre ;
	 private JButton bouton_ok ;
	 private JButton bouton_ok_channel ;
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
	    this.bouton_ok_channel = new JButton("OK");
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
					
					
					fenetre.setVisible(true); 
					
					
					bouton_ok_channel.addMouseListener(new MouseListener(){
						
					    @SuppressWarnings("deprecation")
						public void mouseClicked(MouseEvent e) {
					    	
					    	try{
					    		
							  // Create a MultiUserChat using an XMPPConnection for a room
							  MultiUserChat muc2 = new MultiUserChat(xmppManager.getConnection(), comboPrb.getSelectedItem().toString()+"@conference.apocalypzer-lg-gram");
							
							  // User2 joins the new room
							  // The room service will decide the amount of history to send
				    	      muc2.join(username);
				    	      
				    	      isRunning = true;
							  while (isRunning){
								  Thread.sleep(50);
								  System.out.println("Attente de message ");
							  }
							  xmppManager.destroy();	
							  
							} catch (Exception exc) {
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

}
