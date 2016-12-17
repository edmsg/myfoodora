package myfoodora;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant extends User implements java.io.Serializable{
	
	private static final long serialVersionUID = -7729011553642502461L;
	
	private String name;
	private Coordinates address;
	private Menu menu;
	private int counter = 0; //counter of the number of orders sold
	private double genericDiscountFactor;
	private double specialDiscountFactor;
	private ArrayList<Order> ordersToComplete;
	private HashMap<Meal, Integer> mealCounter; //stores the number of time each meal has been ordered
	private HashMap<Item, Integer> itemCounter; //stores the number of time each item has been ordered
	
	public Restaurant(String username, String name, Coordinates address, MyFoodora sys){
		super(username, sys);
		this.name = name;
		this.address = address;
		this.menu = new Menu();
		genericDiscountFactor = 0.05;
		specialDiscountFactor = 0.1;
		ordersToComplete = new ArrayList<>();
		mealCounter = new HashMap<>();
		itemCounter = new HashMap<>();
		sys.addUser(this);
	}
	
	public Restaurant(String username, String name, Coordinates address, String password, MyFoodora sys){
		super(username, password, sys);
		this.name = name;
		this.address = address;
		this.menu = new Menu();
		genericDiscountFactor = 0.05;
		specialDiscountFactor = 0.1;
		ordersToComplete = new ArrayList<>();
		mealCounter = new HashMap<>();
		itemCounter = new HashMap<>();
		sys.addUser(this);
	}
	
	public Meal makeNewMeal(String name, ArrayList<Item> items, boolean mealOfTheWeek){
		double price = this.computeMealPrice(items, mealOfTheWeek);
		ArrayList<String> type = this.checkTypeMeal(items);
		
		return MealFactory.createMeal(name, items, price, type, mealOfTheWeek);
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
	
	public void addMealToMenu(Meal m){
		menu.addMeal(m);
		mealCounter.put(m, 0);
	}
	
	public void removeMealFromMenu(Meal m){
		menu.removeMeal(m);
		mealCounter.remove(m);
	}
	
	public void addItemToMenu(Item i, String course){
		menu.addItem(i, course);
		itemCounter.put(i, 0);
	}
	
	public void removeItemFromMenu(Item i){
		menu.removeItem(i);
		itemCounter.remove(i);
	}

	
	
	public void receiveOrder(Order o){
		ordersToComplete.add(o);
		counter++;
		for(Meal m : o.getMeals()){
			if(mealCounter.containsKey(m)){
				mealCounter.put(m, mealCounter.get(m) + 1);
			}
			else{
				mealCounter.put(m, 1);
			}
		}
		for(Item i : o.getItems()){
			if(itemCounter.containsKey(i)){
				itemCounter.put(i, itemCounter.get(i) + 1);
			}
			else{
				itemCounter.put(i, 0);
			}
		}
	}
	
	public void orderCompleted(Order o){
		ordersToComplete.remove(o);
	}
	
	public void setMealOfTheWeek(Meal m){
		m.setMealOfTheWeek(true);
		m.setPrice(computeMealPrice(m.getItems(), true));
		getSys().notifyObservers("New offer from the restaurant " + name + " : " + m.toString() + " is now meal of the week.");
	}
	
	public void setNotMealOfTheWeek(Meal m){
		m.setMealOfTheWeek(false);
		m.setPrice(computeMealPrice(m.getItems(), false));
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
	
	public int getCounter(){
		return counter;
	}

	public HashMap<Meal, Integer> getMealCounter() {
		return mealCounter;
	}

	public HashMap<Item, Integer> getItemCounter() {
		return itemCounter;
	}

	public Menu getMenu() {
		return menu;
	}
	
	
	
}
