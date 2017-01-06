package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import myfoodora.HalfMeal;
import myfoodora.Item;
import myfoodora.Meal;
import myfoodora.MealFactory;

public class HalfMealTest {

	@Test
	public void testHalfMeal() {
		Item i1 = new Item("Salad", 2.5);
		Item i2 = new Item("Pasta", 5);
		
		
		Meal m = MealFactory.createMeal("Meal1", i1, i2, 15, null, false);
		
		assertTrue(m instanceof HalfMeal);
		assertTrue(m.getItems().contains(i1));
	}

}
