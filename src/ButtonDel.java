import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException; 
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("unused")
public class ButtonDel extends JButton implements MouseListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Image img;
	private JComboBox<String> comboPrb; 
	private static FilenameFilter xmlFileFilter = new FilenameFilter() {public boolean accept(File dir, String name) {return name.endsWith(".xml");}};
	private File repertoire;
	private File[] files;
	private JButton bouton_ok;
	private File fichier_Choisi;
	private String choix;
	public ButtonDel(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    comboPrb= new JComboBox<String>();
	    bouton_ok= new JButton("OK");
	  }
	  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Suppression d'une tache dans la base");
		fenetre.setSize(250, 275);
		fenetre.setLocationRelativeTo(null);
		fenetre.setLayout(new FlowLayout());
		repertoire = new File("DB_JOBS");
		files =repertoire.listFiles(xmlFileFilter);
		
		comboPrb.setPreferredSize(new Dimension(150, 40));
		for(int i = 0;i<files.length;i++)
		{
			int taille_nom= files[i].getName().length();
			comboPrb.addItem((files[i].getName()).substring(0, taille_nom-4));
		}
		
		
		fenetre.add(comboPrb);
		fenetre.add(bouton_ok);
		
		fenetre.setVisible(true); 
		
		bouton_ok.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	choix =comboPrb.getSelectedItem().toString();
		    	
		    	fichier_Choisi=new File("DB_JOBS/"+choix+".xml");
		    	System.out.println("Fichier choisi : "+fichier_Choisi);
		    	
		    	if(fichier_Choisi.delete())
		    	{
		    		System.out.println("Fichier supprimer");
		    	}
		    	else
		    	{
		    		System.out.println("Fichier non supprimer");
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
