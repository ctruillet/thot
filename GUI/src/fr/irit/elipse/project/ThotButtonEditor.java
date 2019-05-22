package fr.irit.elipse.project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ThotButtonEditor extends DefaultCellEditor{
	private JButton button;

	public ThotButtonEditor(JButton button) {
	    super(new JCheckBox());
	    this.button = button;
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

}
