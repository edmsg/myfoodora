package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import myfoodora.Coordinates;
import myfoodora.Customer;
import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MealFactory;
import myfoodora.MyFoodora;
import myfoodora.Order;
import myfoodora.Restaurant;

public class RestaurantTest {
	MyFoodora sys = new MyFoodora();
	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(1,1), sys);
	Customer antho = new Customer("antho", "Antho", "Gauv", new Coordinates(3, 3), "antho@1E.com", 012012012, "aa", sys);

	
	@Test
	public void testMakeNewMeal() {
		ArrayList<String> type = new ArrayList<>();
		type.add("vegetarian");
		
		
		Item i1 = new Item("Salad", 2, type);
		Item i2 = new Item("Pasta", 2, type);
		
		ArrayList<Item> items = new ArrayList<>();
		items.add(i1);
		items.add(i2);
		
		Meal m1 = ru.makeNewMeal("Meal 1", items, true);
		Meal m2 = MealFactory.createMeal("Meal1", items, 3.6, type, true);
		
		assertEquals(m2.getPrice(), m1.getPrice(), 1e-6);
		assertEquals(m1.getItems(), m2.getItems());
		
		HashSet<String> type1 = new HashSet<>(m1.getType());
		HashSet<String> type2 = new HashSet<>(m2.getType());
		
		assertEquals(type1, type2);
		
	}

	@Test
	public void testComputeMealPrice() {
		Item salad = new Item("Salad", 2);
		Item cake = new Item("Cake", 3);
		
		ArrayList<Item> items = new ArrayList<>();
		items.add(salad);
		items.add(cake);
		
		assertEquals(4.75, ru.computeMealPrice(items, false), 1e-6);
		assertEquals(4.5, ru.computeMealPrice(items, true), 1e-6);
	}

	@Test
	public void testCheckTypeMeal() {
		ArrayList<String> type1 = new ArrayList<>();
		ArrayList<String> type2 = new ArrayList<>();
		ArrayList<String> type3 = new ArrayList<>();
		
		//various types for items
		type1.add("gluten-free");
		type1.add("vegetarian");
		type1.add("homemade");
		
		type2.add("vegan");
		type2.add("homemade");
		type2.add("vegetarian");
		
		type3.add("vegetarian");
		type3.add("gluten-free");
		type3.add("homemade");
		
		Item i1 =  new Item("Salad", 3, type1);
		Item i2 =  new Item("Rice", 2, type2);
		Item i3 =  new Item("Cake", 4, type3);
		
		//we compute the type of the meal constituted of these 3 items
		
		ArrayList<Item> items = new ArrayList<>();
		items.add(i1);
		items.add(i2);
		items.add(i3);
		
		HashSet<String> setResult = new HashSet<String>(ru.checkTypeMeal(items));
		
		//the resulting menu should be homemade and vegetarian, but not vegan nor gluten-free
		
		HashSet<String> setExpected = new HashSet<>();
		setExpected.add("vegetarian");
		setExpected.add("homemade");
		
		assertEquals(setResult, setExpected);
	}
	
	@Test
	public void testCounterItemAndMeal(){
		Order o = Order.exampleOfOrder(antho, ru, sys);
		
		for(Item i : o.getItems()){
			ru.addItemToMenu(i, "s");
		}
		for(Meal m : o.getMeals()){
			ru.addMealToMenu(m);
		}
		
		ru.receiveOrder(o);
		ru.receiveOrder(o);
		
		assertEquals((Integer) 2, ru.getItemCounter().get(o.getItems().get(0)));
		assertEquals((Integer) 2, ru.getMealCounter().get(o.getMeals().get(0)));
	}

}
