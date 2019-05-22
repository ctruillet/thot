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
    public void highlight(String pattern,TypeMot mot, boolean etat){
        Highlighter.HighlightPainter mhp = new MyHighlightPainter(variationColor(mot));
        if(etat){
            mhp=new MyHighlightPainter(Color.white);
        }
        try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());

            int pos = 0;

            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0){

                hilite.addHighlight(pos, pos+pattern.length(), mhp);
                pos += pattern.length();
            }
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte sélectionné");
        }
    }



}