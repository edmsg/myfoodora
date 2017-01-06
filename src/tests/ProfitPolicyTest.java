package tests;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Customer;
import myfoodora.Manager;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;
import myfoodora.TargetProfitDeliveryCost;
import myfoodora.TargetProfitMarkupPercentage;
import myfoodora.TargetProfitServiceFee;

public class ProfitPolicyTest {

	MyFoodora sys = new MyFoodora();
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
	Manager antoine = new Manager("Me Q", "Antoine", "Cr", sys);
	
	@Test
	public void testComputeDeliveryCost() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setMarkupPercentage(.1);
		antoine.setServiceFee(2);
		
		o.computeFinalPrice();
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		
		TargetProfitDeliveryCost tpdc = new TargetProfitDeliveryCost(sys);
		
		antoine.setProfitPolicy(tpdc);
		antoine.setProfitVariablesToMeetTargetProfit(9);
		
		assertEquals(.21, sys.getDeliveryCost(), 1e-6);
		
	}

	@Test
	public void testComputeServiceFee() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setMarkupPercentage(.1);
		antoine.setDeliveryCost(1);
		
		o.computeFinalPrice();
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		
		TargetProfitServiceFee tpsf = new TargetProfitServiceFee(sys);
		
		antoine.setProfitPolicy(tpsf);
		antoine.setProfitVariablesToMeetTargetProfit(9);
		
		assertEquals(2.79, sys.getServiceFee(), 1e-6);
	}
	
	
	@Test
	public void testComputeMarkupPercentage() {
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		antoine.setServiceFee(2);
		antoine.setDeliveryCost(1);
		
		o.computeFinalPrice();
		
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		sys.getHistoryOfOrders().add(o);
		
		System.out.println(" pr" + o.getProfit());
		
		
		TargetProfitMarkupPercentage tpmk = new TargetProfitMarkupPercentage(sys);
		
		antoine.setProfitPolicy(tpmk);
		System.out.println(antoine.computeTotalProfit());
		antoine.setProfitVariablesToMeetTargetProfit(9);
		
		o.computeFinalPrice();
		
		assertEquals(2/12.1, sys.getMarkupPercentage(), 1e-6);
	}
	
}
