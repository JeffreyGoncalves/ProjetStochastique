package InterfaceGraphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class InterfaceGraphique {

	//attributs
	private JPanel fenetre;
	private JFrame frame;
	private Canvas representation;
	private JComboBox<String> choixProbleme;
	private JComboBox<String> choixMethode;
	private JFileChooser fichier;
	private JButton resolution, choixFichier;
	
	private String cheminFichier;
	private String choixP;
	private String choixM;
	
	//Constructeur
	public InterfaceGraphique() {
		
		frame = new JFrame();
		fenetre = new JPanel();
		fenetre.setLayout(new BoxLayout(fenetre,BoxLayout.PAGE_AXIS));
		frame.add(fenetre, BorderLayout.WEST);
		
		//ComboBox du choix du probleme
		String[] pbOptions = {"Probl�me du Voyageur de Commerce", "Autre"};
		choixProbleme = new JComboBox<String>(pbOptions) {

            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }

            
		};
		choixProbleme.setRenderer(new MyComboBoxRenderer("Choix du Probl�me � r�soudre"));
		choixProbleme.setSelectedIndex(-1);
		fenetre.add(choixProbleme,Component.CENTER_ALIGNMENT);
		
		//ComboBox du choix de la methode
		String[] methodesOptions = {"Recuit simul� d�terministe", "Recuit simul� stochastique", "CPLEX d�terministe", "CPLEX stochastique"};
		choixMethode = new JComboBox<String>(methodesOptions) {

            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }

            
		};
		choixMethode.setRenderer(new MyComboBoxRenderer("Choix de la M�thode de R�solution"));
		choixMethode.setSelectedIndex(-1);
		fenetre.add(choixMethode,Component.CENTER_ALIGNMENT);
		
		//Filechooser
		fichier = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fichier.setDialogTitle("Choisissez un fichier de donn�es");
		fichier.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
		fichier.addChoosableFileFilter(filter);
		choixFichier = new JButton("Ajouter un fichier de donn�es");
		choixFichier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int returnValue = fichier.showOpenDialog((Component)e.getSource());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fichier.getSelectedFile().getPath());
				}
				
			}
		});
		
		fenetre.add(choixFichier, Component.CENTER_ALIGNMENT);
		
		//bouton de r�solution
		resolution = new JButton("R�solution");
		resolution.setFont(new Font("Arial", Font.BOLD, 15));
		resolution.setBackground(Color.GREEN);
		resolution.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Resolution");
				
			}
		});
		fenetre.add(resolution,Component.CENTER_ALIGNMENT);
		
		//Canvas
		representation = new Canvas();
		representation.setSize(500, 500);
		frame.add(representation, BorderLayout.EAST);
		
		//Options de frame
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	//Methodes
	void fisheye() {
		
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
