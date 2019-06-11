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
        	System.out.println("Pas de texte sélectionné");
        }
    }

    public void suppr(int pattern,ArrayList<ThotGrammar> liste,ArrayList<Integer> listePos,ArrayList<Integer> ListeText) {
    	word=allOccurency.get(pattern);
    	if(pattern>=0) {
            allOccurency.remove(allOccurency.get(pattern));
            typeEvent.remove(typeEvent.get(pattern));
    	}
    	Remove(pattern,word,liste,listePos,ListeText);
    }
    
    public void Remove(int pattern,String word,ArrayList<ThotGrammar> liste,ArrayList<Integer> listePos,ArrayList<Integer> ListeText) {
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
    	System.out.println("on va ici normalement"+ListeText.toString());
    	for(int j=0;j<liste.size();j++) {
        	int posmax=5000;
        	int posmin=0;
        	boolean flag=true;
    		int i=0;
    		while(i<ListeText.size()&&flag) {
    			if(listePos.get(j)>ListeText.get(i)) {
    				posmin=ListeText.get(i);
    			}
    			if(listePos.get(j)<ListeText.get(i)) {
    				posmax=ListeText.get(i);
    				posmin=ListeText.get(i-1);
    				flag=false;
    			}
    			i++;
    		}
    		
    		if(flag) {
    			posmax=5000;//mettre la bonne valeur ici aussi
    		}
    		
    		if(liste.get(j).getTypeEvent()==ThotTypeEvent.Registre) {
    			posmax=liste.get(j).getPosition()+1;
    			posmin=liste.get(j).getPosition()-1;
    			System.out.println("la position est :"+posmin);
    		}
    		highlight(allOccurency.get(j),typeEvent.get(j),posmin,posmax);
    	}
        /*for(int i=0;i<allOccurency.size();i++) {
        	highlight(allOccurency.get(i),typeEvent.get(i),posmax);
        }*/
        
        System.out.println(allOccurency.toString());

    }
}