package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Managers oversee the system. They add/remove users and set the general parameters of the system.
 *
 */

public class Manager extends User implements java.io.Serializable{
	
	private static final long serialVersionUID = -3739678524659475464L;
	
	private String name;
	private String surname;
	
	public Manager(String username, String name, String surname, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		sys.addUser(this); //the manager is automatically added to the manager list of the system
	}
	
	public Manager(String username, String name, String surname, String password, MyFoodora sys){
		super(username, password, sys);
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
		Courier best = getSys().getCouriers().get(0); //initialisation with the first courier
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
		Courier best = getSys().getCouriers().get(0); //initialisation with the first courier
		for(Courier c : getSys().getCouriers()){
			if(c.getCounter() < best.getCounter()){
				best = c;
			}
		}
		return best;
	}
	
	public double computeAverageIncomePerCustomer(){
		Calendar start = Calendar.getInstance();
		start.add(Calendar.YEAR, -1000);
		Calendar stop = Calendar.getInstance();
		stop.add(Calendar.YEAR, 1);
		double inc = computeTotalIncome(start, stop);
		int N = 0;
		//Counting the number of customers that have ordered at least one time
		for(Customer c : getSys().getCustomers()){
			if(!c.getHistoryOfOrders().isEmpty()){
				N++;
			}
		}
		return inc / N;
	}
	
	public void inactivateUser(User u){
		getSys().removeUser(u);
		getSys().storeInactiveUser(u);
	}
	
	public void activateUser(User u){
		getSys().addUser(u);
	}
	
	public void setProfitVariablesToMeetTargetProfit(double targetProfit){
		getSys().getProfitPolicy().computeVariablesToTargetProfit(targetProfit);
	}
	
	public void setProfitPolicy(ProfitPolicy pp){
		getSys().setProfitPolicy(pp);
	}
	
	public void setSortingPolicy(SortingPolicy sp){
		getSys().setSortingPolicy(sp);
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
