/**
* @author Clement Truillet (clement@ctruillet.eu)
* @version 0.1 du 21/05/2019
*/

package fr.irit.elipse.project;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JCheckBox;

public class ThotTable extends JTable{
	//Attributs
	protected ThotTableModel model;
	private ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected List<ThotButtonEditor> listButtonEditor = new ArrayList<ThotButtonEditor>(1);

	//Constructeur
	public ThotTable(ThotTableModel ttm) {
		super(ttm);
		this.liste = new ArrayList<>();
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = ttm;
		
		this.addMouseListener(new MouseAdapter( ) {
			public void mouseClicked(MouseEvent e) {
				System.out.println("Selection de la ligne " + getSelectedRow());
			}
		});
	}
	
	
	//M�thode
	public void add(String mot) {
		ThotGrammar t = new ThotGrammar(mot);
		this.model.add(t);
		liste.add(t);
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t)); //Nouveau Menu d�roulant
		editor.add(dce);
		//pour le bouton
		ThotButtonEditor editorBoutton=new ThotButtonEditor(new JCheckBox(),t);
		listButtonEditor.add(editorBoutton);

		this.fireTableDataChanged();
	}

	
		//Surchage de la m�thode de JTable
		public TableCellEditor getCellEditor(int row, int column) {
			if(row<this.liste.size() && column==1) {
				return editor.get(row);
			}else if (row<this.liste.size() && column==2) {
				return listButtonEditor.get(row);
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
	
	public String toString() {
		String s= "";
		
		for (int i=0;i<this.liste.size(); i++) {
			s+=this.liste.get(i).toString()+"\n";
		}
		
		return s;
	}

}