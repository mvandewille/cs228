package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class WildlifeTest {

	@Test
	public void updatePlainTest() throws FileNotFoundException
	{
		Plain initial = new Plain("public1-3x3.txt");
		Plain intermediate = new Plain(3);
		Plain end = new Plain("public1-5cycles.txt");
		for(int i = 0; i < 5; i++)
		{
			if (i % 2 == 0)
			{
				Wildlife.updatePlain(initial, intermediate);
			}
			else
			{
				Wildlife.updatePlain(intermediate, initial);
			}
		}
		assertEquals(end.grid[0][0].who(), intermediate.grid[0][0].who());
		assertEquals(end.grid[0][1].who(), intermediate.grid[0][1].who());
		assertEquals(end.grid[0][2].who(), intermediate.grid[0][2].who());
		assertEquals(end.grid[1][0].who(), intermediate.grid[1][0].who());
		assertEquals(end.grid[1][1].who(), intermediate.grid[1][1].who());
		assertEquals(end.grid[1][2].who(), intermediate.grid[1][2].who());
		assertEquals(end.grid[2][0].who(), intermediate.grid[2][0].who());
		assertEquals(end.grid[2][1].who(), intermediate.grid[2][1].who());
		assertEquals(end.grid[2][2].who(), intermediate.grid[2][2].who());
	}
}
