/**
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 * @version 0.1 du 04/01/2019
 */

package fr.irit.elipse.project;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.GraphicsEnvironment;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.table.TableCellEditor;
import javax.swing.text.*;


public class ThotText extends JTextPane {
	//Attributs
	protected List<String> allMot = new ArrayList<String>(1);
	protected String test;
	//Enum
    enum TypeMot {
        VERBE,
        NOM,
        COMPLEMENT,
        ADVERBE
    }
	//Constructeur
    public ThotText() {
        super();
        this.setEditable(false);
    }
    
    //Inner Class
    protected class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{
        public MyHighlightPainter(Color color){
            super(color);
        }
    }
    
    
    //Méthodes
    public StringBuffer readText(String nomFichier){
        File fichier = new File(nomFichier);
        StringBuffer texte = new StringBuffer();

        try {
            Scanner lecteur = new Scanner(fichier);
            while (lecteur.hasNext()){

                texte.append(lecteur.nextLine());
                texte.append("\n");
            }
            //voir si il ne faudrait pas mettre un finally a cause du close !
            lecteur.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Fichier inexistant");
        }
        System.out.println(texte);
        
        return (texte);
    }

    public void displayText(String nomFichier){
        this.setText(new String(readText(nomFichier)));
    }
    
  //Permet de faire la variation de la couleur en fonction du type de mot
    public Color variationColor(TypeMot mot){
        switch(mot){
            case VERBE:
                return Color.red;
            case NOM:
                return Color.blue;
            case COMPLEMENT:
                return Color.green;
            case ADVERBE:
                return Color.orange;
            default:
                return Color.white;
        }
    }

    //permet de changer le fond pour chaque mot envoyé en comptant toute les rebondances
    public void highlight(String pattern){
        Highlighter.HighlightPainter mhp = new MyHighlightPainter(Color.red);
        try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0){

                hilite.addHighlight(pos, pos+pattern.length(), mhp);
                pos += pattern.length();
            }
            System.out.println(allMot.toString());
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte sélectionné");
        }
    }
    
    public void suppresion(int pattern) {
    	System.out.println(pattern);
    	test=allMot.get(pattern);
    	if(pattern>=0) {
            allMot.remove(allMot.get(pattern));
    	}
    	System.out.println(allMot.toString());
    	
    	try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(test.toUpperCase(), pos)) >= 0){

                hilite.removeAllHighlights();
                pos += test.length();
            }
            System.out.println(allMot.toString());
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte sélectionné");
        }
    	

        for(int i=0;i<allMot.size();i++) {
        	highlight(allMot.get(i));
        }

    }



}