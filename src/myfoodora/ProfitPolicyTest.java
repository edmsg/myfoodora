package myfoodora;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class ProfitPolicyTest {

	MyFoodora sys = new MyFoodora();
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Manager antoine = new Manager("Me Q", "Antoine", "Cr", sys);
	
	@Test
	public void testComputeDeliveryPolicy() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setMarkupPercentage(.1);
		antoine.setServiceFee(1);
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		
		TargetProfitDeliveryCost tpdc = new TargetProfitDeliveryCost(sys);
		
		antoine.setProfitPolicy(tpdc);
		antoine.setProfitVariablesToMeetTargetProfit(8);
		
		assertEquals(.21, sys.getDeliveryCost(), 1e-6);
		
	}

	@Test
	public void testComputeServiceFee() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setMarkupPercentage(.1);
		antoine.setDeliveryCost(.21);
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		
		TargetProfitServiceFee tpsf = new TargetProfitServiceFee(sys);
		
		antoine.setProfitPolicy(tpsf);
		antoine.setProfitVariablesToMeetTargetProfit(8);
		
		assertEquals(1, sys.getDeliveryCost(), 1e-6);
	}
	
	
	@Test
	public void testComputeMarkupPercentage() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setServiceFee(1);
		antoine.setDeliveryCost(.21);
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		
		TargetProfitMarkupPercentage tpmk = new TargetProfitMarkupPercentage(sys);
		
		antoine.setProfitPolicy(tpmk);
		antoine.setProfitVariablesToMeetTargetProfit(8);
		
		assertEquals(.1, sys.getDeliveryCost(), 1e-6);
	}
	
}
