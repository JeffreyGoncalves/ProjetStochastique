package Solveur;

import java.util.Random;

import Probl�me.ProblemeLineaire.typeSolution;

public class RecuitSimule<T> extends Solveur<T> {

	//Attributs
	private float temperature;
	private int nbMouvements;
	private ParametresRecuit<T> parametres;
	
	//Constructeur
	public RecuitSimule(ParametresRecuit<T> parametres) {
		this.parametres = parametres;
	}

	//Methodes
	@Override
	public T resolution() {
		temperature = parametres.getTemperatureInitiale();
		
		// TODO : tester le recuit
		
		bouclePrincipale();
		
		return probleme.getSolutionOptimale();
	}
	
	public void baisserTemperature() {
		temperature *= parametres.getCoefficientTemperature();
	}
	
	public void mouvement() {
		probleme.setSolutionTemporaire(probleme.genererVoisin());
	}
	
	public void accepterRefuserMouvement() {
		float delta = probleme.fonctionObjectif(typeSolution.temporaire) - 
				probleme.fonctionObjectif(typeSolution.actuelle);
		
		if(delta < 0) {
			// X <-- X'
			probleme.setSolutionActuelle(probleme.getSolutionTemporaire());
			nbMouvements++;
			
			if(probleme.getMinimisation()) { // Si on doit minimiser f
				if(probleme.fonctionObjectif(typeSolution.actuelle) < probleme.fonctionObjectif(typeSolution.optimale)) {
					// Xoptimal <-- X
					probleme.setSolutionOptimale(probleme.getSolutionActuelle());
				}
			}
			else { // Si on doit maximiser f
				if(probleme.fonctionObjectif(typeSolution.actuelle) > probleme.fonctionObjectif(typeSolution.optimale)) {
					// Xoptimal <-- X
					probleme.setSolutionOptimale(probleme.getSolutionActuelle());
				}
			}
		}
		else {
			// Tirer p dans [0,1]
			float p = (new Random()).nextFloat();
			if(p <= Math.exp((probleme.getMinimisation() ? - delta : delta)/temperature)) {
				// X <-- X'
				probleme.setSolutionActuelle(probleme.getSolutionTemporaire());
				nbMouvements++;
			}
		} 
	}
	
	public void bouclePrincipale() {
		int compteur = 0;
		do {
			boucleMetropolis();
			float taux = nbMouvements/parametres.getNbIterPalier();
			if(taux < parametres.getTxAcceptation()) {
				compteur++;
			}
			
			baisserTemperature();
			
		} while(compteur < parametres.getNbPalierMax());
	}
	
	public void boucleMetropolis() {
		for(int i = 0; i < parametres.getNbIterPalier(); i++) {
			mouvement();
			accepterRefuserMouvement();
		}
	}
	
	//Getters & setters
	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
}
