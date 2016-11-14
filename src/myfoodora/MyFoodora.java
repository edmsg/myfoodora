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
	private double serviceFee;
	private double markupPercentage;
	private double deliveryCost;
	
	
	


	public MyFoodora(){
		managers = new ArrayList<>();
		restaurants = new ArrayList<>();
		couriers = new ArrayList<>();
		customers = new ArrayList<>();
		historyOfOrders = new ArrayList<>();
		
	}
	
	
	public void newOrder(Order o){
		/**
		 * - transmit to restaurant
		 * - search for an available courier
		 * - receive ok from courier
		 * - confirms customer
		 * OK - save to history
		 */
		
		historyOfOrders.add(o);
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

}
