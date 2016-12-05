package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

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
	
	/**
	 * Computes the total income, between dates start and stop
	 */
	public double computeTotalIncome(Calendar start, Calendar stop){
		
		ArrayList<Order> historyOfOrder = this.getSys().getHistoryOfOrders();
		double income = 0;
		for(Order o : historyOfOrder){
			System.out.println("Start : " + start.get(Calendar.DAY_OF_MONTH) + "/" +  start.get(Calendar.MONTH) + "/" + start.get(Calendar.YEAR));
			System.out.println("Stop : " + stop.get(Calendar.DAY_OF_MONTH) + "/" +  stop.get(Calendar.MONTH) + "/" + stop.get(Calendar.YEAR));
			System.out.println("current : " + o.getDate().get(Calendar.DAY_OF_MONTH) + "/" +  o.getDate().get(Calendar.MONTH) + "/" + o.getDate().get(Calendar.YEAR));
			if(start.before(o.getDate()) && stop.after(o.getDate())){
				income += o.getPrice();
			}
		}
		return income;
	}
	
	/**
	 * Computes the total profit, between dates start and stop
	 */
	public double computeTotalProfit(Calendar start, Calendar stop){
		
		ArrayList<Order> historyOfOrder = this.getSys().getHistoryOfOrders();
		double profit = 0;
		for(Order o : historyOfOrder){
			if(start.before(o.getDate()) && stop.after(o.getDate())){
				profit += getSys().getMarkupPercentage()*o.getDueToRestaurant() + getSys().getServiceFee() - getSys().getDeliveryCost() - o.getDiscountDueToCards();
			}
		}
		return profit;
	}
	
	/**
	 * Find and return the restaurant in the database which completed the most orders
	 */
	public Restaurant mostSellingRestaurant(){
		Restaurant best = getSys().getRestaurants().get(0); //initialisation with the first restaurant
		for(Restaurant r : getSys().getRestaurants()){
			if(r.getCounter() > best.getCounter()){
				best = r;
			}
		}
		return best;
	}
	
	/**
	 * Find and return the restaurant in the database which completed the least orders
	 */
	public Restaurant leastSellingRestaurant(){
		Restaurant best = getSys().getRestaurants().get(0); //initialisation with the first restaurant
		for(Restaurant r : getSys().getRestaurants()){
			if(r.getCounter() < best.getCounter()){
				best = r;
			}
		}
		return best;
	}
	
	/**
	 * Find and return the most active courier in the fleet
	 */
	public Courier mostActiveCourier(){
		Courier best = getSys().getCouriers().get(0); //initialisation with the first restaurant
		for(Courier c : getSys().getCouriers()){
			if(c.getCounter() > best.getCounter()){
				best = c;
			}
		}
		return best;
	}
	
	/**
	 * Find and return the least active courier in the fleet
	 */
	public Courier leastActiveCourier(){
		Courier best = getSys().getCouriers().get(0); //initialisation with the first restaurant
		for(Courier c : getSys().getCouriers()){
			if(c.getCounter() < best.getCounter()){
				best = c;
			}
		}
		return best;
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
