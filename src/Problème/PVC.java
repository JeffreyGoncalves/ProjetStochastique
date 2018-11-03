package Problème;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Données.*;

public abstract class PVC extends ProblemeLineaire<Integer[]> {

	//Attributs
	protected boolean stochastique;
	
	//Constructeur
	public PVC() {
		super(true);
	}
	
	//Methodes
	public Integer[] genererVoisin(){	
		Integer[] twoOpt = solutionActuelle.clone();
		
		int ville1 = (new Random()).nextInt(twoOpt.length);
		int ville2 = (new Random()).nextInt(twoOpt.length-1);
		if (ville2 >= ville1)
			ville2++;
		
		int iMax = (ville2-ville1)/2;
		if (iMax < 0)
			iMax += twoOpt.length;
		
		for (int i = 0; i < iMax; i++) {
			int i1 = ville1 + i;
			if (i1 >= twoOpt.length)
				i1 -= twoOpt.length;
			
			int i2 = ville2 - i;
			if (i2 < 0)
				i2 += twoOpt.length;
			
			int temp = twoOpt[i1];
			twoOpt[i1] = twoOpt[i2];
			twoOpt[i2] = temp;
		}
		
		return twoOpt;
	}
	
	public abstract float fonctionObjectif(typeSolution type);
	
	public boolean solutionValide(Integer[] chemin) {
		// Vérifie si aucun élément n'est en double
		Set<Integer> set = new HashSet<Integer>();
	    for (Integer each: chemin) if (!set.add(each)) return false;
	    return true;
	}
	
	public void genererSolutionInitiale() {
		int tailleSolution = ((DonneesPVC)donnees).getCoordonnees().size();
		Integer[] solutionInitiale = new Integer[tailleSolution];
		for(int i = 0; i < tailleSolution; i++) {
			solutionInitiale[i] = i;
		}
		
		solutionActuelle = solutionInitiale.clone();
		solutionOptimale = solutionInitiale.clone();
		solutionTemporaire = solutionInitiale.clone();
	}

	//Getters & setters
	public Boolean getStochastique() {
		return stochastique;
	}

	public void setStochastique(Boolean stochastique) {
		this.stochastique = stochastique;
	}
}
