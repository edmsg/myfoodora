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
	
	public Manager addManager(String username, String name, String surname, String password){
		return new Manager(username, name, surname, password, getSys()); //the manager is automatically added to the system through its construction : no need to add it a second time
	}
	
	public Restaurant addRestaurant(String username, String name, Coordinates address, String password){
		Restaurant r = new Restaurant(username, name, address, password, getSys());
		return r;
	}
	
	public Courier addCourier(String username, String name, String surname, long phoneNumber, String password){
		Courier c = new Courier(username, name, surname, phoneNumber, password, getSys());
		return c;
	}
	
	public Customer addCustomer(String username, String name, String surname, Coordinates address, String email, long phoneNumber, String password){
		Customer c = new Customer(username, name, surname, address, email, phoneNumber, password, getSys());
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
	public double computeProfit(Calendar start, Calendar stop){
		
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
	 * Computes the total profit since the system was created
	 */
	public double computeTotalProfit(){
		ArrayList<Order> historyOfOrder = this.getSys().getHistoryOfOrders();
		double profit = 0;
		for(Order o : historyOfOrder){
			profit += getSys().getMarkupPercentage()*o.getDueToRestaurant() + getSys().getServiceFee() - getSys().getDeliveryCost() - o.getDiscountDueToCards();
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
	 * same as precedent, but instead of searching in the whole restaurant list, we only search in the specified collection
	 */
	public Restaurant mostSellingRestaurant(ArrayList<Restaurant> restaurants){
		Restaurant best = restaurants.get(0); //initialisation with the first restaurant
		for(Restaurant r : restaurants){
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
	 * @return the list of restaurants sorted w.r.t. the number of orders completed
	 */
	
	public String listRestaurantsSortedOrders(){
		String res = "";
		ArrayList<Restaurant> toDisplay = new ArrayList<>();
		toDisplay.addAll(getSys().getRestaurants());
		while(!toDisplay.isEmpty()){
			Restaurant r = mostSellingRestaurant(toDisplay);
			res += r.getUsername() + " (" + r.getCounter() + ")\n";
			toDisplay.remove(r);
		}
		return res;
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
	 * same as precedent, but instead of searching in the whole fleet, we only search in the specified collection
	 */
	public Courier mostActiveCourier(ArrayList<Courier> couriers){
		Courier best = couriers.get(0);  //initialisation with the first courier
		for(Courier c : couriers){
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
	
	/**
	 * @return list of couriers sorted w.r.t. the number of deliveries they performed
	 */
	public String listCouriersSortedDeliveries(){
		String res = "";
		ArrayList<Courier> toDisplay = new ArrayList<>();
		toDisplay.addAll(getSys().getCouriers());
		while(!toDisplay.isEmpty()){
			Courier c = mostActiveCourier(toDisplay);
			res += c.getUsername() + " (" + c.getCounter() + ")\n";
			toDisplay.remove(c);
		}
		return res;
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
