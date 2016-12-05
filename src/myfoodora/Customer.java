package myfoodora;

import java.util.ArrayList;

public class Customer extends User implements Observer {
	private String name;
	private String surname;
	private Coordinates address;
	private String email;
	private long phoneNumber;
	private ArrayList<Order> historyOfOrders;
	private ArrayList<String> messages; //to store the new offers until the customer checks in
	
	public Customer(String username, String name, String surname, Coordinates address, String email, long phoneNumber, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.historyOfOrders = new ArrayList<>();
		this.messages = new ArrayList<>();
		sys.addUser(this);
	}
	
	public Order makeOrder(ArrayList<Item> items, ArrayList<Meal> meals, Restaurant restaurant){
		
		Order o = new Order(items, meals, this, restaurant, getSys());
		getSys().newOrder(o);
		return o;
	}
	
	public void receiveConfirmation(Order o){
		//TODO : que faire ?
		System.out.println("The order is confirmed and will be delivered soon.");
		historyOfOrders.add(o);
	}
	
	@Override
	public void update(String message) {
		this.messages.add(message);
	}
	
	/**
	 * Checks for the messages stored since the last connection, and returns them. Then the messages are deleted.
	 * @return messages received since last connection (new offers from restaurants)
	 */
	public String lastMessages(){
		String result = "";
		for(String m : messages){
			result += m;
			messages.remove(m);
		}
		return result;
	}
	
	/**
	 * This function registers the user as listening to new special offers
	 */
	public void startListeningNewOffers() {
		getSys().registerObserver(this);
	}
	
	/**
	 * This function unregisters the user as listening to new special offers
	 */
	public void stopListeningNewOffers() {
		getSys().removeObserver(this);
	}
	

	public ArrayList<Order> getHistoryOfOrders() {
		return historyOfOrders;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	
	
	
	
}
