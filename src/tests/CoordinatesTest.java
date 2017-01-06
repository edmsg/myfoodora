package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import myfoodora.Coordinates;

public class CoordinatesTest {

	@Test
	public void testDistance() {
		Coordinates a = new Coordinates(1, 1);
		Coordinates b = new Coordinates(5, 4);
		
		assertEquals(5, a.distance(b), 1e-6);
	}

}
