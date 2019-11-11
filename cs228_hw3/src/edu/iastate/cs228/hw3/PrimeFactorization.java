package edu.iastate.cs228.hw3;

/**
 *  
 * @author Max Van de Wille
 *
 */

import java.util.ListIterator;

public class PrimeFactorization implements Iterable<PrimeFactor>
{
	private static final long OVERFLOW = -1;
	private long value; 	// the factored integer 
							// it is set to OVERFLOW when the number is greater than 2^63-1, the
						    // largest number representable by the type long. 
	
	/**
	 * Reference to dummy node at the head.
	 */
	private Node head;
	  
	/**
	 * Reference to dummy node at the tail.
	 */
	private Node tail;
	
	private int size;     	// number of distinct prime factors


	// ------------
	// Constructors 
	// ------------
	
    /**
	 *  Default constructor constructs an empty list to represent the number 1.
	 *  
	 *  Combined with the add() method, it can be used to create a prime factorization.  
	 */
	public PrimeFactorization() 
	{	 
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		size = 0;
	}

	
	/** 
	 * Obtains the prime factorization of n and creates a doubly linked list to store the result.   
	 * Follows the direct search factorization algorithm in Section 1.2 of the project description. 
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization(long n) throws IllegalArgumentException 
	{
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		if (n < 1)
		{
			throw new IllegalArgumentException();
		}
		value = n;
		//figure out brute force search
		int i = 2; 
		int mult = 0;
		long tempVal = n;
		while (tempVal % 2 == 0)
		{
			tempVal = tempVal / 2;
			mult ++;
		}
		add(i, mult);
		
		i = 3;
		mult = 0;
		while (i * i < n)
		{
			if (tempVal % i == 0)
			{
				while (tempVal % i == 0)
				{
					tempVal = tempVal / i;
					mult ++;
				}
				add(i, mult);
			}
			i += 2;
			mult = 0;
		}
		if (tempVal != 1)
		{
			add((int)tempVal, 1);
		}
	}
	
	
	/**
	 * Copy constructor. It is unnecessary to verify the primality of the numbers in the list.
	 * 
	 * @param pf
	 */
	public PrimeFactorization(PrimeFactorization pf)
	{
		head = new Node();
		tail = new Node();
		Node cur = pf.head.next;
		while (cur != pf.head || cur != pf.tail)
		{
			add(cur.pFactor.prime, cur.pFactor.multiplicity);
		}
	}
	
	/**
	 * Constructs a factorization from an array of prime factors.  Useful when the number is 
	 * too large to be represented even as a long integer. 
	 * 
	 * @param pflist
	 */
	public PrimeFactorization (PrimeFactor[] pfList)
	{
		head.next = tail;
		tail.previous = head;
		size = 0;
		for (int i = 0; i < pfList.length; i ++)
		{
			add(pfList[i].prime, pfList[i].multiplicity);
		}
	}
	
	

	// --------------
	// Primality Test
	// --------------
	
    /**
	 * Test if a number is a prime or not.  Check iteratively from 2 to the largest 
	 * integer not exceeding the square root of n to see if it divides n. 
	 * 
	 *@param n
	 *@return true if n is a prime 
	 * 		  false otherwise 
	 */
    public static boolean isPrime(long n) 
	{
    	if ((n > 2) && (n > 0) && (n % 2 == 0))
    	{
    		return false;
    	}
    	for (int i = 3; i * i <= n; i += 2)
    	{
    		if (n % i == 0)
    		{
    			return false;
    		}
    	}
		return true;
	}   

   
	// ---------------------------
	// Multiplication and Division 
	// ---------------------------
	
