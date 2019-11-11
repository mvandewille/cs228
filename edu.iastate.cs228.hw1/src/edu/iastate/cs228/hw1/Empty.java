package edu.iastate.cs228.hw1;

/**
 *  
 * @author Max Van de Wille
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{
	public Empty (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Plain pNew)
	{ 
		// See Living.java for an outline of the function. 
		// See the project description for corresponding survival rules. 
		int[] popArray = new int[5];
		Living temp = null;
		census(popArray);
		
		//a) Rabbit, if more than one neighboring Rabbit;
		if (popArray[4] > 1)
		{
			temp = new Rabbit(pNew, row, column, 0);
		}
		else
		{
			//b) otherwise, Fox, if more than one neighboring Fox; 
			if (popArray[2] > 1)
			{
				temp = new Fox(pNew, row, column, 0);
			}
			else
			{
				//c) otherwise, Badger, if more than one neighboring Badger; 
				if (popArray[0] > 1)
				{
					temp = new Badger(pNew, row, column, 0);
				}
				else
				{
					//d) otherwise, Grass, if at least one neighboring Grass; 
					if (popArray[3] >= 1)
					{
						temp = new Grass(pNew, row, column);
					}
					//e) otherwise, Empty. 
					else
					{
						temp = new Empty(pNew, row, column);
					}
				}
			}
		}
		return temp; 
	}
}
