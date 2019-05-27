/**
* @author Clement Truillet (clement@ctruillet.eu)
* @version 0.1 du 21/05/2019
*/

package fr.irit.elipse.project;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import javax.swing.JCheckBox;

public class ThotTable extends JTable{
	
	//Attributs
	protected ThotTableModel model;
	private ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected ArrayList listee= new ArrayList();
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
				listee.remove(row);
				System.out.println(Arrays.toString(listee.toArray()));
				Text.suppresion(row);
			}
		};
		
		System.out.println("Tabele1");
		String keyStrokeAndKey = "DELETE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		this.getInputMap().put(keyStroke, keyStrokeAndKey);
		this.getActionMap().put(keyStrokeAndKey, action);

	}


	public void add(String mot) {
		ThotGrammar t = new ThotGrammar(mot);
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
				return (TableCellEditor) listee.get(row);
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