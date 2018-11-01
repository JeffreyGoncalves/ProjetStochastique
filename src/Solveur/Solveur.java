package Solveur;

import Probl�me.*;

public abstract class Solveur<T> {

	//Attributs
	protected ProblemeLineaire<T> probleme;
	protected AlgorithmeIteratif iteratif;
	
	//Constructeur
	public Solveur(ProblemeLineaire<T> probleme) {
		this.probleme = probleme;
	}
	
	//Methodes
	public abstract T resolution();
}
