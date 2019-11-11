package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class EmptyTest 
{
	@Test
	public void testWho()
	{
		Living empty = new Empty(null, 0, 0);
		assertEquals(State.EMPTY, empty.who());
	}
	
	@Test
	public void testNext() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Rabbit(plain2, 0, 4, 0);
		assertEquals(expected.who(), plain1.grid[0][4].next(plain2).who());
	}
	
	@Test
	public void testNext2() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Fox(plain2, 0, 2, 0);
		assertEquals(expected.who(), plain1.grid[0][2].next(plain2).who());
	}
	
	@Test
	public void testNext3() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Badger(plain2, 4, 1, 0);
		assertEquals(expected.who(), plain1.grid[4][1].next(plain2).who());
	}
	
	@Test
	public void testNext4() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Grass(plain2, 4, 2);
		assertEquals(expected.who(), plain1.grid[4][2].next(plain2).who());
	}
	
	@Test
	public void testNext5() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Grass(plain2, 0, 5);
		assertEquals(expected.who(), plain1.grid[0][5].next(plain2).who());
	}

}
