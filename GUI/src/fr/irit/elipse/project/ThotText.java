/**
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 * @version 0.1 du 04/01/2019
 */

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ThotText extends JTextPane {
	//Attributs
	protected List<String> allOccurency = new ArrayList<String>(1);
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
    
    
    //M�thodes
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

    //permet de changer le fond pour chaque mot envoy� en comptant toute les rebondances
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
            System.out.println(allOccurency.toString());
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte s�lectionn�");
        }
    }
    
    public void suppresion(int pattern) {
    	System.out.println(pattern);
    	test=allOccurency.get(pattern);
    	if(pattern>=0) {
            allOccurency.remove(allOccurency.get(pattern));
    	}
    	System.out.println(allOccurency.toString());
    	
    	try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(test.toUpperCase(), pos)) >= 0){

                hilite.removeAllHighlights();
                pos += test.length();
            }
            System.out.println(allOccurency.toString());
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte s�lectionn�");
        }
    	

        for(int i=0;i<allOccurency.size();i++) {
        	highlight(allOccurency.get(i));
        }

    }



}