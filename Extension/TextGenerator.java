/**
 * Henry Pacheco Cachon
 * Project 07 Extension
 * This file reads a url containing words and then
 * creates 1000 text files, each with a different length
 */

 import java.io.IOException;
 import java.net.URL;
 import java.util.Scanner;
 import java.util.ArrayList;
 import java.io.FileWriter;
 import java.util.Random;

 public class TextGenerator {

    // Declaring a URL field
    private URL url;
    // Declaring arraylist that will hold all the words
    private ArrayList<String> words;
    // Initializing random generator
    private Random randGen = new Random();

    // Constructor method initializes the url and
    // initializes an empty arraylist
    public TextGenerator(String site){

        try{
            this.url = new URL(site);
            this.words = new ArrayList<String>();
        }
        catch (IOException ex){
            System.out.println("TextGenerator():: url not found " + site);
        }
    } 

    // Method generates an arraylist of all the words in the given url
    public ArrayList<String> getWords(){

        // Creating scanner object 
        try{
            Scanner scan = new Scanner(url.openStream());

            // Going through each line in the url
            while (scan.hasNext()){

                // Adding words to the word arraylist 
                this.words.add(scan.nextLine());
            }
        }
        catch (IOException ex){
            System.out.println("TextGenerator.getWords():: cannot read url");
        }

        return this.words;
    }

    // Method generators a text file with a given word count
    public void writeText(int word_count, String filename){

        try {

            // Getting the current path of the program and navigating to the Data directory
            String path = System.getProperty("user.dir");
            path += "/Data/";

            // Creating write object
            FileWriter write = new FileWriter(path  + filename);

            // Getting a given number of words
            for (int i = 0; i < word_count; i++) {
                
                // Getting a random integer
                int randomIndex = randGen.nextInt(this.words.size());
                // Getting word at random index
                String randomWord = this.words.get(randomIndex);
                randomWord += "\n";

                // Writing to file
                write.append(randomWord);
            }

            // Closing the file
            write.close();
            System.out.println("Done writing file " + filename);
        }
        catch (IOException ex){
            System.out.println("TextGenerator.writeText():: could not write file " + filename);
        }

    }
    

    public static void main(String[] args) {
        
        // Creating generator object 
        TextGenerator generator = new TextGenerator("https://www.mit.edu/~ecprice/wordlist.10000");

        // Creating arraylist of words
        generator.getWords();

        // Creating 1000 text files
        for (int i = 0; i < 1000; i++) {

            // Creating a standard format 
            String filenameFormat = "wordData-%s.txt";
            // Creating a filename string
            String filename = String.format(filenameFormat, i + 1);

            generator.writeText(i + 1, filename);
        }
    }
 }