package myfoodora;

import java.util.ArrayList;

public class FullMeal extends Meal {
	
	public FullMeal(ArrayList<Item> items, double price, ArrayList<String> type, boolean motw){
		super(items, price, type, motw);
	}
	
	@Override
	public String toString(){
		String s = "Full meal : ";
		for(Item i : getItems()){
			s += i.getName() + ", ";
		}
		s += "for " + getPrice() + " euros.";
		
		return s;
	}
}
