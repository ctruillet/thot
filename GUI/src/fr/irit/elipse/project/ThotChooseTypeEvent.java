/**
* @author Clement Truillet (clement.truillet@univ-tlse3.fr)
* @version 0.1 du 20/05/2019
*/

package fr.irit.elipse.project;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ThotChooseTypeEvent extends JComboBox implements ActionListener{
	//Attributs
	protected ThotTypeEvent value = ThotTypeEvent.Retranscription;
	protected ThotGrammar motBalise;
	protected ThotTable Table;
	protected boolean flag;
	
	//Constructeur
	public ThotChooseTypeEvent(ThotGrammar motBalise,ThotTable Table) {
		super();
		this.Table=Table;
		this.motBalise = motBalise;
		this.addActionListener(this);
		flag=true;
		for (ThotTypeEvent tte : ThotTypeEvent.values()) {
			this.addItem(tte);
		}
		ThotTextAreaEditor editorText = new ThotTextAreaEditor(new JCheckBox(),motBalise);
		Table.listee.add(Table.position, editorText);
		Table.fireTableDataChanged();
		
	}
	
	//Méthodes
	public void actionPerformed(ActionEvent e) {
		
		this.value = (ThotTypeEvent) (this.getSelectedItem());
		this.motBalise.setTypeEvent(this.value);
		System.out.println(this.value);
		System.out.println("ici");
		if(flag) {
			flag=false;
			Table.test();
		}else if(this.value.toString()==ThotTypeEvent.Retranscription.toString()){
			ThotTextAreaEditor editorText = new ThotTextAreaEditor(new JCheckBox(),motBalise);
			Table.listee.set(Table.position,editorText);
		}
		else {
			Table.update(this.value);
		}
		
	}
	
	public ThotTypeEvent getChoice() {
		return (this.value);
	}
	
}
