package Problème;

import Données.*;

public class PVCDeterministe extends PVC {

	//Constructeur
	public PVCDeterministe(String fichier) {
		super();
		
		// Initialise les données déterministes du PVC
		stochastique = false;
		donnees = new DonneesPVC(fichier);
		donnees.initialiserDonnees();
	}

	//Methodes
	public float fonctionObjectif(typeSolution type) {
		float[][] contraintes = ((DonneesPVC)donnees).getCouts();
		Integer[] solution = (type == typeSolution.actuelle ? solutionActuelle : 
								type == typeSolution.optimale ? solutionOptimale : solutionTemporaire);
		float objectif = 0.0f;
		
		for(int i = 0; i < solution.length; i++) {
			if(i != (solution.length - 1)) {
				objectif += contraintes[solution[i]][solution[i+1]];
			}
			else {
				objectif += contraintes[solution[i]][solution[0]];
			}
		}

		return objectif;
	}
}
