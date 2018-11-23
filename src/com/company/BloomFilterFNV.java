package com.company;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class BloomFilterFNV {

    public BitSet myBit;//This becomes my Bloom Filter
    private FNV_Imp myImp;//
    private int filterSize;//The size of the bloom filter
    private int a; //h(x) = ax + b This is this a. Use only for addToSingleIndex.
    private int b; //h(x) = ax + b This is this b. Use only for addToSingleIndex.
    private int numOfElmntsAdded = 0;//Counts the number of elements added in the Bloom Filter
    private int k = 0; //The number of hash functions to be generated
    int kOffsetBasisValues[];//This stores the k-offset basis values to be used to calculate FNV
    private ArrayList<kFNV> kFNVS = new ArrayList<kFNV>();//This is a datastructure to store the kFNV functions that are generated

    BloomFilterFNV(int setSize, int bitsPerElement) {
        //Creates a Bloom filter that can store a set S of cardinality setSize.
        //The size of the filter should approximately be setSize * bitsPerElement.

        filterSize = findPrime(setSize * bitsPerElement);
//        System.out.println(filterSize);
        myBit = new BitSet(filterSize);
        //The values of a and b to be sued for the bloom filter will have to be found here
        //If you do this in the add method, it will generate a separate value of a and b
        // for everytime to add something. You are intelligent. You dont want that.
        find_a_b();//Remove this later
        k = (int)(Math.log(2) * bitsPerElement);//Calculates the number of hash functions to eb generated
//        System.out.println( "These many hash functions(k): " + k);
        //Now that we know k, we can initialise the array to size k..
        kOffsetBasisValues = new int[k];
        //..and generate the kOffsetBasisValues.
        //NOTE: You are generating k integers. Then casting them into a long later.
        generateKOffsetBasisValues();

        //Now we want to generate the k-FNV Hash Functions themselves. Okay.
        //Hopefully this should work now...
        for (int i = 0; i < kOffsetBasisValues.length; i++){
            int offsetBasis = kOffsetBasisValues[i];
            kFNV myFNV = new kFNV(offsetBasis, filterSize);
            kFNVS.add(myFNV);
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

        for(int i = 0; i < kFNVS.size(); i++){
            myBit.set(kFNVS.get(i).hashV(s.toLowerCase()));
        }

    }

    public void addToSingleIndex(String s) {

        //Adds the String s to the filter
        //Method should be case insensitive

        int hashValue;
        int filterIndex = 0;

        myImp = new FNV_Imp();
        hashValue = (int) myImp.hashV(s.toLowerCase());//lower case for case insensitive
        //FNV after hashing the string s returned hashValue
        //Math.abs because something weird about 2's complement or what not
        filterIndex = Math.abs((a * Math.abs(hashValue) + b) % filterSize);
        //Set the particular index to 1 in the Bloom Filter
        myBit.set(filterIndex);

    }

    public boolean appears(String s){

        for(int i = 0; i < kFNVS.size(); i++){
            if (! myBit.get(kFNVS.get(i).hashV(s.toLowerCase()))){
                return false;
            }
        }
        return true;
    }

    //Remove this method later
    public boolean appearsToSingleIndex(String s) {

        //Returns true if s appears in the filter; otherwise returns false.
        //Method should be case insensitive
        int hashValue = (int) myImp.hashV(s.toLowerCase());//lower case for case insensitive
        //FNV after hashing the string s returned hashValue
        //Math.abs because something weird about 2's complement or what not
        int filterIndex = Math.abs(a * Math.abs(hashValue) + b) % filterSize;
        if(myBit.get(filterIndex)) return true;
        return false;
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

    //Make this Private later on. Using only for troubleshooting.
    public BitSet geti(){
        return myBit.get(0, filterSize);
    }

    private void generateKOffsetBasisValues(){
        Random rand = new Random();

        for(int i = 0; i < kOffsetBasisValues.length; i++){

            int j = 0;
            while (j == 0){
                j = rand.nextInt();
            }
//            System.out.println("j: " + j);
            j = Math.abs(j);
//            System.out.println("Math.abs(j): " + j);
            j = findPrime(j);
            kOffsetBasisValues[i] = Math.abs(j);
        }

//        for(int i = 0; i < kOffsetBasisValues.length; i++){
//            System.out.println(i + " kOffsetBasisValues: " + kOffsetBasisValues[i]);
//        }
    }

    //Finding the next prime when input is an int
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

    private void find_a_b(){

        Random rand = new Random();
        //random int returned could be 0.
        while (a == 0)
            a = rand.nextInt(filterSize);
        while (b == 0)
            b = rand.nextInt(filterSize);
    }


}
