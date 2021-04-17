/**
 * Henry Pacheco Cachon 
 * Lab 6
 */



public class KeyValuePair<Key,Value> {

    private Key keyEntry;
    private Value data;

    // Constructor method intializes the key and data fields 
    public KeyValuePair(Key k, Value v){
        this.keyEntry = k;
        this.data = v;
    }

    // Method returns the key
    public Key getKey() { return this.keyEntry; }

    // Method returns the value 
    public Value getValue() { return this.data; }

    // Method sets the value field
    public void setValue(Value v) { this.data = v; }

    // Method overrides the default toString() method
    // returns a string containing both the key and value
    public String toString(){

        String output = "%s %s\n";
        output = String.format(output, this.keyEntry, this.data);

        return output;
    }

    // Main 
    public static void main(String[] args) {
        
        // Creating KeyValuePair object
        KeyValuePair<String,String> test = new KeyValuePair<String,String>("Hello", "World");

        // Testing getter methods
        System.out.println("Key: " + test.getKey());
        System.out.println("Value: " + test.getValue());

        // Testing toString method
        System.out.println(test);

        // Testing set method
        test.setValue("Bye");
        System.out.println(test);
    }
}