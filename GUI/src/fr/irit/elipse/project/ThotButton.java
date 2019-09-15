/**
 *  
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 04/01/2019
*/

package fr.irit.elipse.project;

import javax.swing.*;
import java.awt.*;

/**
 * Button
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 * 
 */
class ThotButton extends JButton {
	
	//Constructeur
	public ThotButton() {
        super();
    }
	//Methode
	
	/**
	 * Retourne le label du bouton
	* @return label 
	*/
    public ThotButton(String label){
        super(label);
        Font f = new Font("B612-Regular",Font.BOLD, 10);
        this.setFont(f);
    }
}