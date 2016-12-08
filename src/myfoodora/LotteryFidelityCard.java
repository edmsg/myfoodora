package myfoodora;
import java.lang.Math ;



/**
 * A member that has this card will not access to any offer nor gain any points but will have a certain probability to gain her meal for free each day.
 * @author antoine
 *
 */

public class LotteryFidelityCard {
	
	public double computeNewPrice (double price){
		
		double nbAleatoire = Math.random();
		double p = 0.02;
		if (nbAleatoire <= p){
			price = 0;
		}
		return price;
	}

}
