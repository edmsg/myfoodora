package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MyFoodora;
import myfoodora.Restaurant;
import myfoodora.SortingPolicyHalfMeal;
import myfoodora.SortingPolicyItems;

public class SortingPolicyTest {
	MyFoodora sys = new MyFoodora();

	@Test
	public void testSortingPolicyHalfMeal(){
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		
		Item i1 = new Item("Salad", 2.5);
		Item i2 = new Item("Pasta", 5);
		Item i3 = new Item("Cake", 1.5);
		
		ArrayList<Item> a = new ArrayList<>();
		ArrayList<Item> b = new ArrayList<>();
		ArrayList<Item> c = new ArrayList<>();
		
		a.add(i1);
		a.add(i2);
		a.add(i3);
		b.add(i1);
		b.add(i3);
		c.add(i1);
		c.add(i2);
		
		Meal m1 = ru.makeNewMeal("Meal 1", a, false);
		Meal m2 = ru.makeNewMeal("Meal 2", b, false);
		Meal m3 = ru.makeNewMeal("Meal 3", c, false);
		
		ru.addMealToMenu(m1);
		ru.addMealToMenu(m2);
		ru.addMealToMenu(m3);
		
		ru.getMealCounter().put(m1, 5);
		ru.getMealCounter().put(m2, 3);
		ru.getMealCounter().put(m3, 1);
		
		SortingPolicyHalfMeal sphm = new SortingPolicyHalfMeal(false, sys);
		SortingPolicyHalfMeal sphmReverse = new SortingPolicyHalfMeal(true, sys);
		
		//Sorting from the most ordered to the least ordered
		sys.setSortingPolicy(sphm);
		assertEquals(m2.toString(), sys.getSortingPolicy().sortedShippedOrders(ru).get(0));
		
		//Sorting from the least ordered to the most ordered
		sys.setSortingPolicy(sphmReverse);
		assertEquals(m3.toString(), sys.getSortingPolicy().sortedShippedOrders(ru).get(0));
	}
	
	@Test
	public void testSortingPolicyItems(){
		Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(0, 0), sys);
		
		Item i1 = new Item("Salad", 2.5);
		Item i2 = new Item("Pasta", 5);
		Item i3 = new Item("Cake", 1.5);
		
		ru.addItemToMenu(i1, "s");
		ru.addItemToMenu(i2, "m");
		ru.addItemToMenu(i3, "d");
		
		
		
		ru.getItemCounter().put(i1, 5);
		ru.getItemCounter().put(i2, 3);
		ru.getItemCounter().put(i3, 1);
		
		SortingPolicyItems spi = new SortingPolicyItems(false, sys);
		SortingPolicyItems spiReverse = new SortingPolicyItems(true, sys);
		
		//Sorting from the most ordered to the least ordered
		sys.setSortingPolicy(spi);
		assertEquals(i1.toString(), sys.getSortingPolicy().sortedShippedOrders(ru).get(0));
		
		//Sorting from the least ordered to the most ordered
		sys.setSortingPolicy(spiReverse);
		assertEquals(i3.toString(), sys.getSortingPolicy().sortedShippedOrders(ru).get(0));
	}
	
	
	
}
