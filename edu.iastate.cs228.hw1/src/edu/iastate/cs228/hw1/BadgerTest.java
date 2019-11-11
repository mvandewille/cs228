package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class BadgerTest 
{
	@Test
	public void testWho()
	{
		Living badg = new Badger(null, 0, 0, 0);
		assertEquals(State.BADGER, badg.who());
	}
	
	@Test
	public void oldAge()
	{
		Plain plain1 = new Plain(1);
		Plain plain2 = new Plain(1);
		plain1.grid[0][0] = new Badger(plain1, 0, 0, 4);
		assertEquals(State.EMPTY, plain1.grid[0][0].next(plain2).who());
	}
	
	@Test
	public void testNext() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Fox(plain2, 1, 0, 0);
		assertEquals(expected.who(), plain1.grid[1][0].next(plain2).who());
	}
	
	@Test
	public void testNext2() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Empty(plain2, 1, 2);
		assertEquals(expected.who(), plain1.grid[1][2].next(plain2).who());
	}
	
	@Test
	public void testNext3() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Badger(plain2, 5, 3, 1);
		assertEquals(expected.who(), plain1.grid[5][3].next(plain2).who());
	}
	
	@Test
	public void testNextAge() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Fox(plain2, 1, 0, 0);
		assertEquals(((Animal)expected).myAge(), ((Animal)plain1.grid[1][0].next(plain2)).myAge());
	}
	
	@Test
	public void testNextAge2() throws FileNotFoundException
	{
		String filename = "public2-6x6.txt";
		Plain plain1 = new Plain(filename);
		Plain plain2 = new Plain(6);
		Living expected = new Badger(plain2, 5, 3, 1);
		assertEquals(((Animal)expected).myAge(), ((Animal)plain1.grid[5][3].next(plain2)).myAge());
	}
}
