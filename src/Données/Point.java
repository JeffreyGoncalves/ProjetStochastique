package Données;

public class Point {

	private int x;
	private int y;
	
	//Constructeurs
	public Point() {
		x = 0;
		y = 0;
	}
	
	public Point(int abs, int ord) {
		x = abs;
		y = ord;
	}

	//Getters & Setters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
