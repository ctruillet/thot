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
	protected String mot;
	protected int position;
	protected String Word;
	
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
				System.out.println("la");
				row=getSelectedRow();
				ttm.remove(row);
				liste.remove(row);
				editor.remove(row);
				listeConcept.remove(row);
				System.out.println("aa"+Arrays.toString(listeConcept.toArray()));
				Text.suppr(row);
			}
		};
		
		String keyStrokeAndKey = "DELETE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		this.getInputMap().put(keyStroke, keyStrokeAndKey);
		this.getActionMap().put(keyStrokeAndKey, action);
	}

	public void add(int position, String mot) {
		this.position=position;
		boolean flag=true;
		ThotGrammar t = new ThotGrammar(position, mot);
		this.t=t;
		this.mot=mot;
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t,this)); //Nouveau Menu déroulant
		System.out.println("avant la boucle");
		int i=0;
		while((i<this.liste.size()) && flag) {
			System.out.println("Au debut de la boucle");
			if(t.getPosition()<liste.get(i).getPosition()) {
				System.out.println("ici");
				liste.add(i, t);
				this.model.add(i,t);
				editor.add(i, dce);
				System.out.println("je fais:"+listeConcept.size());
				listeConcept.add(i, listeConcept.get(listeConcept.size()-1));
				listeConcept.remove(listeConcept.size()-1);
				System.out.println(Arrays.toString(listeConcept.toArray()));
				Text.allOccurency.add(i, Text.allOccurency.get(Text.allOccurency.size()-1));
				Text.allOccurency.remove(Text.allOccurency.size()-1);
				Text.typeEvent.add(i, Text.typeEvent.get(Text.typeEvent.size()-1));
				Text.typeEvent.remove(Text.typeEvent.size()-1);
				System.out.println("allocurrency "+Text.allOccurency);
				System.out.println("typevent "+Text.typeEvent);
				System.out.println("1er");
				flag=false;
				
			}
			i++;
		}
		if(flag==true) {
			this.model.add(t);
			liste.add(t);
			editor.add(dce);
		}
		
		this.fireTableDataChanged();
	}

		//Surchage de la méthode de JTable
	public TableCellEditor getCellEditor(int row, int column) {

		
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
	
	public int SelectedRow() {
		return this.getSelectedRow();
	}
	
	public void append(ThotTypeEvent value) {
		Text.typeEvent.add(value);
		Text.highlight(mot,value);
	}
	
	public void updateText(ThotTypeEvent value,int rowSelected) {
		Word=Text.allOccurency.get(rowSelected);
		Text.allOccurency.set(rowSelected,Word);
		Text.typeEvent.set(rowSelected,value);
		Text.Remove(rowSelected,Word);
		Text.highlight(mot,value);
	}
}