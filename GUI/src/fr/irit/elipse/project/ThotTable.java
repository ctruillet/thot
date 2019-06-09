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
	//enlever word ou mot
	//Attributs
	protected ThotTableModel model;
	private ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected ArrayList<Object> listeConcept= new ArrayList<Object>(1);
	protected ArrayList<Integer> ListeText= new ArrayList<Integer>(1);
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
				System.out.println("la");
				row=getSelectedRow();
				ttm.remove(row);
				liste.remove(row);
				editor.remove(row);
				listeConcept.remove(row);
				text.suppr(row);
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
		int posmax=0;
		int i=0;
		if(ListeText.size()==1) {
			posmax=5000;
		}else {
			while(i<ListeText.size()&&flag) {
				if(position<ListeText.get(i)) {
					posmax=ListeText.get(i);
					flag=false;
				}
				i++;
			}
		}
		System.out.println(liste.toString());
		text.highlight(mot,value,posmax);
	}
	
	public void updateText(ThotTypeEvent value,int rowSelected) {
		word = text.allOccurency.get(rowSelected);
		text.allOccurency.set(rowSelected,word);
		text.typeEvent.set(rowSelected,value);
		text.Remove(rowSelected,word);
		int i=0;
		int posmax=0;
		boolean flag=true;
		if(ListeText.size()==1) {
			posmax=5000;//changer cette valeur prendre la grandeur du texte
		}else {
			
			while(i<ListeText.size()&&flag) {
				if(position<ListeText.get(i)) {
					posmax=ListeText.get(i);
					flag=false;
				}
				i++;
			}
		}

		text.highlight(mot,value,posmax);
	}
	
	//si c'est un registre alors:
	//aussi s'occupé de la suprresion
	public void separation() {
		int i=0;
		boolean flag=true;
		while(i<ListeText.size()&&flag) {
			if(position<ListeText.get(i)) {
				ListeText.add(i,position+mot.length());
				flag=false;
			}
			i++;
		}
		if(flag) {
			ListeText.add(position+mot.length());
		}
		for(int j=0;j<ListeText.size();j++) {
		
		}
		System.out.println(ListeText.toString());
	}
	
	//quand on ajout un resgistre pas seulement faire la séparation aussi faire la mise a jour de tout
	//quand on supprime un registre faire la mise a jour
	//faire une arraylist avec la pos de chaque mot ?utiliser thot grammar? et faire une autre liste avec leur position
	//et avec une boucle for si le registre a changé mettre a jour la liste avec la nouvelle posmax
	//si le mot est un registre tout revoir on sauvegardera la pos donc pas de probléme
	
}