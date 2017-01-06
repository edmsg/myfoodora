package myfoodora;
/**
 * A class to represent de positions and compute distances
 */
public class Coordinates implements java.io.Serializable{
	
	private static final long serialVersionUID = 2865038076734694774L;
	
	private double x;
	private double y;
	
	public Coordinates(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Computes the euclidian distance between this object and point
	 * @param point : the point we want to get the distance from
	 * @return distance from this object to point
	 */
	public double distance(Coordinates point){
		return Math.sqrt(Math.pow(this.x - point.getX(), 2) + Math.pow(this.y - point.getY(), 2));
	}
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	
}
