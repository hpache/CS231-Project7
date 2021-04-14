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


public class TextStatistics {

    // Method reads text files, computes the average amount of time taken to create a map
    // and then creates a csv with the data strictly for a BST map
    static void treeTimeComplexity(){

        try {
            
            // Creating file write object
            FileWriter write = new FileWriter("BST-Time.csv");

            // getting the current directory
            String path = System.getProperty("user.dir");

        } 
        catch (IOException ex) {
            System.out.println("TextStatistics.treeTimeComplexity():: Could not write file");
        }

    }
    
}
