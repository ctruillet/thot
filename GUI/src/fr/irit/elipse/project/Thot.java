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
import fr.irit.elipse.project.ThotImportFrame.importType;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

public class Thot extends JFrame{ 
	//Attributs
	private ThotText T_Text = new ThotText();
	private String selection; 
	private String txt;
	private ThotTableModel T_Table;	
	private ThotTable T_grammar;	
	private String directory;
	private static final long serialVersionUID = 0L;
	
	//Constructeur
	Thot() {
		super();
		setType(Type.UTILITY);
		double size[][] = {
			{20,120,120,120,20,40,20,120,120,120,20},
			{20,40,40,100,100,100,20,30,20}
		};
		
		//this.createNewDirectory();
		
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
					T_grammar.add(selection);
					T_grammar.fireTableDataChanged();
					T_Text.highlight(selection);
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
				ThotImportFrame tif = new ThotImportFrame(getParentDirectory());
				tif.setImportType(importType.TEXT);
				try {
					tif.openFrame(contentPane);
					T_Text.displayText(tif.getFilePath());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		//

		/*SimpleAttributeSet styleGras = new SimpleAttributeSet();
		StyleConstants.setBold(styleGras, true);
		StyledDocument doc = T_Text.getStyledDocument();
		doc.setCharacterAttributes(0, 50, styleGras, false);*/

		T_Text.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {  
				int d = ((ThotText) e.getSource()).getSelectionStart();
				int f = ((ThotText) e.getSource()).getSelectionEnd();
				System.out.println("d " + d + " - " + "f " + f);
				if(d==f)return; // pas de texte sélectionné !
				selection = ((ThotText) e.getSource()).getSelectedText();
				System.out.println(selection);
			}
		});
		
		JScrollPane SP_Text = new JScrollPane(T_Text);
		this.T_Text.setText("Veuillez importer un fichier texte.");
		
		T_Table = new ThotTableModel(){
			@Override
         	public boolean isCellEditable ( int row, int col) {
				return true;
         	}						
		};
		T_grammar = new ThotTable(T_Table);
		
		JScrollPane SP_grammar = new JScrollPane(T_grammar);
		
		contentPane.add(thot_title,"2,0,2,1");
		contentPane.add(thot_part,"1,2,3,2");
		contentPane.add(grammar_part,"7,0,9,0");		
		contentPane.add(B_Ajouter,  "5,4");
		contentPane.add(B_OpenText, "2,7");
		contentPane.add(B_Creation, "8,7");
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
		setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 10, 0, 10));
		setJMenuBar(menuBar);
		
		JMenuItem menuConscruct = new JMenuItem("Construction du spectacle");
		menuConscruct.setSelected(true);
		menuBar.add(menuConscruct);
		
		JMenuItem menuMan = new JMenuItem("Manuel d'utilisatation");
		menuBar.add(menuMan);
		
		JMenuItem menuScenar = new JMenuItem("Scénarios");
		menuBar.add(menuScenar);
	}
	
	//Méthodes
	public void setText(String text) {
		this.txt=text;
	}
	
	public String getText() {
		return (this.txt);
	}
	
	public void createNewDirectory() {
		this.directory = "./data/" + new SimpleDateFormat("dd-MM-yyyy_H.mm.s").format(new Date());
		File directory = new File(this.directory);
		directory.mkdir();
		System.out.println(this.directory);
	}
	
	public String getParentDirectory() {
		return (this.directory);
	}
	
	public void save_GRXML() {
		Date curDate = new Date();
		SimpleDateFormat SDFDate = new SimpleDateFormat("hh_mm_ss");
		String output = "grammar_" + SDFDate.format(curDate) + ".grxml";
		boolean exists = (new File(output)).exists();
		if (exists) {
			// File or directory exists
			System.out.println("Le fichier existe dï¿½jï¿½ !\n");
		}
		
		try{
			BufferedWriter output_xml = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8")); 
			// entetes
			output_xml.write("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			output_xml.write("<!DOCTYPE grammar PUBLIC \"-//W3C//DTD GRAMMAR 1.0//EN\"\n\"http://www.w3.org/TR/speech-grammar/grammar.dtd\">\n");
			output_xml.write("<!-- the default grammar language is FR  -->");
			output_xml.write("<grammar  version=\"1.0\"\nmode =\"voice\"\nxmlns=\"http://www.w3.org/2001/06/grammar\"\nxmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\nxsi:schemaLocation=\"http://www.w3.org/2001/06/grammar\nhttp://www.w3.org/TR/speech-grammar/grammar.xsd\"\nxml:lang=\"fr\" root=\"answer\">\n");
			output_xml.write("\n<rule id=\"answer\" scope=\"public\">\n<one-of>\n");
			
			// les mots ï¿½ reconnaitre - rï¿½gles ï¿½ crï¿½er
			for (int i=0;i<T_Table.getRowCount();i++){
				output_xml.write("<item>" + T_Table.getValueAt(i,0) + " </item>\n");
			}
			// crï¿½er les rules
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
