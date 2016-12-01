package myfoodora;

import java.util.ArrayList;

/**
 * Managers oversee the system. They add/remove users and set the general parameters of the system.
 * @author benjamin
 *
 */

public class Manager extends User{
	private String name;
	private String surname;
	
	public Manager(String username, String name, String surname, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		sys.addUser(this); //the manager is automatically added to the manager list of the system
	}
	
	public Manager addManager(String username, String name, String surname){
		return new Manager(username, name, surname, getSys()); //the manager is automatically added to the system through its construction : no need to add it a second time
	}
	
	public Restaurant addRestaurant(String username, String name, Coordinates address){
		Restaurant r = new Restaurant(username, name, address, getSys());
		return r;
	}
	
	public Courier addCourier(String username, String name, String surname, long phoneNumber){
		Courier c = new Courier(username, name, surname, phoneNumber, getSys());
		return c;
	}
	
	public Customer addCustomer(String username, String name, String surname, Coordinates address, String email, long phoneNumber){
		Customer c = new Customer(username, name, surname, address, email, phoneNumber, getSys());
		return c;
	}
	
	public void removeUser(User u){
		getSys().removeUser(u);
	}
	
	public double computeTotalIncome(){
		//TODO : add time period parameter
		ArrayList<Order> historyOfOrder = this.getSys().getHistoryOfOrders();
		double income = 0;
		for(Order o : historyOfOrder){
			income += o.getPrice();
		}
		return income;
	}
	
	public double computeTotalProfit(){
		//TODO : add time period parameter
		ArrayList<Order> historyOfOrder = this.getSys().getHistoryOfOrders();
		double profit = 0;
		for(Order o : historyOfOrder){
			profit += o.getPrice() - o.getDueToRestaurant() - getSys().getDeliveryCost();
		}
		
		return profit;
	}
	
	public void setProfitVariablesToMeetTargetProfit(double targetProfit){
		getSys().getProfitPolicy().computeVariablesToTargetProfit(targetProfit);
	}
	
	public void setProfitPolicy(ProfitPolicy pp){
		getSys().setProfitPolicy(pp);
	}
	
	

	public void setServiceFee(double serviceFee) {
		getSys().setServiceFee(serviceFee);
	}
	public void setMarkupPercentage(double markupPercentage) {
		getSys().setMarkupPercentage(markupPercentage);
	}
	public void setDeliveryCost(double deliveryCost) {
		getSys().setDeliveryCost(deliveryCost);
	}
	

}
