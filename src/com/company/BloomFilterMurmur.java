package com.company;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class BloomFilterMurmur {

    private BitSet myBit;//This becomes my Bloom Filter
    private int filterSize;//The size of the bloom filter
    private int numOfElmntsAdded = 0;//Counts the number of elements added in the Bloom Filter
    private int k = 0; //The number of hash functions to be generated
    int kMurmurSeedValues[];//This stores the k-Murmur seed values to be used to calculate Murmur
    private ArrayList<kMurmur> kMurmurs = new ArrayList<kMurmur>();//This is a datastructure to store the kMurmur functions that are generated

    BloomFilterMurmur(int setSize, int bitsPerElement) {
        //Creates a Bloom filter that can store a set S of cardinality setSize.
        //The size of the filter should approximately be setSize * bitsPerElement.

        filterSize = findPrime(setSize * bitsPerElement);
//        System.out.println(filterSize);
        myBit = new BitSet(filterSize);

        k = (int)(Math.log(2) * bitsPerElement) ;//Calculates the number of hash functions to eb generated
//        System.out.println( "These many hash functions(k): " + k);
        //Now that we know k, we can initialise the array to size k..
        kMurmurSeedValues = new int[k];
        //..and generate the kOffsetBasisValues.
        generateKSeedValues();

        //Now we want to generate the k-Murmur Hash Functions themselves. Okay.
        //Hopefully this should work now...
        for (int i = 0; i < kMurmurSeedValues.length; i++){
            int seed = kMurmurSeedValues[i];
            kMurmur myMurmur = new kMurmur(seed, filterSize);
            kMurmurs.add(myMurmur);
        }

        //Printing out the values:
//        for(int i = 0; i < kFNVS.size(); i++){
//            System.out.println("kFNVS.get(i).getA(): " + kFNVS.get(i).getA());
//            System.out.println("kFNVS.get(i).getB(): " + kFNVS.get(i).getB());
//            System.out.println("kFNVS.get(i).getOffset_basis()" + kFNVS.get(i).getOffset_basis());
//        }
    }

    public void add(String s){

        numOfElmntsAdded++;//Increment counter for number of elements added

        for(int i = 0; i < kMurmurs.size(); i++){
            myBit.set(kMurmurs.get(i).hashV(s.toLowerCase()));
        }

    }

    public boolean appears(String s){

        for(int i = 0; i < kMurmurs.size(); i++){
            if (! myBit.get(kMurmurs.get(i).hashV(s.toLowerCase()))){
                return false;
            }
        }
        return true;
    }

    public int filterSize() {

        //Returns the size of the filter
        return filterSize;
    }

    public int dataSize() {

        //Returns the number of elements added to the filter
        return numOfElmntsAdded;
    }

    public int numHashes() {

        //Returns the number of hash functions used

        return k;
    }

    private void generateKSeedValues(){
        Random rand = new Random();

        for(int i = 0; i < kMurmurSeedValues.length; i++){

            int j = 0;
            while (j == 0){
                j = rand.nextInt();
            }
//            System.out.println("j: " + j);
            j = Math.abs(j);
//            System.out.println("Math.abs(j): " + j);
            j = findPrime(j);
            kMurmurSeedValues[i] = Math.abs(j);
        }

//        for(int i = 0; i < kMurmurSeedValues.length; i++){
//            System.out.println(i + " kMurmurSeedValues: " + kMurmurSeedValues[i]);
//        }
    }

    //Finding the next prime when inout is an int
    private int findPrime(int n) {
        boolean found = false;
        int num = n;
        while (!found) {
            if (isPrime(num))
                return num;
            num++;
        }
        return -1;

    }

    private boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return false;
        return true;
    }

}
