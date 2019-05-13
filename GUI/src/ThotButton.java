/**
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 04/01/2019
*/

package fr.irit.elipse.project;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class ThotButton extends JButton {
    public ThotButton() {
        super();
    }

    public ThotButton(String label){
        super(label);
        Font f = new Font("B612-Regular",Font.BOLD, 10);
        this.setFont(f);        
    }
}