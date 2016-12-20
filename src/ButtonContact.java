import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;

public class ButtonContact extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 
	 public ButtonContact(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		JLabel label_creator = new JLabel("Createur : Joffrey Herard");
		JLabel label_director = new JLabel("Reponsable : Olivier Flauzac");
		JLabel label_version = new JLabel("Vesion : 0.1.0");
		JLabel label_date = new JLabel("2016");
		JLabel label_mail = new JLabel("mail : joffrey.herard[at]etudiant.univ-reims.fr");
		
		JFrame fenetreContact = new JFrame();
		fenetreContact.setTitle("Contact");
		fenetreContact.setSize(400, 80);
		fenetreContact.setLocationRelativeTo(null);
		fenetreContact.setVisible(true); 
		
		fenetreContact.setLayout(new FlowLayout());
		fenetreContact.getContentPane().add(label_creator);
		fenetreContact.getContentPane().add(label_director);
		fenetreContact.getContentPane().add(label_version);
		fenetreContact.getContentPane().add(label_date);
		fenetreContact.getContentPane().add(label_mail);

		
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
