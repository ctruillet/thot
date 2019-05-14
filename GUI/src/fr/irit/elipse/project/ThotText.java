/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 04/01/2019
*/

package fr.irit.elipse.project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class ThotText extends JEditorPane {
    public ThotText() {
        super();
    }

    public ThotText(String mime, String label){
        super(mime, label);   
		
    }
}