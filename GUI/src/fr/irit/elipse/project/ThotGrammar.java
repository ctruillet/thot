/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 05/01/2019
*/

package fr.irit.elipse.project;

public class ThotGrammar{
	//Attributs
	protected String motBalise;
	protected ThotTypeEvent typeEvent; 
	protected ThotConcept concept = new ThotConcept("");
	
	//Constructeurs
	public ThotGrammar() {
		this("");
	}
	
	public ThotGrammar(String mot){
		this.motBalise = mot;
		this.typeEvent = null;
	}
	
	//Méthodes	
	public boolean hasNoEvent() {
		return (this.typeEvent==null);
	}
	
	public void setMotBalise(String motBalise) {
		this.motBalise = motBalise;
	}
	
	public void setTypeEvent(ThotTypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}
	
	public void setConcept(ThotConcept concept) {
		this.concept = concept;
	}	
	
	public String getMotBalise() {
		return (this.motBalise);
	}
		
	public ThotTypeEvent getTypeEvent() {
		return (this.typeEvent);
	}

	public String getTypeEventName() {
		return((this.getTypeEvent()==null) ? "" : this.getTypeEvent().name());
	}


	public String getConcept() {

		return (this.concept.getContent());
	}

	public String getConceptName() {
		return ((this.getConcept()==null) ? "" : this.getConcept());
	}
		
	
	public String toString() {
		return (this.getMotBalise() + " : " + this.getTypeEventName() + " & " + this.getConcept());
	}
	
	public static void main(String[] args) {
		ThotGrammar tc = new ThotGrammar("l'abricot du renard");
		System.out.println(tc.toString() + " + " + tc.hasNoEvent());
		tc.setTypeEvent(ThotTypeEvent.Media);
		System.out.println(tc.toString());
		ThotConcept tc2 = new ThotConcept("corbeau");
		tc.setConcept(tc2);
		tc.setMotBalise("Petit Prince");
		System.out.println(tc.toString() + " + " + tc.hasNoEvent());
		
		ThotGrammar tc3 = new ThotGrammar();
		System.out.println(tc3.toString());
	}
}