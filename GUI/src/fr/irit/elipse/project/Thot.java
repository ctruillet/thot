/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.2 du 05/01/2019
*/

package fr.irit.elipse.project;

import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Thot extends JFrame{ 
	//Attributs
	private ThotText T_Text = new ThotText();
	private String selection;
	protected int position;
	private String txt;
	private ThotTableModel T_Table;	
	private ThotTable T_grammar;	
	private String directory;
	protected JMenuItem menuConstruct;
	protected JMenuItem menuMan;
	protected JMenuItem menuScenar;
	private static final long serialVersionUID = 0L;
	protected int pos;
	protected String selectionn;
	
	//Constructeur
	Thot() {
		super();
		setType(Type.UTILITY);
		double sizeConstructPane[][] = {
			{20,120,120,120,20,40,20,120,120,120,20},
			{23,40,40,100,100,100,20,30,20}
		};
		
		double sizeManPane[][] = {
				{20,250,25,250,25,250,20},
				{53,40,40,100,100,100,20,20}
		};
		double sizeScenarPane[][] = {
				{20,250,25,250,25,250,20},
				{53,40,40,100,100,100,20,20}
		};
		
		this.createNewDirectory();
		
		this.selection = "";
		this.position = -1;
		//Container
		Container constructPane = new Container();
		Container manPane = new Container();
		Container scenarPane = new Container();
		
		TableLayout constructLayout = new TableLayout(sizeConstructPane);
		TableLayout manLayout = new TableLayout(sizeManPane);
		TableLayout scenarLayout = new TableLayout(sizeScenarPane);
		
		constructPane.setLayout(constructLayout);
		manPane.setLayout(manLayout);
		scenarPane.setLayout(scenarLayout);
		
		//Composants
			//Police
		Font fPlain = new Font("B612-Regular",Font.PLAIN, 10);
		Font fBigBold = new Font("B612-Regular",Font.BOLD, 36);
		//Titre
		JLabel thot_title = new JLabel("THOT");
		thot_title.setFont(fBigBold);
		//Descriptions
		JLabel thot_part = new JLabel("<html>Veuillez saisir votre texte ici en indiquant les mots-balises<br/> via le bouton ci-dessous</html>");
		thot_part.setFont(fPlain);
		JLabel grammar_part = new JLabel("Mots-balises et signification"); 
		grammar_part.setFont(fPlain);
		JScrollPane SP_Text = new JScrollPane(T_Text);
		this.T_Text.setText("Veuillez importer un fichier texte.");
		
		JLabel manPage = new JLabel("Manuel d'utilisation"); 
		manPage.setFont(fBigBold);
		JLabel scenarPage = new JLabel("Scénarios"); 
		scenarPage.setFont(fBigBold);
		
		//Tableau
		T_Table = new ThotTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
         	public boolean isCellEditable ( int row, int col) {
				return true;
         	}						
		};
		T_grammar = new ThotTable(T_Table, T_Text);
		
		JScrollPane SP_grammar = new JScrollPane(T_grammar);
		
		
		//Boutons
		//Ajouter un nouveau mot-balise
		ThotButton B_Ajouter = new ThotButton("<html>A<br/>j<br/>o<br/>u<br/>t<br/>e<br/>r</html>");
		B_Ajouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ajout du texte selectionné
				if (!selection.equals("")){
					System.out.println("ajout de \"" + selection + "-> " + position + "\" dans la table");
					T_Text.allOccurency.add(selection);
					T_grammar.add(position, selection);
					selection="";
					position = -1;
				}
            }
		});
			
		//Génération
		ThotButton B_Creation = new ThotButton("G\u00e9n\u00e9ration");
		B_Creation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println(T_Table.toString());
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
				tif.setImportType(ThotImportFrame.importType.TEXT);
				try {
					tif.openFrame(null);
					T_Text.displayText(tif.getFilePath());
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
		B_OpenText.setSize(10,10);
		
		//Selection d'un mot
		T_Text.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {  
				int d = ((ThotText) e.getSource()).getSelectionStart();
				int f = ((ThotText) e.getSource()).getSelectionEnd();
				//System.out.println("d " + d + " - " + "f " + f);
				if(d==f)return; // pas de texte sélectionné !
				selection = ((ThotText) e.getSource()).getSelectedText();
				position = d;
				System.out.println(selection + " -> " + position);
				pos=T_grammar.SelectedRow();
				if(pos!=-1) {
					T_grammar.getCellEditor().stopCellEditing();
				}
				T_grammar.fireTableDataChanged();
			}
		});

		//ConstructPane
		constructPane.add(thot_title,"2,0,2,1");
		constructPane.add(thot_part,"1,2,3,2");
		constructPane.add(grammar_part,"7,0,9,0");		
		constructPane.add(B_Ajouter,  "5,4");
		constructPane.add(B_OpenText, "2,7");
		constructPane.add(B_Creation, "8,7");
		constructPane.add(SP_Text, "1,3,3,5");
		constructPane.add(SP_grammar, "7,2,9,5");
		
		//ManPane
		manPane.add(manPage,"1,1,4,1");
		
		//Scenar Pane
		scenarPane.add(scenarPage,"1,1,4,1");
		
		//Close
		addWindowListener ( new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
        });
		
		//Frame
		setTitle("THOT");
		setForeground(new Color(255,255,255,255));  
		setBounds(0,0,840,525);
		constructPane.setBounds(0,0,840,525);
		manPane.setBounds(0,0,840,525);
		scenarPane.setBounds(0,0,840,525);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		
		//Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 40, 0, 40));
		setJMenuBar(menuBar);
		
		//Item Menu
		menuConstruct = new JMenuItem("Construction du spectacle");
		menuConstruct.setSelected(true);
		menuBar.add(menuConstruct);
		menuConstruct.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {				
			}

			@Override
			public void mouseExited(MouseEvent e) {			
			}

			public void mousePressed(MouseEvent e) {
				System.out.println(constructPane.toString());
				menuConstruct.setSelected(true);
				menuMan.setSelected(false);
				menuScenar.setSelected(false);
				
				constructPane.setVisible(true);
				manPane.setVisible(false);
				scenarPane.setVisible(false);
				
				setContentPane(constructPane);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		menuMan = new JMenuItem("Manuel d'utilisatation");
		menuBar.add(menuMan);
		menuMan.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {				
			}

			@Override
			public void mouseExited(MouseEvent e) {			
			}

			public void mousePressed(MouseEvent e) {
				System.out.println(manPane.toString());
				menuMan.setSelected(true);
				menuConstruct.setSelected(false);
				menuScenar.setSelected(false);
				
				constructPane.setVisible(false);
				manPane.setVisible(true);
				scenarPane.setVisible(false);
				
				setContentPane(manPane);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		menuScenar = new JMenuItem("Scénarios");
		menuBar.add(menuScenar);
		menuScenar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {				
			}

			@Override
			public void mouseExited(MouseEvent e) {			
			}

			public void mousePressed(MouseEvent e) {
				System.out.println(scenarPane.toString());
				menuScenar.setSelected(true);
				menuConstruct.setSelected(false);
				menuMan.setSelected(false);
				
				constructPane.setVisible(false);
				manPane.setVisible(false);
				scenarPane.setVisible(true);
				
				setContentPane(scenarPane);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	
		this.setContentPane(constructPane);
	}
	
	//Méthodes
	public void setText(String text) {
		this.txt=text;
	}
	
	public String getText() {
		return (this.txt);
	}
	
	public void createNewDirectory() {
		this.directory = "./data/" + new SimpleDateFormat("dd-MM-yyyy_H.mm.ss").format(new Date());
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
			System.out.println("Le fichier existe déjà !\n");
		}
		
		try{
			BufferedWriter output_xml = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));
			// entetes
			output_xml.write("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
			output_xml.write("<!DOCTYPE grammar PUBLIC \"-//W3C//DTD GRAMMAR 1.0//EN\"\n\"http://www.w3.org/TR/speech-grammar/grammar.dtd\">\n");
			output_xml.write("<!-- the default grammar language is FR  -->");
			output_xml.write("<grammar  version=\"1.0\"\nmode =\"voice\"\nxmlns=\"http://www.w3.org/2001/06/grammar\"\nxmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\nxsi:schemaLocation=\"http://www.w3.org/2001/06/grammar\nhttp://www.w3.org/TR/speech-grammar/grammar.xsd\"\nxml:lang=\"fr\" root=\"answer\">\n");
			output_xml.write("\n<rule id=\"answer\" scope=\"public\">\n<one-of>\n");
			
			// les mots à reconnaitre - régles à créer
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
		
		JOptionPane.showMessageDialog(this,
                     "Le Grammaire vient d'\u00eatre g\u00e9n\u00e9r\u00e9e",
                     "Confirmation",
                      JOptionPane.INFORMATION_MESSAGE);
	}
	
		
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Thot t = new Thot();
		t.setVisible(true);
	}
}
