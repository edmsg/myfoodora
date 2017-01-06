package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Courier;
import myfoodora.Customer;
import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MealFactory;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;

public class CustomerTest {
	
	private MyFoodora sys = new MyFoodora();
	private Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	private Restaurant ru = new Restaurant("leRu", "Restaurant Universitaire", new Coordinates(3,3), sys);
	private Courier jodev = new Courier("jodev", "Joseph", "Dev", 000111222, "aa", sys);
	
	
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
		
		Meal m = MealFactory.createMeal("Meal1", salad, beefsteak, 5, null, false);
		
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Meal> meals = new ArrayList<>();
		
		items.add(sandwich);
		meals.add(m);
		
		jodev.setAvailable(true);
		
		Order o = antho.createOrderAndCommand(items, meals, ru);

		
		
		assertEquals(o, sys.getHistoryOfOrders().get(0));
	}

}
