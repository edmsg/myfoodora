package myfoodora;

import org.junit.Test;

import static org.junit.Assert.*;



public class FidelityCardTest {
	
	@Test
	public void testCreateBasicFidelityCard() {
		FidelityCard card = FidelityCardFactory.createFidelityCard("basic");
		
		assertEquals(10, card.computeNewPrice(10), 1e-6);
	}
	
	@Test
	public void testCreateLotteryFidelityCard() {
		
		FidelityCard card = FidelityCardFactory.createFidelityCard("lottery");
		double newPrice = card.computeNewPrice(10);
		if(newPrice == 0){
			System.out.println("The order was earned for free");
			return ;
		}
		else{
			//In case the discount didn't happen
			assertEquals(10, newPrice, 1e-6);
		}
		
	}
	
	@Test
	public void testPointFidelityCard() {
		FidelityCard card = FidelityCardFactory.createFidelityCard ("point");
		
		double p1 = card.computeNewPrice(1020);
		double p2 = card.computeNewPrice(100);
		
		assertEquals(1020, p1, 1e-6);
		assertEquals(90, p2, 1e-6);
	}
}

		

