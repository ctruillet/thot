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
import java.util.ArrayList;
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
	protected JMenuItem menuInfo;
	private static final long serialVersionUID = 0L;
	protected int pos;
	protected ArrayList<ArrayList<ThotGrammar>> motGrammar = new ArrayList<ArrayList<ThotGrammar>>();
	
	//Constructeur
	Thot() {
		super();
		setType(Type.UTILITY);
		double[][] sizeConstructPane = {
				{20, 120, 120, 120, 20, 40, 20, 120, 120, 120, 20},
				{23, 40, 40, 100, 100, 100, 20, 30, 20}
		};

		double[][] sizeManPane = {
				{20, 250, 25, 250, 25, 250, 20},
				{53, 40, 40, 100, 100, 100, 20, 20}
		};
		double[][] sizeinfoPane = {
				{20, 250, 25, 250, 25, 250, 20},
				{53, 40, 40, 100, 100, 100, 20, 20}
		};
		
		this.createNewDirectory();
		
		this.selection = "";
		this.position = -1;
		//Container
		Container constructPane = new Container();
		Container manPane = new Container();
		Container infoPane = new Container();
		
		TableLayout constructLayout = new TableLayout(sizeConstructPane);
		TableLayout manLayout = new TableLayout(sizeManPane);
		TableLayout scenarLayout = new TableLayout(sizeinfoPane);
		
		constructPane.setLayout(constructLayout);
		manPane.setLayout(manLayout);
		infoPane.setLayout(scenarLayout);
		
		//Composants
		//Police
		Font fPlain = new Font("B612-Regular",Font.PLAIN, 10);
		Font fInfo = new Font("B612-Regular",Font.PLAIN, 12);
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
		JTextPane infoPage = new JTextPane();
		infoPage.setContentType("text/html");
		infoPage.setText("Le logiciel <strong>THOT</strong> a été réalisé dans le cadre d'un stage à l'IRIT en  2019 par <br />" +
				"Antonin Miloudi - <i>antonin.miloudi@univ-tlse3.fr</i><br />" +
				"Clement Truillet - <i>clement.truillet@univ-tlse3.fr</i><br />" +
				"<br />" +
				"Vous pouvez retrouver le code à l'adresse suivante : <i>https://github.com/ctruillet/thot</i>");
		infoPage.setBackground(null);
		infoPage.setFont(fInfo);
		
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
				pos=T_grammar.SelectedRow();
				if(pos!=-1) {
					T_grammar.getCellEditor().stopCellEditing();
				}
				T_grammar.fireTableDataChanged();
                //Génération de la Grammaire
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
					if(tif.getFilePath()!=null) {
						T_Text.displayText(tif.getFilePath());
					}					
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
		infoPane.add(infoPage,"1,1,4,4");
		
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
		infoPane.setBounds(0,0,840,525);
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
				menuInfo.setSelected(false);
				
				constructPane.setVisible(true);
				manPane.setVisible(false);
				infoPane.setVisible(false);
				
				setContentPane(constructPane);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		menuMan = new JMenuItem("Manuel d'utilisation");
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
				menuInfo.setSelected(false);
				
				constructPane.setVisible(false);
				manPane.setVisible(true);
				infoPane.setVisible(false);
				
				setContentPane(manPane);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		menuInfo = new JMenuItem("Informations");
		menuBar.add(menuInfo);
		menuInfo.addMouseListener(new MouseListener() {
			
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
				System.out.println(infoPane.toString());
				menuInfo.setSelected(true);
				menuConstruct.setSelected(false);
				menuMan.setSelected(false);
				
				constructPane.setVisible(false);
				manPane.setVisible(false);
				infoPane.setVisible(true);
				
				setContentPane(infoPane);
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

	public void cutText(){
		int indexListe = 0;
		this.motGrammar.add(new ArrayList<ThotGrammar>());

		for (int i = 0; i<T_Table.getListe().size(); i++){
			if(T_Table.getTypeEvent(i).equals("Registre")) {
				System.out.println("-----");
				indexListe++;
				this.motGrammar.add(new ArrayList<ThotGrammar>());
			}
			if (motGrammar.get(indexListe).size()!=0  &&
				motGrammar.get(indexListe).get(motGrammar.get(indexListe).size()-1).getMotBalise().toLowerCase().equals(T_Table.getMot(i).toLowerCase())){
					this.motGrammar.get(indexListe).set(this.motGrammar.get(indexListe).size()-1,
														new ThotGrammar(T_Table.getPosition(i),
														T_Table.getMot(i),ThotTypeEvent.Autre,
														(this.motGrammar.get(indexListe).get(this.motGrammar.get(indexListe).size()-1).getConcept() + "& " + T_Table.getMotBalise(i).getConcept())));
			}else{
				this.motGrammar.get(indexListe).add(T_Table.getMotBalise(i));
			}
		}

		System.out.println(this.motGrammar.toString());
	}
	public void save_GRXML(int i) {
		//Créer grammar+i.grxml
		String output = this.getParentDirectory() + "/grammar" + i + ".grxml";
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
			output_xml.write("<!-- the default grammar language is FR  -->\n");
			output_xml.write("<grammar  version=\"1.0\"\n" +
									"\t\t  mode =\"voice\"\n" +
									"\t\t  xmlns=\"http://www.w3.org/2001/06/grammar\"\n" +
									"\t\t  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
									"\t\t  xsi:schemaLocation=\"http://www.w3.org/2001/06/grammar\n" +
									"\t\t\t\t\t\t\t  http://www.w3.org/TR/speech-grammar/grammar.xsd\"\n" +
									"\t\t  xml:lang=\"fr-FR\" root=\"answer\" tag-format=\"semantics/1.0\">\n");
			output_xml.write("\n<rule id=\"answer\" scope=\"public\">\n");
			output_xml.write("\t<ruleref special=\"GARBAGE\"/>\n");
			output_xml.write("\t<one-of>\n");
			for (int j=0;j<this.motGrammar.get(i).size();j++){
				output_xml.write("\t\t<item><ruleref uri=\"#mot"+j+"\"/></item>\n");
			}
			output_xml.write("\t\t<item><ruleref special=\"GARBAGE\"/></item>\n");
			output_xml.write("\t</one-of>\n");
			output_xml.write("\t<tag>out=rules.latest().text</tag>\n</rule>\n\n");
			
			for(int j=0;j<this.motGrammar.get(i).size();j++) {
				output_xml.write("\t<rule id=\"mot"+j+"\">\n" + 
								 	"\t\t<item>"+motGrammar.get(i).get(j).getMotBalise()+"</item>\n" + 
								 	"\t\t<ruleref special=\"GARBAGE\" />\n"
								 	+ "\t\t<tag>out.text=\""+motGrammar.get(i).get(j).getConcept() +"\"</tag>\n" +
									"\t</rule>\n");
			}
			
			// fin  de la grammaire
			output_xml.write("\n</grammar>\n");
			output_xml.close();
		}
		catch (Exception e) {
			System.out.println("Erreur dans la création du fichier grxml.");
		}
	}
	public void save_CSV() {
		
		//Créer grammar+i.grxml
		String output = this.getParentDirectory() + "/grammar.csv";
		boolean exists = (new File(output)).exists();
		if (exists) {
			// File or directory exists
			System.out.println("Le fichier existe déjà !\n");
		}
				
		try{	
			BufferedWriter csvOutput = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));
			// entetes
			
			csvOutput.write("Mots-Balises;Type d'evenement;Concept associee;Description\n");
			for(int i=0; i<this.motGrammar.size(); i++) {
				for(int j=0;j<this.motGrammar.size();j++) {
					csvOutput.write(motGrammar.get(i).get(j).getMotBalise()+";"
								    +motGrammar.get(i).get(j).getTypeEventName()+";"
							        +motGrammar.get(i).get(j).getConceptName()+";"
								    +motGrammar.get(i).get(j).getDescription()+"\n");
					
				}
			}

			csvOutput.close();
			}
			catch (Exception e) {
				System.out.println("Erreur dans la création du fichier CSV.");
			}
	}
	
	
	public void save_GRXML() {
		this.cutText();
	
		for(int i=0; i<this.motGrammar.size(); i++) {
			System.out.println(this.motGrammar.get(i).toString());
			if(this.motGrammar.get(i).size()!=0)
				this.save_GRXML(i);
		}
		this.save_CSV();
		
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