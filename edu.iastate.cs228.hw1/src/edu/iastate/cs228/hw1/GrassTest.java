package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class GrassTest 
{
	@Test
	public void testWho()
	{
		Living empty = new Grass(null, 0, 0);
		assertEquals(State.GRASS, empty.who());
	}
	
	@Test
	public void testNext() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		assertEquals(State.GRASS, plain1.grid[4][3].next(plain2).who());
	}
	
	@Test
	public void testNext2() throws FileNotFoundException
	{
		Plain plain1 = new Plain(2);
		plain1.grid[0][0] = new Grass(plain1, 0, 0);
		plain1.grid[0][1] = new Rabbit(plain1, 0, 1, 0);
		plain1.grid[1][0] = new Rabbit(plain1, 1, 0, 0);
		plain1.grid[1][1] = new Rabbit(plain1, 1, 1, 0);
		Plain plain2 = new Plain(2);
		assertEquals(State.EMPTY, plain1.grid[0][0].next(plain2).who());
	}
}
