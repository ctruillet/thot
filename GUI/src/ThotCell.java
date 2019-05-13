/**
* @author Clement Truillet (clement.Truillet@univ-tlse3.fr)
* @version 0.1 du 13/05/2019
*/


/**
*  Cellule contenant les informations liées à un mot-balise
*/


package fr.irit.elipse.project;

public class ThotCell {
	//Attributs
	protected String motBalise;
	protected ThotTypeEvent typeEvent;
	protected ThotConcept concept;
	
	//Constructeur
	public ThotCell(String motBalise) {
		this.motBalise = motBalise;
	}
	
	//Méthodes	
	public String getMotBalise() {
		return (this.motBalise);
	}
	
	public ThotTypeEvent getTypeEvent() {
		return (this.typeEvent);
	}
	
	public ThotConcept getConcept() {
		return (this.concept);
	}
	
	public String toString() {
		return (this.getMotBalise() + " : " + this.getTypeEvent().name() + " & " + this.getConcept());
	}
	
	public void setTypeEvent(ThotTypeEvent typeEvent, String content) {
		this.typeEvent = new ThotCell(typeEvent, content);
	}
	
	public void setConcept(ThotConcept concept) {
		this.concept = concept;
	}
	
	
}
