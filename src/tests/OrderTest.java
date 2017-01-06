package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Customer;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;

public class OrderTest {
	MyFoodora sys = new MyFoodora();
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	

	@Test
	public void testComputeCost() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		sys.setDeliveryCost(1);
		sys.setMarkupPercentage(.1);
		sys.setServiceFee(2);
		o.computeFinalPrice();
		assertEquals(15.31, o.getPrice(), 1e-6);
	}

	@Test
	public void testExampleOfOrder() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		assertEquals(o.getItems().get(0).getName(), "Salad");
		assertEquals(o.getMeals().get(0).getItems().get(0).getName(), "Salad");
	}

}
