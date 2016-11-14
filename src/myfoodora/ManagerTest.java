package myfoodora;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManagerTest {
	
	private MyFoodora syst = new MyFoodora();
	private Manager john = new Manager("johnny", "John", "Doe", syst);
	
	@Test
	public void testAddManager() {
		john.addManager("Me Q", "Antoine", "Crepel");
		assertEquals("Me Q", syst.getManagers().get(1).getUsername());
	}

	@Test
	public void testAddRestaurant() {
		john.addRestaurant("ru", "le Ru", new Coordinates(10, 10.5));
		assertEquals("ru", syst.getRestaurants().get(0).getUsername());
	}

	@Test
	public void testAddCourier() {
		john.addCourier("jodev", "Jo", "Dev", 000111222);
		assertEquals("jodev", syst.getCouriers().get(0).getUsername());
	}

	@Test
	public void testAddCustomer() {
		john.addCustomer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012);
		assertEquals("antho", syst.getCustomers().get(0).getUsername());
	}

	@Test
	public void testRemoveUser() {
		Manager antoine = john.addManager("Me Q", "Antoine", "Crepel");
		Restaurant ru = john.addRestaurant("ru", "le ru", new Coordinates(10, 10.5));
		Courier jodev = john.addCourier("jodev", "Jo", "Dev", 000111222);
		Customer antho = john.addCustomer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012);
		
		john.removeUser(antoine);
		john.removeUser(ru);
		john.removeUser(jodev);
		john.removeUser(antho);
		
		//after these operations, every list should be empty, except managers which should contain john
		assertEquals(0, syst.getCouriers().size());
		assertEquals(0, syst.getCustomers().size());
		assertEquals(0, syst.getRestaurants().size());
		assertEquals(1, syst.getManagers().size());
	}

}
