package Problème;

public abstract class ProblemeLineaire {

	//attributs
	private Boolean minimisation;
	
	//constructeur
	public ProblemeLineaire() {
		// TODO Auto-generated constructor stub
	}
	
	//Methodes
	public float fonctionObjectif() {	
		return 0.1f;
	}
	
	public void genererSolutionInitiale() {
		
	}
	
	public Boolean solutionValide() {
		return true;
	}

	//getters & setters
	public Boolean getMinimisation() {
		return minimisation;
	}

	public void setMinimisation(Boolean minimisation) {
		this.minimisation = minimisation;
	}
	
	
	
}
