package myfoodora;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyFoodoraTest {

	MyFoodora sys = new MyFoodora();
	Courier jodev = new Courier("jodev", "Joseph", "Dev", 000111222, sys);
	Courier alex = new Courier("alex", "Alex", "Dela", 012012012, sys);
	Courier yvan = new Courier("vania", "Yvan", "Romich", 012012012, sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
	Manager antoine = new Manager("Me Q", "Antoine", "Cr", sys);
	
	@Test
	public void testNewOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddUser() {
		//The users were added to sys when created ; we just need to check they are well in sys 
		assertEquals(sys.getCouriers().size(), 3);
		assertEquals(sys.getRestaurants().get(0), ru);
		assertEquals(sys.getCustomers().get(0), antho);
		assertEquals(sys.getManagers().get(0), antoine);
		
	}

	@Test
	public void testRemoveUser() {
		sys.removeUser(jodev);
		sys.removeUser(alex);
		assertEquals(sys.getCouriers().get(0), yvan);
		assertEquals(sys.getCouriers().size(), 1);
	}

	@Test
	public void testFindAvailableCourier() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		jodev.setPosition(new Coordinates(1, 1));
		alex.setPosition(new Coordinates(2, 1));
		yvan.setPosition(new Coordinates(0, 1));
		
		yvan.setAvailable(false);
		
		
		assertEquals(sys.findAvailableCourier(o), alex);
		
		
		yvan.setAvailable(false);
		alex.setAvailable(false);
		jodev.setAvailable(false);
		
		assertEquals(sys.findAvailableCourier(o), null);
		
	}

}
