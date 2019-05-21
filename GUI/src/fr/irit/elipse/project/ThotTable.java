/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 05/01/2019
*/

package fr.irit.elipse.project;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.util.*;

public class ThotTable extends JTable {
	//Attributs
	protected ThotTableModel model;
	private ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	
	//Constructeur
	public ThotTable(ThotTableModel ttm) {
		super(ttm);
		this.liste = new ArrayList<>();
		this.setAutoCreateRowSorter(true);
		this.model = ttm;
	}
	
	
	//Méthode
	public void add(String mot) {
		ThotGrammar t = new ThotGrammar(mot);
		this.model.add(t);
		liste.add(t);
		System.out.println(this.getValueAt(this.liste.size()-1, 1));
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t));
		editor.add(dce);
		//TableColumn typeEventColumn = this.getColumnModel().getColumn(1);
		//typeEventColumn.setCellEditor(new DefaultCellEditor(new ThotChooseTypeEvent(t)));
		
		for (int i=0; i<liste.size(); i++) {
			System.out.println(this.liste.get(i).toString());
		}
	}
	
	public TableCellEditor getCellEditor(int row, int column) {
		if(row<this.liste.size()) {
			return editor.get(row);
		}
		return super.getCellEditor(row, column);
	}
	
	public void fireTableDataChanged() {
		this.model.fireTableDataChanged();
	}
}