package Données;

public abstract class Parser {

	protected String fichier;
	
	//Constructeur
	public Parser(String fichier) {
		this.fichier = fichier;
	}
	
	//Methodes
	public abstract void lireDonnees();

	//Getters & Setters
	public String getFichier() {
		return fichier;
	}

	public void setFichier(String fichier) {
		this.fichier = fichier;
	}	
}
