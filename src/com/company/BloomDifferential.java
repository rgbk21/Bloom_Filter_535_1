package com.company;

import java.io.*;

public class BloomDifferential {

    private BloomFilterFNV diffFilter;

    public BloomFilterFNV createFilter(){
        //Returns a bloom filter
        //corresponding to the records in the differential file diff.txt
        //12632196 total entries in the database
        //1262147 total entries in the diff file

        diffFilter = new BloomFilterFNV(1262147, 8);

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
                diffFilter.add(temp);
            }
        }catch(IOException e){
            System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
        }
        return diffFilter;
    }

    public String retrieveRecord(String key){

        String myKey = key.replaceAll("\\s+","");
//        System.out.println("Searching for: " + myKey);

        if(diffFilter.appears(myKey)){
//            System.out.println("diffFilter.appears(myKey) is true");
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
//                        System.out.println("Found in DiffFile.txt");
//                        System.out.println(line);
                        return line;
                    }

                }
//                System.out.println("False Positive!");
                return retrieveRecordfromDbase(myKey);
            }catch(IOException e){
                System.out.println("Exception in getFileContents(" + fileName + "), msg=" + e);
            }
        }else{

//            System.out.println("diffFilter.appears(myKey) is false ");
            return retrieveRecordfromDbase(myKey);
        }
        return "Key does not exist!";
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
