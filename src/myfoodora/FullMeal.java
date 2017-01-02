package myfoodora;

import java.util.ArrayList;

public class FullMeal extends Meal implements java.io.Serializable {
	
	private static final long serialVersionUID = 560207794721421214L;
	
	public FullMeal(String name, ArrayList<Item> items, double price, ArrayList<String> type, boolean motw){
		super(name, items, price, type, motw);
		
	}
	
}
