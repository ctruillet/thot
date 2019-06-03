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
	protected List<ThotTypeEvent> typeEvent = new ArrayList<ThotTypeEvent>(1);
	protected String word;
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
    public Color variationColor(ThotTypeEvent mot){
        switch(mot){
            case Retranscription:
                return Color.red;
            case Media:
                return Color.blue;
            case Effet:
                return Color.green;
            case Registre:
                return Color.orange;
            default:
                return Color.white;
        }
    }

    //permet de changer le fond pour chaque mot envoyé en comptant toute les rebondances
    public void highlight(String pattern,ThotTypeEvent value){
        Highlighter.HighlightPainter mhp = new MyHighlightPainter(variationColor(value));
        
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

    public void suppr(int pattern) {
    	word=allOccurency.get(pattern);
    	if(pattern>=0) {
            allOccurency.remove(allOccurency.get(pattern));
            typeEvent.remove(typeEvent.get(pattern));
    	}
    	Remove(pattern,word);
    }
    
    public void Remove(int pattern,String word) {
    	//on pourra trier aussi pour une meilleur compréhension mais c'est pas forcement utile
    	try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(word.toUpperCase(), pos)) >= 0){

                hilite.removeAllHighlights();
                pos += word.length();
            }
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte sélectionné");
        }
    	

        for(int i=0;i<allOccurency.size();i++) {
        	highlight(allOccurency.get(i),typeEvent.get(i));
        }
        System.out.println(allOccurency.toString());

    }
}