import java.util.ArrayList;

/**
 * Henry Pacheco Cachon
 * Lab 06
 */

 import java.util.Comparator;

public class BSTMap<K,V> implements MapSet<K,V> {

    // Declaring the root tree node
    private TNode root;
    // Declaring the comparator for this BST
    private Comparator<K> compareMech;

    public BSTMap(Comparator<K> comp){
        this.root = null;
        this.compareMech = comp;
    }

    @Override
    public V put(K new_key, V new_value) {
        
        if (this.root == null){
            TNode newNode = new TNode(new_key, new_value);
            this.root = newNode;
            return null;
        }
        else{
            return this.root.put(new_key, new_value, this.compareMech);
        }
    }

    // Method checks if the BST contains the given key
    @Override
    public boolean containsKey(K key) {
        
        if (this.root == null){
            return false;
        }
        else{
            return this.root.containsKey(key, this.compareMech);
        }
    }

    // Method gets the value of the given key
    // if the BST doesn't have that key, it returns null
    @Override
    public V get(K key) {

        if (this.root == null){
            return null;
        }
        else{
            return this.root.get(key, this.compareMech);
        }
    }

    // Method returns an arraylist of the keys of the BST
    @Override
    public ArrayList<K> keySet() {
        
        if (this.root == null){
            return null;
        }
        else{
            return this.root.keySet();
        }
    }

    // Method returns an arraylist of the values of the BST
    @Override
    public ArrayList<V> values() {
        
        if (this.root == null){
            return null;
        }
        else{
            return this.root.values();
        }
    }

    // Method returns an arraylist of the key value pairs in the BST
    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {

        if (this.root == null){
            return null;
        }
        else{
            return this.root.entrySet();
        }
    }

    // Method returns the size of the BST
    @Override
    public int size() {
        
        if (this.root == null){
            return 0;
        }
        else{
            return this.root.size();
        }
    }

    // Method returns the depth of the BST
    public int getDepth(){

        if (this.root == null){
            return 0;
        }
        else{
            return this.root.getDepth();
        }
    }

    // Method clears the BST
    @Override
    public void clear() {
        this.root = null;
    }

    // Method overrides the default toString method
    // Prints the tree rotated 90 degrees ccw
    public String toString() {
        if (this.root == null) {
            return "";
        }
        else {
            return this.root.toString("");
        }
    }


    // TNode class representing a node in our tree
    class TNode{

        // Declaring data field
        private KeyValuePair<K,V> data;
        // Declaring left and right nodes
        private TNode left;
        private TNode right;

        // Constructor method, initializes our data field 
        // sets left and right nodes to null
        public TNode(K k, V v ){

            // Initializing key value pair data
            this.data = new KeyValuePair<K,V>(k, v);

            // Initializing left and right nodes 
            this.left = null;
            this.right = null;
        }

        // Takes in a key, a value, and a comparator and inserts
        // the TNode in the subtree rooted at this node
        // Returns the value associated with the key in the subtree
        // root at this node or null if the key does not already exists
        public V put(K key, V value, Comparator<K> comp){
            
            // Getting the root key and root value 
            K rootKey = this.data.getKey();
            V rootValue = this.data.getValue();

            // If the root key and given key are the same,
            // replace the value and print the old value 
            if (comp.compare(key, rootKey) == 0){
                this.data.setValue(value);
                return rootValue;
            }
            
            // If the given key should be before the root key 
            // look at the left subtree 
            else if (comp.compare(key, rootKey) < 0){

                // If the left node is null add the key value pair node to it
                if (this.left == null){
                    TNode newNode = new TNode(key,value);
                    this.left = newNode;
                }
                // If not, then run the put method on this left subtree
                else{
                    return this.left.put(key, value, comp);
                }
            }

            // If the given key should be after the root key
            // look at the right subtree
            else if (comp.compare(key, rootKey) > 0){

                // If the right node is null, then add the key value pair node to it
                if (this.right == null){
                    TNode newNode = new TNode(key,value);
                    this.right = newNode;
                }
                // If not, then run the put methond on the right subtree
                else{
                    return this.right.put(key, value, comp);
                }
            }
            return null;
        }

        // Method inputs a key and a comparator
        // returns the value associated with the key or null if
        // no such key exists
        public V get(K key, Comparator<K> comp){

            K rootKey = this.data.getKey();
            V rootValue = this.data.getValue();

            // If the root key and given key are the same
            if (comp.compare(key, rootKey) == 0){
                return rootValue;
            }
            
            // If given key goes before the root key and the left subtree is not null
            else if (comp.compare(key, rootKey) < 0 && this.left != null){
                return this.left.get(key, comp);
            }

            // If the given key goes after the root key and the right subtree is not null
            else if (comp.compare(key, rootKey) > 0 && this.right != null){
                return this.right.get(key, comp);
            }

            return null;
        }

