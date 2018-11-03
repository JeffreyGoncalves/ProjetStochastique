package InterfaceGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import Données.Point;
import Problème.BooleanArrayHelper;

public class AffichageVilles extends JPanel implements MouseMotionListener, ComponentListener  {
	private static final long serialVersionUID = 1L;
	
	ArrayList<Point> coordonneesAbsolues;
	ArrayList<Point> coordonneesAffichage;
	Boolean[][] liaisons;
	
	private Rectangle.Double tailleFenetre = new Rectangle.Double();
	
	public AffichageVilles() {
		coordonneesAbsolues = new ArrayList<Point>();
		coordonneesAffichage = new ArrayList<Point>();
		
		addComponentListener(this);
		addMouseMotionListener(this);
	}

	public void calculCoordonneesAffichage() {
		coordonneesAffichage.clear();
		
		double tailleMax = (this.getWidth() > this.getHeight()) ? this.getHeight() : this.getWidth();
		double coefficientCote = tailleMax / 
				(tailleFenetre.height > tailleFenetre.width ? tailleFenetre.height : tailleFenetre.width);
		
		for (Point c : coordonneesAbsolues) {
			coordonneesAffichage.add(new Point((float)((c.getX() - tailleFenetre.x) * coefficientCote), 
												(float)((c.getY() - tailleFenetre.y) * coefficientCote)));
		}
	}

	public void setWindowBorders() {
		Point cityMin = new Point(coordonneesAbsolues.get(0).getX(), coordonneesAbsolues.get(0).getY());
		Point cityMax = new Point(coordonneesAbsolues.get(0).getX(), coordonneesAbsolues.get(0).getY());

		for (Point c : coordonneesAbsolues) {
			if (c.getX() < cityMin.getX()) {
				cityMin.setX(c.getX());
			} else if (c.getX() > cityMax.getX()) {
				cityMax.setX(c.getX());
			}
			if (c.getY() < cityMin.getY()) {
				cityMin.setY(c.getY());
			} else if (c.getY() > cityMax.getY()) {
				cityMax.setY(c.getY());
			}
		}

		tailleFenetre = new Rectangle.Double(cityMin.getX() - (cityMax.getX() - cityMin.getX()) * 0.02,
										cityMin.getY() - (cityMax.getY() - cityMin.getY()) * 0.02,
										cityMax.getX() - cityMin.getX() + (cityMax.getX() - cityMin.getX()) * 0.2,
										cityMax.getY() - cityMin.getY() + (cityMax.getY() - cityMin.getY()) * 0.2);		
	}

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int tailleCote = (this.getWidth() > this.getHeight()) ? this.getHeight() : this.getWidth();

		// Affichage du fond et choix de la couleur des villes
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, tailleCote, tailleCote);
		g.setColor(Color.RED);
		
		// Affichage des villes
		for (Point c : coordonneesAffichage) {
			g.fillRect((int) c.getX(), (int) c.getY(), 4, 4);
		}
		
		// Affichage des liaisons
		if(liaisons != null)
			paintPath(g);
	}
	
	public void paintPath(Graphics2D g) {
		g.setColor(Color.BLACK); 
		int nbVilles = coordonneesAbsolues.size();
		for (int i = 0; i < nbVilles; i++) {
			for (int j = 0; j < nbVilles; j++) {
				if(liaisons[i][j]) {
					Point debut = coordonneesAffichage.get(i);
					Point fin = coordonneesAffichage.get(j);
					g.drawLine((int)debut.getX() + 2, (int)debut.getY() + 2, (int)fin.getX() + 2, (int)fin.getY() + 2);	
				}
			}			
		}	
	}
	
	//Methodes redefinies
	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	//Getters & setters
	public void setCoordonneesAbsolues(ArrayList<Point> coordonnees) {
		this.coordonneesAbsolues = coordonnees;	
		liaisons = BooleanArrayHelper.initialise(coordonnees.size(), coordonnees.size());
		
		setWindowBorders();
		calculCoordonneesAffichage();
		repaint();
	}

	public void setLiaisons(Boolean[][] liaisons) {
		this.liaisons = liaisons;
		repaint();
	}
	
	public int getNbVilles() {
		return coordonneesAbsolues.size();
	}
}