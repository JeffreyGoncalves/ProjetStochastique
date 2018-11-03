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
		Boolean[][] solution = (type == typeSolution.actuelle ? solutionActuelle : 
								type == typeSolution.optimale ? solutionOptimale : solutionTemporaire);
		float objectif = 0.0f;
		
		for(int i = 0; i < contraintes.length; i++) {
			for(int j = 0; j < contraintes.length; j++) {
				if(solution[i][j]) {
					objectif += contraintes[i][j];
				}
			}
		}
		
		return objectif;
	}
}
