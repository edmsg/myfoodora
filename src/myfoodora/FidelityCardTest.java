package myfoodora;

import org.junit.Test;

import static org.junit.Assert.*;



public class FidelityCardTest {
	
	@Test
	public void testCreateBasicFidelityCard() {
		FidelityCard card = FidelityCardFactory.createFidelityCard("basic fidelity card");
		
		assertEquals(10, card.computeNewPrice(10), 1e-6);
	}
	
	@Test
	public void testCreateLotteryFidelityCard() {
		
		FidelityCard card = FidelityCardFactory.createFidelityCard ("lottery fidelity card");
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
		FidelityCard card = FidelityCardFactory.createFidelityCard ("point fidelity card");
		
		double p1 = card.computeNewPrice(1020);
		double p2 = card.computeNewPrice(100);
		
		assertEquals(1020, p1, 1e-6);
		assertEquals(90, p2, 1e-6);
	}
	
	
	@Test
	public void testCustomerGetsDiscount(){
		MyFoodora sys = new MyFoodora();
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		
		antho.setFidelityCard(FidelityCardFactory.createFidelityCard("points fidelity card"));
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		System.out.println();
		
	}
}

		

