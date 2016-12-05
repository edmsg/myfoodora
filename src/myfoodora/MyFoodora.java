package myfoodora;

import java.util.ArrayList;

/**
 * This is the core part of the system. It stores all users and deals with general system actions, and store the general parameters..
 * @author benjamin
 *
 */

public class MyFoodora {
	private ArrayList<Manager> managers;
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Courier> couriers;
	private ArrayList<Customer> customers;
	private ArrayList<Order> historyOfOrders;
	private DeliveryPolicy deliveryPolicy;
	private ProfitPolicy profitPolicy;
	private double serviceFee;
	private double markupPercentage;
	private double deliveryCost;
	
	
	


	public MyFoodora(){
		managers = new ArrayList<>();
		restaurants = new ArrayList<>();
		couriers = new ArrayList<>();
		customers = new ArrayList<>();
		historyOfOrders = new ArrayList<>();
		deliveryPolicy = new DeliveryPolicyFastest(this);
		profitPolicy = new TargetProfitDeliveryCost(this);
	}
	
	
	public void newOrder(Order o){
		/**
		 * - transmit to restaurant
		 * - search for an available courier
		 * - receive ok from courier
		 * - confirms customer
		 * OK - save to history
		 */
		
		Courier c = findAvailableCourier(o);
		
		if(c != null){
			o.setCourier(c);
			historyOfOrders.add(o);
			o.getRestaurant().receiveOrder(o);
			o.getCustomer().receiveConfirmation(o);
		}
		else{
			//No available courier
			//TODO : notify the client
			
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
	

}
