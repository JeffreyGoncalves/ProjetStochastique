package Problème;

import Données.*;

public abstract class ProblemeLineaire<T> {

	//Attributs
	protected boolean minimisation;
	protected Donnees donnees;
	protected T solutionActuelle;
	protected T solutionTemporaire;
	protected T solutionOptimale;
	
	public enum typeSolution {
		actuelle, optimale, temporaire
	}
	
	//Constructeur
	public ProblemeLineaire(boolean minimisation) {
		this.minimisation = minimisation;
	}
	
	//Methodes
	public abstract T genererVoisin();
	
	public abstract float fonctionObjectif(typeSolution type);
	
	public abstract void genererSolutionInitiale();
	
	public abstract boolean solutionValide(T solution);

	//Getters & setters
	public boolean getMinimisation() {
		return minimisation;
	}
	
	public T getSolutionActuelle() {
		return solutionActuelle;
	}

	public void setSolutionActuelle(T solutionActuelle) {
		this.solutionActuelle = solutionActuelle;
	}
	
	public T getSolutionTemporaire() {
		return solutionTemporaire;
	}

	public void setSolutionTemporaire(T solutionTemporaire) {
		this.solutionTemporaire = solutionTemporaire;
	}

	public T getSolutionOptimale() {
		return solutionOptimale;
	}

	public void setSolutionOptimale(T solutionOptimale) {
		this.solutionOptimale = solutionOptimale;
	}
	
	public Donnees getDonnees() {
		return donnees;
	}
	
	public void setDonnees(Donnees donnees) {
		this.donnees = donnees;
	}
}
