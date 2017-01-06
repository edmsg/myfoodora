package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MealFactory;
import myfoodora.Menu;

public class MenuTest {
	
	Menu m = new Menu();
	Item i1 = new Item("Salad", 2.5);
	Item i2 = new Item("Pasta", 5);
	
	
	
	

	@Test
	public void testAddItem() {
		m.addItem(i1, "s");
		assertEquals(m.getStarters().get(0).getName(), "Salad");
	}

	@Test
	public void testRemoveItemItemString() {
		m.addItem(i1, "s");
		m.removeItem(i1, "s");
		assertEquals(m.getStarters().size(), 0);
	}

	@Test
	public void testRemoveItemItem() {
		m.addItem(i1, "s");
		m.addItem(i2, "m");
		m.removeItem(i1);
		assertEquals(m.getStarters().size(), 0);
	}

	@Test
	public void testAddMeal() {
		Meal meal = MealFactory.createMeal("Meal1", i1, i2, 10, null, false);
		m.addMeal(meal);
		assertEquals(m.getMeals().get(0).getItems().size(), 2);
		
	}

	@Test
	public void testRemoveMeal() {
		Meal meal = MealFactory.createMeal("Meal1", i1, i2, 10, null, false);
		m.addMeal(meal);
		m.removeMeal(meal);
		assertEquals(m.getMeals().size(), 0);
	}

}
