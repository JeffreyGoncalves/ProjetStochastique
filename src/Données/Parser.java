package Donn�es;

public abstract class Parser {

	private String fichier;
	
	//Constructeur
		public Parser() {
			
		}
		
		//Methodes
		public void lireDonnees() {
		}

		//Getters & Setters
		public String getFichier() {
			return fichier;
		}

		public void setFichier(String fichier) {
			this.fichier = fichier;
		}	
}
