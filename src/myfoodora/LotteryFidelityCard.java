package myfoodora;
import java.lang.Math ;



/**
 *	A fidelity card that gives a certain probability to earn a meal for free every time an order is passed
 */

public class LotteryFidelityCard implements FidelityCard {
	
	public double computeNewPrice (double price){
		
		double nbAleatoire = Math.random();
		double p = 0.02;
		if (nbAleatoire <= p){
			price = 0;
		}
		return price;
	}

}