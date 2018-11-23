package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NaiveDifferential {


    public static void main(String[] args) {
        NaiveDifferential myDiff = new NaiveDifferential();
        //Exists in Diff
        System.out.println(myDiff.retrieveRecord("artistic study , and"));
        //Does not exist in Diff but exists in Dbases
        System.out.println(myDiff.retrieveRecord("arts colleges in Pennsylvania"));
        //Does not exist in either
        System.out.println(myDiff.retrieveRecord("arts_NOUN and_CONJ general_ADJ studies_"));

    }

    public String retrieveRecord(String key){

        String myKey = key.replaceAll("\\s+","");
//        System.out.println("Searching for: " + myKey);

        String fileName = "C:\\Semester 3\\COM S 535 - Data Mining\\PA1\\DiffFile.txt";
        File file = new File(fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arrOfStr = line.split(" ", 5);
                String temp = "";
                for(int i = 0; i < arrOfStr.length - 1; i++){
                    temp = temp + arrOfStr[i];
                }
//                System.out.println(temp);
                if(temp.equals(myKey)){
                    System.out.println("Found in DiffFile.txt");
                    System.out.println(line);
                    return line;
                }

            }
            return retrieveRecordfromDbase(myKey);
        }catch(IOException e) {
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }
        return "1. Key does not exist!";
    }

    private String retrieveRecordfromDbase(String myKey){

//        System.out.println("In retrieveRecordfromDbase()");
//        System.out.println("Searching for: " + myKey);

        String fileName = "C:\\Semester 3\\COM S 535 - Data Mining\\PA1\\database.txt";
        File file = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arrOfStr = line.split(" ", 5);
                String temp = "";
                for(int i = 0; i < arrOfStr.length - 1; i++){
                    temp = temp + arrOfStr[i];
                }
//                System.out.println(temp);
                if(temp.equals(myKey)){
//                    System.out.println("Found in Database.txt");
//                    System.out.println(line);
                    return line;
                }
            }
        }catch(IOException e){
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }
        return "Key does not exist!";
    }

}
