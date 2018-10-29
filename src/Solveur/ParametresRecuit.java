package Solveur;

import Problème.*;
import Problème.ProblemeLineaire.typeSolution;
import java.util.Random;

public class ParametresRecuit<T> {
	
	//Attributs
	private boolean verbose = true;
	private float temperatureInitiale;
	private float txAcceptation;
	private int nbIterPalier;
	private int nbPalierMax;
	private float coefficientTemperature;
	private ProblemeLineaire<T> probleme;
	
	//Constructeur
	public ParametresRecuit(float init, float taux, int nbIter, int nbPalier, float coef, ProblemeLineaire<T> probleme) {
		txAcceptation = taux;
		nbIterPalier = nbIter;
		nbPalierMax = nbPalier;
		coefficientTemperature = coef;
		this.probleme = probleme;
		
		calculerTemperatureInitiale();
	}
	
	//Methode
	public void calculerTemperatureInitiale() {
		// TODO : tester cette fonction
		System.out.print("Calcul de la temperature initiale du recuit : ");
		probleme.genererSolutionInitiale();
		
		temperatureInitiale = 0.05f;
		float taux = 0.0f;
		
		while(taux < txAcceptation) {
			// On double la temperature
			temperatureInitiale *= 2;
			
			// Début du palier
			int nbMouvements = 0;
			for(int i = 0; i < nbIterPalier; i++) {
				// X' <-- solution voisine de X
				probleme.setSolutionTemporaire(probleme.genererVoisin());
				float delta = probleme.fonctionObjectif(typeSolution.temporaire) - 
									probleme.fonctionObjectif(typeSolution.actuelle);
				if(delta < 0) {
					// X <-- X'
					probleme.setSolutionActuelle(probleme.getSolutionTemporaire());
					nbMouvements++;
				}
				else {
					// Tirer p dans [0,1]
					float p = (new Random()).nextFloat();
					if(p <= Math.exp((probleme.getMinimisation() ? - delta : delta)/temperatureInitiale)) {
						// X <-- X'
						probleme.setSolutionActuelle(probleme.getSolutionTemporaire());
						nbMouvements++;
					}
				} // Fin du palier
				
				taux = nbMouvements/nbIterPalier;
				if(verbose)
					System.out.println("Temperature : " + temperatureInitiale + ", taux : " + taux * 100 + "%");
			}
		}
		
		System.out.println(temperatureInitiale + " degrés");
	}

	//Getters & setters
	public float getTemperatureInitiale() {
		return temperatureInitiale;
	}

	public float getTxAcceptation() {
		return txAcceptation;
	}

	public int getNbIterPalier() {
		return nbIterPalier;
	}

	public int getNbPalierMax() {
		return nbPalierMax;
	}
	
	public float getCoefficientTemperature() {
		return coefficientTemperature;
	}
}
