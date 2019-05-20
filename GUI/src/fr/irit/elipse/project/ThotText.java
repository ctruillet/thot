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
import javax.swing.text.*;


public class ThotText extends JTextPane {
	//Attributs
	
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
    
    public void highlight(String pattern){
        Highlighter.HighlightPainter mhp = new MyHighlightPainter(Color.CYAN);

        try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            // Search for pattern
            // see I have updated now its not case sensitive
            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0){
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos+pattern.length(), mhp);
                pos += pattern.length();
            }
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte sélectionné");
        }
    }

}