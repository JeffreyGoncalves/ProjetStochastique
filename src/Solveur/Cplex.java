package Solveur;

import java.util.ArrayList;

import Problème.*;
import Données.*;
import ilog.concert.*;
import ilog.cplex.*;

public class Cplex extends Solveur<Integer[]> {

	//Attributs
	private IloCplex modele;
	private IloLinearNumExpr objectif;
	private IloNumVar[][] x;
	private int nbCities;
	
	//Constructeur
	public Cplex(ProblemeLineaire<Integer[]> probleme, int sizeData) {
		super(probleme);
		nbCities = sizeData;
		
		try {
			
			modele = new IloCplex();
	    	modele.setParam(IloCplex.IntParam.Threads, 4);
	    	
		} catch(IloException e) {
			e.printStackTrace();
		}
	}

	//Methodes
	@Override
	public Integer[] resolution() {
		Boolean[][] solution = solvePVC();
		while(!verifSousTours(solution, getNbCities())) {
			ajoutContraintesSousTours(solution);
			solution = resolutionCPLEX();
		}

		return BooleanArrayHelper.getChemin(solution);
	}
	
	public Boolean[][] resolutionCPLEX() {			
		int ligne = 0,colonne = 0;
		Boolean[][] solution = new Boolean[nbCities][nbCities];
		
	    try {			
		    if(((PVC)probleme).getStochastique()) {
		    	// TODO ajouter contrainte D
		    }
		    
		//Resolution
		modele.setOut(null);
		modele.setWarning(null);
		modele.solve();

			for(ligne = 0; ligne < nbCities; ligne++) {
				for(colonne = 0; colonne < nbCities; colonne++) {
					if(ligne != colonne) {
						if(modele.getValue(x[ligne][colonne]) == 1.0) {
							solution[ligne][colonne] = true;
						}
						else {
							solution[ligne][colonne] = false;
						}
					}
					else {
						solution[ligne][colonne] = false;
					}
				}
			}
		} catch (IloException e) {
			e.printStackTrace();
		}
		
		return solution;
	}
	
	public Boolean[][] solvePVC(){
		
		float[][] couts = ((DonneesPVC)probleme.getDonnees()).getCouts();
		
		try {
			
			// Variables
			x = new IloNumVar[nbCities][];
			for(int i = 0; i < nbCities; i++)
				x[i] = modele.boolVarArray(nbCities);
			
			// Objectif
			IloLinearNumExpr obj = modele.linearNumExpr();
			for(int i = 0; i < nbCities; i++){
				for(int j = 0; j < nbCities; j++){
					if(i != j)
						obj.addTerm(couts[i][j], x[i][j]);
				}
			}
			modele.addMinimize(obj);
			
			// Contraintes A et B
			for(int j = 0; j < nbCities; j++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int i = 0; i < nbCities; i++) {
					if(i != j)
						expr.addTerm(1.0, x[i][j]);
				}
				modele.addEq(expr, 1.0);
			}
			for(int i = 0; i < nbCities; i++) {
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int j = 0; j < nbCities; j++) {
					if(i != j)
						expr.addTerm(1.0, x[i][j]);
				}
				modele.addEq(expr, 1.0);
			}

		} catch(IloException e){
			e.printStackTrace();
		}
		
		return resolutionCPLEX();
	}
	
	public static boolean verifSousTours(Boolean[][] matriceS, int taille) {
		int villeActuelle = 0;
		int compte = 0;
		
		do {
			for (int i = 0; i < matriceS.length; i++) {
				if (matriceS[villeActuelle][i]) {
					villeActuelle = i; 
					break;
				}
			}
			compte++;
		}
		while(villeActuelle != 0);
		
		return compte == taille;
	}

	public ArrayList<ArrayList<Integer>> stockSousTours(Boolean[][] matriceSolution) {
		ArrayList<ArrayList<Integer>> sousTours = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> villesRestantes = new ArrayList<Integer>();

		for(int i = 0; i < nbCities; i++) {
			villesRestantes.add(i);
		}
		
		while(villesRestantes.size() > 0) {
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			int premiereVille = villesRestantes.get(0);
			int n = villesRestantes.get(0);
			do {
				int previousN = n;
				for(int i = 0; i < nbCities; i++) {
					if(matriceSolution[n][i]) {
						tmp.add(n);
						villesRestantes.remove((Integer)i);
						n = i;
						break;
					}
				}
				if(n == previousN)
					System.out.println("INFINI");
			}
			while(n != premiereVille);
			sousTours.add(tmp);
		}
		return sousTours;
	}
	
	public void ajoutContraintesST(ArrayList<Integer> cycle) {
		try {
			int secondMembre = cycle.size() - 1;
			IloLinearNumExpr expr = modele.linearNumExpr();
		
			for(int i = 0; i < cycle.size(); i++) {
				 for(int j=0; j < cycle.size(); j++) {
					 	if(i!=j) {
							expr.addTerm(1.0,x[cycle.get(i)][cycle.get(j)]);
					 	}	
				 }
			}
			modele.addLe(expr, secondMembre);
			
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
	
	public void ajoutContraintesSousTours(Boolean[][] matriceS) {
		ArrayList<ArrayList<Integer>> sousTours = stockSousTours(matriceS);
		
		for(int i = 0; i < sousTours.size(); i++) {
			ajoutContraintesST(sousTours.get(i));
		}
	}
	
	//Getters & setters
	public IloCplex getModele() {
		return modele;
	}

	public IloLinearNumExpr getObjectif() {
		return objectif;
	}

	public int getNbCities() {
		return nbCities;
	}	
}