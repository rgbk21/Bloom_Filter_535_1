package com.company;

import java.util.Random;

public class kFNV {

    private long offset_basis;
    private final long FNV_prime = 0x100000001b3L;
    private int a; //h(x) = (ax + b) % p This is the a.
    private int b; //h(x) = (ax + b) % p This is the b.
    private int filterSize; //h(x) = (ax + b) % p This is the p.

    public kFNV(int offba, int size){

        offset_basis = offba;
        filterSize = size;
        Random rand = new Random();

        while (a == 0)
            a = rand.nextInt(filterSize);
        while (b == 0)
            b = rand.nextInt(filterSize);
    }



    public int hashV(String str){

        long hash = offset_basis;
        int index = 0;
        char[] charArray = str.toCharArray();
        for(int i = 0; i < charArray.length; i++){
            hash = hash ^ charArray[i];
//            System.out.println("hash ^ charArray[i]: " + hash );
            hash = hash * FNV_prime;
//            System.out.println("hash * FNV_prime: " + hash );
        }
        index = (int) ((Math.abs (a * Math.abs(hash) + b)) % filterSize);
        return index;
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

    public long getOffset_basis() {
        return offset_basis;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

}
