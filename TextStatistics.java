/**
 * Henry Pacheco Cachon
 * Project 07
 * This really should be in the WordCounter2 class but it was gonna look ugly
 * so I decided to make this a separate class
 */

 import java.io.IOException;
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
    // and then outputs the results onto the command line
    static void timeComplexity(String data_structure){

        // getting the current directory
        String path = System.getProperty("user.dir");
        // Creating path to data folder
        String dataPath = path + "/Data";
        
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
    }

    // Method creates a csv for the bst map. Basically the contains the same information
    // as the timeComplexity method but with the depth of the tree included
    static void bstStatistics(){

        try {
            // Getting the current directory
            String path = System.getProperty("user.dir");
            // Initializing data directory
            String dataPath = path + "/Data/";
            // Initializing CSV directory
            String csvPath = path + "/CSVs/";

            // Initializing FileWriter object
            FileWriter write = new FileWriter(csvPath + "bst-Time.csv");
            // Initializing File object at data directory
            File dataDirectory = new File(dataPath);

            // Creating header for csv file
            String header = "Year,Total_Word_Count,Unique_Word_Count,Tree_Depth,File_Size,t\n";
            write.append(header);
            // Creating data string format
            String dataFormat = "%s,%s,%s,%s,%s,%s\n";
            
            // Initializing wordcounter object with bst as the data structure
            WordCounter2 tree = new WordCounter2("bst");

            // Going through each file in the data dirctory
            for (File data: dataDirectory.listFiles()){

                // Getting the year
                String year = data.getName().replaceAll("([^0-9])", "");
                // Getting the file size in bytes
                long fileSize = data.length();
                // Getting the file's full path
                String filename = data.getAbsolutePath();

                // Getting arraylist of words from file
                ArrayList<String> words = tree.readWords(filename);

                // Getting initial time
                double t_0 = System.currentTimeMillis();
                // Building word map
                tree.buildMap(words);
                // Getting final time
                double t_f = System.currentTimeMillis();

                // Calculating time elapsed
                double t = t_f - t_0;
                // Getting total word count
                int total = tree.getTotalWordCount();
                // Getting unique word count
                int unique = tree.getUniqueWordCount();
                // Getting depth
                BSTMap wordTree = (BSTMap) tree.getMap();
                int depth = wordTree.getDepth();

                // Creating data string
                String dataString = String.format(dataFormat, year,total,unique,depth,fileSize,t); 

                // Writing data string to csv
                write.append(dataString);

                // Clearing the word map
                tree.clearMap();

                System.out.println("Finished analysing file " + data.getName());
            }

            write.close();
        }

        catch (IOException ex){
            System.out.println("TextStatistics.bstStatistics():: Could not write file");
        }
    }

    // Method creates a csv file containing statistics about the hashmap data structure
    // Same information as the timecomplexity, but with collisions added on
    static void hashmapStatistics(){

        try {
            
            // Getting current directory
            String path = System.getProperty("user.dir");
            // Initializing csv directory
            String csvPath = path + "/CSVs/";
            // Initializing data directory
            String dataPath = path + "/Data/";

            // Creating FileWriter object
            FileWriter write = new FileWriter(csvPath + "hashmap-Time.csv");
            // Initializing File object at data directory
            File dataDirectory = new File(dataPath);

            // Creating header for csv file
            String header = "Year,Total_Word_Count,Unique_Word_Count,Collisions,File_Size,t\n";
            write.append(header);
            // Creating data string format
            String dataFormat = "%s,%s,%s,%s,%s,%s\n";

            // Creating word counter object
            WordCounter2 counter = new WordCounter2("hashmap");

            // Traversing all files
            for (File dataFile : dataDirectory.listFiles()) {
                
                // Getting the year
                String year = dataFile.getName().replaceAll("([^0-9])", "");
                // Getting the file size in bytes
                long fileSize = dataFile.length();
                // Getting the files full path
                String filename = dataFile.getAbsolutePath();

                // Creating arraylist of words from data file
                ArrayList<String> words = counter.readWords(filename);

                // Getting initial time
                double t_0 = System.currentTimeMillis();
                // Building word map
                counter.buildMap(words);
                // Getting final time
                double t_f = System.currentTimeMillis();

                // Calculating time elapsed
                double t = t_f - t_0;
                // Getting total word count
                int total = counter.getTotalWordCount();
                // Getting unique word count
                int unique = counter.getUniqueWordCount();
                // Getting Collisions
                Hashmap wordMap = (Hashmap) counter.getMap();
                int collisions = wordMap.getNumberCollisionts();

                // Creating data string
                String dataString = String.format(dataFormat, year,total,unique,collisions,fileSize,t); 

                // Writing data string to csv
                write.append(dataString);

                // Clearing the word map
                counter.clearMap();

                System.out.println("Finished analysing file " + dataFile.getName());
            }

            write.close();
        } 
        
        catch (IOException ex) {
            System.out.println("TextStatistics.hashmapStatistics():: Could not write file");
        }
    }

    public static void main(String[] args) {
        
        // timeComplexity("hashmap");
        // timeComplexity("bst");
        // bstStatistics();
        hashmapStatistics();
    }
    
}
