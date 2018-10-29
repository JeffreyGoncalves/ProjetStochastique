package Données;

import java.util.Objects;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
						String description = new String(ch,start,length);
						description = description.replaceAll("\\D+", "");
						int sizeMatrix = Integer.parseInt(description);
						//System.out.println("Initializing cost matrix of size " + sizeMatrix);
						
						donnees.initialiserMatrice(sizeMatrix);
						donnees.ajouterCout(sizeMatrix - 1, sizeMatrix - 1, -1);
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

	//Getters & Setters
	public String getTypeFichier() {
		return typeFichier;
	}

	public void setTypeFichier(String typeFichier) {
		this.typeFichier = typeFichier;
	}
	
}
