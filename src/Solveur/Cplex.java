package Solveur;

import Données.Donnees;
import ilog.concert.*;
import ilog.cplex.*;

public class Cplex extends Solveur {

	//attributs
	private IloCplex modele;
	private IloObjective objectif;
	private IloIntVar[] varEntieres;
	private IloNumVar[] varQuelconques;
	
	
	//constructeur
	public Cplex() {
		// TODO Auto-generated constructor stub
	}

	//Methodes
	@Override
	public Boolean[][] resolution() {
		// TODO Auto-generated method stub
		return null;
	}

	//getters & setters
	public IloCplex getModele() {
		return modele;
	}

	public void setModele(IloCplex modele) {
		this.modele = modele;
	}

	public IloObjective getObjectif() {
		return objectif;
	}

	public void setObjectif(IloObjective objectif) {
		this.objectif = objectif;
	}

	public IloIntVar[] getVarEntieres() {
		return varEntieres;
	}

	public void setVarEntieres(IloIntVar[] varEntieres) {
		this.varEntieres = varEntieres;
	}

	public IloNumVar[] getVarQuelconques() {
		return varQuelconques;
	}

	public void setVarQuelconques(IloNumVar[] varQuelconques) {
		this.varQuelconques = varQuelconques;
	}
	
	
	
	
}
