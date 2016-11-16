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
	
	/**
	 * Computes the cost of the order according to :
	 * price = price_of_order (set by restaurant, including discounts like meal of the week) 
	 * + serviceFee (set by the system) 
	 * + price_of_order * markup_percentage
	 * - discount due to fidelity cards
	 * @return the cost of the order
	 */
	
	public double computeCost(){
		
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
	
	/**
	 * This function creates an example of order. We use it in tests because creating an order from scratch is quite time-consuming.
	 * @return an order containing various items and meals.
	 */
	public static Order exampleOfOrder(Customer customer, Restaurant restaurant, MyFoodora sys){
		
		ArrayList<String> type1 = new ArrayList<>();
		ArrayList<String> type2 = new ArrayList<>();
		
		//various types for items
		type1.add("vegetarian");
		type1.add("homemade");
		type2.add("vegetarian");
		
		Item i1 = new Item("Salad", 3, type1);
		Item i2 = new Item("Rice", 2, type2);
		Item i3 = new Item("Cake", 4);
		Item i4 = new Item("Bread", 1, type1); 
		
		
		ArrayList<Item> i = new ArrayList<>();
		i.add(i1);
		i.add(i2);
		i.add(i3);
		
		Meal m = restaurant.makeNewMeal(i, true);
		
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Meal> meals = new ArrayList<>();
		
		items.add(i1);
		items.add(i4);
		
		meals.add(m);
		
		return new Order(items, meals, customer, restaurant, sys);
	}

	public Customer getCustomer() {
		return customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}
	
	
}
