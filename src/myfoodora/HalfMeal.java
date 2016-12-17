package myfoodora;

import java.util.ArrayList;

public class HalfMeal extends Meal implements java.io.Serializable {
	
	private static final long serialVersionUID = 1186577056733883012L;

	public HalfMeal(String name, ArrayList<Item> items, double price, ArrayList<String> type, boolean motw){
		super(items, price, type, motw);
	}
	
	@Override
	public String toString(){
		String s = "Half-meal : ";
		for(Item i : getItems()){
			s += i.getName() + ", ";
		}
		s += "for " + getPrice() + " euros.";
		
		return s;
	}
}

