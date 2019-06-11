/**
* @author Clement Truillet (clement.truillet@univ-tlse3.fr)
* @version 0.1 du 20/05/2019
*/

package fr.irit.elipse.project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThotChooseTypeEvent extends JComboBox implements ActionListener{
	//Attributs
	protected ThotTypeEvent value = ThotTypeEvent.Retranscription;
	protected ThotGrammar motBalise;
	protected ThotTable Table;
	protected JCheckBox checkbox =new JCheckBox();
	protected ThotTypeEvent etat;
	protected ThotConcept concept;
	
	//Constructeur
	public ThotChooseTypeEvent(ThotGrammar motBalise,ThotTable Table) {
		super();
		this.Table=Table;
		this.motBalise = motBalise;
		this.addActionListener(this);
		checkbox=new JCheckBox();
		concept=new ThotConcept("");
		ThotTextAreaEditor editorText = new ThotTextAreaEditor(checkbox,motBalise);
		Table.listeConcept.add(editorText);
		etat=this.value;
		Table.append(etat);
		Table.listePos.add(Table.position);
		Table.fireTableDataChanged();
		for (ThotTypeEvent tte : ThotTypeEvent.values()) {
			this.addItem(tte);
		}
	}
	
	//Méthodes
	public void actionPerformed(ActionEvent e) {
		this.value = (ThotTypeEvent) (this.getSelectedItem());
		this.motBalise.setTypeEvent(this.value);
		if(Table.getSelectedRow()!=-1 && (etat!=this.value)) {
			motBalise.setConcept(concept);
			
			if(this.value==ThotTypeEvent.Registre) {
				Table.separation();
			}
			if(etat==ThotTypeEvent.Registre) {
				int pp=Table.liste.get(Table.getSelectedRow()).getPosition();
				Table.ListeText.remove(Table.ListeText.indexOf(pp));
			}
			if (this.value.toString()==ThotTypeEvent.Media.toString()){
				ThotButtonEditor editorBoutton=new ThotButtonEditor(checkbox,motBalise);
				Table.listeConcept.set(Table.getSelectedRow(),editorBoutton);
				etat=this.value;
			}else {
				ThotTextAreaEditor editorText = new ThotTextAreaEditor(checkbox,motBalise);
				Table.listeConcept.set(Table.getSelectedRow(),editorText);
				etat=this.value;
			}
			Table.updateText(this.value,Table.getSelectedRow());

			
		}
		Table.fireTableDataChanged();
	}
	
	public ThotTypeEvent getChoice() {
		return (this.value);
	}
}