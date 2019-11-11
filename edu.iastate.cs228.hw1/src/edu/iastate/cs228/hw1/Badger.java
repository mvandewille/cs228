package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	
	public Badger (Plain p, int r, int c, int a) 
	{
		plain = p;
		age = a;
		row = r;
		column = c;
	}
	
	/**
	 * A badger occupies the square.
	 */
	public State who()
	{
		return State.BADGER;
	}
	
	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group of foxes. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a badger. 
		
		//creating array to edit in census()
		int[] popArray = new int[5];
		
		//temporary Living object to store next Living type in for return
		Living temp = null;
		census(popArray);
		
		//a) Empty if the Badger is currently at age 4; 
		if (age >= BADGER_MAX_AGE)
		{
			temp = new Empty(pNew, row, column);
		}
		else
		{
			//b) otherwise, Fox, if there is only one Badger but there are more than one Fox in the neighborhood 
			if (popArray[0] == 1 && popArray[2] > 1)
			{
				temp = new Fox(pNew, row, column, 0);
			}
			else
			{
				//c) otherwise, Empty, if Badgers and Foxes together outnumber Rabbits in the neighborhood;
				if ((popArray[0] + popArray[2]) > popArray[4])
				{
					temp = new Empty(pNew, row, column);
				}
				//d) otherwise, Badger (the badger will live on). 
				else
				{
					//adds one cycle of age to the Badger
					temp = new Badger(pNew, row, column, (age + 1));
				}
			}
		}
		return temp; 
	}
}
