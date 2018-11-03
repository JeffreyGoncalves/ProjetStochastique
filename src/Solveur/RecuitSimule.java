package Solveur;

import java.util.Random;

import Problème.*;
import Problème.ProblemeLineaire.typeSolution;

public class RecuitSimule<T> extends Solveur<T> {

	//Attributs
	private float temperature;
	private int nbMouvements;
	private int compteur;
	private ParametresRecuit<T> parametres;
	
	private float coutTotalInitial;
	private float coutTotalFinal;
	
	//Constructeur
	public RecuitSimule(ParametresRecuit<T> parametres, ProblemeLineaire<T> probleme) {
		super(probleme);
		this.parametres = parametres;
	}

	//Methodes
	@Override
	public T resolution() {
		temperature = parametres.getTemperatureInitiale();
		probleme.genererSolutionInitiale();	
		coutTotalInitial = probleme.fonctionObjectif(typeSolution.optimale);
		
		bouclePrincipale();
		
		coutTotalFinal = probleme.fonctionObjectif(typeSolution.optimale);
		
		return probleme.getSolutionOptimale();
	}
	
	public void baisserTemperature() {
		temperature *= parametres.getCoefficientTemperature();
	}
	
	public void mouvement() {
		probleme.setSolutionTemporaire(probleme.genererVoisin());
	}
	
	private boolean verifierDelta(float delta) {
		if(probleme.getMinimisation())
			return (delta < 0);
		else
			return (delta > 0);
	}
	
	private boolean verifierOptimale() {
		if(probleme.getMinimisation()) {
			return probleme.fonctionObjectif(typeSolution.actuelle) < probleme.fonctionObjectif(typeSolution.optimale);
		}
		else {
			return probleme.fonctionObjectif(typeSolution.actuelle) > probleme.fonctionObjectif(typeSolution.optimale);
		}
	}
	
	public void accepterRefuserMouvement() {
		float delta = probleme.fonctionObjectif(typeSolution.temporaire) - 
				probleme.fonctionObjectif(typeSolution.actuelle);
		
		if(verifierDelta(delta)) {
			// X <-- X'
			probleme.setSolutionActuelle(probleme.getSolutionTemporaire());
			nbMouvements++;

			if(verifierOptimale()) { 
				// Xoptimal <-- X
				probleme.setSolutionOptimale(probleme.getSolutionActuelle());
				compteur = 0;
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
		compteur = 0;
		do {
			nbMouvements = 0;
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

	public float getCoutTotalInitial() {
		return coutTotalInitial;
	}

	public float getCoutTotalFinal() {
		return coutTotalFinal;
	}
	
	public ParametresRecuit<T> getParametres() {
		return parametres;
	}
}