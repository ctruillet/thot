/**
* @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
* @version 0.7 du 15/09/2019
*/

package fr.irit.elipse.project;

/**
 * Concept du mot-balise
 * @author Clement Truillet (Clement.Truillet@univ-tlse3.fr)
 *
 */
public class ThotConcept {
	//Attributs
	protected String content;
	
	//Constructeur
	public ThotConcept(String content) {
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
	 * Méthode toString de ThotConcept
	 * @return String
	 * @see ThotConcept
	 */
	public String toString() {
		return (this.getContent()==null)?"null": this.getContent();
	}
}
