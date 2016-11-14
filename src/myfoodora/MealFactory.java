package myfoodora;

import java.util.ArrayList;

public class MealFactory {
	
	
	public static Meal createMeal(ArrayList<Item> items, double price, ArrayList<String> type, boolean mealOfTheWeek){
		if(items.size() == 2){
			return new HalfMeal(items, price, type, mealOfTheWeek);
		}
		else if(items.size() == 3){
			return new FullMeal(items, price, type, mealOfTheWeek);
		}
		else{
			System.out.println(items.size() + " is a wrong number of items. No meal was created.");
		}
		return null;
	}
	
	
	/**
	 * 
	 * returns an instance of HalfMeal constituted of the two items item1 and item2
	 */
	public static Meal createMeal(Item item1, Item item2, double price, ArrayList<String> type, boolean mealOfTheWeek){
		ArrayList<Item> l = new ArrayList<>();
		l.add(item1);
		l.add(item2);
		return new HalfMeal(l, price, type, mealOfTheWeek);
	}
	
	/**
	 * returns an instance of FullMeal constituted of the 3 items item1, item2 and item3
	 */
	public static Meal createMeal(Item item1, Item item2, Item item3, double price, ArrayList<String> type, boolean mealOfTheWeek){
		ArrayList<Item> l = new ArrayList<>();
		l.add(item1);
		l.add(item2);
		l.add(item3);
		return new FullMeal(l, price, type, mealOfTheWeek);
	}
}