	/**
	 * Multiplies the integer v represented by this object with another number n.  Note that v may 
	 * be too large (in which case this.value == OVERFLOW). You can do this in one loop: Factor n and 
	 * traverse the doubly linked list simultaneously. For details refer to Section 3.1 in the 
	 * project description. Store the prime factorization of the product. Update value and size. 
	 * 
	 * @param n
	 * @throws IllegalArgumentException if n < 1
	 */
	public void multiply(long n) throws IllegalArgumentException 
	{
		if (n < 1)
		{
			throw new IllegalArgumentException();
		}
		PrimeFactorization multiplier = new PrimeFactorization(n);
		Node cur = multiplier.head.next;
		while (cur.pFactor != null)
		{
			PrimeFactorizationIterator iter = new PrimeFactorizationIterator();
			while (iter.hasNext() && iter.cursor.pFactor.prime < cur.pFactor.prime)
			{
				iter.next();
				if (iter.cursor.next == tail)
				{
					break;
				}
			}
			if (iter.cursor.pFactor.prime == cur.pFactor.prime)
			{
				iter.cursor.pFactor.multiplicity += cur.pFactor.multiplicity;
			}
			else
			{
				Node newFactor = new Node(cur.pFactor.prime, cur.pFactor.multiplicity);
				link(iter.cursor.previous, newFactor);
			}
			cur = cur.next;
		}
		if (value != -1)
		{
			value = Math.multiplyExact(value, n);
		}
	}
	
	/**
	 * Multiplies the represented integer v with another number in the factorization form.  Traverse both 
	 * linked lists and store the result in this list object.  See Section 3.1 in the project description 
	 * for details of algorithm. 
	 * 
	 * @param pf 
	 */
	public void multiply(PrimeFactorization pf)
	{
		multiply(this, pf);
		if (value != -1)
		{
			value = Math.multiplyExact(value, pf.value);
		}
	}
	
	
	/**
	 * Multiplies the integers represented by two PrimeFactorization objects.  
	 * 
	 * @param pf1
	 * @param pf2
	 * @return object of PrimeFactorization to represent the product 
	 */
	public static PrimeFactorization multiply(PrimeFactorization pf1, PrimeFactorization pf2)
	{
		PrimeFactorization main;
		PrimeFactorization smaller;
		if (pf1.size > pf2.size)
		{
			main = new PrimeFactorization(pf1);
			smaller = new PrimeFactorization(pf2);
		}
		else
		{
			main = new PrimeFactorization(pf1);
			smaller = new PrimeFactorization(pf2);
		}
		Node curSmaller = smaller.head.next;
		while (curSmaller != smaller.head || curSmaller != smaller.tail)
		{
			main.add(curSmaller.pFactor.prime, curSmaller.pFactor.multiplicity);
			curSmaller = curSmaller.next;
		}
		pf1 = main;
		return pf1; 
	}

	
	/**
	 * Divides the represented integer v by n.  Make updates to the list, value, size if divisible.  
	 * No update otherwise. Refer to Section 3.2 in the project description for details. 
	 *  
	 * @param n
	 * @return  true if divisible 
	 *          false if not divisible 
	 * @throws IllegalArgumentException if n <= 0
	 */
	public boolean dividedBy(long n) throws IllegalArgumentException
	{
		if (n <= 0)
		{
			throw new IllegalArgumentException();
		}
		if (this.value != -1 && this.value < n)
		{
			return false;
		}
		if (value % n == 0)
		{
			PrimeFactorization input = new PrimeFactorization(n);
			dividedBy(input);
			return true;
		}
		return false; 
	}

	
	/**
	 * Division where the divisor is represented in the factorization form.  Update the linked 
	 * list of this object accordingly by removing those nodes housing prime factors that disappear  
	 * after the division.  No update if this number is not divisible by pf. Algorithm details are 
	 * given in Section 3.2. 
	 * 
	 * @param pf
	 * @return	true if divisible by pf
	 * 			false otherwise
	 */
	public boolean dividedBy(PrimeFactorization pf)
	{
		if ((this.value != -1 && pf.value != -1 && this.value < pf.value) || this.value != -1 && pf.value == -1)
		{
			return false;
		}
		PrimeFactorization pfCopy = new PrimeFactorization(this);
		PrimeFactorizationIterator iterCopy = new PrimeFactorizationIterator();
		PrimeFactorizationIterator iterPf = new PrimeFactorizationIterator();
		while (true)
		{
			if (iterCopy.cursor.pFactor.prime >= iterPf.cursor.pFactor.prime)
			{
				if (iterCopy.cursor.pFactor.prime > iterPf.cursor.pFactor.prime)
				{
					return false;
				}
				if (iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime && iterCopy.cursor.pFactor.multiplicity < iterPf.cursor.pFactor.multiplicity)
				{
					return false;
				}
				if ((iterCopy.cursor.pFactor.prime == iterPf.cursor.pFactor.prime) && (iterCopy.cursor.pFactor.multiplicity >= iterPf.cursor.pFactor.multiplicity))
				{
					dividedBy(this, pfCopy);
				}
			}
			if (!iterCopy.hasNext() && iterPf.hasNext())
			{
				break;
			}
			iterCopy.next();
		}
		return false;

	}

	
	/**
	 * Divide the integer represented by the object pf1 by that represented by the object pf2. 
	 * Return a new object representing the quotient if divisible. Do not make changes to pf1 and 
	 * pf2. No update if the first number is not divisible by the second one. 
	 *  
	 * @param pf1
	 * @param pf2
	 * @return quotient as a new PrimeFactorization object if divisible
	 *         null otherwise 
	 */
	public static PrimeFactorization dividedBy(PrimeFactorization pf1, PrimeFactorization pf2)
	{
		if (pf1.value % pf2.value == 0)
		{
			PrimeFactorization dividend = new PrimeFactorization(pf1);
			PrimeFactorization divisor = new PrimeFactorization(pf2);
			Node cur = divisor.head.next;
			while (cur.pFactor != null)
			{
				dividend.remove(cur.pFactor.prime, cur.pFactor.multiplicity);
			}
			return dividend;
		}
		else
		{
			return null;
		} 
	}

	
	// -------------------------------------------------
	// Greatest Common Divisor and Least Common Multiple 
	// -------------------------------------------------

