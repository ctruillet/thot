/**
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 * @version 0.1 du 13/06/2019
 */

package fr.irit.elipse.project;

/**
 * Description du mot-balise
 * @author Antonin Miloudi (miloudi.miloudi@univ-tlse3.fr)
 *
 */
public class ThotDescription {
	//Attributs
	protected String content;
	
	//Constructeur
	public ThotDescription(String content) {
		this.content = content;
	}

	//Méthodes
	
	/**
	 * @param content String
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return String
	 */
	public String getContent() {
		return (this.content);
	}
	
	/**
	 * Méthode toString de ThotDescription
	 * @return String
	 * @see ThotDescription
	 */
	public String toString() {
		return (this.getContent()==null)?"null": this.getContent();
	}

}
