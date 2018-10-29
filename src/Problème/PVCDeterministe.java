package Problème;

import Données.*;

public class PVCDeterministe extends PVC {

	//Constructeur
	public PVCDeterministe(boolean minimisation, String fichier) {
		super(minimisation);
		
		// Initialise les données déterministes du PVC
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

	@Override
	public boolean solutionValide() {
		// TODO Auto-generated method stub
		return false;
	}
}
