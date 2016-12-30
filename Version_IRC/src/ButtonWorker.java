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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
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
		
	
	 private JLabel affichage ;	
	 private String username ;
	 private ArrayList<String> Probleme;
	 private String ProblemeCourant;
	 private boolean isRunning ;
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
	    this.affichage = new JLabel("Selection du salon:");	
	    this.comboPrb = new JComboBox<String>();
	    this.Probleme= new  ArrayList<String>();
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		GridLayout gl;
		//fenetre.setLayout(gl=new GridLayout(3, 2));
		fenetre.setLayout(new FlowLayout());
		fenetre.add(label_pseudal);
		fenetre.add(pseudo);
		
	   
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
		    		
					
			    	String username = pseudo.getText();

					System.out.println("Etablir une liste de room");
					
					 // Now start our bot up.
			        BotGetRoom botg = new BotGetRoom();
			        
			        // Enable debugging output.
			        botg.setVerbose(true);
			        
			        // Connect to the IRC server.
			        
					botg.connect("irc.freenode.net");
					
			        // Join the  channel.
			        botg.joinChannel("#TEST_TER_GRID_JH");
			        // on ask le provider
			        
			        botg.sendMessage("#TEST_TER_GRID_JH", "problem?");
			        
			        File resume = new File("resume.txt");
			        while(!resume.exists())
			        {	
			        	System.out.println("On attend le fichier qui resume tout les probleme disponible");
			        	Thread.sleep(500);
			        } 
			        Scanner scanner = new Scanner(new File("resume.txt"));
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						comboPrb.addItem(line);
					}
					System.out.println("Fin de etablir une liste de room");

					
					fenetre.remove(label_pseudal);
					fenetre.remove(pseudo);
					
					fenetre.remove(bouton_ok);
					fenetre.setVisible(false); 
					
					//On rafraichit 
					fenetre.add(affichage);
					fenetre.add(comboPrb);
					fenetre.add(bouton_ok_channel);
					
					
					bouton_ok_channel.setProblemeCourant(comboPrb.getSelectedItem().toString());
					bouton_ok_channel.setUsername(username);
					
					
					fenetre.setVisible(true); 
							
				} catch (NickAlreadyInUseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IrcException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

	

	public JComboBox<String> getComboPrb() {
		return comboPrb;
	}

	public void setComboPrb(JComboBox<String> comboPrb) {
		this.comboPrb = comboPrb;
	}
	
}
