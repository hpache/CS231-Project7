/**
 * Henry Pacheco Cachon
 * Project 06
 */

 import java.io.BufferedReader;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;

import java.io.FileWriter;

public class WordCounter {

    // Declaring field that holds the word tree
    private BSTMap<String,Integer> wordTree;
    // Declaring field that holds the total word count
    private int wordCount;

    // Constructor method initializes an empty tree
    // and it sets the word count to 0
    public WordCounter(){
        this.wordTree = new BSTMap<String,Integer>(new AscendingString());
        this.wordCount = 0;
    }

    // Method generates a word count from a file
    public void analyze(String filename){

        try {
            
            FileReader reader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(reader);

            String line = buffer.readLine();

            while (line != null){

                // Splitting up words
                String[] words = line.split("[^a-zA-Z0-9']");

                // Cleaning up each word
                for (int i = 0; i < words.length; i++){
                    String word = words[i].trim().toLowerCase();

                    // Making sure the word is not an empty string
                    if (word.length() != 0){

                        // if the word is already on the map, increment 1 to its value
                        if (this.wordTree.containsKey(word)){
                            // Get value of word
                            int count = this.wordTree.get(word);
                            // Increment count by 1
                            count += 1;
                            // update the word tree
                            this.wordTree.put(word, count);
                            // Increment wordcount by 1
                            this.wordCount += 1;
                        }
                        // If the word isn't on the map, add it!
                        else{
                            // Adding word to tree
                            this.wordTree.put(word, 1);
                            // increment wordcount by 1
                            this.wordCount += 1;
                        }
                    }
                }

                line = buffer.readLine();
            }

            buffer.close();
            
        } 
        catch (FileNotFoundException ex) {
            System.out.println("WordCounter.analyze():: unable to read file " + filename);
        }
        catch (IOException ex){
            System.out.println("WordCounter.analyze():: error reading file " + filename);
        }
    }

    // Method returns the total word count
    public int getTotalWordCount(){ return this.wordCount; }

    // Method returns the unique word count
    public int getUniqueWordCount() { return this.wordTree.size(); }
    
    // Method gets the count for a specific word
    public int getCount(String word){ return this.wordTree.get(word); }

    // Method returns the frequency of a given word
    public double getFrequency(String word){

        // Getting the total count
        int totalCount = this.getTotalWordCount();
        // Getting the unique word count
        int wordCount = this.getCount(word);

        // Calculating the frequency 
        double frequency = (double) wordCount / (double) totalCount;

        return frequency;
    }

    // Method returns the word tree
    public BSTMap<String,Integer> getTree() { return this.wordTree; }

    // Method writes the word tree to a txt file
    public void writeWordCountFile(String filename){

        try{

            // Initializing file write
            FileWriter write = new FileWriter(filename);

            // Initializing key value arraylist
            ArrayList<KeyValuePair<String,Integer>> pairs = this.wordTree.entrySet();

            // Adding total word count to the first line
            String firstLine = "totalWordCount: %s \n";
            firstLine = String.format(firstLine, this.wordCount);
            write.append(firstLine);

            // Traversing over the pairs
            for (KeyValuePair<String,Integer> pair: pairs){
                write.append(pair.toString());
            }

            // Closing the writer
            write.close();
        }
        catch (IOException ex){
            System.out.println("WordCounter.writeWordCountFile():: error writing" + filename);
        }
    }

    // Method creates a word tree from a word map text file
    public void readWordCountFile(String filename){

        try{
            // Initializing readers 
            FileReader reader = new FileReader(filename);
            BufferedReader buffer = new BufferedReader(reader);

            // Separating the first line
            String firstLine = buffer.readLine();
            // Getting the total word count
            String[] first = firstLine.split(":");
            String numberCount = first[1].trim();
            // Initializing the total word count
            this.wordCount = Integer.parseInt(numberCount);

            // Reading the next lines 
            String line = buffer.readLine();

            while (line != null){

                String[] pairs = line.split(" ");

                // Going through the pair
                for (int i = 0; i < pairs.length; i++){

                    // Getting the key and value
                    String key = pairs[0];
                    String value = pairs[1];

                    if (key.length() != 0 && value.length() != 0){
                        
                        // Trimming value string
                        value = value.trim();
                        // Converting value to an integer 
                        int keyCount = Integer.parseInt(value);

                        // Adding pair to tree
                        this.wordTree.put(key, keyCount);
                    }
                }
                line = buffer.readLine();
            }
            buffer.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("WordCounter.readWordCountFile():: File not found " + filename);
        }
        catch(IOException ex){
            System.out.println("WordCounter.readWordCountFile():: Error reading file + " + filename);
        }
    }

    // Unit test!
    public static void main(String[] args) {

        if (args.length == 2){

            // Getting the read and write file strings
            String readFile = args[0];
            String writeFileName = args[1];

            // Initializing word counter object
            WordCounter words = new WordCounter();

            // Getting initial time 
            long t_0 = System.currentTimeMillis();

            // Analyzing the reading file
            words.analyze(readFile);

            // Getting final time
            long t_f = System.currentTimeMillis(); 
            // Calculating time elapse 
            long delta_t = t_f - t_0;

            // Writing with given file name 
            words.writeWordCountFile(writeFileName);

            System.out.println("Time elapsed: " + delta_t);

        }

        // Default main method
        if (args.length == 0){
            WordCounter test = new WordCounter();
            test.analyze("counttest.txt");

            // Testing total count method
            System.out.println("Total word count: " + test.getTotalWordCount()); 
            // Testing unique count method
            System.out.println("Unique word count: " + test.getUniqueWordCount());
            // testing word count method
            System.out.println("The count: " + test.getCount("the"));
            // Testing word frequency method
            System.out.println("The frequency: " + test.getFrequency("the"));
            // Printing the tree 
            System.out.println(test.getTree());

            // Writing tree to file
            test.writeWordCountFile("counts_ct_test.txt");
        }
    }    
}
