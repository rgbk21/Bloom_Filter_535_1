package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class EmpericalComparison {

    //Ok, so this is what I am going to do...

    //This is what my experiment is going to be:
    //3 Modes in the experiment
    //Mode 1: Generate a random Key from the database.txt
    //Mode 2: Generate a random key from the DiffFile.txt
    //Mode 3: Generate a key that is not in Diff but is in database.txt

    //Choose a number r randomly between 0 to size of file
    //grams.txt: (12632196)
    //DiffFile.txt: (1262147)
    //Start a loop to read the grams.txt until you reach line number r.
    //Use counter for counting the number of lines
    //The key value on your line r is the key value that you are going to search for.
    //We use the BloomDifferential and find the key. Check time taken.
    //We use the NaiveDiffernetial and find the key. Check the time taken.
    //Repeat these steps, say 10000 times. Check time differnece. out put results. Sound good?

    public static void main(String[] args) {

        EmpericalComparison myEmperor = new EmpericalComparison();
        myEmperor.startEmpiricalExp();

    }


    public void startEmpiricalExp(){

        int noOfExp = 10;//Number of times you want to conduct the Experiment
        long bloomTime = 0;//The total time taken by the BloomDifferential Method
        long naiveTime = 0;//The total time taken by the NaiveDifferential Method
        long startTime;
        long endTime;
        String key="";//This will be the key that will be searched in BloomDiffernetial and NaiveDifferential

        //Now create and store the BloomDifferential to be used for the experiment
//        BloomDifferential myBloom = new BloomDifferential();
//        myBloom.createFilter();

        //Now create and store the BloomDifferential to be used for the experiment
        BloomDifferential myBloom = new BloomDifferential();
        myBloom.createFilter();
        //Now create and store the NaiveDifferential to be used for the experiment
        NaiveDifferential myNaivete = new NaiveDifferential();

        for(int i = 0; i < noOfExp; i++){
            //Generate Key
            //*****************************************
            //********** CHOOSE YOUR MODE *************
            //******(COMMENT OUT OTHER MODES)**********
            //*****************************************
            //Mode 1: Generate a random Key from the database.txt
            key = generateRandomKey();
            //Mode 2: Generate a random key from the DiffFile.txt
//            key = genRandKeyDiffFile();
            //Mode 3: Generate a key that is not in Diff but is in database.txt
//            key = genRandKeySubset();

            System.out.println("*** Bloom Differential Experiment: " + i + " ***" );
            //Start experiment for BloomDifferential--------
            //Start timer for BloomFilter
            startTime = System.currentTimeMillis();
            myBloom.retrieveRecord(key);
            endTime   = System.currentTimeMillis();
            //End timer for Bloom Filter
            bloomTime = bloomTime + (endTime - startTime);
            //End experiment for BloomDifferential--------

            System.out.println("*** Naive Differential Experiment: " + i + " ***" );
            //Start experiment for NaiveDifferential--------
            startTime = System.currentTimeMillis();
            myNaivete.retrieveRecord(key);
            endTime   = System.currentTimeMillis();
            naiveTime = naiveTime + (endTime - startTime);
            //End experiment for NaiveDifferential--------
            System.out.println("***** END OF ITERATION *****");
            System.out.println();
            System.out.println();
        }
        System.out.println("Total Bloom Time: " + bloomTime);
        System.out.println("Total Naive Time: " + naiveTime);
    }

    private String generateRandomKey(){

        int randomLineNumber = 0;//key will be located at this line number
        int countLineNum = 1;//Counts the line numbers currently iterating through in text doc
        String key = "";
        Random rand = new Random();
        randomLineNumber = rand.nextInt(12632196);//12632196 is the no. of entries in database
//        System.out.println(randomLineNumber);

        //Search in grams for the key at randomLineNumber
        String fileName = "C:\\Semester 3\\COM S 535 - Data Mining\\PA1\\grams.txt";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while(countLineNum < randomLineNumber){
                br.readLine();
                countLineNum++;
            }
            line = br.readLine();//This should be line at line number: randomLineNumber
            String[] arrOfStr = line.split(" ", 5);

            for(int i = 0; i < arrOfStr.length ; i++){
                key = key + arrOfStr[i];
            }
//            System.out.println(key);
            //temp should now contain the key at the line randomLineNumber.
            //Well now it does.


        }catch(IOException e){
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }

        return key;
    }

    private String genRandKeyDiffFile(){

        int randomLineNumber = 0;//key will be located at this line number
        int countLineNum = 1;//Counts the line numbers currently iterating through in text doc
        String key = "";
        Random rand = new Random();
        randomLineNumber = rand.nextInt(1262147);//1262147 is the no. of entries in diffFile.txt
//        System.out.println("randomLineNumber: " + randomLineNumber);

        //Search in grams for the key at randomLineNumber
        String fileName = "C:\\Semester 3\\COM S 535 - Data Mining\\PA1\\DiffFile.txt";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while(countLineNum < randomLineNumber){
                br.readLine();
                countLineNum++;
            }
            line = br.readLine();//This should be line at line number: randomLineNumber
            String[] arrOfStr = line.split(" ", 5);

            for(int i = 0; i < arrOfStr.length - 1 ; i++){
                key = key + arrOfStr[i];
            }
//            System.out.println("Key generated: " + key);
            //temp should now contain the key at the line randomLineNumber.
            //Well now it does.


        }catch(IOException e){
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }

        return key;
    }

    private String genRandKeySubset(){

        int randomLineNumber = 0;//key will be located at this line number
        int countLineNum = 1;//Counts the line numbers currently iterating through in text doc
        String key = "";
        Random rand = new Random();
        randomLineNumber = rand.nextInt(722);//722 is the no. of entries in subset.txt
//        System.out.println(randomLineNumber);

        //Search in grams for the key at randomLineNumber
        String fileName = "C:\\Semester 3\\COM S 535 - Data Mining\\PA1\\subset.txt";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while(countLineNum < randomLineNumber){
                br.readLine();
                countLineNum++;
            }
            line = br.readLine();//This should be line at line number: randomLineNumber
            String[] arrOfStr = line.split(" ", 5);

            for(int i = 0; i < arrOfStr.length - 1 ; i++){
                key = key + arrOfStr[i];
            }
//            System.out.println(key);
            //temp should now contain the key at the line randomLineNumber.
            //Well now it does.


        }catch(IOException e){
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }

        return key;

    }

    //I want to get atleast one false positive.
    // So that I know my code is working properly.


}
