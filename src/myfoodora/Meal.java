package myfoodora;

import java.util.ArrayList;

/**
 * Meals are sets of dishes, and benefit of a discount (default value : 5%)
 * They can be meals of the week, thus benefit of a greater discount (default : 10%)
 * 
 * For now, they come in two kinds : full meals (3 items), or half-meals (2 items).
 * We use a factory pattern so this can be easily extended in the future.
 * 
 * @author benjamin
 *
 */

public abstract class Meal {
	private double price;
	private ArrayList<Item> items;
	private ArrayList<String> type;
	private boolean MealOfTheWeek;
	
	
	
	public Meal(ArrayList<Item> items, double price, ArrayList<String> type, boolean mealOfTheWeek){
		this.items = items;
		this.MealOfTheWeek = mealOfTheWeek;
		this.type = type;
		this.price = price;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}

}
