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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import java.io.*;
import org.jdom2.*;
import org.jdom2.output.*;


@SuppressWarnings("unused")
public class ButtonAjout extends JButton implements MouseListener {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Image img;
	private JFrame fenetreAjout;
	
	private JLabel contraintes ;
	private JLabel split ;
	private JLabel exec ;
	private JLabel build ;
	
	private JTextField contraintes1 ;
	private JTextField split1 ;
	private JTextField exec1 ;
    private JTextField build1 ;
    
    private JButton bouton_ok ;
    
    static org.jdom2.Document document;
	static Element racine;
	
	 public ButtonAjout(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.fenetreAjout = new JFrame();
		this.contraintes= new JLabel("Chemin du fichier de contrainte");
		this.split= new JLabel("Chemin du fichier contenant le Split");
		this.exec= new JLabel("Chemin du fichier contenant l'Execution");
		this.build= new JLabel("Chemin du fichier contenant le build");
	    this.contraintes1 =new JTextField("Path_dufichier_perl_de_Contraintes");
	    this.split1= new JTextField("path_du_fichier_du_code_de_split");
	    this.exec1= new JTextField("path_du_fichier_du_code_de_exec");
	    this.build1= new JTextField("path_du_fichier_du_code_de_build");
	    this.bouton_ok=new JButton("OK");
	    this.racine = new Element("JOB");
	    this.document=new Document(racine);

	}
	 
	 
	public int Ajouter_TYPE_DE_JOBS(String contraintesT,String splitT,String exec1T,String buildT)
	{
		/*Ici on va crer le fichier XML dans un dossier propre a la machine */
		/*On va tester, l'existence du dossier  */
		File f = new File ("DB_JOBS");
		if (f.exists()&& f.isDirectory()){
		     /*Le fichier existe on va faire la creation du fichier XML associer*/
			  
			
		}else{
		     System.out.println("Mon répertoire n'existe pas");
		}
		return 0;
	}  
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetreAjout = new JFrame();
		fenetreAjout.setTitle("Ajout d'une tache dans la base");
		fenetreAjout.setSize(250, 275);
		fenetreAjout.setLocationRelativeTo(null);
		fenetreAjout.setLayout(new FlowLayout());
		fenetreAjout.add(contraintes);
		fenetreAjout.add(contraintes1);
		fenetreAjout.add(split);
		fenetreAjout.add(split1);
		fenetreAjout.add(exec);
		fenetreAjout.add(exec1);
		fenetreAjout.add(build);
		fenetreAjout.add(build1);
		fenetreAjout.add(bouton_ok);
		fenetreAjout.setVisible(true); 
		
		contraintes1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(contraintes1);
		        String nomFic = "";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	contraintes1.setText(fc.getSelectedFile().getAbsolutePath());
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
		split1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(contraintes1);
		        String nomFic = "";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	split1.setText(fc.getSelectedFile().getAbsolutePath());
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
		exec1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(contraintes1);
		        String nomFic = "";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	exec1.setText(fc.getSelectedFile().getAbsolutePath());
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
		build1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers texte", "txt");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(contraintes1);
		        String nomFic = "";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	build1.setText(fc.getSelectedFile().getAbsolutePath());
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
		bouton_ok.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	Ajouter_TYPE_DE_JOBS(contraintes1.getText(),split1.getText(),exec1.getText(),build1.getText());
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
