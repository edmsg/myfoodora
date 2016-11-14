package myfoodora;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class RestaurantTest {

	Restaurant ru = new Restaurant("le ru", "Ru", new Coordinates(1,1));
	
	@Test
	public void testMakeNewMeal() {
		fail("Not yet implemented");
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

}
