/**
* @author Philippe Truillet (Philippe.Truillet@irit.fr)
* @version 0.1 du 05/01/2019
*/

package fr.irit.elipse.project;

public class ThotGrammar{
	//Attributs
	protected int position;
	protected String motBalise;
	protected ThotTypeEvent typeEvent; 
	protected ThotConcept concept = new ThotConcept("");
	
	//Constructeurs
	public ThotGrammar(int position) {
		this(position, "");
	}
	
	public ThotGrammar(int position, String mot){
		this.position = position;
		this.motBalise = mot;
		this.typeEvent = null;
	}

	public ThotGrammar(int position, String mot, ThotTypeEvent typeEvent){
		this(position,mot);
		this.setTypeEvent(typeEvent);
	}

	public ThotGrammar(int position, String mot, ThotTypeEvent typeEvent, String concept){
		this(position,mot, typeEvent);
		this.setConcept(new ThotConcept(concept));
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

	public int getPosition() {
		return (this.position);
	}
		
	
	public String toString() {
		return (this.getMotBalise() + " - " + this.getTypeEventName() + " : " + this.getConcept() );
	}
}