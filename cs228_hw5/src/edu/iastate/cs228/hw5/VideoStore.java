package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner; 

/**
 * 
 * @author Max Van de Wille
 *
 */

public class VideoStore 
{
	protected SplayTree<Video> inventory;     // all the videos at the store
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement. 
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
    	inventory = new SplayTree<>();
    	setUpInventory(videoFile);
    }
    
    
   /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
     * add videos to the inventory by calling the addBST() method, which does not splay. 
     * 
     * Refer to Section 3.2 for the format of video file. 
     * 
     * @param  videoFile  correctly formated if exists
     * @throws FileNotFoundException 
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
    	File videoF = new File(videoFile);
    	Scanner fileScan = new Scanner(videoF);
    	while (fileScan.hasNextLine())
    	{
    		String tempVideo = fileScan.nextLine();
    		int numCop = parseNumCopies(tempVideo);
    		if (numCop <= 0)
    		{
    			numCop = 1;
    		}
    		Video newVid = new Video(parseFilmName(tempVideo), numCop);
    		inventory.addBST(newVid);
    	}
    }
	
    
    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film
     * @return
     */
	public Video findVideo(String film) 
	{
		Iterator<Video> iter = inventory.iterator();
		while (iter.hasNext())
		{
			Video temp = iter.next();
			if (film.equals(temp.getFilm()))
			{
				return temp;
			}
		}
		return null; 
	}


	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		Video toAdd = new Video(film, n);
		inventory.add(toAdd);
	}
	

	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		addVideo(film, 1);
	}
	

	/**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
    	File vidFile = new File(videoFile);
    	Scanner fileScan = new Scanner(vidFile);
    	while (fileScan.hasNextLine())
    	{
    		String vidString = fileScan.nextLine();
    		inventory.addBST(new Video(parseFilmName(vidString), parseNumCopies(vidString)));
    	}
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film)
	{
		if (findVideo(film).getNumAvailableCopies() > 0)
		{
			return true;
		}
		return false; 
	}

	
	
	/**
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		if (n <= 0 || film == null || film.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		Video tempVid = findVideo(film);
		if (tempVid == null)
		{
			throw new FilmNotInInventoryException();
		}
		if (tempVid.getNumAvailableCopies() <= 0)
		{
			throw new AllCopiesRentedOutException();
		}
		if (n > tempVid.getNumAvailableCopies())
		{
			tempVid.rentCopies(tempVid.getNumAvailableCopies());
		}
		else 
		{
			tempVid.rentCopies(n);
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		File vidFile = new File(videoFile);
		Scanner fileScan = new Scanner(vidFile);
		int errorPriority = 0;
		while (fileScan.hasNextLine())
		{
			String videoLine = fileScan.next();
			try 
			{
				 videoRent(parseFilmName(videoLine), parseNumCopies(videoLine));
			} 
			catch (IllegalArgumentException e)
			{
				System.out.println("Film " + parseFilmName(videoLine) + " has an invalid request");
				errorPriority = 1;
			}
			catch (FilmNotInInventoryException f)
			{
				System.out.println("Film " + parseFilmName(videoLine) + " is not in inventory");
				if (errorPriority > 2 || errorPriority < 1)
				{
					errorPriority = 2;
				}
			}
			catch (AllCopiesRentedOutException a)
			{
				System.out.println("Film " + parseFilmName(videoLine) + " has been rented out");
				if (errorPriority < 1)
				{
					errorPriority = 3;
				}
			}
		}
		if (errorPriority == 1)
		{
			throw new IllegalArgumentException();
		}
		else if (errorPriority == 2)
		{
			throw new FilmNotInInventoryException();
		}
		else if (errorPriority == 3)
		{
			throw new AllCopiesRentedOutException();
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		if (n <= 0 || film == null || film.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		Video tempVid = findVideo(film);
		if (tempVid == null)
		{
			throw new FilmNotInInventoryException();
		}
		if (tempVid.getNumRentedCopies() <= n)
		{
			tempVid.addNumCopies(tempVid.getNumRentedCopies());
		}
		else 
		{
			tempVid.addNumCopies(n);
		}
	}
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException												
	{
		File vidFile = new File(videoFile);
		Scanner fileScan = new Scanner(vidFile);
		int errorPriority = 0;
		while (fileScan.hasNextLine())
		{
			String videoLine = fileScan.next();
			try 
			{
				videoReturn(parseFilmName(videoLine), parseNumCopies(videoLine));
			} 
			catch (IllegalArgumentException e) 
			{
				System.out.println("Film " + parseFilmName(videoLine) + " has an invalid request");
				errorPriority = 1;
			}
			catch (FilmNotInInventoryException f)
			{
				System.out.println("Film " + parseFilmName(videoLine) + " is not in inventory");
				if (errorPriority == 0)
				{
					errorPriority = 2;
				}
			}
		}
		if (errorPriority == 1)
		{
			throw new IllegalArgumentException();
		}
		else if (errorPriority == 2)
		{
			throw new FilmNotInInventoryException();
		}
	}
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return
	 */
	public String inventoryList()
	{
		Iterator<Video> iter = inventory.iterator();
		String invList = "";
		while (iter.hasNext())
		{
			Video temp = iter.next();
			invList += "\n";
			invList += temp.getFilm();
			invList += " (" + temp.getNumCopies() + ")";
		}
		return invList; 
	}

	
	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return 
	 */
	public String transactionsSummary()
	{
		String transactions = "Rented films:\n\n";
		transactions += rentedVideosList() + "\n\n";
		transactions += "Films remaining in inventory:\n\n";
		transactions += unrentedVideosList();
		return transactions; 
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList()
	{
		Iterator<Video> iter = inventory.iterator();
		String videoList = "";
		while (iter.hasNext())
		{
			Video temp = iter.next();
			if (temp.getNumRentedCopies() > 0)
			{
				videoList += temp.getFilm();
				videoList += (" (" + temp.getNumCopies() + ")\n");
			}
		}
		return videoList; 
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList()
	{
		Iterator<Video> iter = inventory.iterator();
		String videoList = "";
		while (iter.hasNext())
		{
			Video temp = iter.next();
			videoList += temp.getFilm();
			if (temp.getNumAvailableCopies() != 0)
			{
				videoList += (" (" + temp.getNumCopies() + ")\n");
			}
		}
		return videoList; 
	}	

	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) 
	{
		Scanner lineScan = new Scanner(line);
		String videoTitle = "";
		int i = 0;
		while (lineScan.hasNext())
		{
			String temp = lineScan.next();
			if (temp.length() > 1 && temp.charAt(0) == '(' && Character.isDigit(temp.charAt(1)))
			{
				
			}
			else 
			{
				if (i > 0)
				{
					videoTitle += " ";
				}
				videoTitle += temp;
			}
			i++;
		}
		return videoTitle; 
	}
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) 
	{
		Scanner lineScan = new Scanner(line);
		int numCopies = 0;
		while (lineScan.hasNext())
		{
			String temp = lineScan.next();
			if (temp.charAt(0) == '(' && (Character.isDigit(temp.charAt(1)) || temp.charAt(1) == '-'))
			{
				String largeNum = "";
				int i = 1;
				while (Character.isDigit(temp.charAt(i)))
				{
					largeNum += temp.charAt(i);
					i++;
				}
				numCopies = Integer.parseInt(largeNum);
			}
		}
		return numCopies; 
	}
}
