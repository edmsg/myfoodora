package myfoodora;

import java.util.ArrayList;

public class Customer extends User {
	private String name;
	private String surname;
	private Coordinates address;
	private String email;
	private long phoneNumber;
	
	public Customer(String username, String name, String surname, Coordinates address, String email, long phoneNumber, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		sys.addUser(this);
	}
	
	public Order makeOrder(ArrayList<Item> items, ArrayList<Meal> meals, Restaurant restaurant){
		
		Order o = new Order(items, meals, this, restaurant, getSys());
		getSys().newOrder(o);
		return o;
	}
}