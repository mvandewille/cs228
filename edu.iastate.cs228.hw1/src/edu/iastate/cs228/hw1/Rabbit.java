package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal 
{	
	/**
	 * Creates a Rabbit object.
	 * @param p: plain  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) 
	{
		plain = p;
		age = a;
		row = r;
		column = c;
	}
		
	// Rabbit occupies the square.
	public State who()
	{
		return State.RABBIT; 
	}
	
	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for a rabbit. 
		int[] popArray = new int[5];
		Living temp = null;
		census(popArray);
		if (age >= RABBIT_MAX_AGE)
		{
			temp = new Empty(pNew, row, column);
		}
		else
		{
			if (popArray[3] == 0)
			{
				temp = new Empty(pNew, row, column);
			}
			else
			{
				if (((popArray[0] + popArray[2]) >= popArray[4]) && (popArray[2] > popArray[0]))
				{
					temp = new Fox(pNew, row, column, 0);
				}
				else
				{
					if (popArray[0] > popArray[4])
					{
						temp = new Badger(pNew, row, column, 0);
					}
					else
					{
						temp = new Rabbit(pNew, row, column, age + 1);
					}
				}
				
			}
		}
		return temp; 
	}
}
