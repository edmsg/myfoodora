package myfoodora;

import java.util.ArrayList;

/**
 * This is the core part of the system. It stores all users and deals with general system actions, and store the general parameters..
 * @author benjamin
 *
 */

public class MyFoodora implements Observable, java.io.Serializable {
	
	private static final long serialVersionUID = -6071035973578485124L;
	
	private ArrayList<Manager> managers;
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Courier> couriers;
	private ArrayList<Customer> customers;
	private ArrayList<Order> historyOfOrders;
	private ArrayList<User> inactiveUsers; //to store the users that were turned to inactive by a manager
	private ArrayList<Observer> listenersSpecialOffers; //store the users that are interested in receiving new offers
	private DeliveryPolicy deliveryPolicy;
	private ProfitPolicy profitPolicy;
	private SortingPolicy sortingPolicy;
	private double serviceFee = 2;
	private double markupPercentage = .1;
	private double deliveryCost = 1;
	
	
	


	public MyFoodora(){
		managers = new ArrayList<>();
		restaurants = new ArrayList<>();
		couriers = new ArrayList<>();
		customers = new ArrayList<>();
		historyOfOrders = new ArrayList<>();
		inactiveUsers = new ArrayList<>();
		listenersSpecialOffers = new ArrayList<>();
		deliveryPolicy = new DeliveryPolicyFastest(this);
		profitPolicy = new TargetProfitDeliveryCost(this);
		sortingPolicy = new SortingPolicyHalfMeal(false, this);
	}
	
	
	public void newOrder(Order o){
		
		Courier c = findAvailableCourier(o);
		
		if(c != null){
			//System.out.println(c.getUsername());
			o.setCourier(c);
			historyOfOrders.add(o);
			o.getRestaurant().receiveOrder(o);
			o.getCustomer().receiveConfirmation(o);
		}
		else{
			System.out.println("No courier avalaible ! Try again later.");
		}
	}
	
	
	
	
	public void addUser(User u){
		if(u instanceof Manager){
			managers.add((Manager) u);
			System.out.println(u.getUsername() + " added to managers");
		}
		else if(u instanceof Restaurant){
			restaurants.add((Restaurant) u);
			System.out.println(u.getUsername() + " added to restaurants");
		}
		else if(u instanceof Courier){
			couriers.add((Courier) u);
			System.out.println(u.getUsername() + " added to couriers");
		}
		else if(u instanceof Customer){
			customers.add((Customer) u);
			System.out.println(u.getUsername() + " added to customers");
		}
	}
	
	public void removeUser(User u){
		if(u instanceof Manager){
			if(managers.contains(u)){
				managers.remove(u);
				System.out.println(u.getUsername() + " removed from " + u.getClass().getName());
			}
			else{
				System.out.println(u.getUsername() + " was not found in the managers. Nothing was removed.");
			}
		}
		else if(u instanceof Restaurant){
			if(restaurants.contains(u)){
				restaurants.remove(u);
				System.out.println(u.getUsername() + " removed from " + u.getClass().getName());
			}
			else{
				System.out.println(u.getUsername() + " was not found in the restaurants. Nothing was removed.");
			}
		}
		else if(u instanceof Courier){
			if(couriers.contains(u)){
				couriers.remove(u);
				System.out.println(u.getUsername() + " removed from " + u.getClass().getName());
			}
			else{
				System.out.println(u.getUsername() + " was not found in the couriers. Nothing was removed.");
			}
		}
		else if(u instanceof Customer){
			if(customers.contains(u)){
				customers.remove(u);
				System.out.println(u.getUsername() + " removed from " + u.getClass().getName());
			}
			else{
				System.out.println(u.getUsername() + " was not found in the customers. Nothing was removed.");
			}
		}
	}
	
	/**
	 * This function finds an available and volunteer courier to deliver the order o.
	 * @param o the order to be delivered
	 * @return c the courier that will take the delivery
	 */
	public Courier findAvailableCourier(Order o){
		ArrayList<Courier> couriersSearch = new ArrayList<>();
		//We need to make sure the courier is volunteer, so we need to search for the best available courier until one accepts the delivery
		couriersSearch.addAll(couriers);
		Courier c = null;
		
		//while we have couriers left
		while(!couriersSearch.isEmpty()){
			c = deliveryPolicy.findAvailableCourier(o, couriersSearch);
			if(c == null || c.acceptDelivery(o)){
				break;
			}
			//if the courier refuses the delivery, we keep searching for another one
			couriersSearch.remove(c);
		}
		if(c == null){
			System.out.println("No courier was found for this delivery");
		}
		return c;
	}
	
	public void storeInactiveUser(User u){
		inactiveUsers.add(u);
	}
	
	@Override
	public void registerObserver(Observer o){
		if(!listenersSpecialOffers.contains(o)){
			listenersSpecialOffers.add(o);
		}
	}
	
	@Override
	public void removeObserver(Observer o){
		if(listenersSpecialOffers.contains(o)){
			listenersSpecialOffers.remove(o);
		}
	}
	
	@Override
	public void notifyObservers(String message){
		for(Observer o : listenersSpecialOffers){
			o.update(message);
		}
	}
	
	public User lookForUserByUsername(String username){
		for(Customer c : customers){
			if(c.getUsername().equals(username)){
				return (User) c;
			}
		}
		for(Courier c : couriers){
			if(c.getUsername().equals(username)){
				return (User) c;
			}
		}
		for(Restaurant r : restaurants){
			if(r.getUsername().equals(username)){
				return (User) r;
			}
		}
		for(Manager m : managers){
			if(m.getUsername().equals(username)){
				return (User) m;
			}
		}
		System.out.println("User " + username + " was not found.");
		return null;
		
	}
	
	public User lookForInactiveUser(String username){
		for(User u : inactiveUsers){
			if(u.getUsername().equals(username)){
				return u;
			}
		}
		System.out.println(username + " was not found in the users.");
		return null;
	}
	
	public ArrayList<Manager> getManagers() {
		return managers;
	}

	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	public ArrayList<Courier> getCouriers() {
		return couriers;
	}

	public ArrayList<Customer> getCustomers() {

		return customers;
	}
	
	public double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public double getMarkupPercentage() {
		return markupPercentage;
	}

	public void setMarkupPercentage(double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}

	public double getDeliveryCost() {
		return deliveryCost;
	}

	public void setDeliveryCost(double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}
	
	public ArrayList<Order> getHistoryOfOrders() {
		return historyOfOrders;
	}
	
	public void setDeliveryPolicy(DeliveryPolicy dp){
		deliveryPolicy = dp;
	}
	
	public void setProfitPolicy(ProfitPolicy pp){
		profitPolicy = pp;
	}

	public ProfitPolicy getProfitPolicy() {
		return profitPolicy;
	}

	public ArrayList<User> getInactiveUsers() {
		return inactiveUsers;
	}


	public SortingPolicy getSortingPolicy() {
		return sortingPolicy;
	}


	public void setSortingPolicy(SortingPolicy sortingPolicy) {
		this.sortingPolicy = sortingPolicy;
	}

	public ArrayList<Observer> getListenersSpecialOffers() {
		return listenersSpecialOffers;
	}
	
	
	
	

}
