package mini1;

import java.util.Arrays;

public class KeepMeInTheLoop
{
	private KeepMeInTheLoop()
	{
		
	}
	
	public static void main(String[] args)
	{
		System.out.println(containsWithGaps("hamburgers", "burrs"));
		System.out.println("true");
	}
	
	public static int findNth(java.lang.String s, char ch, int n)
	{
		int count = 0;
		if ((s.indexOf(ch) <= 0) || n <= 0)
		{
			return -1;
		}
		for (int i = 0; i < s.length(); i++ )
		{
			if (s.charAt(i) == ch)
			{
				count ++;
			}
			if (count == n)
			{
				return i;
			}
		}
		return -1;		
	}
	
	public static java.lang.String doubleConsonants(java.lang.String s)
	{
		String r = "";
		char ch = s.charAt(0);
		for (int i = 1; i < s.length(); i++)
		{
			if (ch == s.charAt(i))
			{
				r = r + ch + ch;
				if (i != s.length()-1)
				{
					i++;	
				}
			}
			else
			{
				if ("aeiouAEIOU".indexOf(ch) >= 0)
				{
					r = r + ch;
				}
				else
				{
					r = r + ch + ch;
				}
			}
			ch = s.charAt(i);
		}
		if ("aeiouAEIOU".indexOf(ch) == -1)
		{
			if (r.charAt(r.length()-2) != r.charAt(r.length()-1))
				{
				r = r + s.charAt(s.length()-1) + s.charAt(s.length()-1);
				}
		}
		else
		{
			r = r + ch;
		}
		return r;
	}
	
	public static int findStoppingTime(int n)
	{
		int iterations = 0;
		int number = n;
		if (n <= 0)
		{
			return -1;
		}
		while (n != 1)
		{
			iterations ++;
			if ((number % 2) == 0)
			{
				number = number / 2;
			}
			else
			{
				number = number*3 + 1;
			}
			if (number == 1)
			{
				break;
			}
		}
		return iterations;
	}
	
	public static int howLong(double balance, double monthlyCost, double interestRate, double increase)
	{
		int months = 0;
		double bal = balance;
		double netMonthlyCost = monthlyCost;
		while (bal >= netMonthlyCost)
		{
			months ++;
			bal = bal - netMonthlyCost;
			netMonthlyCost = netMonthlyCost + increase;
			bal = bal + (bal*(interestRate/12));
		}
		return months;
	}
	
	public static boolean isIBeforeE(java.lang.String s)
	{
		int j = 0;
		int k = 0;
		for (int i = 0; i < s.length()-1; i++)
		{
			j = i + 1;
			k = i - 1;
			if (s.charAt(i) == 'e')
			{
				if(s.charAt(j) == 'i')
			    {
					if (i == 0)
					{
						return false;
					}
					if (s.charAt(k) != 'c')
					{
						return false;
					}
			    }
			}
		}
		return true;
	}
	 
	public static int findSecondLargest(java.lang.String nums)
	{
		int max = Integer.MIN_VALUE;
		int second = Integer.MIN_VALUE;
		String[] num = nums.split(" ");
		int current = 0;
		for (int i = 0; i < num.length; i++)
		{
			current = Integer.valueOf(num[i]);
			if (current > max)
			{
				second = max;
				max = current;
			}
			else
			{
				if ((current <= max) && (current > second))
				{
					second = current;
				}
			}
		}
		return second;
	}
	 
	public static boolean isPermutation(java.lang.String s, java.lang.String t)
	{
		char[] arrayS = new char[s.length()];
		char[] arrayT = new char[t.length()];
		if (s.length() != t.length())
		{
			return false;
		}
		else
		{
			for (int i = 0; i < s.length(); i++)
			{
				arrayS[i] = s.charAt(i);
				arrayT[i] = t.charAt(i);
			}

			Arrays.sort(arrayS);
			Arrays.sort(arrayT);
			for (int b = 0; b < s.length(); b ++)
			{
				if (arrayT[b] != arrayS[b])
				{
					return false;
				}
			}
			return true;
		}
	}
	
	public static boolean containsWithGaps(java.lang.String source, java.lang.String target)
	{
		
		if (target == "")
		{
			return true;
		}
		if (target.length() > source.length())
		{
			return false;
		}
		
		int count = 0;
		int sourceCount = 0;
		int targetCount = 0;
		char sh = 0;
		char th = 0;
		
		while (sourceCount <= (source.length()-1))
		{
			sh = source.charAt(sourceCount);
			th = target.charAt(targetCount);
			
			if (sh == th)
			{
				sourceCount ++;
				targetCount ++;
				count ++;
			}
			if (sh == th && count == target.length())
			{
				return true;
			}
			else
			{
				sourceCount ++;
			}
		}
		return false;
	}
}
