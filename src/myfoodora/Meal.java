package myfoodora;

import java.util.ArrayList;

/**
 * Meals are sets of dishes, and benefit of a discount (default value : 5%)
 * They can be meals of the week, thus benefit of a greater discount (default : 10%)
 * 
 * For now, they come in two kinds : full meals (3 items), or half-meals (2 items).
 * We use a factory pattern so this can be easily extended in the future.
 * 
 *
 */

public abstract class Meal implements java.io.Serializable{
	private static final long serialVersionUID = 351718281812180285L;
	
	private String name;
	private double price;
	private ArrayList<Item> items;
	private ArrayList<String> type;
	private boolean mealOfTheWeek;
	
	
	
	public Meal(String name, ArrayList<Item> items, double price, ArrayList<String> type, boolean mealOfTheWeek){
		this.name = name;
		this.items = items;
		this.mealOfTheWeek = mealOfTheWeek;
		this.type = type;
		this.price = price;
	}
	
	public ArrayList<Item> getItems(){
		return items;
	}
	
	public double getPrice(){
		return price;
	}
	
	public ArrayList<String> getType() {
		return type;
	}

	public void setPrice(double price){
		this.price = price;
	}
	
	public void setMealOfTheWeek(boolean mealOfTheWeek) {
		this.mealOfTheWeek = mealOfTheWeek;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		String s = "";
		s += this.name + " :\n";
		for(Item i : items){
			s += i.getName() + " : " + i.getPrice();
			if(i.getType().size() != 0){
				s += " (";
				for(String type : i.getType()){
					s += type + " ";
				}
				s += ")";
			}
			s += "\n";
		}
		s += "Total price : " + this.price;
		if(type.size() != 0){
			s += " (";
			for(String t : type){
				s += t + " ";
			}
			s += ")";
		}
		s += "\n";
		s += "Special offer : " + this.mealOfTheWeek;
		
		return s;
	}

}
