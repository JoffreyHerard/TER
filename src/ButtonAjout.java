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
import javax.swing.JOptionPane;
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
	private JLabel exec ;
	private JLabel nom_p ;
	private JLabel commande ;
	private JLabel rang ;
	private JLabel nom_fic;
	
	private JTextField nom_fic_1;
	private JTextField rang1 ;
	private JTextField commande1 ;
	private JTextField contraintes1 ;
	private JTextField nom_p1 ;
	private JTextField exec1 ;
    
    private JButton bouton_ok ;
    

	
	 public ButtonAjout(String str){
	    super(str);
	    this.name = str;
	    this.addMouseListener(this);
	    this.fenetreAjout = new JFrame();
	    this.nom_p= new JLabel("Nom : ");
	    this.nom_p1= new JTextField("Nom du probleme");
	    
	    this.nom_fic= new JLabel("Nom du fichier exec: ");
	    this.nom_fic_1= new JTextField("Nom du fichier a executer");
	    
		this.contraintes= new JLabel("Chemin du fichier de contrainte");
		
		this.exec= new JLabel("Chemin du fichier d'Execution");
	    this.contraintes1 =new JTextField("Path_dufichier_perl_de_Contraintes");
	    this.exec1= new JTextField("path_du_fichier_du_code_de_exec");
	    this.bouton_ok=new JButton("OK");
		this.commande= new JLabel("Ligne de commande d'execution");
	    this.commande1 =new JTextField("Ligne de comandes a executer");
	    this.rang= new JLabel("Rang du probleme");
	    this.rang1 =new JTextField("Rang");
	   
	    
	}
	 
	public String FileToString(String PathFile)
	{
		String fic ="";
		//lecture du fichier texte	
		try
		{
			InputStream ips=new FileInputStream(PathFile); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null)
			{
				System.out.println(ligne);
				fic+=ligne;
			}
			br.close(); 
			ipsr.close();
			ips.close();
		}		
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return fic;
	} 
	public int Ajouter_TYPE_DE_JOBS(String nom,String nom_fic,String contraintesT,String exec1T,String commandeT,String rang)
	{
		/*Ici on va crer le fichier XML dans un dossier propre a la machine */
		/*On va tester, l'existence du dossier  */
		int retour = -42;
		String Fichier_Perl;
		String Fichier_Exec;
		String Fichier_Commande;
		
		File d = new File ("DB_JOBS");
		File f = new File ("DB_JOBS/"+nom+".xml");
		if (d.exists()&& d.isDirectory()){
		     /*Le dosseier existe on va faire la creation du fichier XML associer*/
			if (!f.exists())
			{
				try {

					
						Element JOB = new Element("JOB");
						Document doc = new Document(JOB);
						
						
						Fichier_Perl=FileToString(contraintesT);
	
						Fichier_Exec=FileToString(exec1T);
						Fichier_Commande=FileToString(commandeT);
						
						JOB.addContent(new Element("code_Perl").setText(Fichier_Perl));
						
						JOB.addContent(new Element("code_exec").setText(Fichier_Exec));
						JOB.addContent(new Element("cmd").setText(Fichier_Commande));
						JOB.addContent(new Element("rang").setText(rang));
						JOB.addContent(new Element("nom_fic").setText(nom_fic));
						
	
						// new XMLOutputter().output(doc, System.out);
						XMLOutputter xmlOutput = new XMLOutputter();
	
						// display nice nice
						xmlOutput.setFormat(Format.getPrettyFormat());
						if(!(new File("DB_JOBS/"+nom+".xml").exists())){
							xmlOutput.output(doc, new FileWriter("DB_JOBS/"+nom+".xml"));
						}
						else
						{
							//messagebox
						}
						
						System.out.println("Fichier Enregistrer!");
						
					
				  } catch (IOException io) {
					System.out.println(io.getMessage());
				  }
				retour= 0;
			}
			else
			{	
				MessageBox.show("Erreur", "Problemes deja existant");
				retour= -1;
			}
			
		}else{
		     System.out.println("Mon répertoire n'existe pas");
		     d.mkdir();
		     try {

			 		Element JOB = new Element("JOB");
					Document doc = new Document(JOB);
					
					
					Fichier_Perl=FileToString(contraintesT);
			
					Fichier_Exec=FileToString(exec1T);

					Fichier_Commande=FileToString(commandeT);
					
					JOB.addContent(new Element("code_Perl").setText(Fichier_Perl));

					JOB.addContent(new Element("code_exec").setText(Fichier_Exec));
					JOB.addContent(new Element("cmd").setText(Fichier_Commande));
					JOB.addContent(new Element("rang").setText(rang));


					

					// new XMLOutputter().output(doc, System.out);
					XMLOutputter xmlOutput = new XMLOutputter();
					
					// display nice nice
					xmlOutput.setFormat(Format.getPrettyFormat());
					if(!(new File("DB_JOBS/"+nom+".xml").exists())){
						xmlOutput.output(doc, new FileWriter("DB_JOBS/"+nom+".xml"));
					}else
					{
						//messagebox
					}

					System.out.println("Fichier Enregistrer!");
				  } catch (IOException io) {
					System.out.println(io.getMessage());
				  }
				retour= 0;
		}
		
		return retour;
	}  
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JFrame fenetreAjout = new JFrame();
		fenetreAjout.setTitle("Ajout d'une tache dans la base");
		fenetreAjout.setSize(230,280);
		fenetreAjout.setLocationRelativeTo(null);
		fenetreAjout.setLayout(new FlowLayout());
		fenetreAjout.add(nom_p);
		fenetreAjout.add(nom_p1);
		fenetreAjout.add(nom_fic);
		fenetreAjout.add(nom_fic_1);
		fenetreAjout.add(contraintes);
		fenetreAjout.add(contraintes1);
		fenetreAjout.add(exec);
		fenetreAjout.add(exec1);
		fenetreAjout.add(commande);
		fenetreAjout.add(commande1);
		fenetreAjout.add(rang);
		fenetreAjout.add(rang1);
		fenetreAjout.add(bouton_ok);
		fenetreAjout.setVisible(true); 
		
		commande1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers liste de commande", "dc");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(commande1);
		        String nomFic = "";
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	commande1.setText(fc.getSelectedFile().getAbsolutePath());
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
		contraintes1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers Perl", "pl");
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
		
		exec1.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	JFileChooser fc = new JFileChooser();
				 
		        //Creer un filtre qui ne sélectionnera que les fichiers .txt
		        FileNameExtensionFilter ff = new FileNameExtensionFilter("Fichiers executable", "pl");
		        fc.setFileFilter(ff);
		 
		        int returnVal = fc.showOpenDialog(exec1);
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
		
		bouton_ok.addMouseListener(new MouseListener() {

		    public void mouseClicked(MouseEvent e) {
		    	int retour =Ajouter_TYPE_DE_JOBS(nom_p1.getText(),nom_fic_1.getText(),contraintes1.getText(),exec1.getText(),commande1.getText(),rang1.getText());
		    	switch(retour)
		    	{
			    	case -1:
			    		// le fichier existe deja 
			    		
			    		JOptionPane.showMessageDialog(null, "Le fichier existe deja", "Erreur", JOptionPane.ERROR_MESSAGE);
			    		break;
	
			    	case 0:
			    		// Good
			    		break;
	
			    	default:
			    		// Une erreur inconnu
			    		break;
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

	public JFrame getFenetreAjout() {
		return fenetreAjout;
	}

	public void setFenetreAjout(JFrame fenetreAjout) {
		this.fenetreAjout = fenetreAjout;
	}

	public JLabel getContraintes() {
		return contraintes;
	}

	public void setContraintes(JLabel contraintes) {
		this.contraintes = contraintes;
	}


	public JLabel getExec() {
		return exec;
	}

	public void setExec(JLabel exec) {
		this.exec = exec;
	}


	public JLabel getNom_p() {
		return nom_p;
	}

	public void setNom_p(JLabel nom_p) {
		this.nom_p = nom_p;
	}

	public JTextField getContraintes1() {
		return contraintes1;
	}

	public void setContraintes1(JTextField contraintes1) {
		this.contraintes1 = contraintes1;
	}

	public JTextField getNom_p1() {
		return nom_p1;
	}

	public void setNom_p1(JTextField nom_p1) {
		this.nom_p1 = nom_p1;
	}

	
	public JTextField getExec1() {
		return exec1;
	}

	public void setExec1(JTextField exec1) {
		this.exec1 = exec1;
	}


	public JButton getBouton_ok() {
		return bouton_ok;
	}

	public void setBouton_ok(JButton bouton_ok) {
		this.bouton_ok = bouton_ok;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JLabel getCommande() {
		return commande;
	}

	public void setCommande(JLabel commande) {
		this.commande = commande;
	}

	public JLabel getRang() {
		return rang;
	}

	public void setRang(JLabel rang) {
		this.rang = rang;
	}

	public JLabel getNom_fic() {
		return nom_fic;
	}

	public void setNom_fic(JLabel nom_fic) {
		this.nom_fic = nom_fic;
	}

	public JTextField getNom_fic_1() {
		return nom_fic_1;
	}

	public void setNom_fic_1(JTextField nom_fic_1) {
		this.nom_fic_1 = nom_fic_1;
	}

	public JTextField getRang1() {
		return rang1;
	}

	public void setRang1(JTextField rang1) {
		this.rang1 = rang1;
	}

	public JTextField getCommande1() {
		return commande1;
	}

	public void setCommande1(JTextField commande1) {
		this.commande1 = commande1;
	}
	
	
}
