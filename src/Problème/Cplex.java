package Solveur;

import java.util.ArrayList;

import Problème.*;
import Données.*;
import ilog.concert.*;
import ilog.cplex.*;

public class Cplex extends Solveur<Boolean[][]> {

	//attributs
		private IloCplex modele;
		private IloLinearNumExpr objectif;
		private IloNumVar[][] x;
		private int nbCities;
		private Integer[] cycle;
		
		
		//constructeur
		public Cplex(ProblemeLineaire<Boolean[][]> probleme, int sizeData) {
			super(probleme);
			nbCities = sizeData;
			
			try {
				
				modele = new IloCplex();
		    	modele.setParam(IloCplex.IntParam.Threads, 8);
		    	//initialisationVariables();
		    	//genererFonctionObjectif(((DonneesPVC)probleme.getDonnees()).getCouts());
		    	
			}catch(IloException e) {
				e.printStackTrace();
			}
		}

		//Methodes
		@Override
		public Boolean[][] resolution() {
			
			int ligne = 0,colonne = 0;
			Boolean[][] solution = new Boolean[nbCities][nbCities];
			
		    try {			
			    
			    if(((PVC)probleme).getStochastique()) {
			    	
			    	// TODO add constraint D
			    }
			    
			//Resolution
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
			   
			System.out.println("Cplex process finished successfully !");
			
			} catch (IloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
			
			return solution ;
		}
		
		public Boolean[][] solvePVC(){
			
			float[][] couts = ((DonneesPVC)probleme.getDonnees()).getCouts();
			
			try{
				modele = new IloCplex();
				modele.setParam(IloCplex.IntParam.Threads, 8);
				
				// Variables
				x = new IloNumVar[nbCities][];
				for(int i = 0; i < nbCities; i++)
					x[i] = modele.boolVarArray(nbCities);
				IloNumVar[] u = modele.numVarArray(nbCities, 0, Double.MAX_VALUE);
				
				// Objectif
				IloLinearNumExpr obj = modele.linearNumExpr();
				for(int i = 0; i < nbCities; i++){
					for(int j = 0; j < nbCities; j++){
						if(i != j)
							obj.addTerm(couts[i][j], x[i][j]);
					}
				}
				modele.addMinimize(obj);
				
				// Contraintes
				
				//Contrainte (b)
				for(int j = 0; j < nbCities; j++){
					IloLinearNumExpr expr = modele.linearNumExpr();
					for(int i = 0; i < nbCities; i++){
						if(i != j)
							expr.addTerm(1.0, x[i][j]);
					}
					modele.addEq(expr, 1.0);
				}
				
				//Contrainte (a)
				for(int i = 0; i < nbCities; i++){
					IloLinearNumExpr expr = modele.linearNumExpr();
					for(int j = 0; j < nbCities; j++){
						if(i != j)
							expr.addTerm(1.0, x[i][j]);
					}
					modele.addEq(expr, 1.0);
				}

			} catch(IloException e){
				e.printStackTrace();
			}
			return resolution();
		}
		public void initialisationVariables() {
			
			int ligne = 0;
			try {
				
				x = new IloNumVar[nbCities][nbCities];
				System.out.println("HELLO");
				IloNumVar[] u = modele.numVarArray(nbCities, 0, Double.MAX_VALUE); //Pour l'éliminition des sous-tours
				for (ligne = 0; ligne < nbCities; ligne++) {
					
					x[ligne] = modele.boolVarArray(nbCities);
				}
				
			}catch(IloException e) {
				e.printStackTrace();
			}
		}
		
		public void genererFonctionObjectif(float[][] couts) {
			
			int ligne = 0,colonne = 0;
			
			try {
				
				objectif = modele.linearNumExpr();
				for(ligne = 0; ligne < nbCities; ligne++){
			        for (colonne = 0; colonne < nbCities; colonne++){
			            if(colonne != ligne){
			                //objectif.addTerm(couts[ligne][colonne], x[ligne][colonne]);
			            }
			        }
			    }
			    modele.addMinimize(objectif);
				
			}catch(IloException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void afficher(Boolean[][] tableau) {
	        int taille = tableau.length;
	        for(int i = 0; i < taille; i++) {
	            for(int j = 0; j < tableau[i].length; j++) {
	                System.out.print((tableau[i][j] ? "1 " : "0 "));
	            }
	            System.out.println();
	        }
	    }
		
		public void remplirCycleSolution(Boolean[][] matriceSolution) {
			
			cycle = new Integer[matriceSolution.length];
			int villeDepart = 0, cpt = 1;
			cycle[0] = 0;
			
			do {
				
				for(int i=0; i < cycle.length; i++) {
					
					if(matriceSolution[villeDepart][i]) {
						
						villeDepart = i;
						break;
					}
				}

				cycle[cpt] = villeDepart;
				cpt++;
				
			}while(villeDepart != 0);
			
			afficherCycleSolution();
		}
		
		public void afficherCycleSolution() {
			
			String affiche = "";
			
			for(int i=0; i < cycle.length; i++) {
				
				affiche += cycle[i] + "\t";
			}
			
			System.out.println(affiche);
			
		}
		
		public static boolean verifSousTours(Boolean[][] matriceS, int taille) {
			
			int villeDepart = 0;
			int cpt = 0;
			
			do {
				
				for(int i=0; i < matriceS.length; i++) {
					
					if(matriceS[villeDepart][i]) {
						
						villeDepart = i;
						break;
					}
				}
				
				cpt++;
				
			}while(villeDepart != 0);
			
			return cpt == taille;
			
		}
		
		public void ajoutContrainteST(Integer[] cycle) {
			
			
			
			afficherCycleSolution();
			try {
				
					int secondMembre = compterElementsCycle(cycle) - 1;
				
					
					IloLinearNumExpr contrainte = modele.linearNumExpr();
				
					for(int i = 0; i < compterElementsCycle(cycle); i++) {
						
						for(int j = 0; j < compterElementsCycle(cycle); j++) {
					
							if(i != j) {
									
									contrainte.addTerm(1.0, x[cycle[i]][cycle[j]]);		
							}
						}
						
					}
					modele.addLe(contrainte,secondMembre);
				
			} catch(IloException e) {
				e.printStackTrace();
			}
		}
		
		public int compterElementsCycle(Integer[] tmp) {
			
			int cpt = 0;
			
			for(int i=0; i < tmp.length; i++) {
				
				if(tmp[i] != null) {
					cpt++;
				}
			}
			
			return cpt;
		}
		
		public ArrayList<Integer[]> stockSousTours(Boolean[][] matriceS){
			
			ArrayList<Integer[]> sousTours = new ArrayList<Integer[]>();
			Integer[] villesRestantes = new Integer[nbCities];
			int nbVilles = 0, cpt = 0;
			
			for(int i=0; i < nbCities; i++) {
				
				villesRestantes[i] = i;
			}
			while(compterElementsCycle(villesRestantes) > 0) {
				
				Integer[] tmp = new Integer[compterElementsCycle(villesRestantes)];
				while(villesRestantes[cpt] == null) {
					cpt++;
				}
				int PVille = villesRestantes[cpt];
				int n = villesRestantes[cpt];
				
				do {
					
					for(int i = 0; i < nbCities; i++) {
						
						int compteur = 0;
						if(matriceS[n][i]) {
							
							while(tmp[compteur] != null) {
								compteur++;
							}
							
							tmp[compteur] = n;
							villesRestantes[i] = null;
							n = i;
							nbVilles++;
							break;
						}
					}
				} while(n != PVille);
				sousTours.add(tmp);
			}
			return sousTours;
		}
		
		
		public void ajoutContraintesSousTours(Boolean[][] matriceS) {
			
			ArrayList<Integer[]> st = stockSousTours(matriceS);
			
			for(int i = 0; i < st.size(); i++) {
				
				ajoutContrainteST(st.get(i));
			}
		}
		
		//getters & setters
		public IloCplex getModele() {
			return modele;
		}

		public IloLinearNumExpr getObjectif() {
			return objectif;
		}

		public Integer[] getCycle() {
			return cycle;
		}

		public int getNbCities() {
			return nbCities;
		}	
	
}
