package InterfaceGraphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Données.DonneesPVC;
import Problème.*;
import Solveur.*;

public class InterfaceGraphique {

	//Attributs
	private JPanel fenetre;
	private JFrame frame;

	private AffichageVilles representation;
	private JComboBox<String> choixProbleme;
	private JComboBox<String> choixMethode;
	private JFileChooser fichier;
	private JButton resolution, choixFichier;
	
	private String cheminFichier;
	private String choixP;
	private String choixM;
	
	private ProblemeLineaire<Boolean[][]> pvc;
	
	//Constructeur
	public InterfaceGraphique() {
		frame = new JFrame();
		//frame.setResizable(false);
		frame.setTitle("Projet d'Optimisation Stochastique");
		fenetre = new JPanel();
		fenetre.setLayout(new BoxLayout(fenetre, BoxLayout.PAGE_AXIS));
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		frame.add(fenetre, BorderLayout.WEST);
		fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		//ComboBox du choix du probleme
		String[] pbOptions = {"Probleme du Voyageur de Commerce Deterministe", 
								"Probleme du Voyageur de Commerce Stochastique"};
		choixProbleme = new JComboBox<String>(pbOptions) {
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }
		};
		
		choixProbleme.setRenderer(new MyComboBoxRenderer<String>("Choix du probleme a resoudre"));
		choixProbleme.setSelectedIndex(-1);
		choixProbleme.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				choixP = (String) choixProbleme.getSelectedItem();
				autoriserResolution();
				choixFichier.setEnabled(true);
			}
		});
		
		options.add(choixProbleme,Component.CENTER_ALIGNMENT);
		
		//Combobox du choix de la methode
		String[] methodesOptions = {"Recuit simule", "CPLEX"};
		choixMethode = new JComboBox<String>(methodesOptions) {
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }
		};
		choixMethode.setRenderer(new MyComboBoxRenderer<String>("Choix de la methode de resolution"));
		choixMethode.setSelectedIndex(-1);
		choixMethode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choixM = (String) choixMethode.getSelectedItem();	
				autoriserResolution();
			}
		});
		
		options.add(choixMethode,Component.CENTER_ALIGNMENT);
		
		TitledBorder timeBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Options de resolution");
		Font timeFont = new Font("Arial",Font.BOLD,15);
		timeBorder.setTitleFont(timeFont);
		options.setBorder(timeBorder);
		fenetre.add(options,Component.CENTER_ALIGNMENT);	
		fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		// Choix du fichier
		JPanel fichierPanel = new JPanel();
		JLabel ltextBox = new JLabel("Fichier choisi: ");
		JLabel nomFichier = new JLabel();
		fichierPanel.setLayout(new BoxLayout(fichierPanel, BoxLayout.PAGE_AXIS));
		fichierPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fichier = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fichier.setDialogTitle("Choisissez un fichier de donnees");
		fichier.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
		fichier.addChoosableFileFilter(filter);
		choixFichier = new JButton("Ajouter un fichier de donnees");
		choixFichier.setEnabled(false);
		choixFichier.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnValue = fichier.showOpenDialog((Component)e.getSource());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					cheminFichier = fichier.getSelectedFile().getPath();
					nomFichier.setText(fichier.getSelectedFile().getName());
					
					afficherVillesFichier();
					autoriserResolution();
				}
			}
		});
		
		fichierPanel.add(choixFichier, BorderLayout.WEST);
		
		if(cheminFichier != null) {
			nomFichier.setText(cheminFichier);
		}
		else {
			nomFichier.setText("");
		}
		nomFichier.setMaximumSize(new Dimension(248,20));
		fichierPanel.add(ltextBox,Component.LEFT_ALIGNMENT);
		fichierPanel.add(nomFichier, Component.LEFT_ALIGNMENT);
		timeBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), 
																			"Selection de fichier");
		timeBorder.setTitleFont(timeFont);
		fichierPanel.setBorder(timeBorder);
		 
		fenetre.add(fichierPanel, BorderLayout.WEST);
		fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		// Bouton de resolution
		JPanel boutonR = new JPanel();
		boutonR.setLayout(new BoxLayout(boutonR, BoxLayout.PAGE_AXIS));
		boutonR.setAlignmentX(Component.CENTER_ALIGNMENT);
		resolution = new JButton("Resolution");
		resolution.setEnabled(false);
		resolution.setFont(new Font("Arial", Font.BOLD, 30));
		resolution.setBackground(Color.darkGray);
		resolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resolution();
				System.out.println("Resolution terminee");	
			}
		});
		
		boutonR.add(resolution, Component.LEFT_ALIGNMENT);
		fenetre.add(boutonR);
		
		// Representation des villes
		representation = new AffichageVilles();
		representation.setPreferredSize(new Dimension(500, 500));
		frame.add(representation, BorderLayout.EAST);
		
		// Options de frame
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	//Methodes	
	private void autoriserResolution() {
		if(choixP != null && choixM != null && cheminFichier != null) { 
			resolution.setEnabled(true);
			resolution.setBackground(Color.GREEN);
		}
	}
	
	private void afficherVillesFichier() {
		if(choixP == "Probleme du Voyageur de Commerce Deterministe") {
			pvc = new PVCDeterministe(cheminFichier);
			DonneesPVC donnees = (DonneesPVC)pvc.getDonnees();
			representation.setCoordonneesAbsolues(donnees.getCoordonnees());
		}
		else if(choixP == "Probleme du Voyageur de Commerce Stochastique") {
			pvc = new PVCStochastique(cheminFichier);
			DonneesPVC donnees = (DonneesPVC)pvc.getDonnees();
			representation.setCoordonneesAbsolues(donnees.getCoordonnees());
		}
	}
	
	private void resolution() {
		System.out.println("Probleme lineaire : " + choixP);
		System.out.println("Methode de resolution : " + choixM);
		
		if(choixP == "Probleme du Voyageur de Commerce Deterministe") {
			if(!(pvc instanceof PVCDeterministe)) {
				// TODO : message box
				System.out.println("Vous avez telecharge un fichier en tant que PVC deterministe.");
				System.out.println("Si vous souhaitez changer de probleme, re-telechargez le fichier !");
				return;
			}
		}
		else if(choixP == "Probleme du Voyageur de Commerce Stochastique") {
			if(!(pvc instanceof PVCStochastique)) {
				System.out.println("Vous avez telecharge un fichier en tant que PVC stochastique.");
				System.out.println("Si vous souhaitez changer de probleme, re-telechargez le fichier !");
				return;
			}
		}
		else {
			System.out.println("Aucun autre probleme que le PVC n'est implemente");
		}
		
		choixMethode("PVC");
	}
	
	private void choixMethode(String prblm) {
		if(prblm == "PVC") {
			switch(choixM) {
			case "Recuit simule": 
				int nbVilles = representation.getNbVilles();
				ParametresRecuit<Boolean[][]> parametres = new ParametresRecuit<Boolean[][]>(0.8f, nbVilles * nbVilles, 
																							100, 0.9f, pvc);
				Solveur<Boolean[][]> solveurPVC = new RecuitSimule<Boolean[][]>(parametres, pvc);
				Boolean[][] liaisons = solveurPVC.resolution();
				representation.setLiaisons(liaisons);
				break;
			case "CPLEX": 
				// TODO : cplex
				break;
			}
		}
	}

	//Getters & Setters
	public String getCheminFichier() {
		return cheminFichier;
	}
	
	public String getChoixMethode() {
		return choixM;
	}
	
	public String getChoixProbleme() {
		return choixP;
	}
	
	public static void main(String[] args) {
		new InterfaceGraphique();
	}
}
