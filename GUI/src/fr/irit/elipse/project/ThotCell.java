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
		this.typeEvent = null;
	}
	
	//Méthodes	
	public boolean hasNoEvent() {
		return (this.typeEvent==null);
	}
	
	public String getMotBalise() {
		return (this.motBalise);
	}
	
	public ThotTypeEvent getTypeEvent() {
		return (this.typeEvent);
	}
	
	public String getTypeEventName() {
		return((this.getTypeEvent()==null) ? "null" : this.getTypeEvent().name());
	}
	
	
	public ThotConcept getConcept() {
		return (this.concept);
	}
	
	public String toString() {
		return (this.getMotBalise() + " : " + this.getTypeEventName() + " & " + this.getConcept());
	}
	
	public void setTypeEvent(ThotTypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}
	
	public void setConcept(ThotConcept concept) {
		this.concept = concept;
	}
	
	public void setMotBalise(String motBalise) {
		this.motBalise = motBalise;
	}
	
	
	public static void main(String[] args) {
		ThotCell tc = new ThotCell("le pommier du renard");
		System.out.println(tc.toString() + " + " + tc.hasNoEvent());
		tc.setTypeEvent(ThotTypeEvent.Media);
		System.out.println(tc.toString());
		ThotConcept tc2 = new ThotConcept("corbeau");
		tc.setConcept(tc2);
		tc.setMotBalise("Petit Prince");
		System.out.println(tc.toString() + " + " + tc.hasNoEvent());
	}
	
}
