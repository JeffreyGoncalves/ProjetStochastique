package Données;

public class Point {

	private float x;
	private float y;
	
	//Constructeurs
	public Point() {
		x = 0.0f;
		y = 0.0f;
	}
	
	public Point(float abs, float ord) {
		x = abs;
		y = ord;
	}

	//Getters & Setters
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
