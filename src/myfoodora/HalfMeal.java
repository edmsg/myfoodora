package myfoodora;

import java.util.ArrayList;

public class HalfMeal extends Meal implements java.io.Serializable {
	
	private static final long serialVersionUID = 1186577056733883012L;

	public HalfMeal(String name, ArrayList<Item> items, double price, ArrayList<String> type, boolean motw){
		super(name, items, price, type, motw);
	}
	
	
}

