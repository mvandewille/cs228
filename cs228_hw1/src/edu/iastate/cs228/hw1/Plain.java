package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{
		// Assumption: The input file is in correct format. 
		// 
		// You may create the grid plain in the following steps: 
		// 
		// 1) Reads the first line to determine the width of the grid.
		// 
		// 2) Creates a grid object. 
		// 
		// 3) Fills in the grid according to the input file. 
		// 
		// Be sure to close the input file when you are done. 
		File readFile = new File(inputFileName);
		Scanner widthScan = new Scanner(readFile);
		int widthCount = 0;
		while (widthScan.hasNextLine())
		{
			widthCount++;
			widthScan.nextLine();
		}
		widthScan.close();
		width = widthCount;
		Living[][] fileGrid = new Living[width][width];
		
		
		Scanner fileScan = new Scanner(readFile);
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < width; j++)
			{
				String temp = fileScan.next();
				if (temp.charAt(0) == 'E')
				{
					fileGrid[i][j] = new Empty(this, i, j);
				}
				if (temp.charAt(0) == 'G')
				{
					fileGrid[i][j] = new Grass(this, i, j);
				}
				if (temp.charAt(0) == 'F')
				{
					fileGrid[i][j] = new Fox(this, i, j, (temp.charAt(1) - '0'));
				}
				if (temp.charAt(0) == 'B')
				{
					fileGrid[i][j] = new Badger(this, i, j, (temp.charAt(1) - '0'));
				}
				if (temp.charAt(0) == 'R')
				{
					fileGrid[i][j] = new Rabbit(this, i, j, (temp.charAt(1) - '0'));
				}
			}
		}
		grid = fileGrid;
		fileScan.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param width  the grid 
	 */
	public Plain(int w)
	{
		width = w;
		grid = new Living[w][w];

	}
	
	
	public int getWidth()
	{  
		return width;
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random(); 
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int randomInt = generator.nextInt(5);
				if (randomInt == 0)
				{
					grid[i][j] = new Badger(this, i, j, 0);
				}
				if (randomInt == 1)
				{
					grid[i][j] = new Grass(this, i, j);
				}
				if (randomInt == 2)
				{
					grid[i][j] = new Rabbit(this, i, j, 0);
				}
				if (randomInt == 3)
				{
					grid[i][j] = new Fox(this, i, j, 0);
				}
				if (randomInt == 4)
				{
					grid[i][j] = new Empty(this, i, j);
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.
	 */
	public String toString()
	{
		String returnString = "";
		String tempString;
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < width; j++)
			{
				tempString = grid[i][j].who().name();
				if (grid[i][j].who() == State.GRASS || grid[i][j].who() == State.EMPTY)
				{
					returnString = returnString + tempString.charAt(0) + "  ";
				}
				else
				{
					returnString = returnString + tempString.charAt(0) + ((Animal) grid[i][j]).myAge() + " ";
				}
			}
			returnString = returnString + "\n";
		}
		return returnString;
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose.
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		// 1. Open the file.
		// 
		// 2. Write to the file. The five life forms are represented by characters 
		//    B, E, F, G, R. Leave one blank space in between. Examples are given in
		//    the project description.
		// 
		// 3. Close the file.
		File printFile = new File(outputFileName);
		PrintWriter printer = new PrintWriter(printFile);
		printer.write(this.toString());
		printer.close();
	}			
}
