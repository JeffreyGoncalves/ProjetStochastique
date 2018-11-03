package Données;

import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class ParserPVC extends Parser {

	//Attributs
	private String typeFichier;
	private DonneesPVC donnees;
	
	//Constructeur
	public ParserPVC(String fichier, DonneesPVC donnees) {
		super(fichier);
		this.donnees = donnees;
	}

	//Methodes
	public void lireDonnees() {
		typeFichier = "";

		//On recupere l'extension du fichier
		int i = fichier.lastIndexOf('.');
		if (i > 0) {
		    typeFichier = fichier.substring(i+1);
		    //System.out.println(typeFichier);
		}
		
		if(Objects.equals(typeFichier, "xml")) {
			parseXML();
			//Instant start = Instant.now();
			calculPos();      
			//Instant finish = Instant.now();
			//System.out.println("Calcul des positions : " + Duration.between(start, finish).toMillis() + "ms");
		}
		
		if(Objects.equals(typeFichier, "tsp")) {
			parseTSP();
		}
	}
	
	public void parseTSP() {
		
	}
	
	public void parseXML() {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		
		try {
		 	SAXParser parser = parserFactory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {
					
				int idCityA = -1;
				int idCityB = 0;
				
				boolean isGraph = false;
				boolean isDescription = false;
				boolean isVertex = false;
				boolean isEdge = false;
			
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException{
				
					if (qName.equalsIgnoreCase("description")) {
					
						isDescription = true;
					}
				
					if (qName.equalsIgnoreCase("graph")) {
					
						isGraph = true;
					}
				
					if (qName.equalsIgnoreCase("vertex")) {
						
						isVertex = true;
					}
				
					if (qName.equalsIgnoreCase("edge")) {
						
						isEdge = true;
						float coutActuel = Float.parseFloat(attributes.getValue(0));
						
						if(idCityA!=idCityB) {
							
							donnees.ajouterCout(idCityA, idCityB, coutActuel);
							//System.out.println("Filling cost matrix["+ idCityA + "][" + idCityB + "] with c = " + coutActuel);
						}
						else {
							
							if(coutActuel == 9999.0) {
								donnees.ajouterCout(idCityA, idCityB, -1);
							}
							else {
								
								donnees.ajouterCout(idCityA, idCityB, -1);
								donnees.ajouterCout(idCityA, idCityB + 1, coutActuel);
								idCityB++;
								
							}
						}
						
					}	
								
				}
				
				public void endElement(String uri, String localName, String qName) throws SAXException{
					if(qName.equalsIgnoreCase("vertex")) {	
						idCityB = 0;
					}
				}
				
				public void characters(char ch[], int start, int length) throws SAXException {
					//On initialise la matrice des couts
					if(isDescription) {	
						String description = new String(ch, start, length);
						description = description.replaceAll("\\D+", "");
						int tailleMatrice;
						if(description.equals("")) {
							tailleMatrice = Integer.parseInt(fichier.replaceAll("\\D+",""));
						}
						else {
							tailleMatrice = Integer.parseInt(description);
						}

						//System.out.println("Initializing cost matrix of size " + sizeMatrix);
						
						donnees.initialiserMatrice(tailleMatrice);
						donnees.ajouterCout(tailleMatrice - 1, tailleMatrice - 1, -1);
						isDescription = false;
					}
					
					if(isGraph) {
						isGraph = false;
					}
					
					if(isVertex) {
						isVertex = false;
						idCityA++;
					    //System.out.println("Parsing data for city " + idCityA);
					}
					
					if(isEdge) {
						isEdge = false;
						idCityB++;
					}
				}
			};
			
			parser.parse(fichier, handler);
			} catch (Exception e) {	 
			e.printStackTrace();
		}
	}
	
	private void calculPos() {
		int dim = donnees.getCouts().length;
		double[][] M = new double[dim][dim];
		int i = 0;
		int j = 0;
		
		//double[][] cout = convertFloatsToDoubles(donnees.getCouts());
		float[][] cout = donnees.getCouts();
		
		for (i = 0; i < dim; i++){
			for (j = 0; j < dim; j++)
				M[i][j] = ((cout[0][j])*(cout[0][j]) + (cout[i][0])*(cout[i][0]) - (cout[i][j])*(cout[i][j]))*0.5;
		}
		
		Matrix m = new Matrix(M);
		EigenvalueDecomposition e = m.eig();
		Matrix U = e.getV();
		Matrix S = e.getD();

		int rankrow = S.getRowDimension();
		int rankcol = S.getColumnDimension();
		
		for (i = 0; i < rankcol; i++){
			for (j = 0; j < rankrow; j++){
				double a = S.get(i, j);
				a = Math.sqrt(a);
				S.set(i, j, a);
			}
		}
		
		Matrix x = U.times(S);
		ArrayList<Point> coordonnees = new ArrayList<Point>();
		for(i = 0; i < dim; i++) {
			Point ville = new Point((float)(x.get(i, dim - (2 - 0))), (float)(x.get(i, dim - (2 - 1))));
			coordonnees.add(ville);
		}
		
		donnees.setCoordonnees(coordonnees);
	}

	//Getters & Setters
	public String getTypeFichier() {
		return typeFichier;
	}

	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}
	
}
