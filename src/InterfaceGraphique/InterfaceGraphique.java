package InterfaceGraphique;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
		frame.setTitle("Projet d'Optimisation Stochastique");
		fenetre = new JPanel();
		fenetre.setLayout(new BoxLayout(fenetre,BoxLayout.PAGE_AXIS));
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options,BoxLayout.PAGE_AXIS));
		frame.add(fenetre, BorderLayout.WEST);
		fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		//ComboBox du choix du probleme
		String[] pbOptions = {"Problème du Voyageur de Commerce", "Autre"};
		choixProbleme = new JComboBox<String>(pbOptions) {

            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }

            
		};
		choixProbleme.setRenderer(new MyComboBoxRenderer("Choix du problème à résoudre"));
		choixProbleme.setSelectedIndex(-1);
		choixProbleme.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				choixP = (String) choixProbleme.getSelectedItem();
				
			}
		});
		options.add(choixProbleme,Component.CENTER_ALIGNMENT);
		
		
		//ComboBox du choix de la methode
		String[] methodesOptions = {"Recuit simulé déterministe", "Recuit simulé stochastique", "CPLEX déterministe", "CPLEX stochastique"};
		choixMethode = new JComboBox<String>(methodesOptions) {

            @Override
            public Dimension getMaximumSize() {
                Dimension max = super.getMaximumSize();
                max.height = getPreferredSize().height;
                return max;
            }

            
		};
		choixMethode.setRenderer(new MyComboBoxRenderer("Choix de la méthode de résolution"));
		choixMethode.setSelectedIndex(-1);
		choixMethode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				choixM = (String) choixMethode.getSelectedItem();
				
			}
		});
		options.add(choixMethode,Component.CENTER_ALIGNMENT);
		
		
		
		TitledBorder timeBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Options de résolution");
		Font timeFont = new Font("Arial",Font.BOLD,15);
		timeBorder.setTitleFont(timeFont);
		options.setBorder(timeBorder);
		fenetre.add(options,Component.CENTER_ALIGNMENT);
		
		fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		//Filechooser
		JPanel fichierPanel = new JPanel();
		JLabel ltextBox = new JLabel("Fichier choisi: ");
		JTextField textBox = new JTextField();
		fichierPanel.setLayout(new BoxLayout(fichierPanel, BoxLayout.PAGE_AXIS));
		fichierPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fichier = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		fichier.setDialogTitle("Choisissez un fichier de données");
		fichier.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
		fichier.addChoosableFileFilter(filter);
		choixFichier = new JButton("Ajouter un fichier de données");
		choixFichier.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int returnValue = fichier.showOpenDialog((Component)e.getSource());
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					cheminFichier = fichier.getSelectedFile().getPath();
					textBox.setText(cheminFichier);
				}
				
			}
		});
		
		fichierPanel.add(choixFichier, BorderLayout.WEST);
		
		  if(cheminFichier != null) {
			  
			  textBox.setText(cheminFichier);
		  }
		  else {
			  textBox.setText("Choisir un fichier");
		  }
		 textBox.setMaximumSize(new Dimension(500,20));
		 fichierPanel.add(ltextBox,Component.LEFT_ALIGNMENT);
		 fichierPanel.add(textBox, Component.LEFT_ALIGNMENT);
		 timeBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Sélection de fichier");
		 timeBorder.setTitleFont(timeFont);
		 fichierPanel.setBorder(timeBorder);
		 
		 fenetre.add(fichierPanel, BorderLayout.WEST);
		 fenetre.add(Box.createRigidArea(new Dimension(0,50)));
		
		//bouton de résolution
		JPanel boutonR = new JPanel();
		boutonR.setLayout(new BoxLayout(boutonR, BoxLayout.PAGE_AXIS));
		boutonR.setAlignmentX(Component.CENTER_ALIGNMENT);
		resolution = new JButton("Résolution");
		resolution.setFont(new Font("Arial", Font.BOLD, 30));
		resolution.setBackground(Color.GREEN);
		resolution.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Resolution");
				
			}
		});
		boutonR.add(resolution, Component.LEFT_ALIGNMENT);
		fenetre.add(boutonR);
		
		//Canvas
		representation = new Canvas();
		representation.setSize(400, 400);
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
