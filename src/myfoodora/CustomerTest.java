package myfoodora;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CustomerTest {
	
	private MyFoodora sys = new MyFoodora();
	private Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
	private Restaurant ru = new Restaurant("leRu", "Restaurant Universitaire", new Coordinates(3,3), sys);
	
	@Test
	public void testMakeOrder() {
		/**
		 * To test :
		 * - transmit to restaurant
		 * - search for an available courier
		 * - receive ok from courier
		 * - confirms customer
		 * OK - save to history
		 */
		
		Item sandwich = new Item("Sandwich", 2.5);
		Item salad = new Item("Salad", 1);
		Item beefsteak = new Item("Beefsteak", 5);
		
		Meal m = MealFactory.createMeal(salad, beefsteak, 5, null, false);
		
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Meal> meals = new ArrayList<>();
		
		items.add(sandwich);
		meals.add(m);
		
		Order o = antho.makeOrder(items, meals, ru);
		
		
		assertEquals(o, sys.getHistoryOfOrders().get(0));
	}

}
