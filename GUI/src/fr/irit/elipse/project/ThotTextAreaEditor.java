/**
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 * @version 0.7 du 15/09/2019
 * @see DefaultCellEditor
 */


package fr.irit.elipse.project;

import javax.swing.*;
import java.awt.*;

/**
 * TextArea 
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 *
 */
public class ThotTextAreaEditor extends DefaultCellEditor {
	//Attributs
    protected JScrollPane scrollpane;
    protected JTextArea textarea;
    protected ThotGrammar motBalise;
    protected int column;
    
    //Constructeur
    /**
     * 
     * @param checkBox
     * @param motBalise
     */
    public ThotTextAreaEditor(JCheckBox checkBox,ThotGrammar motBalise) {
        super(new JCheckBox());
        scrollpane = new JScrollPane();
        textarea = new JTextArea();
        this.motBalise=motBalise;
        this.textarea.setText(motBalise.getConcept());
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        scrollpane.getViewport().add(textarea);
    }

    //Méthodes
    
	/**
	 * @return Component
	 * @param table JTable
	 * @param value Object
	 * @param isSelected boolean
	 * @param row int
	 * @param column int
	 */
    public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {

    	this.column=column;
        textarea.setText((String) value);
        return scrollpane;
    }
    
	/**
	 * @return Object
	 */
    public Object getCellEditorValue() {

    	if(this.column==2) {
    		motBalise.setConcept(new ThotConcept(textarea.getText()));
    	}else {
    		motBalise.setDescription(new ThotDescription(textarea.getText()));
    	}
    	
        return textarea.getText();
    }
}
