/**
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @version 0.3 du 13/06/2019
 */

package fr.irit.elipse.project;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Tableau des mot-balises
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @see JTable
 * @see ThotGrammar
 *
 */
public class ThotTable extends JTable{
	//Attributs
	protected ThotTableModel model;
	protected ArrayList<ThotGrammar> liste;
	protected List<TableCellEditor> editor = new ArrayList<TableCellEditor>(1);
	protected ArrayList<ThotTextAreaEditor> listeConcept = new ArrayList<ThotTextAreaEditor>(1);
	protected ArrayList<ThotTextAreaEditor> listeDescription = new ArrayList<ThotTextAreaEditor>(1);
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
				listeDescription.remove(row);
				text.suppr(row,liste,listePos,ListeText);
			}
		};
		String keyStrokeAndKey = "DELETE";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		this.getInputMap().put(keyStroke, keyStrokeAndKey);
		this.getActionMap().put(keyStrokeAndKey, action);
		ListeText.add(0);
	}

	/**
	 * Ajoute un mot dans le tableau
	 * @param position
	 * @param mot
	 */
	public void add(int position, String mot) {
		this.position=position;
		boolean flag=true;
		ThotGrammar t = new ThotGrammar(position, mot);
		this.t=t;
		this.mot=mot;
		DefaultCellEditor  dce = new DefaultCellEditor (new ThotChooseTypeEvent(t,this)); //Nouveau Menu d�roulant
		int i=0;
		while((i<this.liste.size()) && flag) {
			if(t.getPosition()<liste.get(i).getPosition()) {
				liste.add(i, t);
				this.model.add(i,t);
				editor.add(i, dce);
				listeConcept.add(i, listeConcept.get(listeConcept.size()-1));
				listeConcept.remove(listeConcept.size()-1);
				listeDescription.add(i, listeDescription.get(listeDescription.size()-1));
				listeDescription.remove(listeDescription.size()-1);
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

	/**
	 * Surchage de la méthode de JTable
	 * @return TableCellEditor
	 * @param row 
	 * @param column 
	 */

	public TableCellEditor getCellEditor(int row, int column) {	
		if(row<this.liste.size() && column==1) {
			return editor.get(row);
		}else if (row<this.liste.size() && column==2) {
			return listeConcept.get(row);
		}else if (row<this.liste.size() && column==3) {
			return listeDescription.get(row);
		}
		return super.getCellEditor(row, column);
	}
	
	/**
	 * Envoi une alerte au modele quand un evenement a lieu
	 */
	public void fireTableDataChanged() {
		this.model.fireTableDataChanged();
	}
	
	
	public int getRowCount() {
		return (this.model.getRowCount());
	}

	/**
	 * Renvoit la liste des ThotGrammar
	 * @return ArrayList<ThotGrammar>
	 * @see ThotGrammar
	 */
	public ArrayList<ThotGrammar> getListe(){
		return (this.liste);
	}

	/**
	 * Méthode toString de ThotTable
	 * @see ThotTable
	 * @return String
	 */
	public String toString() {
		StringBuilder s= new StringBuilder();
		
		for (int i=0;i<this.liste.size(); i++) {
			s.append(this.liste.get(i).toString()).append("\n");
		}
		return s.toString();
	}
	
	/**
	 * Retourne la ligne selectionnée
	 * @return int
	 */
	public int SelectedRow() {
		return this.getSelectedRow();
	}
	
	/**
	 * 
	 * @param value
	 * @see ThotTypeEvent
	 */
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
		
		text.highlight(mot,value,posmin,posmax);
	}
	
	
	/**
	 * @see ThotTypeEvent
	 * @param value
	 * @param rowSelected
	 */
	public void updateText(ThotTypeEvent value,int rowSelected) {
		word = text.allOccurency.get(rowSelected);
		text.allOccurency.set(rowSelected,word);
		text.typeEvent.set(rowSelected,value);
		text.remove(rowSelected,word,liste,listePos,ListeText);
	}
	
	/**
	 * 
	 */
	public void separation() {
		int i=0;
		boolean flag=true;
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