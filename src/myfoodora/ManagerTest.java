package myfoodora;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ManagerTest {
	
	MyFoodora sys = new MyFoodora();
	Manager john = new Manager("johnny", "John", "Doe", sys);
	
	
	
	@Test
	public void testAddManager() {
		john.addManager("Me Q", "Antoine", "Crepel");
		assertEquals("Me Q", sys.getManagers().get(1).getUsername());
	}

	@Test
	public void testAddRestaurant() {
		john.addRestaurant("ru", "le Ru", new Coordinates(10, 10.5));
		assertEquals("ru", sys.getRestaurants().get(0).getUsername());
	}

	@Test
	public void testAddCourier() {
		john.addCourier("jodev", "Jo", "Dev", 000111222);
		assertEquals("jodev", sys.getCouriers().get(0).getUsername());
	}

	@Test
	public void testAddCustomer() {
		john.addCustomer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012);
		assertEquals("antho", sys.getCustomers().get(0).getUsername());
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
		assertEquals(0, sys.getCouriers().size());
		assertEquals(0, sys.getCustomers().size());
		assertEquals(0, sys.getRestaurants().size());
		assertEquals(1, sys.getManagers().size());
	}
	
	@Test
	public void testTotalIncome() {
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		
		john.setDeliveryCost(1);
		john.setMarkupPercentage(.1);
		john.setServiceFee(2);
		
		Order a = Order.exampleOfOrder(antho, ru, sys);
		Order b = Order.exampleOfOrder(antho, ru, sys);
		Order c = Order.exampleOfOrder(antho, ru, sys);
		
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1);
		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.add(Calendar.YEAR, -1);
		
		a.setDate(oneYearAgo);
		
		sys.getHistoryOfOrders().add(a);
		sys.getHistoryOfOrders().add(b);
		sys.getHistoryOfOrders().add(c);
		
		System.out.println(a.getDueToRestaurant());
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		
		assertEquals(30.62, john.computeTotalIncome(oneMonthAgo, tomorrow), 1e-6);
		
	}
	
	@Test
	public void testTotalProfit() {
		
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		
		john.setDeliveryCost(1);
		john.setMarkupPercentage(.1);
		john.setServiceFee(2);
		
		Order a = Order.exampleOfOrder(antho, ru, sys);
		Order b = Order.exampleOfOrder(antho, ru, sys);
		Order c = Order.exampleOfOrder(antho, ru, sys);
		
		Calendar oneMonthAgo = Calendar.getInstance();
		oneMonthAgo.add(Calendar.MONTH, -1);
		Calendar oneYearAgo = Calendar.getInstance();
		oneYearAgo.add(Calendar.YEAR, -1);
		
		a.setDate(oneYearAgo);
		
		System.out.println(a.getDueToRestaurant());
		
		sys.getHistoryOfOrders().add(a);
		sys.getHistoryOfOrders().add(b);
		sys.getHistoryOfOrders().add(c);
		
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		
		assertEquals(4.42, john.computeTotalProfit(oneMonthAgo, tomorrow), 1e-6);
		
	}
	
	@Test
	public void testMostLeastSellingRestaurant(){
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Restaurant ru1 = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		Restaurant ru2 = new Restaurant("le ru 2", "Ru", new Coordinates(0, 0), sys);
		Restaurant ru3 = new Restaurant("le ru 3", "Ru", new Coordinates(0, 0), sys);
		
		Order a = Order.exampleOfOrder(antho, ru1, sys);
		Order b = Order.exampleOfOrder(antho, ru1, sys);
		Order c = Order.exampleOfOrder(antho, ru2, sys);
		
		ru1.receiveOrder(a);
		ru1.receiveOrder(b);
		ru2.receiveOrder(c);
		
		assertEquals(ru1, john.mostSellingRestaurant());
		assertEquals(ru3, john.leastSellingRestaurant());
	}
	
	@Test
	public void testMostLeastActiveCourier(){
		Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		Courier jodev = new Courier("jodev", "Joseph", "Dev", 000111222, sys);
		Courier alex = new Courier("alex", "Alex", "Dela", 012012012, sys);
		Courier yvan = new Courier("vania", "Yvan", "Romich", 012012012, sys);
		
		Order a = Order.exampleOfOrder(antho, ru, sys);
		Order b = Order.exampleOfOrder(antho, ru, sys);
		Order c = Order.exampleOfOrder(antho, ru, sys);
		
		jodev.acceptDelivery(a);
		jodev.acceptDelivery(b);
		alex.acceptDelivery(c);
		
		assertEquals(jodev, john.mostActiveCourier());
		assertEquals(yvan, john.leastActiveCourier());
	}

}
