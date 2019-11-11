package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal 
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) 
	{
		plain = p;
		age = a;
		row = r;
		column = c;
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		return State.FOX; 
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a fox. 
		int[] popArray = new int[5];
		Living temp = null;
		census(popArray);
		
		//a) Empty if the Fox is currently at age 6; 
		if (age == FOX_MAX_AGE)
		{
			temp = new Empty(pNew, row, column);
		}
		else
		{
			//b) otherwise, Badger, if there are more Badgers than Foxes in the neighborhood; 
			if (popArray[0] > popArray[2])
			{
				temp = new Badger(pNew, row, column, 0);
			}
			else
			{
				//c) otherwise, Empty, if Badgers and Foxes together outnumber Rabbits in the neighborhood; 
				if ((popArray[0] + popArray[2]) > popArray[4])
				{
					temp = new Empty(pNew, row, column);
				}
				//d) otherwise, Fox (the fox will live on). 
				else
				{
					temp = new Fox(pNew, row, column, age + 1);
				}
			}
		}
		return temp;
	}
}
