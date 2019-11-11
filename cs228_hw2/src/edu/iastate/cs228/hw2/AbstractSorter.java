package edu.iastate.cs228.hw2;

/**
 *  
 * @author Max Van de Wille
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence. 
 * 
 */
public abstract class AbstractSorter
{
	
	protected Point[] points;    // array of points operated on by a sorting algorithm. 
	                             // stores ordered points after a call to sort(). 
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass constructor.
		 
	protected Comparator<Point> pointComparator = null;
	
	private Point referencePoint = null; 	      // common reference point for computing the polar angle 

	
	// Add other protected or private instance variables you may need. 
	

	protected AbstractSorter()
	{
		// No implementation needed. Provides a default super constructor to subclasses. 
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter, and QuickSorter.
	}
	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0)
		{
			throw new IllegalArgumentException("Given point array is null or of length 0");
		}
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++)
		{
			points[i] = pts[i];
		}
	}

		
	
	/**
	 * 
	 * @param p
	 * @throws IllegalArgumentException  if p == null 
	 */
	public void setReferencePoint(Point p) throws IllegalArgumentException 
	{
		if (p == null)
		{
			throw new IllegalArgumentException("Given point is null");
		}
		referencePoint = p;
	}
	

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order == 0, by y-coordinate
	 * if order == 1, and by polar angle with respect to referencePoint if order == 2. Assign the 
     * comparator to the variable pointComparator. 
     * 
     * If order == 2, the method cannot be called when referencePoint == null.  Call setRereferencePoint()
     * first to set referencePoint. 
	 * 
	 * Need to create an object of the PolarAngleComparator class and call the compareTo() method in the 
	 * Point class.  
	 * 
	 * @param order  0   by x-coordinate 
	 * 				 1   by y-coordinate
	 * 			     2   by polar angle w.r.t referencePoint 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 2
	 *         IllegalStateException if order == 2 and referencePoint == null; 
	 */
	public void setComparator(int order) throws IllegalArgumentException, IllegalStateException
	{
		if (order < 0 || order > 2)
		{
			throw new IllegalArgumentException();
		}
		if (order == 2 && referencePoint == null)
		{
			throw new IllegalStateException();
		}
		//for orders 0 and 1, boolean xORy must be set for points
		if (order == 0 || order == 1)
		{
			for (int i = 0; i < points.length; i++)
			{
				//xORy set to true so it will sort by x
				if (order == 0)
				{
					Point.setXorY(true);
				}
				//xORy set to false so it will sort by y
				else
				{
					Point.setXorY(false);
				}
			}
			//overrides compare to use built-in method in Point class that already accounts for sorting by x or y
			pointComparator = new Comparator<Point>()
					{
						@Override
						public int compare(Point p1, Point p2)
						{
							return p1.compareTo(p2);
						}
					};
		}
		if (order == 2)
		{
			//if sorting by polar angle, simply use compare from PolarAngleComparator
			pointComparator = new PolarAngleComparator(referencePoint);
		}
	}

	

	/**
	 * Use the created pointComparator to conduct sorting.  
	 * 
	 * Ought to be protected. Made public for testing. 
	 */
	public abstract void sort(); 
	
	
	/**
	 * Obtain the point in the array points[] that has median index 
	 * 
	 * @return	median point 
	 */
	public Point getMedian()
	{
		return points[points.length/2]; 
	}
	
	
	/**
	 * Copies the array points[] onto the array pts[]. 
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts)
	{
		//copies pts into instance array
		for (int i = 0; i < points.length; i++)
		{
			pts[i] = points[i];
		}
	}
	

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j)
	{
		//swaps points using temp as temporary storage
		Point temp = points[j];
		points[j] = points[i];
		points[i] = temp;
	}	
}
