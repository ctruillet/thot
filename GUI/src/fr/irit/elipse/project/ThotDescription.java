/**
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @version 0.1 du 13/06/2019
 */

package fr.irit.elipse.project;

public class ThotDescription {
	//Attributs
	protected String content;
	
	//Constructeur
	public ThotDescription(String content) {
		this.content = content;
	}

	//M�thodes
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
