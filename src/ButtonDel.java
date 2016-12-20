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

@SuppressWarnings("unused")
public class ButtonDel extends JButton implements MouseListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	 private Image img;
	 
	 public ButtonDel(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Suppression d'une tache dans la base");
		fenetre.setSize(250, 275);
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
