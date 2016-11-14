package myfoodora;

import java.util.ArrayList;

public class Order {
	private MyFoodora sys;
	private ArrayList<Item> items;
	private ArrayList<Meal> meals;
	private Customer customer;
	private Restaurant restaurant;
	private double price;
	
	public Order(ArrayList<Item> items, ArrayList<Meal> meals, Customer customer, Restaurant restaurant, MyFoodora sys){
		this.items = items;
		this.meals = meals;
		this.customer = customer;
		this.restaurant = restaurant;
		this.sys = sys;
		
		this.price = computeCost();
	}
	
	public double computeCost(){
		/**
		 * Computes the cost of the order according to :
		 * price = price_of_order (set by restaurant, including discounts like meal of the week) 
		 * + serviceFee (set by the system) 
		 * + price_of_order * markup_percentage
		 * - discount due to fidelity cards
		 */
		double priceOfOrder = 0;
		double total = 0;
		
		for(Item i : items){
			priceOfOrder += i.getPrice();
		}
		for(Meal m : meals){
			priceOfOrder += m.getPrice();
		}
		
		total = priceOfOrder*(1 + sys.getMarkupPercentage()) + sys.getServiceFee(); //TODO :  - discount due to cards
		
		return total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	
}