	/**
	 * Computes the greatest common divisor (gcd) of the represented integer v and an input integer n.
	 * Returns the result as a PrimeFactor object.  Calls the method Euclidean() if 
	 * this.value != OVERFLOW.
	 *     
	 * It is more efficient to factorize the gcd than n, which can be much greater. 
	 * 
	 * @param n
	 * @return prime factorization of gcd
	 * @throws IllegalArgumentException if n < 1
	 */
	public PrimeFactorization gcd(long n) throws IllegalArgumentException
	{
		if (n < 1)
		{
			throw new IllegalArgumentException();
		}
		if (this.value != -1)
		{
			return new PrimeFactorization(Euclidean(value, n));
		}
		return null; 
	}
	

	/**
	  * Implements the Euclidean algorithm to compute the gcd of two natural numbers m and n. 
	  * The algorithm is described in Section 4.1 of the project description. 
	  * 
	  * @param m
	  * @param n
	  * @return gcd of m and n. 
	  * @throws IllegalArgumentException if m < 1 or n < 1
	  */
 	public static long Euclidean(long m, long n) throws IllegalArgumentException
	{
 		if (m < 1 || n < 1)
 		{
 			throw new IllegalArgumentException();
 		}
 		long t1;
 		long t2;
 		if (m > n)
 		{
 			t1 = m;
 			t2 = n;
 		}
 		else
 		{
 			t1 = n;
 			t2 = m;
 		}
			long t3 =  t1 % t2;					// follows format t1 = x * t2 + t3
			while (t3 != 0)						// where t3 is remainder, thus when t3 == 0,
			{									// t2 is the gcd
				t1 = t2;
				t2 = t3;						//shifts t1 and t2 every round when they are no longer needed	
				t3 = t1 % t2;
			}
 		return t2; 
	}

 	
	/**
	 * Computes the gcd of the values represented by this object and pf by traversing the two lists.  No 
	 * direct computation involving value and pf.value. Refer to Section 4.2 in the project description 
	 * on how to proceed.  
	 * 
	 * @param  pf
	 * @return prime factorization of the gcd
	 * @throws IllegalArgumentException if pf == null
	 * 
	 */
	public PrimeFactorization gcd(PrimeFactorization pf) throws IllegalArgumentException
	{
		if (pf.equals(null))
		{
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = pf.new PrimeFactorizationIterator();
		int topFactor = 0;
		while (iter.hasNext())
		{
			if (containsPrimeFactor(iter.cursor.pFactor.prime))
			{
				topFactor = iter.cursor.pFactor.prime;
				iter.next();
			}
		}
		if (topFactor == 0)
		{
			return null;
		}
		if (this.value == -1 || pf.value == -1)
		{
			updateValue();
		}
		return new PrimeFactorization(topFactor); 
	}
	
	
	/**
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the gcd of two numbers represented by pf1 and pf2
	 * @throws IllegalArgumentException if pf1 == null or pf2 == null 
	 * 
	 */
	public static PrimeFactorization gcd(PrimeFactorization pf1, PrimeFactorization pf2) throws IllegalArgumentException
	{
		if (pf1.equals(null) || pf2.equals(null))
		{
			throw new IllegalArgumentException();
		}
		PrimeFactorizationIterator iter = pf1.new PrimeFactorizationIterator();
		int topFactor = 0;
		while (iter.hasNext())
		{
			if (pf2.containsPrimeFactor(iter.cursor.pFactor.prime))
			{
				topFactor = iter.cursor.pFactor.prime;
				iter.next();
			}
		}
		if (topFactor == 0)
		{
			return null;
		}
		if (pf1.value == -1 || pf2.value == -1)
		{
			pf1.updateValue();
		}
		return new PrimeFactorization(topFactor); 

	}

	
	/**
	 * Computes the least common multiple (lcm) of the two integers represented by this object 
	 * and pf.  The list-based algorithm is given in Section 4.3 in the project description. 
	 * 
	 * @param pf  
	 * @return factored least common multiple
	 * @throws IllegalArgumentException if pf == null 
	 * 
	 */
	public PrimeFactorization lcm(PrimeFactorization pf) throws IllegalArgumentException
	{
		if (pf.equals(null))
		{
			throw new IllegalArgumentException();
		}
		return lcm(this, pf); 
	}

	
	/**
	 * Computes the least common multiple of the represented integer v and an integer n. Construct a 
	 * PrimeFactors object using n and then call the lcm() method above.  Calls the first lcm() method. 
	 * 
	 * @param n
	 * @return factored least common multiple 
	 * @throws IllegalArgumentException if n < 1'
	 * 
	 */
	public PrimeFactorization lcm(long n) throws IllegalArgumentException 
	{
		if (n < 1)
		{
			throw new IllegalArgumentException();
		}
		PrimeFactorization input = new PrimeFactorization(n);
		return lcm(this, input); 
	}

	/**
	 * Computes the least common multiple of the integers represented by pf1 and pf2. 
	 * 
	 * @param pf1
	 * @param pf2
	 * @return prime factorization of the lcm of two numbers represented by pf1 and pf2
	 * @throws IllegalArgumentException if pf1 == null or pf2 == null
	 * 
	 */
	public static PrimeFactorization lcm(PrimeFactorization pf1, PrimeFactorization pf2) throws IllegalArgumentException
	{
		if (pf1.equals(null) || pf2.equals(null))
		{
			throw new IllegalArgumentException();
		}
		PrimeFactorization returnArr = new PrimeFactorization();
		if (pf1.value == -1 || pf2.value == -1)
		{
			returnArr.value = OVERFLOW;
		}
		PrimeFactorization large;
		PrimeFactorization small;
		
		if (pf1.size > pf2.size)
		{
			large = new PrimeFactorization(pf1);
			small = new PrimeFactorization(pf2);
		}
		else
		{
			small = new PrimeFactorization(pf1);
			large = new PrimeFactorization(pf2);
		}

		PrimeFactorizationIterator iterL = large.new PrimeFactorizationIterator();
		PrimeFactorizationIterator iterS = small.new PrimeFactorizationIterator();
		
		while (iterL.hasNext() && iterS.hasNext())
		{
			if (iterL.cursor.pFactor.prime != iterS.cursor.pFactor.prime)
			{
				if (iterL.cursor.pFactor.prime > iterS.cursor.pFactor.prime)
				{
					returnArr.add(iterS.cursor.pFactor.prime, iterS.cursor.pFactor.multiplicity);
					iterS.next();
				}
				else
				{
					returnArr.add(iterL.cursor.pFactor.prime, iterL.cursor.pFactor.multiplicity);
					iterL.next();
				}
			}
			else
			{
				if (iterL.cursor.pFactor.multiplicity > iterS.cursor.pFactor.multiplicity)
				{
					returnArr.add(iterL.cursor.pFactor.prime, iterL.cursor.pFactor.multiplicity);
				}
				else
				{
					returnArr.add(iterL.cursor.pFactor.prime, iterS.cursor.pFactor.multiplicity);
				}
				iterL.next();
				iterS.next();
			}
		}
		while (iterL.hasNext() && !iterS.hasNext())
		{
			returnArr.add(iterL.cursor.pFactor.prime, iterL.cursor.pFactor.multiplicity);
		}
		while (iterS.hasNext() && !iterL.hasNext())
		{
			returnArr.add(iterS.cursor.pFactor.prime, iterS.cursor.pFactor.multiplicity);
		}
		return returnArr; 
	}

	
	// ------------
	// List Methods
	// ------------
	
	/**
	 * Traverses the list to determine if p is a prime factor. 
	 * 
	 * Precondition: p is a prime. 
	 * 
	 * @param p  
	 * @return true  if p is a prime factor of the number v represented by this linked list
	 *         false otherwise 
	 */
	public boolean containsPrimeFactor(int p)
	{
		Node cur = head.next;
		while (!cur.equals(head) || !cur.equals(head))
		{
			if (cur.pFactor.prime == p)
			{
				return true;
			}
			cur = cur.next;
		}
		return false; 
	}
	
	// The next two methods ought to be private but are made public for testing purpose. Keep
	// them public 
	
	/**
	 * Adds a prime factor p of multiplicity m.  Search for p in the linked list.  If p is found at 
	 * a node N, add m to N.multiplicity.  Otherwise, create a new node to store p and m. 
	 *  
	 * Precondition: p is a prime. 
	 * 
	 * @param p  prime 
	 * @param m  multiplicity
	 * @return   true  if m >= 1
	 *           false if m < 1
	 */
    public boolean add(int p, int m) 
    {
    	if (m >= 1)
    	{
    		Node cur = head.next;
    		while (cur.pFactor != null)
    		{
    			if (cur.pFactor.prime == p)
    			{
    				cur.pFactor.multiplicity += m;
    				return true;
    			}
    			cur = cur.next;
    		}
    		Node addNode = new Node(p, m);
    		cur = head.next;
    		while (cur.pFactor != null)
    		{
    			if (cur.pFactor.prime > p)
    			{
    				break;
    			}
    			else
    			{
    				cur = cur.next;
    			}
    				
    		}
    		link(cur.previous, addNode);
    		return true;
    	}
    	return false; 
    }

	    
    /**
     * Removes m from the multiplicity of a prime p on the linked list.  It starts by searching 
     * for p.  Returns false if p is not found, and true if p is found. In the latter case, let 
     * N be the node that stores p. If N.multiplicity > m, subtracts m from N.multiplicity.  
     * If N.multiplicity <= m, removes the node N.
     * 
     * Precondition: p is a prime. 
     * 
     * @param p
     * @param m
     * @return true  when p is found. 
     *         false when p is not found. 
     * @throws IllegalArgumentException if m < 1
     */
    public boolean remove(int p, int m) throws IllegalArgumentException
    {
    	if (m < 1)
    	{
    		throw new IllegalArgumentException();
    	}
    	Node cur = head.next;
    	while (cur.pFactor != null)
    	{
    		if (cur.pFactor.prime == p)
    		{
    			if (cur.pFactor.multiplicity > m)
    			{
    				cur.pFactor.multiplicity -= m;
    			}
    			else
    			{
    				unlink(cur);
    			}
    			return true;
    		}
    		cur = cur.next;
    	}
    	return false; 
    }


    /**
     * 
     * @return size of the list
     */
	public int size() 
	{
		return size; 
	}

	
	/**
	 * Writes out the list as a factorization in the form of a product. Represents exponentiation 
	 * by a caret.  For example, if the number is 5814, the returned string would be printed out 
	 * as "2 * 3^2 * 17 * 19". 
	 */
	@Override 
	public String toString()
	{
		String returnStr = "";
		Node cur = head.next;
		while (cur != tail)
		{
			returnStr += cur.pFactor.toString();
			if (cur.next != tail)
			{
				returnStr += " * ";
			}
			cur = cur.next;
		}
		return returnStr; 
	}

	
	// The next three methods are for testing, but you may use them as you like.  

	/**
	 * @return true if this PrimeFactorization is representing a value that is too large to be within 
	 *              long's range. e.g. 999^999. false otherwise.
	 */
	public boolean valueOverflow() {
		return value == OVERFLOW;
	}

	/**
	 * @return value represented by this PrimeFactorization, or -1 if valueOverflow()
	 */
	public long value() {
		return value;
	}

	
	public PrimeFactor[] toArray() {
		PrimeFactor[] arr = new PrimeFactor[size];
		int i = 0;
		for (PrimeFactor pf : this)
			arr[i++] = pf;
		return arr;
	}


	
	@Override
	public PrimeFactorizationIterator iterator()
	{
	    return new PrimeFactorizationIterator();
	}
	
	/**
	 * Doubly-linked node type for this class.
	 */
    private class Node 
    {
		public PrimeFactor pFactor;			// prime factor 
		public Node next;
		public Node previous;
		
		/**
		 * Default constructor for creating a dummy node.
		 */
		public Node()
		{
			pFactor = null;
			next = null;
			previous = null;
		}
	    
		/**
		 * Precondition: p is a prime
		 * 
		 * @param p	 prime number 
		 * @param m  multiplicity 
		 * @throws IllegalArgumentException if m < 1 
		 */
		public Node(int p, int m) throws IllegalArgumentException 
		{	
			if (m < 1)
			{
				throw new IllegalArgumentException();
			}
			pFactor = new PrimeFactor(p, m);
			next = null;
			previous = null;
		}   

		
		/**
		 * Constructs a node over a provided PrimeFactor object. 
		 * 
		 * @param pf
		 * @throws IllegalArgumentException
		 */
		public Node(PrimeFactor pf)
		{
			pFactor = pf;
			next = null;
			previous = null;
		}


		/**
		 * Printed out in the form: prime + "^" + multiplicity.  For instance "2^3". 
		 * Also, deal with the case pFactor == null in which a string "dummy" is 
		 * returned instead.  
		 */
		@Override
		public String toString() 
		{
			return pFactor.toString(); 
		}
    }

    
    private class PrimeFactorizationIterator implements ListIterator<PrimeFactor>
    {  	
        // Class invariants: 
        // 1) logical cursor position is always between cursor.previous and cursor
        // 2) after a call to next(), cursor.previous refers to the node just returned 
        // 3) after a call to previous() cursor refers to the node just returned 
        // 4) index is always the logical index of node pointed to by cursor

        private Node cursor = head.next;
        private Node pending = null;    // node pending for removal
        private int index;      
  	  
    	// other instance variables ... 
        
        boolean removable = false;
      
        /**
    	 * Default constructor positions the cursor before the smallest prime factor.
    	 */
    	public PrimeFactorizationIterator()
    	{
    		cursor = head.next;
    		index = 1;
    	}

    	@Override
    	public boolean hasNext()
    	{
    		if (cursor.next.pFactor == null)
    		{
    			return false;
    		}
    		return true;
    	}

    	
    	@Override
    	public boolean hasPrevious()
    	{
    		if (cursor.previous.pFactor == null)
    		{
    			return false;
    		}
    		return true; 
    	}

 
    	@Override 
    	public PrimeFactor next() 
    	{
    		PrimeFactor temp = cursor.pFactor;
    		cursor = cursor.next;
    		pending = cursor.previous;
    		removable = true;
    		index ++;
    		return temp;
    	}

 
    	@Override 
    	public PrimeFactor previous() 
    	{
    		PrimeFactor temp = cursor.previous.pFactor;
    		cursor = cursor.previous;
    		pending = cursor;
    		removable = true;
    		index --;
    		return temp;
    	}

   
    	/**
    	 *  Removes the prime factor returned by next() or previous()
    	 *  
    	 *  @throws IllegalStateException if pending == null 
    	 */
    	@Override
    	public void remove() throws IllegalStateException
    	{
    		if (pending == null)
    		{
    			throw new IllegalStateException();
    		}
    		else if (removable)
    		{
    			unlink(pending);
    			removable = false;
    		}
    		index --;
    	}
 

    	/**
    	 * Adds a prime factor at the cursor position.  The cursor is at a wrong position 
    	 * in either of the two situations below: 
    	 * 
    	 *    a) pf.prime < cursor.previous.pFactor.prime if cursor.previous != head. 
    	 *    b) pf.prime > cursor.pFactor.prime if cursor != tail. 
    	 * 
    	 * Take into account the possibility that pf.prime == cursor.pFactor.prime. 
    	 * 
    	 * Precondition: pf.prime is a prime. 
    	 * 
    	 * @param pf  
    	 * @throws IllegalArgumentException if the cursor is at a wrong position. 
    	 */
    	@Override
        public void add(PrimeFactor pf) throws IllegalArgumentException 
        {
    		if ((pf.prime < cursor.previous.pFactor.prime && cursor.previous != head) || (pf.prime > cursor.pFactor.prime && cursor != tail))
    		{
    			throw new IllegalArgumentException();
    		}
    		Node newNode = new Node(pf);
    		link(cursor.previous, newNode);
    		removable = false;
    		index ++;
        }


    	@Override
		public int nextIndex() 
		{
			return index;
		}


    	@Override
		public int previousIndex() 
		{
			return index - 1;
		}

		@Deprecated
		@Override
		public void set(PrimeFactor pf) 
		{
			throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support set method");
		}
        
    	// Other methods you may want to add or override that could possibly facilitate 
    	// other operations, for instance, addition, access to the previous element, etc.
    	// 
    	// ...
    	// 
    }

    
    // --------------
    // Helper methods 
    // -------------- 
    
    /**
     * Inserts toAdd into the list after current without updating size.
     * 
     * Precondition: current != null, toAdd != null
     */
    private void link(Node current, Node toAdd)
    {
    	toAdd.next = current.next;
    	toAdd.previous = current;
    	current.next.previous = toAdd;
    	current.next = toAdd;
    	size ++;
    }

	 
    /**
     * Removes toRemove from the list without updating size.
     */
    private void unlink(Node toRemove)
    {
    	toRemove.next.previous = toRemove.previous;
    	toRemove.previous.next = toRemove.next;
    	size --;
    }


    /**
	  * Remove all the nodes in the linked list except the two dummy nodes. 
	  * 
	  * Made public for testing purpose.  Ought to be private otherwise. 
	  */
	public void clearList()
	{
		Node cur = head.next;
		while (cur != head || cur != tail)
		{
			unlink(cur);
			cur = cur.next;
		}
		size = 0;
	}	
	
	/**
	 * Multiply the prime factors (with multiplicities) out to obtain the represented integer.  
	 * Use Math.multiply(). If an exception is throw, assign OVERFLOW to the instance variable value.  
	 * Otherwise, assign the multiplication result to the variable. 
	 * 
	 */
	private void updateValue()
	{
		try
		{
			Node cur = head.next;
			value = 1;
			while (cur != tail)
			{
				int primeNum = cur.pFactor.prime;
				int mult = cur.pFactor.multiplicity;
				value = value * ((long) Math.pow(primeNum, mult));
				cur = cur.next;
			}
		}
		catch (ArithmeticException e) 
		{
			value = OVERFLOW;
		}
		
	}
}
