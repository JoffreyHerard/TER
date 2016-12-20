import java.awt.Color;
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

public class ButtonAjout extends JButton implements MouseListener {
	
	 private String name;
	 private Image img;
	 
	 public ButtonAjout(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetreAjout = new JFrame();
		fenetreAjout.setTitle("Ajout d'une tache dans la base");
		fenetreAjout.setSize(250, 275);
		fenetreAjout.setLocationRelativeTo(null);

		fenetreAjout.setVisible(true); 
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
