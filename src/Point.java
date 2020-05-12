
public class Point {
	private int radius; //Radius of a center
	private int X; //X coordinate of a center
	private int Y; //Y coordinate of a center
	private int number; //Point number
	
	public Point(int x, int y, int radius){
		this.X = x;
		this.Y = y;
		this.radius = radius;
	}

	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	public int getX() {
		return X;
	}
	
	public int getY() {
		return Y;
	}
	
	public int getRadius(){
		return radius;
	}
	public int getNumber() {
		return number;
	}
	
}
