package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Courier;
import myfoodora.Customer;
import myfoodora.DeliveryPolicyFairOccupation;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;

public class DeliveryPolicyFairOccupationTest {

	MyFoodora sys = new MyFoodora();
	Courier jodev = new Courier("jodev", "Joseph", "Dev", 000111222, "aa", sys);
	Courier alex = new Courier("alex", "Alex", "Dela", 012012012, "aa", sys);
	Courier yvan = new Courier("vania", "Yvan", "Romich", 012012012, "aa", sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	
	@Test
	public void testFindAvailableCourier() {
		DeliveryPolicyFairOccupation dpf = new DeliveryPolicyFairOccupation(sys);
		
		Order o = Order.exampleOfOrder(antho, ru, sys);
		jodev.setCounter(5);
		alex.setCounter(3);
		yvan.setCounter(1);
		yvan.setAvailable(false);
		
		ArrayList<Courier> couriers = new ArrayList<>();
		couriers.add(jodev);
		couriers.add(alex);
		couriers.add(yvan);
		
		Courier c = dpf.findAvailableCourier(o, couriers);
		
		assertEquals(alex, c);
	}

}
