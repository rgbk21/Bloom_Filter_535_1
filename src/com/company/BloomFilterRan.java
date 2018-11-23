package com.company;
/**
 * @author Xiaoyun Fu and Gaurav Raj 
 * This class implements a bloom filter using k random hash functions.
 *
 */
import java.util.BitSet;
import java.util.Random;



public class BloomFilterRan
{
  /**
   * instance variables
   */
	int p;// filter size, calculated as the least prime number >= m
	int m;// calculated as setSize * bitPerElement
	int n;//set size of the data to stored in this bloom filter
	int k; //number of hash functions used
	BitSet filter;//the bloom filter to store data set s
	int dataSize; // to keep track of the number of elements added to filter
	int[] a; //used to store the k random numbers(a \in [1, p-1]) 
    int[] b; //used to store the k random numbers(b \in [0, p-1]) 
	
   public BloomFilterRan(int setSize, int bitsPerElement)
   {
	   m = setSize * bitsPerElement;
	   p = leastPrime(m);
	   n = setSize;
	   k = (int) (Math.log(2) * p / n); //k = ln2 * filterSize / setSize
	   filter = new BitSet(p);
	   dataSize = 0;
	   a = new int[k];
	   b = new int[k]; 
	   hashFunctions(k); // find the k pairs of <a, b>, fill up array a and b
   }
   
   /**
    * Add string s to the filter. This method is case-insensitive. For example,
    * it does not distinguish between "Galaxy" and "gaLaxy".
    * String s is added to filter by setting all values at indices hi(s) to true 
    * for all i = 0, 1, ..., k-1.
    * @param s a string to be added to filter
    */
   public void add(String s)
   {
	   String test = s.toLowerCase();

	   for(int i = 0; i < k; i++)
	   {
  	   //change input
		   test = fi(i, test);
	       filter.set(hashValue(i, test));
	   }
	   dataSize++;
   }
   
   /**
    * Returns true if s appears in the filter, flase otherwise. 
    * This method is case-insensitive.
    * @param s a string to be tested membership in filter
    * @return true if the value stored at index hi(s) is true 
    *         for all i = 0, 2, ..., k-1, flase otherwise
    */
   public boolean appears(String s)
   {
	   String test = s.toLowerCase();
	   for(int i = 0; i < k; i++)
	   {
		   //change input
		   test = fi(i, test);
		   if(filter.get(hashValue(i, test)) == false)
			   return false;  //if hi(test) is false for some i \in {0, 1, ..., k-1}
	   }
	   return true;  //if hi(test) is true for all i \in {0, 1, ..., k-1}
   }
   
   /**
    * 
    * @return the size of the filter, the size of the table(BitSet)
    */
   public int filterSize()
   {
	   return p;
   }
   
   /**
    * 
    * @return the number of elements added to filter
    */
   public int dataSize()
   {
	   return dataSize;
   }
   
   /**
    *  
    * @return number of hash functions used
    */
   public int numHashes()
   {
	   return k;
   }
   
   /**
    * Compute the hash value of str using the ith hash function. 
    * Convert x(String) to int using hashCode() method: called pre-hashing
    * @param i indicate the ith hash function is used. i \in {0, 1, ..., k-1}
    * @param str a string whose hash value is to be computed
    * @return hi(str) the hash value of str using the ith hash function
    */
   private int hashValue(int i, String str)
   {
 	  int x = str.hashCode();	  
 	  return Math.abs((a[i]*x + b[i])) % p;  //a[i] \in {1, 2, ..., p-1}, b[i] \in {0, 1, ..., p-1}
   }
   
   /**
    * Generate k pairs of random numbers <a, b> and store them in int[] a and int[] b
    * @param k the number of hash functions(num of <a, b> pairs)
    */
   private void hashFunctions(int k)
   {
 	  Random rand = new Random();
 	  for(int i = 0; i < k; i++)
 	  {
 		  int  x = rand.nextInt(p - 1) + 1; // x \in {1, 2, ..., p - 1}
 		  a[i] = x;
 		  int  y = rand.nextInt(p);    //y \in {0, 1, 2, ..., p - 1}
 		  b[i] = y;  
 	  }	   
   }
   
   /**
    * Change the input string x by appending the ith character in x to x. 
    * i denotes the ith hash function
    * @param i the ith hash function, i starts with 0
    * @param x the input string
    * @return x appended by its ith character
    */
   private String fi(int i, String x)
   {
	   String test = x;
	   if(x.length() < k)
	       for(int j = 0; j< k - x.length(); j++)
	    	   test = test + "$";
	   return test + test.charAt(i);
   }
   
   
   /**
    * Find the least prime number p that is greater than hash table size m
    * @param m an int 
    * @return the least prime number p that is greater than m
    */
   private int leastPrime(int m)
   {
 	  for(int i = m; m < 2*m; i++)
 	      if(isPrime(i))
 	    	  return i;
 	  return 0;
   }
   
   private boolean isPrime(int n)
   {
 	    // Corner cases
 	    if (n <= 1)  return false;
 	    if (n <= 3)  return true;

 	    // This is checked so that we can skip middle five numbers in below loop
 	    if (n%2 == 0 || n%3 == 0) return false;
         // It uses the fact that a prime (except 2 and 3) is of form 
 	    // 6k - 1 or 6k + 1 and looks only at divisors of this form.
 	    for (int i=5; i <= Math.sqrt(n); i=i+6)
 	        if (n%i == 0 || n%(i+2) == 0)
 	           return false;

 	    return true;
   }
	
	
	
}
