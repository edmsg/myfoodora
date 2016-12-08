package myfoodora;

/**
 * A client can select to have this fidelity card. Instead of having the special offer she will gain a point for each 10 euros spent in the restaurant. Once she will reach 100 points she will receive a 10% discount on the next order.
 * @author antoine
 *
 */

public class PointFidelityCard {
	private int points;
	
	public PointFidelityCard (int points){
		this.points = points;
	}
	
	public double computeNewPrice (double price){
		if (points==100){
			price = 0.9*price;
		}
		points = points + (int) (price/10);
		return price;
	}
	
} 
