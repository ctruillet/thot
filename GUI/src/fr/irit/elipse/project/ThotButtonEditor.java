/**
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @version 0.1 du 13/06/2019
 * @see DefaultCellEditor
 */

package fr.irit.elipse.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Editeur de bouton 
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 *
 */
public class ThotButtonEditor extends DefaultCellEditor implements ActionListener{

	//attributs
	protected JButton button;
	protected String label;
	protected Boolean isPushed;
	protected String directory;
	protected ThotConcept value;
	protected ThotGrammar motBalise;
	protected ThotTable table;

	//constructeur
	@SuppressWarnings("deprecation")
	public ThotButtonEditor(JCheckBox jCheckBox, ThotGrammar motBalise) {
		super(new JCheckBox());
		this.button = new JButton();
		this.motBalise = motBalise;
		this.button.setLabel(motBalise.getConcept());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ThotImportFrame tif = new ThotImportFrame(getParentDirectory());
				tif.setImportType(ThotImportFrame.importType.MEDIA);
				try {
					Component contentPane=getContentPane();
					tif.openFrame(contentPane);
					if(tif.getFilePath()!=null) {
						label=tif.getFileName();
						button.setText(label);
						motBalise.setConcept(new ThotConcept(label));
					}

				} catch (Exception e1) {
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
	//méthodes
	protected Component getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param table JTable
	 * @param value Object
	 * @param isSelected boolean
	 * @param row int
	 * @param column int
	 * @return Component
	 */
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

	/**
	 * Renvoit la valeur de la cellule
	 * @return Object
	 */
	public Object getCellEditorValue() {
		if (isPushed)  {
			JOptionPane.showMessageDialog(button ,label + ": Ouch!");
		}
		isPushed = false;
		return label;
	}

	/**
	 * @return boolean
	 */
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	/**
	 * 
	 */
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}


	/**
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {

	}


	/**
	 * 
	 * @return String
	 */
	public String getParentDirectory() {
		return (this.directory);
	}

}
