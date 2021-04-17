/**
 * Henry Pacheco Cachon
 * Project 07
 * This file is meant to gather time complexity data 
 * with a wider range of points. Its purpose is to make
 * csvs with time complexity data.
 */

 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.File;
 import java.util.ArrayList;

public class TimeComplexity {

    // Method computes the average value of a given array of doubles
    static double average(double[] data){

        // Initializing total placeholder 
        double total = 0;

        // Traversing data array and adding to total sum
        for (double d : data) {
            total += d;
        }

        // Computing average
        double average  = (double) total / data.length;

        return average;
    }

    // Method averages time span by using 5 data points
    static double averageTime(WordCounter2 counter, ArrayList<String>  words){

        // Initialize array with 5 entries
        double[] times = new double[3];

        for (int i = 0; i < times.length; i++){

            // Clearing map
            counter.clearMap();

            // Getting time span in ms
            double t = counter.buildMap(words) * 1e-06;
            times[i] = t;
        }

        return average(times);
    }
    
    // Method writes csv containing time complexity data
    static void writeTimeCSV(String data_structure){

        try {

            // Getting current directory
            String path = System.getProperty("user.dir");
            // Initializing data directory
            String dataPath = path + "/Data/";
            // Initializing CSV directory 
            String csvPath = path + "/CSV/";

            // Creating filename with given data structure
            String filename = String.format("%s.csv",data_structure);

            // Initializing FileWrite object 
            FileWriter write = new FileWriter(csvPath + filename);

            // Initializing header string for csv file
            String header = "Total_Word_Count,t\n";
            write.append(header);

            // Initializing counter object
            WordCounter2 counter = new WordCounter2(data_structure);

            // Creating data format string
            String dataFormat = "%s,%s\n";

            // Getting data directory
            File dataDirectory = new File(dataPath);

            // Going through each file in the directory
            for (File data : dataDirectory.listFiles()) {
                
                // getting the data file's name
                String name = data.getAbsolutePath();

                // Initializing arraylist of words
                ArrayList<String> words = counter.readWords(name);

                // Getting the average amount of time taken to create a word map
                // Converted to ms 
                double t = averageTime(counter, words);

                // Getting the total number of words
                int total = counter.getTotalWordCount();

                // Creating data string and appending it to the csv
                String dataString = String.format(dataFormat, total,t);
                write.append(dataString);

                // Clearing word map
                counter.clearMap();

                System.out.println("Finished reading file " + data.getName());
            }
            
            write.close();
        } 
        catch (IOException ex) {
            System.out.println("TimeComplexity.writeTimeCSV():: Could not write file");
        }
    }

    public static void main(String[] args) {
        writeTimeCSV("hashmap");
        writeTimeCSV("bst");
    }
}
