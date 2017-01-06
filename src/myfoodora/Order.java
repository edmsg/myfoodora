package myfoodora;

import java.util.ArrayList;
import java.util.Calendar;

public class Order implements java.io.Serializable {
	
	private static final long serialVersionUID = -2863254009477402724L;
	
	private MyFoodora sys;
	private ArrayList<Item> items;
	private ArrayList<Meal> meals;
	private String name;
	private Customer customer;
	private Restaurant restaurant;
	private Courier courier = null; //initially no courier ; updated when a courier is defined
	private double price;
	private double profit;
	private double dueToRestaurant;
	private double discountDueToCards;
	private Calendar date;
	
	public Order(ArrayList<Item> items, ArrayList<Meal> meals, Customer customer, Restaurant restaurant, MyFoodora sys){
		this.profit = 0;
		this.items = items;
		this.meals = meals;
		this.customer = customer;
		this.restaurant = restaurant;
		this.sys = sys;
		this.date = Calendar.getInstance();
		
		this.dueToRestaurant = computeDueToRestaurant();
		this.price = computeCost();
		this.name = customer.getUsername() + restaurant.getUsername() + "Unfinished...";
	}
	
	public double computeDueToRestaurant(){
		double priceOfOrder = 0;
		
		for(Item i : items){
			priceOfOrder += i.getPrice();
		}
		for(Meal m : meals){
			priceOfOrder += m.getPrice();
		}
		
		return priceOfOrder;
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

		double total = this.dueToRestaurant*(1 + sys.getMarkupPercentage()) + sys.getServiceFee(); 
		return total;
	}
	
	/**
	 * Computes the cost of the order, taking in account the fidelity cards.
	 * This has to be performed only once, when the order is confirmed.
	 */
	public void computeFinalPrice(){
		double total = this.dueToRestaurant*(1 + sys.getMarkupPercentage()) + sys.getServiceFee(); 
		double totalWithRed = customer.getFidelityCard().computeNewPrice(total);
		
		discountDueToCards = total - totalWithRed;
		
		price = totalWithRed;
		
		name = customer.getUsername() + restaurant.getUsername() + Double.toString(price).substring(0,3);
		
		computeProfit();
	}
	
	public void computeProfit(){
		this.profit = sys.getMarkupPercentage()*this.dueToRestaurant + sys.getServiceFee() - sys.getDeliveryCost() - discountDueToCards;
	}
	
	public void addItemToOrder(Item i){
		items.add(i);
		//update the price :
		dueToRestaurant = computeDueToRestaurant();
		price = computeCost();
	}
	
	public void addMealToOrder(Meal m){
		meals.add(m);
		//update price
		dueToRestaurant = computeDueToRestaurant();
		price = computeCost();
	}
	
	public void removeItemFromOrder(Item i){
		items.remove(i);
		//update the price :
		dueToRestaurant = computeDueToRestaurant();
		price = computeCost();
	}
	
	public void removeMealFromOrder(Meal m){
		meals.remove(m);
		//update price
		dueToRestaurant = computeDueToRestaurant();
		price = computeCost();
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
		
		Meal m = restaurant.makeNewMeal("Meal 1", i, true);
		
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

	public double getPrice() {
		return price;
	}

	public double getDueToRestaurant() {
		return dueToRestaurant;
	}

	public double getDiscountDueToCards() {
		return discountDueToCards;
	}

	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date){
		this.date = date;
	}

	public Courier getCourier() {
		return courier;
	}

	public void setCourier(Courier courier) {
		this.courier = courier;
	}

	public String getName() {
		return name;
	}

	public double getProfit() {
		return profit;
	}
	
	
}
