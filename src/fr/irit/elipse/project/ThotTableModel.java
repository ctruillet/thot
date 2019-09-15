/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 05/01/2019
*/

package fr.irit.elipse.project;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Modele du tableau des mot-balises
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 *
 */
public class ThotTableModel extends AbstractTableModel {
	
	//Attributs
	private static final long serialVersionUID = 1L;
	private static final String[] entetes = { "Mots-Balises", "Type d'\u00e9v\u00e9nement", "Concept associ\u00e9","Description" };
	private ArrayList<ThotGrammar> liste;
	
	//Constructeur
	public ThotTableModel() {
		this.liste = new ArrayList<>();
	}
	
	//Méthode
	
	public int getColumnCount() {
		return entetes.length;
	}
	

	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}
	

	public int getRowCount() {
		return liste.size();
	}

	public String getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0: 
				return ((liste.get(rowIndex)).getMotBalise());
			case 1: 
				return ((liste.get(rowIndex)).getTypeEventName());
			case 2: 
				return ((liste.get(rowIndex)).getConceptName());
			case 3:
				return ((liste.get(rowIndex)).getDescription());
			default:
				return("");
		}
	}

	/**
	 * Méthode toString de la classe ThotTableModel
	 * @return String
	 */
	public String toString(){
		StringBuilder s= new StringBuilder("==========================\n");

		for (int i=0;i<this.liste.size(); i++) {
			s.append(this.liste.get(i).toString()).append("\n");
		}

		return (s+"==========================");
	}

	
	public String getMot(int i){
		return (this.liste.get(i).getMotBalise());
	}

	public String getTypeEvent(int i) {
		return ((this.liste.get(i))).getTypeEventName();
	}

	/**
	 * 
	 * @param i
	 * @return ThotGrammar
	 * @see ThotGrammar
	 */
	public ThotGrammar getMotBalise(int i){
		return (this.liste.get(i));
	}

	/**
	 * 
	 * @param i
	 * @return int
	 */
	public int getPosition(int i){
		return (this.liste.get(i).getPosition());
	}

	/**
	 * 
	 * @param i
	 * @return String
	 */
	public String getConcept(int i){
		return (this.liste.get(i).getConcept());
	}

	/**
	 * 
	 * @param i
	 * @return String
	 */
	public String getDescription(int i){
		return (this.liste.get(i).getDescription());
	}

	/**
	 * 
	 * @return ArrayList<ThotGrammar>
	 * @see ThotGrammar
	 */
	public ArrayList<ThotGrammar> getListe(){
		return (this.liste);
	}

	/**
	 * 
	 * @param t
	 * @see ThotGrammar
	 */
	public void add(ThotGrammar t) {
		liste.add(t);
	}
	
	/**
	 * 
	 * @param i
	 * @param t
	 * @see ThotGrammar
	 */
	public void add(int i,ThotGrammar t) {
		liste.add(i,t);
	}
	
	/**
	 * Suppression d'une ligne
	 * @param num
	 */
	public void remove(int num){
		this.liste.remove(num);
		this.fireTableDataChanged();
	}

	/**
	 * 
	 */
	public void fireEditingStopped() {
		this.fireEditingStopped();
		
	}

}