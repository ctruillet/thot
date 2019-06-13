package fr.irit.elipse.project;

public class ThotDescription {
	//Attributs
	protected String content;
	
	//Constructeur
	public ThotDescription(String content) {
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
		return (this.getContent()==null)?"null":this.getContent().toString();
	}

}
