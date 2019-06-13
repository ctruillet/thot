/**
 * @author Clement Truillet (clement.Truillet@univ-tlse3.fr)
 * @version 0.1 du 15/05/2019
 */


package fr.irit.elipse.project;

import javax.swing.*;
import java.awt.*;

public class ThotTextAreaEditor extends DefaultCellEditor {
    protected JScrollPane scrollpane;
    protected JTextArea textarea;
    protected ThotGrammar motBalise;
    protected int column;
    
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

    public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
    	this.column=column;
        textarea.setText((String) value);
        return scrollpane;
    }

    public Object getCellEditorValue() {
    	if(this.column==2) {
    		motBalise.setConcept(new ThotConcept(textarea.getText()));
    	}else {
    		motBalise.setDescription(new ThotDescription(textarea.getText()));
    	}
    	
        return textarea.getText();
    }
}
