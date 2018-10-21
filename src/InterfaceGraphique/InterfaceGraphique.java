package InterfaceGraphique;

import java.awt.Canvas;

import javax.swing.*;

public class InterfaceGraphique {

	//attributs
	private JPanel fenetre;
	private JFrame frame;
	private Canvas representation;
	private JComboBox choixProbleme;
	private JComboBox choixMethode;
	private JFileChooser fichier;
	private JButton resolution;
	
	//Constructeur
	public InterfaceGraphique() {
		
	}
	
	//Methodes
	void fisheye() {
		
	}

	//Getters & Setters
	public JPanel getFenetre() {
		return fenetre;
	}

	public void setFenetre(JPanel fenetre) {
		this.fenetre = fenetre;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public Canvas getRepresentation() {
		return representation;
	}

	public void setRepresentation(Canvas representation) {
		this.representation = representation;
	}

	public JComboBox getChoixProbleme() {
		return choixProbleme;
	}

	public void setChoixProbleme(JComboBox choixProbleme) {
		this.choixProbleme = choixProbleme;
	}

	public JComboBox getChoixMethode() {
		return choixMethode;
	}

	public void setChoixMethode(JComboBox choixMethode) {
		this.choixMethode = choixMethode;
	}

	public JFileChooser getFichier() {
		return fichier;
	}

	public void setFichier(JFileChooser fichier) {
		this.fichier = fichier;
	}

	public JButton getResolution() {
		return resolution;
	}

	public void setResolution(JButton resolution) {
		this.resolution = resolution;
	}
	
	
	
	
	
}
