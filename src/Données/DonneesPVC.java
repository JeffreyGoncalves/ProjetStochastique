package Données;

public class DonneesPVC extends Donnees {

	//Attributs
	private float[][] couts;
	private float variance;
	
	//Constructeur
	public DonneesPVC(String fichier) {
		parser = new ParserPVC(fichier, this);
		variance = 20.0f;
	}
	
	//Methodes
	@Override
	public void initialiserDonnees() {
		parser.lireDonnees();
	}
	
	public void ajouterCout(int idDep, int idArr, float cout) {
		this.couts[idDep][idArr] = cout;
	}
	
	public void initialiserMatrice(int taille) {
		
		this.couts = new float[taille][taille];
	}
		
	//Getters & Setters
	public float[][] getCouts() {
		return couts;
	}

	public void setCouts(float[][] couts) {
		this.couts = couts;
	}

	public float getVariance() {
		return variance;
	}

	public void setVariance(float variance) {
		this.variance = variance;
	}
	
	// Test de parsing 
	/*
	public static void main(String[] args) {
		DonneesPVC pvc = new DonneesPVC("./data/att48.xml");
		//System.out.println(pvc.parser.getFichier());
		
		pvc.initialiserDonnees();
		
		float[][] costs = pvc.getCouts();
		if(costs != null) {
			for(int i=0;i<costs.length;i++) {
				for(int j=0;j<costs.length;j++) {
					if(i == j) {
						double c = costs[i][j];
						System.out.println("Cout du trajet partant de la ville " + i + " vers la ville " + j + " = " + c);
					}
				}
			}
		}
	}
	*/
}
