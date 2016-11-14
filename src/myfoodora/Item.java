package myfoodora;

import java.util.ArrayList;

/**
 * Item is the base class for representing various dishes
 * 
 * @author benjamin
 */

public class Item {
	private String name;
	private double price;
	private ArrayList<String> type; //contains a list of keywords ("vegan", "gluten-free", etc.)
	
	
	public Item(String name, double price){
		/**
		 * Standard constructor (when the item has no special keyword like vegan or gluten-free)
		 */
		this.name = name;
		this.price = price;
		this.type = null;
	}
	
	public Item(String name, double price, ArrayList<String> type){
		this.name = name;
		this.price = price;
		this.type = type;
	}
	
	
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public ArrayList<String> getType() {
		return type;
	}
}
