package myfoodora;

import java.util.ArrayList;

public class Restaurant extends User {
	private String name;
	private Coordinates address;
	private Menu menu;
	private double genericDiscountFactor;
	private double specialDiscountFactor;
	private ArrayList<Order> ordersToComplete;
	
	public Restaurant(String username, String name, Coordinates address, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.address = address;
		this.menu = new Menu();
		genericDiscountFactor = 0.05;
		specialDiscountFactor = 0.1;
		ordersToComplete = new ArrayList<>();
		sys.addUser(this);
	}
	
	public Meal makeNewMeal(ArrayList<Item> items, boolean mealOfTheWeek){
		double price = this.computeMealPrice(items, mealOfTheWeek);
		ArrayList<String> type = this.checkTypeMeal(items);
		
		return MealFactory.createMeal(items, price, type, mealOfTheWeek);
	}
	
	public double computeMealPrice(ArrayList<Item> items, boolean mealOfTheWeek){
		double price = 0;
		for(Item i : items){
			price += i.getPrice();
		}
		if(mealOfTheWeek){
			price *= (1 - specialDiscountFactor);
		}
		else{
			price *= (1 - genericDiscountFactor);
		}
		
		return price;
	}
	
	/**
	 * This function return the type of the meal constituted of items, based on the fact that a meal has a type if and only if all its items has this type.
	 * @param items
	 * @return type of the meal constituted of items
	 */
	public ArrayList<String> checkTypeMeal(ArrayList<Item> items){
		ArrayList<String> type = new ArrayList<>();
		//First we look at the types of the first item
		for(String s : items.get(0).getType()){
			type.add(s);
		}
		//we create an array to keep the result
		ArrayList<String> result = new ArrayList<>();
		result.addAll(type);
		
		//then we check if those types are contained in all other items
		for(String s : type){
			boolean b = true;
			for(Item i : items){
				if(!i.getType().contains(s)){
					b = false;
					break;
				}
			}
			//if the type was not contained in all items, then the meal does not have this type
			if(b == false){
				result.remove(s);
			}
			
			b = true;
		}
		return result;
	}

	
	
	public void receiveOrder(Order o){
		ordersToComplete.add(o);
	}
	
	public void orderCompleted(Order o){
		ordersToComplete.remove(o);
	}
	
	
	public Coordinates getAddress() {
		return address;
	}

	public void setGenericDiscountFactor(double genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
	}

	public void setSpecialDiscountFactor(double specialDiscountFactor) {
		this.specialDiscountFactor = specialDiscountFactor;
	}
	
	
	
}
