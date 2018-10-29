package Solveur;

import Problème.*;

public abstract class Solveur<T> {

	//Attributs
	protected ProblemeLineaire<T> probleme;
	protected AlgorithmeIteratif iteratif;
	
	//Constructeur
	public Solveur() {
		// TODO Auto-generated constructor stub
	}
	
	//Methodes
	public abstract T resolution();
}
