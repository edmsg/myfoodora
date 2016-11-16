package myfoodora;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrderTest {
	MyFoodora sys = new MyFoodora();
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	

	@Test
	public void testComputeCost() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		assertEquals(o.computeCost(), 12.1, 1e-6);
	}

	@Test
	public void testExampleOfOrder() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		assertEquals(o.getItems().get(0).getName(), "Salad");
		assertEquals(o.getMeals().get(0).getItems().get(0).getName(), "Salad");
	}

}
