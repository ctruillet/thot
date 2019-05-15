/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.2 du 05/01/2019
*/

package fr.irit.elipse.project;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import javax.swing.*;
import info.clearthought.layout.TableLayout;

import java.util.Date;
import java.text.SimpleDateFormat;

import fr.irit.elipse.project.ThotButton;

class Thot extends JFrame{ 
	//Attributs
	private ThotText T_Text;
	private String selection; 
	private String txt;
	private ThotTable T_Table;	
	private static final long serialVersionUID = 0L;
	
	//Constructeur
	Thot() {
		super();
		double size[][] = {
			{20,120,120,120,20,40,20,120,120,120,20},
			{20,40,40,100,100,100,20,30,20}
		};
		
		this.selection = "";
		
		Container contentPane = getContentPane();
		
		TableLayout layout = new TableLayout(size);
		contentPane.setLayout(layout);
		
		// ajout des composants
		Font fPlain = new Font("B612-Regular",Font.PLAIN, 10);
		Font fBigBold = new Font("B612-Regular",Font.BOLD, 36);
		
		JLabel thot_title = new JLabel("THOT");
		thot_title.setFont(fBigBold);
		JLabel thot_part = new JLabel("<html>Veuillez saisir votre texte ici en indiquant les mots-balises<br/> via le bouton ci-dessous</html>");
		thot_part.setFont(fPlain);
		
		JLabel grammar_part = new JLabel("Mots-balises et signification"); 
		grammar_part.setFont(fPlain);
		
		ThotButton B_Ajouter = new ThotButton("<html>A<br/>j<br/>o<br/>u<br/>t<br/>e<br/>r</html>");
		B_Ajouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ajout du texte selectionné
				if (!selection.equals("")){
					System.out.println("ajout de \"" + selection + "\" dans la table");
					T_Table.add(selection);
					T_Table.fireTableDataChanged();	
					selection="";
				}		
            }
		});
		
		ThotButton B_Balisage = new ThotButton("Balisage");	
		// désactivation du bouton 
		B_Balisage.setEnabled(false);
		
		ThotButton B_Creation = new ThotButton("G\u00e9n\u00e9ration");
		B_Creation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Génération de la Grammaire
				System.out.println("Generation de la grammaire");
				save_GRXML();
            }
        });
		
		//Import Text
		ThotButton B_OpenText = new ThotButton("Import text");
		B_OpenText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				System.out.println("Import text");	
            }
        });

		if(this.txt == "")
			B_OpenText.setVisible(true);
		
		
		
		//
		
		this.txt="<html>C'est alors qu'apparut le <b>renard</b> :<ul><li>Bonjour, dit le renard.</li><li>Bonjour, répondit poliment le petit prince, qui se retourna mais ne vit rien.</li><li>Je suis l\u00e0, dit la voix sous le pommier</li><li>Qui es-tu ? dit le petit prince. Tu es bien joli.</li><li>Je suis un renard, dit le renard.</li></ul>Viens jouer avec moi, lui proposa le petit prince. Je suis tellement triste...</html>";

		
		T_Text = new ThotText("text/html",this.txt);
		T_Text.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {  
				int d = ((ThotText) e.getSource()).getSelectionStart();
				int f = ((ThotText) e.getSource()).getSelectionEnd();
				System.out.println("d " + d + " - " + "f " + f);
				if(d==f)return; // pas de texte sÃ©lectionnÃ© !
				selection = ((ThotText) e.getSource()).getSelectedText();
				System.out.println(selection);
			}
		});
		
		JScrollPane SP_Text = new JScrollPane(T_Text);
		
		
		T_Table = new ThotTable(){
			@Override
         	public boolean isCellEditable ( int row, int col) {
				return true;
         	}						
		};
		JTable T_grammar = new JTable(T_Table);
		T_grammar.setAutoCreateRowSorter(true);
		
		JScrollPane SP_grammar = new JScrollPane(T_grammar);
		
		contentPane.add(thot_title,"2,0,2,1");
		contentPane.add(thot_part,"1,2,3,2");
		contentPane.add(grammar_part,"7,0,9,0");		
		contentPane.add(B_Ajouter,  "5,4");
		contentPane.add(B_Balisage, "2,7");
		contentPane.add(B_Creation, "8,7");
		contentPane.add(B_OpenText, "1,3,3,5");
		contentPane.add(SP_Text, "1,3,3,5");
		contentPane.add(SP_grammar, "7,2,9,5");
		B_OpenText.setSize(10,10);
		
		// finalisation de l'interface
		addWindowListener ( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
        });
		
		setTitle("THOT");
		setForeground(new Color(255,255,255,255));     
		setBounds(0,0,840,510);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	public void setText(String text) {
		this.txt=text;
	}
	
	public String getText() {
		return (this.txt);
	}
	
	void save_GRXML() {
		Date curDate = new Date();
		SimpleDateFormat SDFDate = new SimpleDateFormat("hh_mm_ss");
		String output = "grammar_" + SDFDate.format(curDate) + ".grxml";
		boolean exists = (new File(output)).exists();
		if (exists) {
			// File or directory exists
			System.out.println("Le fichier existe déjà  !\n");
		}
		
		try{
			BufferedWriter output_xml = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8")); 
			// entetes
			output_xml.write("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			output_xml.write("<!DOCTYPE grammar PUBLIC \"-//W3C//DTD GRAMMAR 1.0//EN\"\n\"http://www.w3.org/TR/speech-grammar/grammar.dtd\">\n");
			output_xml.write("<!-- the default grammar language is FR  -->");
			output_xml.write("<grammar  version=\"1.0\"\nmode =\"voice\"\nxmlns=\"http://www.w3.org/2001/06/grammar\"\nxmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\nxsi:schemaLocation=\"http://www.w3.org/2001/06/grammar\nhttp://www.w3.org/TR/speech-grammar/grammar.xsd\"\nxml:lang=\"fr\" root=\"answer\">\n");
			output_xml.write("\n<rule id=\"answer\" scope=\"public\">\n<one-of>\n");
			
			// les mots à reconnaitre - règles à  créer
			for (int i=0;i<T_Table.getRowCount();i++){
				output_xml.write("<item>" + T_Table.getValueAt(i,0) + " </item>\n");
			}
			// créer les rules
			// String rules="";
			// output_xml.write(rules);
			
			// fin  de la grammaire
			output_xml.write("</one-of>\n</rule>\n</grammar>\n");
			output_xml.close();
		}
		catch (Exception e) {
			
		}
		
		JOptionPane d = new JOptionPane();
		d.showMessageDialog(this,
                     "Le Grammaire vient d'\u00eatre g\u00e9n\u00e9r\u00e9e",
                     "Confirmation",
                      JOptionPane.INFORMATION_MESSAGE);
	}
	
		
	public static void main(String[] args){
		Thot t = new Thot();
		t.setVisible(true);
	}
}
