package Problème;

import Données.DonneesPVC;

public class PVCStochastique extends PVC {

	//Constructeur
	public PVCStochastique(String fichier) {
		super();
		
		// Initialise les données stochastiques du PVC
		stochastique = true;
		donnees = new DonneesPVC(fichier);
		donnees.initialiserDonnees();
	}
	
	//Methodes	
	public float fonctionObjectif(typeSolution type) {
		float[][] contraintes = ((DonneesPVC)donnees).getCouts();
		Integer[] solution = (type == typeSolution.actuelle ? solutionActuelle : 
								type == typeSolution.optimale ? solutionOptimale : solutionTemporaire);
		float variance = ((DonneesPVC)donnees).getVariance();
		float objectif = 0.0f;
		
		for(int i = 0; i < solution.length; i++) {
			if(i != (solution.length - 1)) {
				// TODO : fonction objectif stochastique
				objectif += contraintes[solution[i]][solution[i+1]] + variance;
			}
			else {
				objectif += contraintes[solution[i]][solution[0]] + variance;
			}
		}
		
		return objectif;
	}
}
