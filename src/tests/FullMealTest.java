package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.FullMeal;
import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MealFactory;

public class FullMealTest {

	@Test
	public void testFullMeal() {
		Item i1 = new Item("Salad", 2.5);
		Item i2 = new Item("Pasta", 5);
		Item i3 = new Item("Cake", 1.5);
		
		Meal m = MealFactory.createMeal("Meal1", i1, i2, i3, 15, null, false);
		
		assertTrue(m instanceof FullMeal);
		assertTrue(m.getItems().contains(i1));
	}

}
