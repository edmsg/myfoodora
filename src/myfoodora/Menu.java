package myfoodora;

import java.util.ArrayList;

/**
 * Each restaurant stores the proposed dishes in a menu.
 * It also stores the proposed pre-defined meals.
 * 
 * @author benjamin
 *
 */

public class Menu {
	private ArrayList<Item> starters;
	private ArrayList<Item> mainDishes;
	private ArrayList<Item> desserts;
	private ArrayList<Meal> meals;
	
	public Menu(){
		this.starters = new ArrayList<>();
		this.mainDishes = new ArrayList<>();
		this.desserts = new ArrayList<>();
		this.meals = new ArrayList<>();
	}
	
	public void addItem(Item i, String course){
		/**
		 * @param item the item to be added to the menu
		 * @param course to specify if the item is a starter ("s"), a main dish ("m"), or a dessert ("d")
		 */
		
		if(course.equals("s")){
			starters.add(i);
		}
		else if(course.equals("m")){
			mainDishes.add(i);
		}
		else if(course.equals("d")){
			desserts.add(i);
		}
		else{
			System.out.println("Invalid course parameter. The accepted parameters are \"s\" for starters, \"m\" for main dishes and \"d\" for desserts. Nothing was added.");
		}
	}
	
	public void removeItem(Item i, String course){
		/**
		 * There is an overloaded version of this function where the course parameter is detected automatically
		 * @param item the item to be removed from the menu
		 * @param course to specify if the item is a starter ("s"), a main dish ("m"), or a dessert ("d")
		 */
		if(course.equals("s")){
			if(starters.contains(i)){
				starters.remove(i);
			}
			else{
				System.out.println("No " + i.getName() + " was found in the starters. Nothing was removed.");
			}
		}
		else if(course.equals("m")){
			if(mainDishes.contains(i)){
				mainDishes.remove(i);
			}
			else{
				System.out.println("No " + i.getName() + " was found in the main dishes. Nothing was removed.");
			}
		}
		else if(course.equals("d")){
			if(desserts.contains(i)){
				desserts.remove(i);
			}
			else{
				System.out.println("No " + i.getName() + " was found in the desserts. Nothing was removed.");
			}
		}
		else{
			System.out.println("Invalid course parameter. The accepted parameters are \"s\" for starters, \"m\" for main dishes and \"d\" for desserts. Nothing was removed.");
		}
	}
	
	public void removeItem(Item i){
		/**
		 * This function finds where is located the item in the menu and removes it.
		 */
		if(starters.contains(i)){
			starters.remove(i);
		}
		else if(mainDishes.contains(i)){
			mainDishes.remove(i);
		}
		else if(desserts.contains(i)){
			desserts.remove(i);
		}
		else{
			System.out.println("No " + i.getName() + " was found in the menu. Nothing was removed.");
		}
		
	}
	
	public void addMeal(Meal m){
		meals.add(m);
	}
	
	public void removeMeal(Meal m){
		if(meals.contains(m)){
			meals.remove(m);
		}
		else{
			System.out.println("The menu was not found. Nothing was removed.");
		}
	}
	
	public String menuText(){
		String s = "Menu :\nStarters :\n";
		for(Item i : starters){
			s += i.getName() + "\n";
		}
		s += "\nMain Dishes :\n";
		for(Item i : mainDishes){
			s += i.getName() + "\n";
		}
		s += "\nDesserts :\n";
		for(Item i : desserts){
			s += i.getName() + "\n";
		}
		//add meals
		s += "\nMeals :";
		for(Meal m : meals){
			s += "\n";
			for(Item i : m.getItems()){
				s += i.getName() + " ";
			}
		}
		
		return s;
	}

	public ArrayList<Item> getStarters() {
		return starters;
	}

	public ArrayList<Item> getMainDishes() {
		return mainDishes;
	}

	public ArrayList<Item> getDesserts() {
		return desserts;
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}
	
}
