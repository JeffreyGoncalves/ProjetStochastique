package Problème;

public class PVC extends ProblemeLineaire {

	//attributs
	private Boolean stochastique;
	private Boolean[][] solutionActuelle;
	private Boolean[][] solutionOptimale;
	
	//constructeur
	public PVC() {
		// TODO Auto-generated constructor stub
	}
	
	//Methodes
	public Boolean[][] genererVoisin(){
		return null;
	}
	public float fonctionObjectif() {	
		return 0.1f;
	}
	
	public void genererSolutionInitiale() {
		
	}
	
	public Boolean solutionValide() {
		return true;
	}

	
	//getters & setters
	public Boolean getStochastique() {
		return stochastique;
	}

	public void setStochastique(Boolean stochastique) {
		this.stochastique = stochastique;
	}

	public Boolean[][] getSolutionActuelle() {
		return solutionActuelle;
	}

	public void setSolutionActuelle(Boolean[][] solutionActuelle) {
		this.solutionActuelle = solutionActuelle;
	}

	public Boolean[][] getSolutionOptimale() {
		return solutionOptimale;
	}

	public void setSolutionOptimale(Boolean[][] solutionOptimale) {
		this.solutionOptimale = solutionOptimale;
	}

	
}
