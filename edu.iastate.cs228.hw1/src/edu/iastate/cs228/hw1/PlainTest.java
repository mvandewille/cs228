package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class PlainTest {

	@Test
	public void testWidth()
	{
		Plain testPlain = new Plain(9);
		assertEquals(9, testPlain.getWidth());
	}

	@Test
	public void testFileWidth() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain testPlain = new Plain(file);
		assertEquals(6, testPlain.getWidth());
	}
	
	@Test
	public void testFilePlain() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain testPlain = new Plain(file);
		assertEquals(State.RABBIT, testPlain.grid[3][3].who());
	}
	
	@Test
	public void testFilePlain2() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain testPlain = new Plain(file);
		assertEquals(State.FOX, testPlain.grid[0][3].who());
	}
	
	@Test
	public void testFilePlain3() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain testPlain = new Plain(file);
		assertEquals(State.BADGER, testPlain.grid[2][3].who());
	}
	
	@Test
	public void toStringTest() throws FileNotFoundException
	{
		Plain filePlain = new Plain("public1-3x3.txt");
		String testy = "G  B0 F0 \nF0 F0 R0 \nF0 E  G  \n";
		assertEquals(testy, filePlain.toString());
	}
	
	@Test
	public void printWriter() throws FileNotFoundException
	{
		Plain plain1 = new Plain("public1-3x3.txt");
		plain1.write("writerTest.txt");
		Plain plain3 = new Plain("writerTest.txt");
		assertEquals(plain3.grid[0][0].who(), plain1.grid[0][0].who());
		assertEquals(plain3.grid[0][1].who(), plain1.grid[0][1].who());
		assertEquals(plain3.grid[0][2].who(), plain1.grid[0][2].who());
		assertEquals(plain3.grid[1][0].who(), plain1.grid[1][0].who());
		assertEquals(plain3.grid[1][1].who(), plain1.grid[1][1].who());
		assertEquals(plain3.grid[1][2].who(), plain1.grid[1][2].who());
		assertEquals(plain3.grid[2][0].who(), plain1.grid[2][0].who());
		assertEquals(plain3.grid[2][1].who(), plain1.grid[2][1].who());
		assertEquals(plain3.grid[2][2].who(), plain1.grid[2][2].who());
	}
	
	@Test
	public void testRandom()
	{
		Plain test = new Plain(2);
		test.randomInit();
		assertNotEquals(test.grid[0][0].who(),null);
		assertNotEquals(test.grid[0][1].who(),null);
		assertNotEquals(test.grid[1][0].who(),null);
		assertNotEquals(test.grid[1][1].who(),null);
	}
}
