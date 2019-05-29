package fr.irit.elipse.project;

import java.awt.*;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class ThotButtonEditor extends DefaultCellEditor implements ActionListener{

	//attribut
	protected JButton button;
	protected String label;
	protected Boolean isPushed;
	protected String directory;
	protected ThotConcept value;
	protected ThotGrammar motBalise;
	protected ThotTable table;

	//constructeur
	public ThotButtonEditor(JCheckBox jCheckBox, ThotGrammar motBalise) {
		super(new JCheckBox());
		this.button = new JButton();
		this.motBalise = motBalise;
		this.button.setLabel(motBalise.getConcept());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ThotImportFrame tif = new ThotImportFrame(getParentDirectory());
				try {
					Component contentPane=getContentPane();
					tif.openMedia(contentPane);
					if(tif.getFilePath()!=null) {
						label=tif.getFileName();
						button.setText(label);
						motBalise.setConcept(new ThotConcept(label));
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		jCheckBox.setOpaque(true);
		jCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}
	//méthode
	protected Component getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {

		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else{
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value ==null) ? "" : value.toString();
		isPushed = true;
		return button;
	}

	public Object getCellEditorValue() {
		if (isPushed)  {
			JOptionPane.showMessageDialog(button ,label + ": Ouch!");
		}
		isPushed = false;
		return new String(label) ;
	}

	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}


	public void actionPerformed(ActionEvent e) {

	}


	public String getParentDirectory() {
		return (this.directory);
	}

}
