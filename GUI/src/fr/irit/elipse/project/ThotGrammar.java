/**
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 * @author Clement Truillet (clement.truillet@univ-tlse3.fr)
 * @version 0.3 du 05/06/2019
 */

package fr.irit.elipse.project;

/**
 * Classe representant un mot-balise
 * @author Philippe Truillet (Philippe.Truillet@irit.fr)
 * @author Clement Truillet (clement.truillet@univ-tlse3.fr)
 *
 */
public class ThotGrammar{
	//Attributs
	protected int position;
	protected String motBalise;
	protected ThotDescription description=new ThotDescription("");
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
	
	public ThotGrammar(int position, String mot, ThotTypeEvent typeEvent, String concept,String description){
		this(position,mot, typeEvent,concept);
		this.setDescription(new ThotDescription(description));
	}

	//Méthodess
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean hasNoEvent() {
		return (this.typeEvent==null);
	}
	
	/**
	 * 
	 * @param motBalise
	 */
	public void setMotBalise(String motBalise) {
		this.motBalise = motBalise;
	}
	
	/**
	 * @see ThotDescription
	 * @param description
	 */
	public void setDescription(ThotDescription description) {
		this.description = description;
	}
	
	/**
	 * 
	 * @param typeEvent
	 * @see ThotTypeEvent
	 */
	public void setTypeEvent(ThotTypeEvent typeEvent) {
		this.typeEvent = typeEvent;
	}
	
	/**
	 * @see ThotConcept
	 * @param concept
	 */
	public void setConcept(ThotConcept concept) {
		this.concept = concept;
	}	
	
	/**
	 * 
	 * @return String
	 */
	public String getMotBalise() {
		return (this.motBalise);
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getDescription() {
		return (this.description.getContent());
	}
		
	/**
	 * 
	 * @return ThotTypeEvent
	 * @see ThotTypeEvent
	 */
	public ThotTypeEvent getTypeEvent() {
		return (this.typeEvent);
	}

	/**
	 * 
	 * @return String
	 */
	public String getTypeEventName() {
		return((this.getTypeEvent()==null) ? "" : this.getTypeEvent().name());
	}

	/**
	 * 
	 * @return String
	 */
	public String getConcept() {
		return (this.concept.getContent());
	}

	/**
	 * 
	 * @return String
	 */
	public String getConceptName() {
		return ((this.getConcept()==null) ? "" : this.getConcept());
	}

	/**
	 * 
	 * @return int
	 */
	public int getPosition() {
		return (this.position);
	}
		
	/**
	 * @return String
	 */
	public String toString() {
		return (this.getMotBalise() + " - " + this.getTypeEventName() + " : " + this.getConcept() );
	}
}