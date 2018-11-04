package InterfaceGraphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Données.DonneesPVC;
import Problème.*;
import Problème.ProblemeLineaire.typeSolution;
import Solveur.*;

public class InterfaceGraphique {

	//Attributs
	private JPanel fenetre;
	private JFrame frame;
	private JPanel informations;

	private AffichageVilles representation;
	private JComboBox<String> choixProbleme;
	private JComboBox<String> choixMethode;
	private JFileChooser fichier;
	private JButton resolution, choixFichier;
	
	private String cheminFichier;
	private String choixP;
	private String choixM;
	
	private JLabel tempsResolution;
	private JLabel tempLabel;
	private JLabel temperatureInitiale;
	private JLabel coutInitial;
	private JLabel coutFinal;
	
	private ProblemeLineaire<Integer[]> pvc;
	
	private long tempsExecution;
	
	//Constructeur
	public InterfaceGraphique() {
		frame = new JFrame();
		frame.setResizable(false);
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
					
					afficherInformations("Reset");
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
		fenetre.add(Box.createRigidArea(new Dimension(0,25)));
		
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
		
		fenetre.add(Box.createRigidArea(new Dimension(0, 25)));
		
		// Panel d'informations
		JPanel informations = panelInformations();
		fenetre.add(informations);
		
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
				afficherInformations("Recuit simule");
				int nbVilles = representation.getNbVilles();
				ParametresRecuit<Integer[]> parametres = new ParametresRecuit<Integer[]>(0.8f, nbVilles * nbVilles, 
																							100, 0.9f, pvc);
				RecuitSimule<Integer[]> solveurPVC = new RecuitSimule<Integer[]>(parametres, pvc);
				Instant start = Instant.now();
				Integer[] liaisons = solveurPVC.resolution();       
				Instant finish = Instant.now();
				tempsExecution = Duration.between(start, finish).toMillis();
				
				afficherResolution(liaisons, solveurPVC);
				
				break;
			case "CPLEX": 
				afficherInformations("CPLEX");
				int nbCities = representation.getNbVilles();
				Cplex solveurCplex = new Cplex(pvc, nbCities);
				
				Instant debut = Instant.now();
				Integer[] solution = solveurCplex.resolution();
				Instant fin = Instant.now();
				tempsExecution = Duration.between(debut, fin).toMillis();

				representation.setLiaisons(solution);
				pvc.genererSolutionInitiale();	
				float coutInitial = pvc.fonctionObjectif(typeSolution.optimale);
				pvc.setSolutionOptimale(solution);
				float coutFinal = pvc.fonctionObjectif(typeSolution.optimale);
				afficherResolution(solution, coutInitial, coutFinal);
				
				break;
			}
		}
	}
	
	private void afficherResolution(Integer[] liaisons, RecuitSimule<Integer[]> solveur) {
		representation.setLiaisons(liaisons);
		coutInitial.setText(String.valueOf(solveur.getCoutTotalInitial()));
		coutFinal.setText(String.valueOf(solveur.getCoutTotalFinal()));
		temperatureInitiale.setText(String.valueOf(solveur.getParametres().getTemperatureInitiale()));
		if(tempsExecution < 1000)
			tempsResolution.setText(String.valueOf(tempsExecution) + " ms");
		else
			tempsResolution.setText(String.valueOf(tempsExecution / 1000) + " s");
	}
	
	private void afficherResolution(Integer[] liaisons, float cInitial, float cFinal) {
		representation.setLiaisons(liaisons);
		coutInitial.setText(String.valueOf(cInitial));
		coutFinal.setText(String.valueOf(cFinal));
		temperatureInitiale.setText("Terminé");
		if(tempsExecution < 1000)
			tempsResolution.setText(String.valueOf(tempsExecution) + " ms");
		else
			tempsResolution.setText(String.valueOf(tempsExecution / 1000) + " s");
	}
	
	private JPanel panelInformations() {
		informations = new JPanel();
		informations.setLayout(new GridLayout(0, 2));
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), 
				"Informations");
		border.setTitleFont(new Font("Arial", Font.BOLD, 15));
		informations.setBorder(border);
		
		temperatureInitiale = new JLabel("");
		coutInitial = new JLabel("");
		coutFinal = new JLabel("");
		tempsResolution = new JLabel("");
		
		tempLabel = new JLabel("Temperature initiale : ");
		informations.add(tempLabel);
		informations.add(temperatureInitiale);
		informations.add(new JLabel("Cout total initial : "));
		informations.add(coutInitial);
		informations.add(new JLabel("Cout total final : "));
		informations.add(coutFinal);
		informations.add(new JLabel("Temps de resolution : "));
		informations.add(tempsResolution);
		informations.setVisible(false);
		
		return informations;
	}
	
	private void afficherInformations(String type) {
		if(type == "Recuit simule") {
			tempLabel.setText("Temperature initiale : ");
			informations.setVisible(true);
		}
		else if (type == "CPLEX") {
			// TODO : modifier les infos et afficher
			tempLabel.setText("Etat de la resolution : ");
			temperatureInitiale.setText("En cours");
			informations.setVisible(true);
		}
		else if (type == "Reset"){
			informations.setVisible(false);
			tempsResolution.setText("");
			temperatureInitiale.setText("");
			coutInitial.setText("");
			coutFinal.setText("");
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
