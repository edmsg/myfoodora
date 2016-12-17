package myfoodora;

import java.util.ArrayList;

/**
 * Item is the base class for representing various dishes
 * 
 */

public class Item implements java.io.Serializable {
	
	private static final long serialVersionUID = 8594445455607168104L;
	
	private String name;
	private double price;
	private ArrayList<String> type; //contains a list of keywords ("vegan", "gluten-free", etc.)
	
	
	public Item(String name, double price){
		/**
		 * Standard constructor (when the item has no special keyword like vegan or gluten-free)
		 */
		this.name = name;
		this.price = price;
		this.type = new ArrayList<>();
	}
	
	public Item(String name, double price, ArrayList<String> type){
		this.name = name;
		this.price = price;
		this.type = type;
	}
	
	@Override
	public String toString(){
		return name + ", for " + price;
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
