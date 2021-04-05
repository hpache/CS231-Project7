import java.util.Comparator;

/**
 * Henry Pacheco Cachon
 * Lab 06
 */

public class AscendingString implements Comparator<String> {

    // Method compares two given strings
    // If stringA comes before stringB then it returns a negative number
    // If stringA is equal to stringB then it returns 0
    // If stringA comes after stringB then it returns a positive number 
    public int compare(String stringA, String stringB) {
        
        return stringA.compareTo(stringB);
    }
    
    // Unit test
    public static void main(String[] args) {
        
        AscendingString test = new AscendingString();

        String a = "Hello";
        String b = "Hey";
        String c = "hello";
        String d = "Hello";

        System.out.println(test.compare(a, b));
        System.out.println(test.compare(a, c));
        System.out.println(test.compare(a, d));
    }
}