        // Method checks if a branch contains a key
        // returns true if there is a match
        // returns false if there is not a match in the root or any subtrees
        public boolean containsKey(K key, Comparator<K> comp){

            // Getting the root key
            K rootKey = this.data.getKey();

            // If the root key is a match return true 
            if (comp.compare(key, rootKey) == 0){
                return true;
            }

            // If the key should be before the root key and the left subtree is not empty, then check it
            else if (comp.compare(key,rootKey) < 0 && this.left != null){
                return this.left.containsKey(key, comp);
            }

            // If the key should be after the root key and the right subtree is not empty, then check it
            else if (comp.compare(key, rootKey) > 0 && this.right != null){
                return this.right.containsKey(key, comp);
            }

            return false;
        }

        // Method returns the key value pairs of a node and its subtree
        public ArrayList<KeyValuePair<K,V>> entrySet(){

            // Initializing key value pair arraylist 
            ArrayList<KeyValuePair<K,V>> pairs = new ArrayList<KeyValuePair<K,V>>();

            // Getting the root pair
            KeyValuePair<K,V> rootPair = this.data;
            // Adding pair to the arraylist
            pairs.add(rootPair);

            if (this.left != null){
                pairs.addAll(this.left.entrySet());
            }

            if (this.right != null){
                pairs.addAll(this.right.entrySet());
            }

            return pairs;
        }

        // Method returns the arraylist of the values of the root and its subtrees 
        public ArrayList<V> values(){

            // Initializing value arraylist
            ArrayList<V> values = new ArrayList<V>();

            // Getting root value
            V rootValue = this.data.getValue();
            // Root value added to arraylist
            values.add(rootValue);

            if (this.left != null){
                values.addAll(this.left.values());
            }

            if (this.right != null){
                values.addAll(this.right.values());
            }

            return values;
        }

        // Method returns the arraylist of the keys of the root and its subtrees
        public ArrayList<K> keySet(){

            // Initializing arraylist
            ArrayList<K> keys = new ArrayList<K>();

            // Getting root key and adding it to the arraylist
            K rootKey = this.data.getKey();
            keys.add(rootKey);

            if (this.left != null){
                keys.addAll(this.left.keySet());
            }

            if (this.right != null){
                keys.addAll(this.right.keySet());
            }

            return keys;
        }


        // Method returns the size of a tree
        public int size(){

            int size = 1;

            if (this.left != null){
                size += this.left.size();
            }

            if (this.right != null){
                size += this.right.size();
            }

            return size;
        }

        // Method returns the depth of the tree
        public int getDepth(){

            // Initializing the left subtree depth
            int leftDepth = 0;
            // Initializing the right subtree depth
            int rightDepth = 0;

            if (this.left != null){
                leftDepth = this.left.getDepth();
            }

            if (this.right != null){
                rightDepth = this.right.getDepth();
            }

            // Compare both left and right depths and pick the largest one
            if (leftDepth > rightDepth){
                return leftDepth + 1;
            }
            else{
                return rightDepth + 1;
            }
        }

        // Method overrides the toString method for every node
        // Meant to print a BST rotated 90 degrees ccw
        public String toString(String indent) {
            String result = "";
            if (this.right != null) {
                result += this.right.toString(indent + "\t");
            }
            result += indent + this.data + "\n";
            if (this.left != null) {
                result += this.left.toString(indent + "\t");
            }
            return result;
        }

    }


    public static void main(String[] args) {
        AscendingString comp = new AscendingString();
        BSTMap<String,Integer> test = new BSTMap<>(comp);
        
        // Adding 5 nodes to BST
        test.put("it", 0);
        test.put("best",1);
        test.put("age",2);
        test.put("foolishness",2);
        test.put("was",4);
        test.put("the",6);

        // Testing toString method 
        System.out.println(test);

        // Testing depth method
        System.out.println("The tree's depth is (3): " + test.getDepth());

        // Testing get methods
        System.out.println("Returning value for key A: "  + test.get("A"));
        System.out.println("Returning value for key F: " + test.get("F"));

        // Testing size method
        System.out.println("Size of the BST: " + test.size());

        // Testing the containsKey method
        System.out.println("The BST Contains A: " + test.containsKey("A"));
        System.out.println("The BST Contains F: " + test.containsKey("F"));
        System.out.println("The BST Contains key: " + test.containsKey("key"));

        // Testing the entrySet method
        System.out.println(test.entrySet());

        // Testing the values method
        System.out.println(test.values());

        // Testing the keySet method
        System.out.println(test.keySet());

        // Testing clear method
        test.clear();
        System.out.println(test);
        System.out.println("Size of the BST: " + test.size());

        System.out.println(test.containsKey("A"));
    }
}
