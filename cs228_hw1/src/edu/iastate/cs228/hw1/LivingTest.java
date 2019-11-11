package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

/**
 * 
 * @author Max Van de Wille
 *
 */
public class LivingTest 
{
	public void censusTest() throws FileNotFoundException
	{
		String file = "public2-3x3.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 1;
		compareArr[1] = 0;
		compareArr[2] = 2;
		compareArr[3] = 1;
		compareArr[4] = 0;
		blank.grid[0][0].census(population);
		assertEquals(compareArr, population);
	}
	
	public void censusTest2() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 1;
		compareArr[1] = 2;
		compareArr[2] = 1;
		compareArr[3] = 1;
		compareArr[4] = 1;
		blank.grid[0][3].census(population);
		assertEquals(compareArr, population);
	}
	
	public void censusTest3() throws FileNotFoundException
	{
		String file = "public3-10x10.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 0;
		compareArr[1] = 1;
		compareArr[2] = 0;
		compareArr[3] = 3;
		compareArr[4] = 0;
		blank.grid[0][9].census(population);
		assertEquals(compareArr, population);
	}
	
	public void censusTest4() throws FileNotFoundException
	{
		String file = "public3-10x10.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 0;
		compareArr[1] = 0;
		compareArr[2] = 0;
		compareArr[3] = 0;
		compareArr[4] = 9;
		blank.grid[6][4].census(population);
		assertEquals(compareArr, population);
	}
	
	public void censusTest5() throws FileNotFoundException
	{
		String file = "public2-6x6.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 1;
		compareArr[1] = 2;
		compareArr[2] = 1;
		compareArr[3] = 1;
		compareArr[4] = 1;
		blank.grid[5][3].census(population);
		assertEquals(compareArr, population);
	}
	
	public void censusTest6() throws FileNotFoundException
	{
		String file = "public1-3x3.txt";
		Plain blank = new Plain(file);
		int[] population = new int[5];
		int[] compareArr = new int[5];
		compareArr[0] = 1;
		compareArr[1] = 1;
		compareArr[2] = 4;
		compareArr[3] = 2;
		compareArr[4] = 1;
		blank.grid[1][1].census(population);
		assertEquals(compareArr, population);
	}
}
