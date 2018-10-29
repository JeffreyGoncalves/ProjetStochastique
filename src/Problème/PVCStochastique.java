package Problème;

import Données.DonneesPVC;

public class PVCStochastique extends PVC {

	//Constructeur
	public PVCStochastique(boolean minimisation, String fichier) {
		super(minimisation);
		
		// Initialise les données stochastiques du PVC
		donnees = new DonneesPVC(fichier);
		donnees.initialiserDonnees();
	}
	
	//Methodes
	public Boolean[][] genererVoisin(){
		// TODO : coder 2-opt
		return null;
	}
	
	public float fonctionObjectif(typeSolution type) {
		float[][] contraintes = ((DonneesPVC)donnees).getCouts();
		Boolean[][] solution = (type == typeSolution.actuelle ? solutionActuelle : 
								type == typeSolution.optimale ? solutionOptimale : solutionTemporaire);
		float variance = ((DonneesPVC)donnees).getVariance();
		float objectif = 0.0f;
		
		for(int i = 0; i < contraintes.length; i++) {
			for(int j = 0; j < contraintes.length; j++) {
				if(solution[i][j]) {
					// TODO : fonction objectif stochastique
					objectif += contraintes[i][j] + variance;
				}
			}
		}
		
		return objectif;
	}

	@Override
	public boolean solutionValide() {
		// TODO Auto-generated method stub
		return false;
	}
}
