import java.util.ArrayList;
import java.util.Comparator;

/**
 * Henry Pacheco Cachon
 * Lab 07
 */

// Class representing a hash map using the chaining approach to collisions 
public class Hashmap<K,V> implements MapSet<K,V> {

    // Declaring data array field, will hold a linked list of key,value pairs
    private Object[] data;
    // Declaring size field
    private int size;
    // Declaring field keeping track of the number of collision
    private int numberCollisions = 0;
    // Declaring comperator field
    private Comparator<K> comp;

    // Constructor method initializes comparator field
    // initializes the data array with default size = 10
    public Hashmap(Comparator<K> incomp){

        // intializing comparator 
        this.comp = incomp;

        // Initializing default size and data array
        this.size = 10;
        this.data = new Object[10];
    }

    // Constructor method initializes the comparator field and size field
    // As well as initializes the data array with a given size
    public Hashmap(int capacity, Comparator<K> incomp){
        this.comp = incomp;
        this.size = capacity;
        this.data = new Object[this.size];
    }

    // Method creates a haschode for a key
    public int makeHashcode(K key){

        // Getting hashcode
        int hashcode = Math.abs(key.hashCode());
        // Modding it to make sure it is not outside the data array
        hashcode = hashcode % this.size;

        return hashcode; 
    }

    // Auxiliary method extends our array
    public void extend(){

        int currentSize = this.data.length;
        int newSize = currentSize * 2;
        Object[] newArray = new Object[newSize];

        for (int i = 0; i < currentSize; i++) {
            newArray[i] = this.data[i];
        }

        this.data = newArray;
    }

    // Method puts a key pair into our hash table 
    @Override
    public V put(K new_key, V new_value) {

        // Creating key,value pair variable
        KeyValuePair<K,V> newEntry = new KeyValuePair<K,V>(new_key, new_value);
        
        // Checking if the array is over 80% capacity
        if (this.size > (this.data.length * 0.8)){

            // Extend the array first
            this.extend();

            // Calculating index
            int index = this.makeHashcode(new_key);

            // Checking if that location is null or not
            if (this.data[index] == null){

                // Create a new linked list
                LinkedList<KeyValuePair<K,V>> linkedData = new LinkedList<KeyValuePair<K,V>>();
                // Adding key,value pair to linkedlist 
                linkedData.addFirst(newEntry);

                // Adding linked list to data array
                this.data[index] = linkedData;
            }
            else{
                LinkedList<KeyValuePair<K,V>> linkedData = (LinkedList<KeyValuePair<K,V>>) this.data[index];
                // Adding check variable, just to see if there is already a key
                int check = 0;

                // look through the linked list
                for (KeyValuePair<K,V> entries: linkedData){
                    K key1 = entries.getKey();

                    // If there is already a key, update the value 
                    if (this.comp.compare(key1, new_key) == 0){
                        entries.setValue(new_value);
                        check += 1;
                    }
                }

                // If the check is 0 then this is a new key
                if (check == 0){
                    linkedData.addFirst(newEntry);
                }
                this.numberCollisions += 1;
            }
            return new_value;
            
        }
        else{
            
            // Calculating index
            int index = this.makeHashcode(new_key);

            // Checking if that location is null or not
            if (this.data[index] == null){

                // Create a new linked list
                LinkedList<KeyValuePair<K,V>> linkedData = new LinkedList<KeyValuePair<K,V>>();
                // Adding key,value pair to linkedlist 
                linkedData.addFirst(newEntry);

                // Adding linked list to data array
                this.data[index] = linkedData;
            }
            else{
                LinkedList<KeyValuePair<K,V>> linkedData = (LinkedList<KeyValuePair<K,V>>) this.data[index];
                int check = 0;

                // look through the linked list
                for (KeyValuePair<K,V> entries: linkedData){
                    K key1 = entries.getKey();

                    // If there is already a key, update the value 
                    if (this.comp.compare(key1, new_key) == 0){
                        entries.setValue(new_value);
                        check += 1;
                    }
                }

                // If the check is 0 then this is a new key
                if (check == 0){
                    linkedData.addFirst(newEntry);
                }
                this.numberCollisions += 1;
            }
            return new_value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        // TODO Auto-generated method stub
        return false;
    }

    // Method returns the value of a given key
    @Override
    public V get(K key) {
        
        // Calculating key's index
        int index = this.makeHashcode(key);
        
        // Checking if this location is empty
        if (this.data[index] == null){
            return null;
        }
        else{

            // Getting the stored linked list
            LinkedList<KeyValuePair<K,V>> entryList = (LinkedList<KeyValuePair<K,V>>) this.data[index];
            // Declaring value placeholder
            V value = null;

            // Traversing the linked list and looking for a match
            for (KeyValuePair<K,V> pair: entryList){

                K key1 = pair.getKey();

                if (this.comp.compare(key1, key) == 0){
                    value = pair.getValue();
                }

            }


            return value;
        }
    }

    @Override
    public ArrayList<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }


    // Unit test
    public static void main(String[] args) {
        
        Hashmap<String,Integer> test = new Hashmap<String,Integer>(new AscendingString());
        
        test.put("Apple", 2);
        test.put("Appricot", 2);
        test.put("Apple",5);

        System.out.println(test.get("Apple"));
    }
    
}