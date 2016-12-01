package myfoodora;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProfitPolicyTest {

	MyFoodora sys = new MyFoodora();
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Manager antoine = new Manager("Me Q", "Antoine", "Cr", sys);
	
	@Test
	public void testComputeDeliveryPolicy() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		TargetProfitDeliveryCost tpdc = new TargetProfitDeliveryCost(sys);
		
		
		antoine.setProfitPolicy(tpdc);
		antoine.setProfitVariablesToMeetTargetProfit(10);
		
		System.out.println(sys.getDeliveryCost());
		
		
	}

}
