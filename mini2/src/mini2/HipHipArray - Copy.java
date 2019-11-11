package mini2;

import java.util.Arrays;

import java.util.ArrayList;

public class HipHipArray
{
	private HipHipArray()
	{
		
	}

	public static void main(String[] args)
	{
			int[] array = {2,4,6,8};
			//System.out.println(Arrays.toString(shift(array)));
			shift(array, 1);
			System.out.println(Arrays.toString(array));
			System.out.println("Expected: [2, 3]");
	}

	public static int[] makeHistogram(double[] data, int numBins, double min, double max)
	{
		double binSize = (max - min)/ numBins;
		int counter = 0;
		int[] histogram = new int[numBins];
		for (int i = 0; i < numBins; i++)
		{
			counter = 0;
			for (int j = 0; j < data.length; j++)
			{
				if ((data[j] >= (min + (i*binSize))) && (data[j] < (min + ((i + 1)*binSize))))
				{
					counter ++;
				}
			}
			histogram[i] = counter;
		}
		return histogram;
	}

	public static boolean isPermutation(int[] arr)
	{
		if (arr.length == 0)
		{
			return false;
		}
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] > (arr.length - 1)||(arr[i] < 0))
			{
				return false;
			}
			for (int k = i + 1; k < arr.length; k++)
			{
				if (arr[k] == arr[i])
				{
					return false;
				}
			}
		}
		return true;
	}

	public static int[] createPalindrome(int[] arr)
	{
		if (arr.length == 0)
		{
			return new int[0];
		}
		int[] palindrome = new int[2*(arr.length) - 1];
		int k = 2;
		for (int i = 0; i < arr.length; i++)
		{
			palindrome[i] = arr[i];
		}
		for (int j = arr.length; j < 2*(arr.length) - 1; j++)
		{
			palindrome[j] = arr[arr.length - k];
			k++;
		}
		return palindrome;
	}

	public static boolean[] makeSieve(int size, int[] divisors)
	{
		int[] intArr = new int[size];
		boolean[] arr = new boolean[size];
		for (int i = 0; i < size; i++)
		{
			intArr[i] = i;
			arr[i] = true;
		}
		for (int j = 0; j < size; j++)
		{
			if (intArr[j] == 0)
			{
				arr[j] = false;
			}
			for (int k = 0; k < divisors.length; k++)
			{
				if (intArr[j] > divisors[k])
				{
					if (intArr[j]%divisors[k] == 0)
					{
						arr[j] = false;
					}
				}
			}
		}
		return arr;
	}

	public static void shift(int[] arr, int amount)
	{
		
	}

	public static void rotate(int[] arr, int amount)
	{
	    int offset = arr.length - amount % arr.length;
	    if (offset > 0) 
	    {
	        int[] copy = arr.clone();
	        for (int i = 0; i < arr.length; ++i) 
	        {
	            int j = (i + offset) % arr.length;
	            arr[i] = copy[j];
	        }
	    }
	}

	public static java.lang.String[] removeDups(java.lang.String[] arr)
	{
	    ArrayList<String> finalArr = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++)
		{
			if (finalArr.contains(arr[i]) == false)
			{
				finalArr.add(arr[i]);
			}
		}
		return finalArr.toArray(new String[finalArr.size()]);
	}

	public static int[] longestRun(int[] arr)
	{
        int index = 0, maxIndex = 0, length = 0, maxLength = 0;
        for(int i = 0; i < arr.length; ++i) 
        {
            if(i == 0) 
            {
                index = 0;
                length = 1;
            } 
            else 
            {
                if(arr[i] >= arr[i-1]) 
                {
                    length++;
                } 
                else 
                {
                    if(length > maxLength) 
                    {
                        maxIndex = index;
                        maxLength = length;
                    }
                    length = 1;
                    index = i;
                }
            }
        }
        if(length > maxLength) 
        {
            maxIndex = index;
            maxLength = length;
        }
        int[] result = new int[maxLength];
        for(int i = 0; i < maxLength; ++i) 
        {
            result[i] = arr[maxIndex+i];
        }
        return result;
	}
}
