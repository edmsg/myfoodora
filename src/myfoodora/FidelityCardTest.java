package myfoodora;

import org.junit.Test;

public class FidelityCardTest {
	
	@Test
	
	public double testBasicFidelityCard(double price) {
		/**
		 * To test :
		 * - create a new basic fidelity card
		 * - compute new price
		 */
		
		FidelityCard card = FidelityCardFactory.createFidelityCard ("basic fidelity card");
		double newPrice = card.computeNewPrice(price); 
		return newPrice;
	}
	
	public double testBLotteryFidelityCard(double price) {
		/**
		 * To test :
		 * - create a new lottery fidelity card
		 * - compute new price
		 */
		
		FidelityCard card = FidelityCardFactory.createFidelityCard ("lottery fidelity card");
		double newPrice = card.computeNewPrice(price); 
		return newPrice;
	}
	
	public double testPointFidelityCard(double price, int point) {
		/**
		 * To test :
		 * - create a new point fidelity card
		 * - compute new price
		 * - compute the points of each customer
		 */
		
		FidelityCard card = FidelityCardFactory.createFidelityCard ("point fidelity card");
		double newPrice = card.computeNewPrice(price); 
		return newPrice;
	}
}

		


