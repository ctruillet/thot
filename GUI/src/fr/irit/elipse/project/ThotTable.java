/**
* @author Clement Truillet (clement@ctruillet.eu)
* @version 0.1 du 21/05/2019
*/

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThotTable extends JTable{
	
	//Attributs
	protected ThotTableModel model;
	private ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected ArrayList listeConcept= new ArrayList();
	protected int row;
	protected ThotGrammar t;
	protected ThotText Text;
	
	//Constructeur
	public ThotTable(ThotTableModel ttm, ThotText Text) {
		super(ttm);
		this.Text=Text;
		this.liste = new ArrayList<>();
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = ttm;

		//Suprresion de la ligne en appuyant sur la touche Suprr
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				row=getSelectedRow();
				ttm.remove(row);
				liste.remove(row);
				editor.remove(row);
				listeConcept.remove(row);
				System.out.println(Arrays.toString(listeConcept.toArray()));
				Text.suppresion(row);
			}
		};
		
		String keyStrokeAndKey = "DELETE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		this.getInputMap().put(keyStroke, keyStrokeAndKey);
		this.getActionMap().put(keyStrokeAndKey, action);

	}


	public void add(int position, String mot) {
		ThotGrammar t = new ThotGrammar(position, mot);
		this.model.add(t);
		liste.add(t);
		this.t=t;
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t,this)); //Nouveau Menu déroulant
		editor.add(dce);
		this.fireTableDataChanged();
	}

		//Surchage de la méthode de JTable
	public TableCellEditor getCellEditor(int row, int column) {
		System.out.println("on est ici");
		System.out.println(row);
		if(row<this.liste.size() && column==1) {
			return editor.get(row);
		}else if (row<this.liste.size() && column==2) {
			return (TableCellEditor) listeConcept.get(row);
		}
		return super.getCellEditor(row, column);
	}
	
	//Envoit une alerte au modele quand un evenement a lieu
	public void fireTableDataChanged() {
		this.model.fireTableDataChanged();
	}
	
	public int getRowCount() {
		
		return (this.model.getRowCount());
	}

	public ArrayList<ThotGrammar> getListe(){
		return (this.liste);
	}

	public String toString() {
		String s= "";
		
		for (int i=0;i<this.liste.size(); i++) {
			s+=this.liste.get(i).toString()+"\n";
		}
		
		return s;
	}

}