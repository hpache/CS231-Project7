/**
 * Henry Pacheco Cachon
 * Project 07
 */

 import java.io.FileReader;
 import java.io.BufferedReader;
 import java.io.FileWriter;
 import java.util.ArrayList;
 import java.io.IOException;
 import java.io.FileNotFoundException;

public class WordCounter2 {
    
    // Initializing word map field 
    private MapSet<String,Integer> wordMap;
    // Initializing word count field 
    private int wordCount;

    // Constructor method initializes our data structure by using the given string
    // initializes word count to 0
    public WordCounter2(String data_structure){

        // Initializing BST if the given string is bst
        if (data_structure == "bst"){
            this.wordMap = new BSTMap<String,Integer>( new AscendingString());
        }

        // Initializing a hash map if given string is hashmap
        else if (data_structure == "hashmap"){
            this.wordMap = new Hashmap<String,Integer>(new AscendingString());
        }

        this.wordCount = 0;
    }

    // Method reads a given text file and returns an arraylist
    // of all the words in the file
    public ArrayList<String> readWords(String filename){

        try {

            // Initializing readers
            FileReader reader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(reader);

            // Resetting the word count to 0
            this.wordCount = 0;

            // Initializing arraylist to hold all the words
            ArrayList<String> allWords = new ArrayList<String>();

            // Getting the first line from text file
            String line = buffer.readLine();

            while (line != null){
                
                // Splitting line into words
                String[] words = line.split("[^a-zA-Z0-9']");


                // Cleaning up each word
                for (int i = 0; i < words.length; i++) {
                    String word = words[i].trim().toLowerCase();

                    // Checking if the string is empty
                    if (word.length() != 0){

                        // Adding word to the arraylist
                        allWords.add(word);
                        // Add one to wordcount
                        this.wordCount += 1;
                    }
                }

                line = buffer.readLine();
            }

            // closing the file
            buffer.close();
            return allWords;
        } 
        catch (FileNotFoundException ex){
            System.out.println("WordCounter2.readWords():: file not found " + filename);
            return null;
        }
        catch (IOException ex){
            System.out.println("WordCounter2.readWords():: file error " + filename);
            return null;
        }
    }

    // Method builds a word map from a given arraylist of strings
    // and returns the amount of time that it took to build the map
    public double buildMap(ArrayList<String>  words){

        // Initializing the starting time
        double t_0 = 0;
        // Initializing final time
        double t_f = 0;

        // Getting starting time
        t_0 = System.nanoTime();

        // Traversing through each element in the arraylist
        for (String word : words) {
            
            // Check if the word is already in the map
            if (this.wordMap.containsKey(word)){

                // Get the count of the word
                int count = this.wordMap.get(word);
                // Add one to the count
                count += 1;
                // update the word map entry
                this.wordMap.put(word, count);
            }

            // If the word isn't already in the map, just add it
            else{
                this.wordMap.put(word,1);
            }
        }

        // Getting the final time
        t_f = System.nanoTime();

        return t_f - t_0;
    }

    // Method clears the word map
    public void clearMap() { this.wordMap.clear(); }

    // Method returns the total word count
    public int getTotalWordCount() { return this.wordCount; }

    // Method returns the unique word count
    public int getUniqueWordCount() { return this.wordMap.size(); }

    // Method returns the word map
    public MapSet<String,Integer> getMap() { return this.wordMap; }

    // Method returns the count for a given word
    // returns 0 if the given word is not in the map
    public int getCount(String word){
        
        // Checking if the map contains the word
        if (this.wordMap.containsKey(word)){
            return this.wordMap.get(word);
        }
        else{
            return 0;
        }
    }

    // Method returns the frequency of a given word
    // returns 0 if the given word is not in the map
    public double getFrequency(String word){

        // Checking if the map contains the word
        if (this.wordMap.containsKey(word)){
            
            // Getting the unique count of the given word
            double wordCount = this.getCount(word);
            // Calculating the frequency 
            double frequency = wordCount / this.wordCount;

            return frequency;
        }
        else{
            return 0;
        }
    }

    // Method writes a word count file from the current word map
    public boolean writeWordCountFile(String filename){

        try {

            // Initializing file writer object
            FileWriter write = new FileWriter(filename);
            
            // Creating first line string
            String firstLine = "totalWordCount: %s\n";
            firstLine = String.format(firstLine, this.getTotalWordCount());
            // Appending first line to file
            write.append(firstLine);

            // Getting key-value pairs from word map
            ArrayList<KeyValuePair<String,Integer>> pairs = this.wordMap.entrySet();

            // Traversing pairs
            for (KeyValuePair<String,Integer> pair : pairs) {
                write.append(pair.toString());
            }

            // Closing the file
            write.close();

            return true;
        } 
        catch (IOException ex) {
            System.out.println("WordCounter2.writeWordCountFile():: Could not write file " + filename);
            return false;
        }
    }

    // Method reads a word count file 
    public boolean readWordCountFile(String filename){

        try {
            
            // clearing the current word map
            this.clearMap();

            // Initializing reading objects
            FileReader reader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(reader);

            // Reading the first line
            String firstLine  = buffer.readLine();
            // Getting the total word count
            String[] first = firstLine.split(":");
            String numberCount = first[1].trim();
            this.wordCount = Integer.parseInt(numberCount);

            // Reading the next lines
            String line = buffer.readLine();

            while (line != null){

                String[] pairs = line.split(" ");

                // Going through the pairs
                for (int i = 0; i < pairs.length; i++) {
                    
                    // Getting key and value
                    String key = pairs[0];
                    String value = pairs[1];

                    // If both the key and values are not empty
                    if (key.length() != 0 && value.length() != 0){

                        // Trimming the value string
                        value = value.trim();
                        // Converting value to integer
                        int keyCount = Integer.parseInt(value);
                        
                        // Adding pair to map
                        this.wordMap.put(key, keyCount);
                    }
                }

                line = buffer.readLine();
            }
            
            // Closing buffer
            buffer.close();

            return true;
        }
        catch (FileNotFoundException ex){
            System.out.println("WordCounter2.readWordCountFile():: cannot find file " + filename);
            return false;
        } 
        catch (IOException ex) {
            System.out.println("WordCounter2.readWordCountFile():: cannot read file " + filename);
            return false;
        }
    }


    // Unit test!
    public static void main(String[] args) {
        
        WordCounter2 test = new WordCounter2("hashmap");

        // Testing the readWords method
        ArrayList<String> words = test.readWords("counttest.txt");
        System.out.println(words);

        // Testing the build map method
        System.out.println(test.buildMap(words));

        // Testing count methods
        System.out.println("Total word count (24): " + test.getTotalWordCount());
        System.out.println("Unique word count (10): " + test.getUniqueWordCount());
        System.out.println("Count for 'times' (2): " + test.getCount("times"));
        System.out.println("Frequency for 'times' (0.8): " + test.getFrequency("times"));

        // Testing the writeWordCount method
        test.writeWordCountFile("count_test1.txt");

        // Testing the BST map
        WordCounter2 bst = new WordCounter2("bst");
        bst.buildMap(words);

        // Comparing total word count and unique word count
        System.out.println("Total word count (24): " + bst.getTotalWordCount());
        System.out.println("Unique word count (10): " + bst.getUniqueWordCount());
    }
}
