package Problème;

public abstract class PVC extends ProblemeLineaire<Boolean[][]> {

	//Attributs
	protected boolean stochastique;
	
	//Constructeur
	public PVC(boolean minimisation) {
		super(minimisation);
	}
	
	//Methodes
	public abstract Boolean[][] genererVoisin();
	
	public abstract float fonctionObjectif(typeSolution type);
	
	public abstract boolean solutionValide();
	
	public void genererSolutionInitiale() {
		// TODO : generer une solution initiale
	}

	//Getters & setters
	public Boolean getStochastique() {
		return stochastique;
	}

	public void setStochastique(Boolean stochastique) {
		this.stochastique = stochastique;
	}
}
