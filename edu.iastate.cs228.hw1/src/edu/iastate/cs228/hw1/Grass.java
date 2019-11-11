package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{
	public Grass (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew)
	{
		// See Living.java for an outline of the function. 
		// See the project description for the survival rules for grass. 
		int[] popArray = new int[5];
		Living temp = null;
		census(popArray);
		
		//a) Empty if at least three times as many Rabbits as Grasses in the neighborhood; 
		if ((popArray[3]*3) <= popArray[4])
		{
			temp = new Empty(pNew, row, column);
		}
		else
		{
			//b) otherwise, Rabbit if there are at least three Rabbits in the neighborhood;
			if (popArray[4] >= 3)
			{
				temp = new Rabbit(pNew, row, column, 0);
			}
			//c) otherwise, Grass. 
			else
			{
				temp = new Grass(pNew, row, column);
			}
		}
		return temp; 
	}
}
