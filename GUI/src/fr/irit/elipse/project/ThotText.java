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

class ThotText extends JTextPane {
    public ThotText() {
        super();
        this.setEditable(false);
    }

    /*public ThotText(String mime, String label){
        super(mime, label);

    }*/

    //throw exeception
    public StringBuffer readText(String nomFichier)
    {
        File fichier = new File(nomFichier);
        StringBuffer texte = new StringBuffer();

        try {
            Scanner lecteur = new Scanner(fichier);
            while (lecteur.hasNext()){

                texte.append(lecteur.nextLine());
                texte.append("\n");
            }
            //voire si il ne faudrait pas mettre un finally a cause du clause !
            lecteur.close();
        }
        catch (FileNotFoundException exc) {
            System.out.println("Fichier inexistant");
        }
        System.out.println(texte);
        return texte;
    }

    public void affichageText(String nomFichier){
        this.setText(new String(readText(nomFichier)));
    }

    public void selection(String mot){
        //String text = this.getText().replaceAll("\\s"+mot+"[\\s.?!]", " **"+mot+"** ");
        //this.setText(text);

    }

    public void modification(String mot,String nomFichier)
    {
        final String chemin = nomFichier;
        final File fichier =new File(chemin);
        //int n = 5;
        try {
            // creation d'un writer (un écrivain)
            final FileWriter writer = new FileWriter(fichier);
            try {
                writer.write(this.getText());
            } finally {

                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de modifier le fichier");
        }
    }
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
    {
        public MyHighlightPainter(Color color)
        {
            super(color);
        }
    }
    public void surligne(String pattern)
    		/*SimpleAttributeSet styleGras = new SimpleAttributeSet();
		StyleConstants.setBold(styleGras, true);
		StyledDocument doc = T_Text.getStyledDocument();
		doc.setCharacterAttributes(0, 50, styleGras, false);*/
    {
        Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.CYAN);

        try
        {

            Highlighter hilite = this.getHighlighter();
            Document doc = this.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            // Search for pattern
            // see I have updated now its not case sensitive
            while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0)
            {
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }
        } catch (BadLocationException e) {
        }
    }

}