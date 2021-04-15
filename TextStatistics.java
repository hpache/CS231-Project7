/**
 * Henry Pacheco Cachon
 * Project 07
 * This really should be in the WordCounter2 class but it was gonna look ugly
 * so I decided to make this a separate class
 */

 import java.io.IOException;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.BufferedReader;
 import java.io.FileWriter;
 import java.util.ArrayList;
 import java.io.File;


public class TextStatistics {

    // Method calculates the average of a given array 
    // returns the average time in ms
    static double average(Double[] times){

        double totalSum = 0;

        for (double time: times){
            totalSum += time;
        }

        return (totalSum / times.length) * 1e-6;
    }

    // Method calculates the average time it takes to create a word map
    // N = 5
    static String timeData(WordCounter2 counter, File file){

        // Clearing the map
        counter.clearMap();

        // Getting the filename
        String filename = file.getAbsolutePath();
        // Initializing an array to hold all the time spans
        Double[] times = new Double[5];

        // Initialzing the arraylist of words
        ArrayList<String> words = counter.readWords(filename);

        // Creating the word map 5 times and getting time spand
        for (int i = 0; i < 5; i++){

            // Clearing the map
            counter.clearMap();

            // Getting initial time
            double t_0 = System.nanoTime();
            // Building the word map
            counter.buildMap(words);
            // Getting final time
            double t_f = System.nanoTime();

            // Adding time span to array
            times[i] = t_f - t_0;
        }

        // Calculating average in ms
        double averageTime = average(times);
        // Getting the total word count
        int totalWord = counter.getTotalWordCount();
        // Getting the unique word count
        int uniqueWord = counter.getUniqueWordCount();

        return "" + totalWord + "," + uniqueWord + "," + averageTime + "\n";
    }

    // Method reads text files, computes the average amount of time taken to create a map
    // and then creates a csv with the data strictly for a BST map
    static void TimeComplexity(String data_structure){

        try {

            // getting the current directory
            String path = System.getProperty("user.dir");
            // Creating path to csv folder
            String csvPath = path + "/CSVs/";
            // Creating path to data folder
            String dataPath = path + "/Data";
            // Creating filename based on the data structure used
            String filename = "%s-Time.csv";
            filename = String.format(filename, data_structure);

            // Creating file write object
            FileWriter write = new FileWriter(csvPath + filename);

            // Creating header string
            String header = "File_Size,Total_Word_Count,Unique_Word_Count,t\n";
            write.append(header);

            // Creating data format for output
            String dataFormat = "File Size(bytes): %s\nTotal Word Count: %s\nUnique Word Count: %s\nTime(ms): %s";

            // Creating wordcounter object for tree
            WordCounter2 counter = new WordCounter2(data_structure);
            
            // Getting files in the data directory
            File dataFiles = new File(dataPath);

            // Going through each data file
            for (File data: dataFiles.listFiles()){

                // Getting file size
                long size = data.length();

                // Getting time complexity data string 
                String dataString = timeData(counter, data);
                // Adding file size to data string
                String finalDataString = size + "," + dataString;

                // Appending string to csv file
                write.append(finalDataString);
                
                // Prompt to show that things are moving along
                System.out.println("Finished analyzing " + data.getName());

                // Splitting data string
                String fileSize = finalDataString.split(",")[0];
                String total = finalDataString.split(",")[1];
                String unique = finalDataString.split(",")[2];
                String time = finalDataString.split(",")[3];

                // Printing out data
                String output = String.format(dataFormat, fileSize,total,unique,time);
                System.out.println(output);
            }

            write.close();
        } 
        catch (IOException ex) {
            System.out.println("TextStatistics.treeTimeComplexity():: Could not write file");
        }
    }

    public static void main(String[] args) {
        
        TimeComplexity("hashmap");
        TimeComplexity("bst");
        
    }
    
}
