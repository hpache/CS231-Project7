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
    // Declaring the number of array slots used
    private int numSlots;
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
        this.numSlots = 0;
        this.data = new Object[10];
    }

    // Constructor method initializes the comparator field and size field
    // As well as initializes the data array with a given size
    public Hashmap(int capacity, Comparator<K> incomp){
        this.comp = incomp;
        this.numSlots = 0;
        this.data = new Object[capacity];
    }

    // Method creates a haschode for a key
    public int makeHashCode(K key){

        // Getting hashcode
        int hashcode = Math.abs(key.hashCode());
        // Modding it to make sure it is not outside the data array
        hashcode = hashcode % this.data.length;

        return hashcode; 
    }

    // Auxiliary method extends our array
    private void extend(){

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

        // Checking if the array needs to be extended
        // The current threshold is set to 80% usage
        if (this.numSlots > (this.data.length * 0.3)){
            this.extend();
            this.numberCollisions = 0;
        }

        // Get the index for the key
        int index = this.makeHashCode(new_key);

        // Check if there is a collision
        if (this.data[index] != null){

            // Get current linked list 
            MapNode currentData = (MapNode) this.data[index];

            // Check if that key is alread in the linked list 
            if (currentData.containsKey(new_key)){

                // Add one to number of collisions if this is the 
                // first time that the key is added
                this.numberCollisions += 1;
            }

            // run the add method for the linked list
            currentData.add(new_key, new_value);

            return new_value;
        }

        // If there isn't a collision, then just add it
        else{
            this.data[index] = new MapNode(new_key,new_value,this.comp);
            this.numSlots += 1;
            return new_value;
        } 
    }

    @Override
    public boolean containsKey(K key) {

        // Calculating the key's index
        int index = this.makeHashCode(key);

        // Checking if the location is empty
        if (this.data[index] == null){
            return false;
        }
        else{

            // Get the linked list at the index
            MapNode currentData  = (MapNode) this.data[index];

            // Run the contains key method for that linked list
            return currentData.containsKey(key);
        }
    }

    // Method returns the value of a given key
    @Override
    public V get(K key) {
        
        // Calculating key's index
        int index = this.makeHashCode(key);
        
        // Checking if this location is empty
        if (this.data[index] == null){
            return null;
        }
        else{

            // Getting the linked list at the index 
            MapNode currentData = (MapNode) this.data[index];
            // Run the get method from the linked list
            return currentData.get(key);
        }
    }

    // Method returns an arraylist of all the keys in the hashmap
    @Override
    public ArrayList<K> keySet() {
        
        // Initializing arraylist of all keys
        ArrayList<K> allKeys = new ArrayList<K>();

        // Traversing data array
        for (Object data: this.data){

            if (data != null){

                // Casting data as a linked list
                MapNode linkedList = (MapNode) data;

                // Adding all keys from linked list
                allKeys.addAll(linkedList.keySet());
            }
        }

        return allKeys;
    }

    // Method returns an arraylist of all the values in the hashmap
    @Override
    public ArrayList<V> values() {
        
        // Initializing value arraylist
        ArrayList<V> allValues = new ArrayList<V>();

        // Traversing data array
        for (Object data: this.data){

            if (data != null){

                // Casting data as a linked list 
                MapNode linkedList = (MapNode) data;

                // Adding values from the linked list
                allValues.addAll(linkedList.values());
            }
        }

        return allValues;
    }

    // Method returns an arraylist of all the unique pairs in the hashmap
    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {

        // Initializing key-value pair arraylist
        ArrayList<KeyValuePair<K,V>> allPairs = new ArrayList<KeyValuePair<K,V>>();

        // Traversing data array
        for (Object data: this.data){

            if (data != null){

                // Casting data as a linked list 
                MapNode linkedList = (MapNode) data;

                // Adding values from the linked list
                allPairs.addAll(linkedList.entrySet());
            }
        }

        return allPairs;
    }

    // Method returns the number of unique keys in the hashmap
    @Override
    public int size() {
        
        // Initializing total size integer
        int totalSize = 0;

        // Going through each point in the array
        for (Object data: this.data){

            // Checking if the data is null
            if (data != null){

                // Casting data as MapNode type
                MapNode linkedList = (MapNode) data;

                // Add to the size from the linked list
                totalSize += linkedList.size();
            }
        }

        return totalSize;
    }

    // Method returns the number of collisions
    public int getNumberCollisionts() { return this.numberCollisions; }

    // Method empties the data array
    @Override
    public void clear() { this.data = new Object[this.data.length]; }




    // Class responsible for the nodes used in this hash map data structure 
    class MapNode{

        // Decalring data field
        private KeyValuePair<K,V> data;
        // Declaring pointer to next node
        private MapNode next;
        // Declaring comparator field
        private Comparator<K> incomp;

        // Constructor method initializes the data field and 
        // sets the next field to null and initializes the comparator
        public MapNode(K key, V value, Comparator<K> comp){
            this.data = new KeyValuePair<K,V>(key, value);
            this.next = null;
            this.incomp = comp;
        }

        // Method returns the key value pair in the node
        public KeyValuePair<K,V> getPair() { return this.data; }
        
        // Method returns the key in the node
        public K getKey() { return this.data.getKey(); }

        // Method returns the data in the node
        public V getData() { return this.data.getValue(); }

        // Adds a key value pair in the node
        // checks if the given key matches the key of the current node
        public void add(K new_key, V new_val){

            // If the new key matches, update the current node
            if (incomp.compare(new_key, this.getKey()) == 0){
                this.data.setValue(new_val);
            }
            
            // If the new key doesn't match and the next node is null, add a new node
            else if (incomp.compare(new_key, this.getKey()) != 0 && this.next == null){

                // Create a new node
                MapNode newNode = new MapNode(new_key,new_val,this.incomp);
                // Set the next node to the new node
                this.next = newNode;
            }

            // If the key doesn't match and the next node is not null, then check that node
            else if (incomp.compare(new_key, this.getKey()) != 0 && this.next != null){
                this.next.add(new_key, new_val);
            }
        }

        // Method returns the value of a given key, returns null if it can't find it
        public V get(K key){

            // If the key matches the current node, return its value
            if (this.incomp.compare(key, this.getKey()) == 0){
                return this.getData();
            }

            // If they don't match and the next node is not null, check that node
            if (this.incomp.compare(key, this.getKey()) != 0 && this.next != null){
                return this.next.get(key);
            }

            return null;
        }

        // Method checks if the current node contains a given key
        public boolean containsKey(K key){

            // If the key is a match return true
            if (this.incomp.compare(key, this.getKey()) == 0){
                return true;
            }

            // If the key isn't a match and the next node is not null, then check that node
            else if (this.incomp.compare(key, this.getKey()) != 0 && this.next != null){
                return this.next.containsKey(key);
            }
            
            return false;
        }

        // Method returns the size of the current node branch'
        public int size(){

            // Adding one to size since the current node is assumed to be not null
            int size = 1;

            // Checks the next node if it isn't null
            if (this.next != null){
                size += this.next.size();
            }

            return size;
        }

        // Method returns an arraylist of keys
        public ArrayList<K> keySet(){

            // Initialize an arraylist of keys
            ArrayList<K> keys = new ArrayList<K>();

            // Adding the current key to the arraylist 
            keys.add(this.getKey());

            // Checking if the next node is not null to get more keys
            if (this.next != null){
                keys.addAll(this.next.keySet());
            }

            return keys;
        }

        // Method returns an arraylist of values
        public ArrayList<V> values(){

            // Initialize an arraylist of values 
            ArrayList<V> values = new ArrayList<V>();

            // Adding current value to arraylist 
            values.add(this.getData());

            // Checking if the next node is not null to get more values
            if (this.next != null){
                values.addAll(this.next.values());
            }

            return values;
        }

        //  Method returns an arraylist of the key-value pairs 
        public ArrayList<KeyValuePair<K,V>> entrySet(){

            // Initializing arraylist of key-value pairs
            ArrayList<KeyValuePair<K,V>> pairs = new ArrayList<KeyValuePair<K,V>>();

            // Adding current pair
            pairs.add(this.getPair());

            // Checking if the next node is not null
            if (this.next != null){
                pairs.addAll(this.next.entrySet());
            }

            return pairs;
        }

    }

    // Unit Test!!
    public static void main(String[] args) {
        
        Hashmap<String,Integer> test = new Hashmap<String,Integer>(new AscendingString());

        test.put("Apple", 1);
        test.put("Apple",4);
        test.put("Appricot",5);
        test.put("Bottle", 100);

        // Checking if the get method works 
        System.out.println(test.get("Apple"));
        System.out.println(test.get("Bottle"));
        System.out.println(test.get("Hi"));

        // Checking if the containsKey method works
        System.out.println(test.containsKey("Apple"));
        System.out.println(test.containsKey("Bottle"));
        System.out.println(test.containsKey("key"));

        // Testing size method
        System.out.println(test.size());

        // Testing keySet method
        System.out.println(test.keySet());

        // Testing values method
        System.out.println(test.values());

        // Testing entrySet method
        System.out.println(test.entrySet());
    }
}