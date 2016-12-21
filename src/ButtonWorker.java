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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings({ "serial", "unused" })
public class ButtonWorker extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 private JFrame fenetre ;
	 private ButtonOKdo bouton_ok ;
	 private JTextField pseudo ;
	 private JLabel label_pseudal ;
		
	 private JTextField pass ;
	 private JLabel label_pass ;
	 private JLabel affichage ;	
	 private String username ;
	 private String password ;
	 
	 public ButtonWorker(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.fenetre = new JFrame();
	    this.bouton_ok = new ButtonOKdo("OK");
	    this.pseudo = new JTextField("pseudo");
	    this.label_pseudal = new JLabel("Pseudo");	
	    this.pass = new JTextField("Password");
	    this.label_pass = new JLabel("Mot de passe");
	    this.affichage = new JLabel("Affichage du chat :");	
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
		fenetre.add(affichage);
		fenetre.setSize(400, 500);
		//gl.setColumns(2);
		//gl.setRows(3);
		
		fenetre.setLocationRelativeTo(null);

		fenetre.setVisible(true); 
		
		
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
