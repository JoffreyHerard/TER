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

@SuppressWarnings("unused")
public class ButtonContact extends JButton implements MouseListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Image img;
	private JLabel label_creator ;
	private JLabel label_director ;
	private JLabel label_version ;
	private JLabel label_date ;
	private JLabel label_mail ; 
	
	 public ButtonContact(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    label_creator = new JLabel("Createur : Joffrey Herard");
		label_director = new JLabel("Responsable : Olivier Flauzac");
		label_version = new JLabel("Version : 0.1.0");
		label_date = new JLabel("2016");
		label_mail = new JLabel("mail : joffrey.herard[at]etudiant.univ-reims.fr");
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		JLabel label_creator = new JLabel("Createur : Joffrey Herard");
		JLabel label_director = new JLabel("Reponsable : Olivier Flauzac");
		JLabel label_version = new JLabel("Version : 0.1.0");
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

	public JLabel getLabel_creator() {
		return label_creator;
	}

	public void setLabel_creator(JLabel label_creator) {
		this.label_creator = label_creator;
	}

	public JLabel getLabel_director() {
		return label_director;
	}

	public void setLabel_director(JLabel label_director) {
		this.label_director = label_director;
	}

	public JLabel getLabel_version() {
		return label_version;
	}

	public void setLabel_version(JLabel label_version) {
		this.label_version = label_version;
	}

	public JLabel getLabel_date() {
		return label_date;
	}

	public void setLabel_date(JLabel label_date) {
		this.label_date = label_date;
	}

	public JLabel getLabel_mail() {
		return label_mail;
	}

	public void setLabel_mail(JLabel label_mail) {
		this.label_mail = label_mail;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
