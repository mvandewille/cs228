package edu.iastate.cs228.hw2;

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       RotationalPointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		RotationalPointScanner[] scanners = new RotationalPointScanner[4]; 
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() and draw() 
		//        methods in the RotationalPointScanner class.  You can visualize the result of each scan.  
		//        (Windows have to be closed manually before rerun.) 
		// 
		//     c) After all four scans are done for the input, print out the statistics table (cf. Section 2). 
		//
		// A sample scenario is given in Section 2 of the project description. 
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("keys: 1 (random integers)  2 (file input)  3 (exit)");
		boolean dummyLoopBreak = false;
		//sets up variable to break while loop if a 3 is inputted
		while (dummyLoopBreak == false)
		{
			Scanner inputScan = new Scanner(System.in);
			int trial = 1;
			System.out.print("Trial " + trial + ": ");
			int keyInput = inputScan.nextInt();
			if (keyInput == 1)
			{
				System.out.print("Enter number of random points: ");
				int numPts = inputScan.nextInt();
				Random randomGen = new Random();

				//sets new array of points created by generateRandomPoints
				Point[] randoms = new Point[numPts];
				randoms = generateRandomPoints(numPts, randomGen);
				//constructs each RotationalPointScanner using one of four sorting algorithms and pre-made randomized point array
				scanners[0] = new RotationalPointScanner(randoms, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(randoms, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(randoms, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(randoms, Algorithm.QuickSort);
			}
			if (keyInput == 2)
			{
				System.out.print("File name: ");
				String fileName = inputScan.next();
				
				//constructs each RotationalPointScanner using the file name constructor which will read points from the file
				scanners[0] = new RotationalPointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(fileName, Algorithm.QuickSort);
			}
			if (keyInput == 3)
			{
				//breaks the while loop, ending simulation
				dummyLoopBreak = true;
				break;
			}
			//runs through each rotationalPointScanner and calls scan() and draw() 
			for (int i = 0; i < scanners.length; i++)
			{
				scanners[i].scan();
				scanners[i].draw();
			}
			System.out.println();
			
			//calls stats() within the right frame
			System.out.println("algorithm \tsize \ttime (ns)");
			System.out.println("----------------------------------");
			System.out.println(scanners[0].stats());
			System.out.println(scanners[1].stats());
			System.out.println(scanners[2].stats());
			System.out.println(scanners[3].stats());
			System.out.println("----------------------------------");
			System.out.println();
			
			//moves on to next trial and restarts while loop
			trial++;
		}
	}
	
 
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if (numPts < 1)
		{
			throw new IllegalArgumentException();
		}
		//creates return array of same length as given number of points
		Point[] randomPoints = new Point[numPts];
		for (int i = 0; i < numPts; i++)
		{
			//sets new point equal to random X and Y coordinates within -50, 50 range
			randomPoints[i] = new Point((rand.nextInt(101) - 50), (rand.nextInt(101) - 50));
		}
		return randomPoints;
	}
	
}
