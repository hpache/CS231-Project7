/**
 * Henry Pacheco Cachon
 * Created 03/20/2021
 * Lab 04
 * This file is designed to implement a
 * linked list data structure in java
 */

 import java.util.Iterator;
 import java.util.ArrayList;
 import java.util.Collections;

 // This class will be used to represent a LinkedList data structure
 public class LinkedList<T> implements Iterable<T>{

    // Declaring field which contains the head of our linkedlist
    private Node<T> head;
    // Initializing field which contains the number of nodes in our linkedlist
    private int size;

    // Constructor method which initializes the linkedlist to be empty by defaul
    public LinkedList(){

        // Empty linkedlist -> null for head node and size = 0
        this.head = null;
        this.size = 0;
    }

    // This method clears our linkedlist and makes it empty
    public void clear(){
        this.head = null;
        this.size = 0;
    }

    // This method returns the size of the linkedlist
    public int size(){ return this.size; }

    // This method inserts an item at the beginning of the list
    public void addFirst(T data){

        // Creating a new node from the input data
        Node<T> newHead = new Node<T>(data);

        // Checks if the linkedList is empty
        if (this.head == null){
            // Setting newHead node as the current node
            this.head = newHead;
            // Add 1 to the size field
            this.size += 1;
        }
        else{
            // Get the current head of the node
            Node<T> oldHead = this.head;
            // Setting the current head to the next node for the newHead node
            newHead.setNext(oldHead);
            // Setting the current head to the newHead node
            this.head = newHead;
            // Add 1 to the size field
            this.size += 1;
        }
    }

    // This method appends an item to the linked list
    public void addLast(T data){

        // Creating new node from the input data
        Node<T> newNode = new Node<T>(data);

        // Checking if the linked list is empty
        if (this.head == null){
            this.head = newNode;
            this.size += 1;
        }
        else{

            // Initialize variable for the currentNode
            Node<T> currentNode = this.head;

            // Traverse the linkedList until you get to the last none-null element
            while (currentNode.getNext() != null){
                currentNode = currentNode.getNext();
            }
            
            // Set the nextnode to be the newNode
            currentNode.setNext(newNode);
            this.size += 1;
        }
    }

    // Auxiliary method, returns node at given index
    public Node<T> get(int index){

        if (index < 0 || index >= this.size){
            return null;
        }
        Node<T> currentNode = this.head;
        for (int i = 0; i < index; i++){
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }


    // Adds a node at a given index of the linkedList 
    public void add(int index, T data){

        Node<T> newNode = new Node<T>(data);

        // Check if the list is empty an the index is greater than 0
        if (this.size == 0 && index > 0){
            System.out.println("Index is out of bounds!");
            return;
        }

        // First check if the index is 0
        if (index == 0){
            this.addFirst(data);
        }
        else{

            // Get left node
            Node<T> leftNode = this.get(index - 1);
            // Get the node at index
            Node<T> rightNode = this.get(index);

            leftNode.setNext(newNode);
            newNode.setNext(rightNode);

            this.size += 1;
        }
    
    }

    // This method removes a node at the given index of the linkedList
    public void remove(int index){

        // Checks if the node is at the end of the linkedList
        if (index == this.size - 1){
            Node<T> leftNode = this.get(index-1);
            leftNode.setNext(null);
        }
        // Checks if the node is the first element
        if (index == 0){
            this.head = this.get(1);
            this.size -= 1;
        }
        // Only runs when the node is not the inital nor the last element
        else{
            Node<T> leftNode = this.get(index - 1);
            Node<T> rightNode = this.get(index + 1);
            leftNode.setNext(rightNode);
            this.size -= 1;
        }
    }

    // Method converts the linked list to an arrayList
    public ArrayList<T> toArrayList(){
        
        // Initializing a new arraylist
        ArrayList<T> outputArrayList = new ArrayList<T>();

        // Traversing each data element in the linkedlist
        for (T data: this){

            // adding the data to an arrayList
            outputArrayList.add(data);
        }

        return outputArrayList;
    }

    // Method converts the linked list to a shuffled arraylist
    public ArrayList<T> toShuffledList(){

        // Initializing an arraylist created from the linked list
        ArrayList<T> shuffledList = this.toArrayList();
        // Using the collections package to shuffle all the elements in the arraylist
        Collections.shuffle(shuffledList);

        return shuffledList;
    }
    
    // Return a new LLIterator pointing to the head of the list
    public Iterator<T> iterator() {
        return new LLIterator(this.head);
    }



    // This class will be used to represent a node object
    // which will be used in the LinkedList class
    private class Node<T>{
        
        // Declaring field which will contain the next node
        private Node<T> next;
        // Declaring field which will contain the data of the current node
        private T data;

        // Constructor method which will create our node given a data item
        public Node(T item){
            this.next = null;
            this.data = item;
        }
        
        // Method returns the current node's data
        public T getData(){ return this.data; }

        // Method sets the pointer of the current node to the next node
        public void setNext(Node<T> n){ this.next = n; }

        // Method returns the node after the current node
        public Node<T> getNext(){ return this.next; }

        // Overrides the default toString method, only prints data of node
        public String toString() { return "" + this.data; }
    }



    // This class handles the iterator for the linkedlist data structure
    private class LLIterator implements Iterator<T>{

        // Declaring fields for the currentNode in a linkedList
        private Node<T> currentNode;

        // Constructor intializes our head and next fields
        public LLIterator(Node<T> head){
            this.currentNode = head;
        }

        // Method returns true if there are more nodes to traverse
        // returns false if there no nodes left to traverse
        public boolean hasNext(){

            if (this.currentNode == null){
                return false;
            }
            else{
                return true;
            }
        }

        // Returns the data of the next node in the linkedList 
        public T next(){ 

            // If the currentNode is null, return null just in case!
            if (this.currentNode == null){
                return null;
            }
            // Else move on to the next node!
            else{
                // Getting the next node from the currentNode
                T currentData = this.currentNode.getData();
                Node<T> nextNode = this.currentNode.getNext();
                // Set the currentNode field to the nextNode 
                this.currentNode = nextNode;
                
                return currentData;
            }
        }

        // This method doesn't do anything
        public void remove(){ }
    }

    public static void main(String[] args) {
        LinkedList<Integer> llist = new LinkedList<Integer>();

		// add ten numbers to the list, in order
		for(int i=0;i<5;i++) {
				llist.addFirst(i);
		}
		System.out.println("There are " + llist.size() + " lights");

		llist.clear();

		System.out.println("There are " + llist.size() + " lights");

		for(int i=0;i<4;i++) {
				llist.addFirst(i);
		}

		System.out.println("There are " + llist.size() + " lights");

		// execute a foreach loop
		for(Integer q: llist) {
				System.out.println("value: " + q);
		}

        // Adding integer 5 at index 3
        llist.add(3, 5);

        System.out.println("There are " + llist.size() + " lights");

        for (Integer q: llist){
            System.out.println("Value: " + q);
        }

        // Removing node at index 3
        llist.remove(3);

        System.out.println("There are " + llist.size() + " lights");


        for (Integer q: llist){
            System.out.println("Value: " + q);
        }

        // Returns last element of the linkedlist
        System.out.println("Last element in the linkedlist: " + llist.get(llist.size() - 1));

        //Checking if the arraylist conversion works
        System.out.println("As an arraylist: " + llist.toArrayList());

        //Checking ig the arraylist got properly shuffled
        System.out.println("Shuffled arraylist: " + llist.toShuffledList());
    }

 }
