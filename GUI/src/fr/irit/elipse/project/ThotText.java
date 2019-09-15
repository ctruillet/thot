/**
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @version 0.3 du 13/06/2019
 * NB : Je n'ai aucune idée de ce que Antonin a voulu faire avec cette classe.
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

/**
 * Classe de gestion du texte de travail
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 *
 */
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
        this.setEditable(true);
    }
    
    //Inner Class
    protected class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{
        public MyHighlightPainter(Color color){
            super(color);
        }
    }
    
    //Méthodes
    
    /**
     * 
     * @param nomFichier
     * @return
     */
    public StringBuffer readText(String nomFichier){
        File fichier = new File(nomFichier);
        StringBuffer texte = new StringBuffer();

        try {
            Scanner lecteur = new Scanner(fichier);
            while (lecteur.hasNext()){

                texte.append(lecteur.nextLine());
                texte.append("\n");
            }
            lecteur.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Fichier inexistant");
        }
        
        return (texte);
    }

    /**
     * 
     * @param nomFichier
     */
    public void displayText(String nomFichier){
        this.setText(new String(readText(nomFichier)));
    }
    
    /**
     * Colore un mot-balise suivant son type d'evenement
     * @param mot
     * @return
     * @see ThotTypeEvent
     */
    public Color variationColor(ThotTypeEvent mot){
        switch(mot){
            case Retranscription:
                return Color.red;
            case Media:
                return Color.cyan;
            case Effet:
                return Color.green;
            case Registre:
                return Color.orange;
            default:
                return Color.white;
        }
    }

    /**
     * Change la couleur de toutes les occurences d'une mot-balise
     * @param pattern
     * @param value
     * @param posmin
     * @param posmax
     * @see ThotTypeEvent
     */
    public void highlight(String pattern,ThotTypeEvent value,int posmin,int posmax){
        Highlighter.HighlightPainter mhp = new MyHighlightPainter(variationColor(value));
        
        try{
            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0 && pos<=posmax){
            	if(pos>=posmin) {
            		hilite.addHighlight(pos, pos+pattern.length(), mhp);
            	}
                    
            	pos += pattern.length();
            }
            
        }catch (BadLocationException e) {
        	System.out.println("Pas de texte s�lectionn�");
        }
    }

    /**
     * 
     * @param pattern
     * @param liste
     * @param listePos
     * @param listeText
     * @see ThotGrammar
     */
    public void suppr(int pattern,ArrayList<ThotGrammar> liste,ArrayList<Integer> listePos,ArrayList<Integer> listeText) {
    	word=allOccurency.get(pattern);
    	if(pattern>=0) {
            allOccurency.remove(pattern);
            typeEvent.remove(pattern);
    	}
    	remove(pattern,word,liste,listePos,listeText);
    }
    
    /**
     * 
     * @param pattern
     * @param word
     * @param liste
     * @param listePos
     * @param listeText
     * @see ThotGrammar
     */
    public void remove(int pattern,String word,ArrayList<ThotGrammar> liste,ArrayList<Integer> listePos,ArrayList<Integer> listeText) {
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
        	System.out.println("Pas de texte s�lectionn�");
        }
    	for(int j=0;j<liste.size();j++) {
        	int posmax=this.getText().length();
        	int posmin=0;
        	boolean flag=true;
    		int i=0;
    		while(i<listeText.size()&&flag) {
    			if(listePos.get(j)>listeText.get(i)) {
    				posmin=listeText.get(i);
    			}
    			if(listePos.get(j)<listeText.get(i)) {
    				posmax=listeText.get(i);
    				posmin=listeText.get(i-1);
    				flag=false;
    			}
    			i++;
    		}
    		
    		if(liste.get(j).getTypeEvent()==ThotTypeEvent.Registre) {
    			posmax=liste.get(j).getPosition()+1;
    			posmin=liste.get(j).getPosition()-1;
    		}
    		highlight(allOccurency.get(j),typeEvent.get(j),posmin,posmax);
    	}   
    }
}