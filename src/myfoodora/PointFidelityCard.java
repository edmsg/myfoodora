package myfoodora;

/**
 *	Fidelity Card which adds points at each order, and grants a discount when a certain number of points is reached.
 */
public class PointFidelityCard implements FidelityCard, java.io.Serializable {
	private static final long serialVersionUID = -6233348177136503067L;
	
	private int points;
	
	public PointFidelityCard (){
		this.points = 0;
	}
	
	public double computeNewPrice (double price){
		
		while(points >= 100){
			price *= .9;
			points -= 100;
		}
		
		points += (int) price/10;
		
		return price;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
} 