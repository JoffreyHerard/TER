import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


public class main {

	public static void main(String[] args) {
		JFrame fenetre = new JFrame();
		ImageIcon icone = new ImageIcon("/home/apocalypzer/workspace/TER/src/images/urca.jpg");
		JLabel image = new JLabel(icone);
		
	    fenetre.setTitle("Calcul Grid XMPP");
	    fenetre.setSize(250, 310);
	    fenetre.setResizable(false);
	    fenetre.setLocationRelativeTo(null);
	    fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    fenetre.setVisible(true);
	   
	    
	    
	    fenetre.setContentPane(new Panneau()); 
	    
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
