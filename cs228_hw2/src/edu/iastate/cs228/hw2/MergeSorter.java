package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if (pts.length != 1)
		{
			Point[] leftHalf = new Point[pts.length/2];
			Point[] rightHalf = new Point[pts.length - pts.length/2];
			for (int i = 0; i < leftHalf.length; i++)
			{
				leftHalf[i] = pts[i];
			}
			for (int j = leftHalf.length; j < pts.length; j++)
			{
				rightHalf[j - leftHalf.length] = pts[j];
			}
			mergeSortRec(leftHalf);
			mergeSortRec(rightHalf);
		}
	}

	
	// Other private methods in case you need ...

}
