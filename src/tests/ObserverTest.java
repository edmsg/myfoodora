package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Customer;
import myfoodora.Meal;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;

public class ObserverTest {

	MyFoodora sys = new MyFoodora();
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	Customer alex = new Customer("alex", "Alex", "Del", new Coordinates(3, 3), "alex@1E.com", 012012012, "aa", sys);
	Customer jodev = new Customer("jodev", "Jo", "Dev", new Coordinates(3, 3), "jodev@1E.com", 012012012, "aa", sys);
	
	@Test
	public void testObserver(){
		
		antho.startListeningNewOffers();
		alex.startListeningNewOffers();
		jodev.startListeningNewOffers();
		jodev.stopListeningNewOffers();
		
		Order o = Order.exampleOfOrder(antho, ru, sys);
		Meal m  = o.getMeals().get(0);
		
		ru.setMealOfTheWeek(m);
		
		assertEquals(true, jodev.getMessages().isEmpty());
		assertEquals(1, alex.getMessages().size());
		assertEquals(1, antho.getMessages().size());
	}

}
