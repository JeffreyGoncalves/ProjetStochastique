package Problème;

public class BooleanArrayHelper {
	public static Boolean[][] toBoolean(boolean[][] tableau) {
		Boolean[][] retour = new Boolean[tableau.length][];
		for(int i = 0; i < tableau.length; i++) {
			for(int j = 0; j < tableau[i].length; j++) {
				retour[i][j] = (Boolean)tableau[i][j];
			}
		}
		
		return retour;
	}
	
	public static Boolean[][] toBoolean(int[] chemin) {
		Boolean[][] tableau = initialise(chemin.length, chemin.length);
		for(int i = 0; i < chemin.length; i++) {
			if(i != (chemin.length - 1)) {
				tableau[chemin[i]][chemin[i+1]] = true;
			}
			else {
				tableau[chemin[i]][chemin[0]] = true;
			}
		}
		
		return tableau;
	}
	
	public static Boolean[][] clone(Boolean[][] tableau) {
		Boolean[][] retour = new Boolean[tableau.length][tableau[0].length];
		for(int i = 0; i < tableau.length; i++) {
			for(int j = 0; j < tableau[i].length; j++) {
				retour[i][j] = tableau[i][j];
			}
		}
		
		return retour;
	}
	
	public static Boolean[][] initialise(int largeur, int hauteur) {
		Boolean[][] tableau = new Boolean[largeur][hauteur];
		for(int i = 0; i < largeur; i++) {
			for(int j = 0; j < hauteur; j++) {
				tableau[i][j] = false;
			}
		}
		
		return tableau;
	}
	
	public static void afficher(Boolean[][] tableau) {
		int taille = tableau.length;
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < tableau[i].length; j++) {
				System.out.print((tableau[i][j] ? "1 " : "0 "));
			}
			System.out.println();
		}
	}
	
	public static Integer[] getChemin(Boolean[][] solution) {
		int nbVillesVisitees = 0;
		int x = 0;
		Integer[] chemin = new Integer[solution.length];
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
	
	public static Integer[] getCheminV2(Boolean[][] solution) {
        Integer[] cycle = new Integer[solution.length];
        int villeDepart = 0, cpt = 1;
        cycle[0] = 0;
        
        do {
            for(int i = 0; i < cycle.length; i++) {
                
                if(solution[villeDepart][i]) {
                    
                    villeDepart = i;
                    break;
                }
            }

            cycle[cpt] = villeDepart;
            cpt++;
            
        } while(villeDepart != 0);
        
        return cycle;

	}
}
