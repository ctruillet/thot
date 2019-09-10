/**
* @author Clement Truillet (clement.Truillet@univ-tlse3.fr)
* @version 0.1 du 13/05/2019
*/

package fr.irit.elipse.project;

public class ThotConcept {
	//Attributs
	protected String content;
	
	//Constructeur
	public ThotConcept(String content) {
		this.content = content;
	}

	//Méthodes
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return (this.content);
	}
	
	public String toString() {
		return (this.getContent()==null)?"null": this.getContent();
	}
}
