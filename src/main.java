import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.LoggerContext;

@SuppressWarnings("unused")
public class main {

	@SuppressWarnings("static-access")
	public static void main(String[] args){
		
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		
		JFrame fenetre = new JFrame();
		ImageIcon icone = new ImageIcon("src/images/urca.jpg");
		JLabel image = new JLabel(icone);
		
	    fenetre.setTitle("Calcul Grid XMPP");
	    fenetre.setSize(250, 310);
	    fenetre.setResizable(false);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   
	   
	    
	    fenetre.setContentPane(new Panneau()); 
	    JOptionPane jop = new JOptionPane();            


	    JOptionPane jop1 = new JOptionPane(), jop2 = new JOptionPane();

	    
	    

		String nom_hote = jop1.showInputDialog(null, "Nom du serveur ?", "Information Serveur", JOptionPane.QUESTION_MESSAGE);
		String addr_hote = jop1.showInputDialog(null, "Adresse du serveur?", "Information Serveur", JOptionPane.QUESTION_MESSAGE);
	    
	    jop2.showMessageDialog(null, "Nom du serveur XMPP: " + nom_hote+" Adresse serveur XMPP: "+addr_hote, "Serveur XMPP", JOptionPane.INFORMATION_MESSAGE);
	    
	    int estHOST = jop.showConfirmDialog(null, "Etes vous l'host du serveur XMPP?", "Host XMPP", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

	    if(estHOST != JOptionPane.NO_OPTION && 

	    		estHOST != JOptionPane.CANCEL_OPTION && 

	    				estHOST != JOptionPane.CLOSED_OPTION){

	         switch(estHOST)
	         {
		         case JOptionPane.OK_OPTION:
		        	 XmppManager.setADRESSE_HOTE(nom_hote);
		        	 XmppManager.setNOM_HOTE(nom_hote);
		        	 break;
	
		         case JOptionPane.NO_OPTION:
		        	 XmppManager.setADRESSE_HOTE(addr_hote);
		        	 XmppManager.setNOM_HOTE(nom_hote);
		        	 break;
	
		         default:
		        	 
		        	 break;
	         }

	    }
	    fenetre.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/urca.jpg")); 
	    
	    fenetre.setLayout(new FlowLayout());
	    fenetre.getContentPane().add(image,BorderLayout.WEST);
	    fenetre.getContentPane().add(new ButtonLaunch("Lancer une tache"),BorderLayout.EAST);
	    fenetre.getContentPane().add(new ButtonWorker("Client d'une tache"),BorderLayout.EAST);
	    fenetre.getContentPane().add(new ButtonAjout("Ajouter une tache a la base"),BorderLayout.EAST);
	    fenetre.getContentPane().add(new ButtonDel("Retirer une tache de la base"),BorderLayout.EAST);
	    fenetre.getContentPane().add(new ButtonContact("Contact"),BorderLayout.EAST);
	    
	    fenetre.setVisible(true);
	}
	
}
