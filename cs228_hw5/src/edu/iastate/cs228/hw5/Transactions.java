package edu.iastate.cs228.hw5;


import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Max Van de Wille
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 * @throws AllCopiesRentedOutException 
	 * @throws FilmNotInInventoryException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException
	{	
		// TODO 
		// 
		// 1. Construct a VideoStore object.
		// 2. Simulate transactions as in the example given in Section 4 of the 
		//    the project description. 
		
		System.out.println("Transactions at a Video Store");
		System.out.println("keys: 1 (rent) \t \t 2 (bulk rent)");
		System.out.println("      3 (return) \t 4 (bulk return)");
		System.out.println("      5 (summary) \t 6 (exit)");
		Scanner inputScan = new Scanner(System.in);
		VideoStore store = new VideoStore();
		while(true)
		{
			System.out.println("");
			System.out.print("Transaction: ");
			int keyInput = Integer.parseInt(inputScan.nextLine());
			if (keyInput == 1)
			{
				System.out.print("Film to rent: ");
				String videoIn = inputScan.nextLine();
				store.videoRent(VideoStore.parseFilmName(videoIn), VideoStore.parseNumCopies(videoIn));
			}
			else if (keyInput == 2)
			{
				System.out.print("Video file (rent)");
				store.bulkRent(inputScan.nextLine());
			}
			else if (keyInput == 3)
			{
				System.out.print("Film to return: ");
				String videoIn = inputScan.nextLine();
				store.videoReturn(VideoStore.parseFilmName(videoIn), VideoStore.parseNumCopies(videoIn));
			}
			else if (keyInput == 4)
			{
				System.out.print("Video file (return): ");
				store.bulkReturn(inputScan.nextLine());
			}
			else if (keyInput == 5)
			{
				System.out.print(store.transactionsSummary());
			}
			else if (keyInput == 6)
			{
				break;
			}
		}
		
	}
}
