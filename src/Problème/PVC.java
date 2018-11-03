package Problème;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Données.*;

public abstract class PVC extends ProblemeLineaire<Boolean[][]> {

	//Attributs
	protected boolean stochastique;
	
	//Constructeur
	public PVC() {
		super(true);
	}
	
	//Methodes
	public Boolean[][] genererVoisin(){	
		int[] chemin = getChemin(solutionActuelle);
		int[] voisin = inv2opt(chemin);
		return BooleanArrayHelper.toBoolean(voisin);
	}
	
	public int[] inv2opt(int[] sommets) {
		
		int ville1 = (new Random()).nextInt(sommets.length);
		int ville2 = (new Random()).nextInt(sommets.length-1);
		if (ville2 >= ville1)
			ville2++;
		
		int iMax = (ville2-ville1)/2;
		if (iMax < 0)
			iMax += sommets.length;
		
		for (int i = 0; i < iMax; i++) {
			int i1 = ville1 + i;
			if (i1 >= sommets.length)
				i1 -= sommets.length;
			
			int i2 = ville2 - i;
			if (i2 < 0)
				i2 += sommets.length;
			
			int temp = sommets[i1];
			sommets[i1] = sommets[i2];
			sommets[i2] = temp;
		}
		
		return sommets;
	}
	
	public abstract float fonctionObjectif(typeSolution type);
	
	public boolean solutionValide(Boolean[][] solution) {
		int[] chemin = getChemin(solution);

		Set<Integer> set = new HashSet<Integer>();
	    for (Integer each: chemin) if (!set.add(each)) return false;
	    return true;
	}
	
	private int[] getChemin(Boolean[][] solution) {
		int nbVillesVisitees = 0;
		int x = 0;
		int[] chemin = new int[solution.length];
		while(nbVillesVisitees < solution.length) {
			chemin[nbVillesVisitees] = x;
			for(int y = 0; y < solution.length; y++) {
				if(solution[x][y]) {
					nbVillesVisitees++;					
					x = y;
					break;
				}
			}
		}
		
		return chemin;
	}
	
	public void genererSolutionInitiale() {
		int tailleSolution = ((DonneesPVC)donnees).getCoordonnees().size();
		Boolean[][] solutionInitiale = BooleanArrayHelper.initialise(tailleSolution, tailleSolution);
		
		for(int i = 0; i < tailleSolution; i++) {
			if(i == (tailleSolution - 1)) {
				solutionInitiale[i][0] = true;
			}
			else {
				solutionInitiale[i][i+1] = true;
			}
		}
		
		solutionActuelle = BooleanArrayHelper.clone(solutionInitiale);
		solutionOptimale = BooleanArrayHelper.clone(solutionInitiale);
		solutionTemporaire = BooleanArrayHelper.clone(solutionInitiale);
	}

	//Getters & setters
	public Boolean getStochastique() {
		return stochastique;
	}

	public void setStochastique(Boolean stochastique) {
		this.stochastique = stochastique;
	}
}
