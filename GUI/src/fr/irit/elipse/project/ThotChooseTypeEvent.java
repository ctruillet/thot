/**
* @author Clement Truillet (clement.truillet@univ-tlse3.fr)
* @version 0.1 du 20/05/2019
*/

package fr.irit.elipse.project;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.*;

public class ThotChooseTypeEvent extends JComboBox implements ActionListener{
	//Attributs
	protected ThotTypeEvent value = ThotTypeEvent.Retranscription;
	
	//Constructeur
	public ThotChooseTypeEvent() {
		super();
		
		this.addActionListener(this);
		for (ThotTypeEvent tte : ThotTypeEvent.values()) {
			this.addItem(tte);
		}

	}
	
	//Méthodes
	public void actionPerformed(ActionEvent e) {
		this.value = (ThotTypeEvent) (this.getSelectedItem());
	}
	
	public ThotTypeEvent getChoice() {
		return (this.value);
	}
	
	public static void main(String[] args) {
		JFrame panel = new JFrame();
		Container contentPane = panel.getContentPane();
		ThotChooseTypeEvent tcte = new ThotChooseTypeEvent();
		contentPane.add(tcte);
		panel.setSize(400, 300);
		panel.setVisible(true);
		System.out.println(tcte.getChoice());
		
	}
}
