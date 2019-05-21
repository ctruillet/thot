/**
* @author Clement Truillet (clement@ctruillet.eu)
* @version 0.1 du 21/05/2019
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

		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t)); //Nouveau Menu déroulant
		editor.add(dce);
		this.fireTableDataChanged();
	}
	
		//Surchage de la méthode de JTable
	public TableCellEditor getCellEditor(int row, int column) { 
		if(row<this.liste.size() && column==1) {
			return editor.get(row);
		}
		return super.getCellEditor(row, column);
	}
	
		//Envoit une alerte au modele quand un evenement a lieu
	public void fireTableDataChanged() {
		this.model.fireTableDataChanged();
	}
	
	public String toString() {
		String s= "";
		
		for (int i=0;i<this.liste.size(); i++) {
			s+=this.liste.get(i).toString()+"\n";
		}
		
		return s;
	}
}