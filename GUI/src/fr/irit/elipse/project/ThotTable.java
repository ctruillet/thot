/**
* @author Clement Truillet (clement@ctruillet.eu)
* @version 0.1 du 21/05/2019
*/

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ThotTable extends JTable{
	//enlever word ou mot
	//Attributs
	protected ThotTableModel model;
	protected ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected ArrayList<Object> listeConcept = new ArrayList<Object>(1);
	protected ArrayList<Integer> listePos = new ArrayList<Integer>(1);
	protected ArrayList<Integer> ListeText = new ArrayList<Integer>(1);
	protected int row;
	protected ThotGrammar t;
	protected ThotText text;
	protected String mot;
	protected int position;
	protected String word;
	
	//Constructeur
	public ThotTable(ThotTableModel ttm, ThotText text) {
		super(ttm);
		this.text=text;
		this.liste = new ArrayList<>();
		this.setAutoCreateRowSorter(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.model = ttm;

		//Suprresion de la ligne en appuyant sur la touche Suprr
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				row=getSelectedRow();
				if(liste.get(row).getTypeEvent()==ThotTypeEvent.Registre) {
					int pp=liste.get(row).getPosition();
					ListeText.remove((Integer) pp);
				}
				ttm.remove(row);
				liste.remove(row);
				editor.remove(row);
				listePos.remove(row);
				listeConcept.remove(row);
				text.suppr(row,liste,listePos,ListeText);
			}
		};
		String keyStrokeAndKey = "DELETE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		this.getInputMap().put(keyStroke, keyStrokeAndKey);
		this.getActionMap().put(keyStrokeAndKey, action);
		ListeText.add(0);
	}

	public void add(int position, String mot) {
		this.position=position;
		boolean flag=true;
		ThotGrammar t = new ThotGrammar(position, mot);
		this.t=t;
		this.mot=mot;
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t,this)); //Nouveau Menu déroulant
		int i=0;
		while((i<this.liste.size()) && flag) {
			if(t.getPosition()<liste.get(i).getPosition()) {
				liste.add(i, t);
				this.model.add(i,t);
				editor.add(i, dce);
				listeConcept.add(i, listeConcept.get(listeConcept.size()-1));
				listeConcept.remove(listeConcept.size()-1);
				listePos.add(i, listePos.get(listePos.size()-1));
				listePos.remove(listePos.size()-1);
				text.allOccurency.add(i, text.allOccurency.get(text.allOccurency.size()-1));
				text.allOccurency.remove(text.allOccurency.size()-1);
				text.typeEvent.add(i, text.typeEvent.get(text.typeEvent.size()-1));
				text.typeEvent.remove(text.typeEvent.size()-1);
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
		text.typeEvent.add(value);
		boolean flag=true;
		int posmax=text.getText().length();
		int posmin=0;
		int i=0;

		while(i<ListeText.size()&&flag) {
			if(position>ListeText.get(i)) {
				posmin=ListeText.get(i);
			}
			if(position<ListeText.get(i)) {
				posmax=ListeText.get(i);
				posmin=ListeText.get(i-1);
				flag=false;
					
			}
			i++;
		}
		
		if(flag) {
			posmax=text.getText().length();//mettre la bonne valeur ici aussi
		}
		
		text.highlight(mot,value,posmin,posmax);
	}
	
	public void updateText(ThotTypeEvent value,int rowSelected) {
		word = text.allOccurency.get(rowSelected);
		text.allOccurency.set(rowSelected,word);
		text.typeEvent.set(rowSelected,value);
		text.Remove(rowSelected,word,liste,listePos,ListeText);
	}
	
	//si c'est un registre alors:
	//aussi s'occupé de la suprresion
	public void separation() {
		int i=0;
		boolean flag=true;
		System.out.println("la position ajouté est : fin normalmeent"+listePos.get(SelectedRow()));
		while(i<ListeText.size()&&flag) {
			if(listePos.get(SelectedRow())<ListeText.get(i)) {
				ListeText.add(i,listePos.get(SelectedRow()));
				flag=false;
			}
			i++;
		}
		if(flag) {
			ListeText.add(listePos.get(SelectedRow()));
		}
	}
}