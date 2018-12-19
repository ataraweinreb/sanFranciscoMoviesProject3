

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class is a generic implementation of a binary search tree which implements the Collection interface. 
 * It serves as the parent class of MovieList.
 * It has the nested classes of Node and bstIterator.
 * 
 * @author atara
 */
public class BST<E extends Comparable<E>> implements Collection<E> {

	//instance variables of BST
	private Node<E> root; //the root of the tree
	private int size; //the number of nodes in the tree
	
	/**
	 * Constructs a new empty binary search tree.
	 */
	public BST() {
		root = null;
		size = 0;
	}
	
	/**
	 * Constructs a new empty binary search tree, with the specified root.
	 * @param root the specified root node of the BST
	 */
	public BST(Node<E> root) {
		this.root = root;
		size = 0;
	}
	
	/**
	 * This method checks if the tree is empty, meaning if it has no nodes.
	 * @return true when the size of the tree is 0, meaning that it has no nodes
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * This method gets the size of the tree, the number of nodes in it.
	 * @return size the number of nodes in the list
	 */
	public int size() {
		return size;
	}
	
	/**
	 * This method should override the toString method.
	 * @return the String representation of this BST
	 */
	public String toString() {
		String result = "";
		if (isEmpty()) {
			return "Empty list.";
		}
		else {
			return myHelper(root, "");
		}
	}
	
	/**
	 * Private recursive method to traverse the tree inorder 
	 * and add the data in the nodes to an answer string, ans.
	 * Ans is then returned to the public toString() method.
	 * @param node the node which we are currently at in the tree
	 * @param ans a string containing the concatenated data from the nodes
	 * @return the string format of all of the data in the tree inorder
	 */
	private String myHelper(Node<E> node, String ans) {
		if (node != null) {
			ans += myHelper(node.left, ans) + node + "\n" + myHelper(node.right, ans);
		}
		return ans;
	}	

	/**
	 * This method returns the node who contains the specified data
	 * @param data the data to search for in the nodes
	 * @return the node which contains this specified data
	 */
	public E get(E data) {
		if (isEmpty() || !contains(data)) {
			return null;
		}
		return get(data, root);
	}
	
	/**
	 * Private recursive method to get the node with the specified data.
	 * This method is called by the public get method.
	 * @param data the data to search for in the nodes
	 * @param node the current node which to search and compare
	 * @return the node which contains this specified data
	 */
	private E get(E data, Node<E> node) {
		if (data.compareTo(node.data) == 0) {
			return node.data;
		}
		if (data.compareTo(node.data) < 0) {
			return get(data, node.left);
		}
		else {
			return get(data, node.right);
		}
	}
	
	/**
	 * This method returns a shallow copy of the binary search tree.
	 * @return a shallow copy of the binary search tree
	 */
	public Object clone() {
		Node<E> copy = clone(this.root);
		BST<E> answer = new BST<>(copy);
		answer.size = this.size;
		return answer;
	}
	
	/**
	 * Private method to make a shallow copy of the binary search tree.
	 * @param node the node we are currently at in the tree
	 * @return the root node for the cloned binary search tree
	 */
	private Node<E> clone(Node<E> node) {
		if (node == null) {
			return null;
		}
		Node<E> temp = node;
		temp.data = node.data;
		temp.left = clone(node.left);
		temp.right = clone(node.right);
		return temp;
	}

	/**
	 * Returns the smallest element in this tree that is strictly greater than the given element, 
	 * or null if no such element exists in the tree
	 * @param data the given element to compare to
	 * @return the smallest element in this tree that is strictly greater than the given element
	 */
	public E higher(E data) {
		if (data == null) {
			return null;
		}
		if (last().compareTo(data) <= 0) {
			return null;
		}
		return findHigher(root, data);
	}
	
	/**
	 * Private recursive method to return the smallest element in the set 
	 * that is strictly greater than the given element
	 * @param root the current node we are at in the tree
	 * @param data the data to compare to, to find a node that is greater then this
	 * @return the smallest element in the set that is strictly greater then data
	 */
	private E findHigher(Node<E> root, E data) {
		if (root == null) {
			return null;
		}

		if (root.data.compareTo(data) <= 0) {
			return findHigher(root.right, data);
		}
		
		else { 
			E temp = findHigher(root.left, data);
			if (temp == null) {
				return root.data;
			}
			return temp;
		}
	}

	/**
	 * This method calls the private, recursive, findLower method,
	 * to find the greatest data in the tree that is strictly lower then the specified data,
	 * or null if that data does not exist 
	 * @param data the specified data
	 * @return the greatest data in the tree that is strictly less then the specified data
	 */
	public E lower(E data) {
		if (data == null) {
			return null;
		}
		if (first().compareTo(data) > 0) {
			return null;
		}
		return findLower(root, data);
	}
	
	/**
	 * Private, recursive method to find the greatest data in the tree 
	 * that is strictly lower then the specified data, or null if that does not exist 
	 * @param root the current node we are at in the tree
	 * @param data the specified data to compare to
	 * @return the greatest data in the tree that is strictly lower then the specified data, or null
	 */
	private E findLower(Node<E> root, E data) {
		if (root == null) {
			return null;
		}
		if (root.data.compareTo(data) >= 0) { //too big
			return findLower(root.left, data); //so go to left
		}
		else { //smaller, but check if there is anything bigger than that, that is still smaller than data
			E temp = findLower(root.right, data); //see if you can find something that is even smaller
			if (temp == null) {
				return root.data; //if you can't find anything, return the answer from before 
			}
			return temp; 	
		}
	}
	
	/**
	 * Returns the smallest data that is greater than or equal to the parameter data, 
	 * or null if there is no such data
	 * @param data the specified data to look for and compare to
	 * @return smallest data that is greater than or equal to the parameter data, or null
	 */
	public E ceiling(E data) {
		if (isEmpty()) {
			return null;
		}
		if (data == null) {
			return null;
		}
		if (last().compareTo(data) < 0) {
			return null;
		}
		if (contains(data)) {
			return data;
		}
		else {
			return ceiling(data, root);
		}
	}
	
	/**
	 * Private recursive method to find the smallest data that is greater than or equal to the parameter data
	 * @param data the specified data
	 * @param node the current node we are at in the tree
	 * @return the smallest data that is greater than or equal to the parameter data
	 */
	private E ceiling(E data, Node<E> node) {
		//base case if you go down a branch that e/t is less than what you want
		if (node == null) {
			return null;
		}
		//if what u are looking for is greater than the current node we want
		if (data.compareTo(node.data) > 0) {
			return ceiling(data, node.right);
		}
		else {
			//return null if can't find answer in that branch
			E temp = ceiling(data, node.left);
			//if u couldn't find answer in the branch than return the previous
			if (temp == null) {
				return node.data;
			}
			return temp;
		}
	}

	/**
	 * Finds the largest data that is less than or equal to the specified data, 
	 * or null if it does not exist
	 * @param data the specified data
	 * @return the largest data that is less than or equal to the specified data, or null
	 */
	public E floor(E data) {
		if (isEmpty()) {
			return null;
		}
		if (data == null) {
			return null;
		}
		if (first().compareTo(data) > 0) {
			return null;
		}
		if (contains(data)) {
			return data;
		}
		else {
			return floor(data, root);
		}
	}
	
	/**
	 * Private recursive method to find the largest data that is less than or equal to the specified data, 
	 * or null if it does not exist
	 * @param data the specified data
	 * @param node the current node we are at in the tree
	 * @return the largest data that is less than or equal to the specified data, or null
	 */
	private E floor(E data, Node<E> node) {
		if (node == null) {
			return null;
		}
		if (data.compareTo(node.data) > 0) {
			if (node.right != null) {
				if (node.right.data.compareTo(data) < 0) {
					return floor(data, node.right);
				}
				else {
					return node.data;
				}
			}
			else {
				return node.data;
			}
		}
		else {
			return floor(data, node.left);
		}
	}
	
	/**
	 * Finds the lowest element in the tree, the leftmost element
	 * @return the lowest element in the tree
	 */
	public E first() {
		if (isEmpty()) {
			return null;
		}
		return first(root);
	}
	
	/**
	 * Private recursive method to find the lowest element in the tree, the leftmost element
	 * @param node the current node we are at 
	 * @return the lowest element in the tree
	 */
	private E first(Node<E> node) {
		if (node.left == null) {
			return node.data;
		}
		else {
			return first(node.left);
		}
	}	

	/**
	 * This method returns the largest data stored in the tree, 
	 * found in the rightmost node of the tree.
	 * @return the largest data stored in the tree
	 */
	public E last() {
		if (isEmpty())
			return null;
		
		return last(root);
	}
	
	/**
	 * Private method to return the largest data stored in the tree, 
	 * found in the rightmost node of the tree.
	 * @param node the current node we are at in the tree
	 * @return the largest data stored in the tree
	 */
	private E last(Node<E> node) {
		if (node.right == null) 
			return node.data;
		else
			return last(node.right);
	}
	
	/**
	 * @override This method overrides the equals method
	 * It checks if 2 BSTs are equal, 
	 * meaning that they are the same size and type, and have the same nodes
	 * @return true if the BSTs are found to be equal
	 * @return false if the BSTs are found to be unequal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (this.getClass() != o.getClass()) {
			return false;
		}
		//cast
		BST other = (BST)o;
		
		if (this.size() != other.size()) {
			return false;
		}
		
		Iterator<E> rootA = this.iterator();
		Iterator<E> rootB = other.iterator();
		
		while (rootA.hasNext()) {
			if (!rootA.next().equals(rootB.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Creates and returns an array of the elements in this BST
	 * @return an array of the elements in this BST
	 */
	public Object[] toArray() {
		ArrayList<E> temp = new ArrayList<>();
		fill(root, temp);
		Object[] answer = temp.toArray();
		return answer;
	}
	
	/**
	 * Creates and returns an array of the elements in this BST
	 * @param node the node we are currently at in the tree
	 * @param arr the array to fill with elements from the tree
	 * @param index the current index in the array
	 * @return an array of the elements in this BST
	 */
	private void fill(Node<E> root, ArrayList<E> temp) {
		if (root != null) {
			fill(root.left, temp);
			temp.add(root.data);
			fill(root.right, temp);
		}
	}
	
	/**
	 * Returns an array of the data in the BST;
	 * the runtime type of the returned array is that of the specified array
	 * @return the array of the specified type which contains all of the elements in this collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		Object[] array = toArray();
		if (a.length < size) {
			return (T[]) Arrays.copyOf(array, size, a.getClass());
		}
		System.arraycopy(array, 0, a, 0, size);
		if (a.length > size) {
			a[size] = null;
		}
		return a;
	}
	
	/**
	 * This boolean method adds new data to the tree by creating a new node and incrementing the size. 
	 * Returns true if the node is successfully added. 
	 * @throws IllegalArgumentException if an attempt is made to add null or a duplicate value to the tree
	 */
	public boolean add(E data) {
		if (data == null || contains(data))
			//throw new IllegalArgumentException("Invalid data. This data cannot be added to the tree.");
			return false;
		size++;
		root = recAdd(root, data);
		return true;
	}
	
	/**
	 * Private recursive method to add a new node to the tree
	 * This method is called by the public add method
	 * @param node the current node we are checking for appropriate placement
	 * @param data the data to be added to the tree
	 * @return the new node with the the data to be added
	 */
	private Node<E> recAdd(Node<E> node, E data) {
		
		if (node == null) 
			return new Node<E>(data, null, null);
		
		if (data.compareTo(node.data) < 0) 
			node.left = recAdd(node.left, data);
		
		else 
			node.right = recAdd(node.right, data);
		
		return node;
	}
	
	/**
	 * Additional method to add a new node to the tree
	 * void return type, not boolean
	 * @param data the data to add to the tree
	 */
	public void add2(E data) {
		//can't have nulls in the tree
		if (data == null) {
			return;
		}
		
		//create node
		Node<E> newNode = new Node<>(data, null, null);
		
		//otherwise, check if newNode should be placed to the left of right in the tree
		add2(root, newNode);
	}
	
	/**
	 * Recursive method to add a new node to the BST
	 * @param currentRoot the current place we are at in the tree
	 * @param newNode the new node to place in the proper position in the tree
	 */
	private void add2(Node<E> currentRoot, Node<E> newNode) {
		//empty tree -> make it the root, increment the size and finished
		if (currentRoot == null) {
			root = newNode;
			size++;
			return;
		}
		//already in the tree
		if (currentRoot == newNode) {
			return;
		}
		else if (newNode.data.compareTo(currentRoot.data) < 0) {
			if (currentRoot.left != null) {
				add2(currentRoot.left, newNode);
			}
			else currentRoot.left = newNode;
			size++;
			return;
		}
		else {
			if (newNode.data.compareTo(currentRoot.data) > 0) {
				if (currentRoot.right != null) {
					add2(currentRoot.right, newNode);
				}
				else currentRoot.right = newNode;
				size++;
				return;
			}
		}
	}
	
	/**
	 * This method is unsupported by the BST class
	 * @throws UnsupportedOperationException if the method is invoked
	 */
	public boolean addAll(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException("Invalid operation.");
	}

	/**
	 * This method removes all nodes from the BST
	 */
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * Checks if the BST contains the specified object
	 * @return true if it contains the specified object o, and false if it does not contain o
	 */
	public boolean contains(Object o) {
		try {
			E data = (E)o; //safe cast
			if (data == null || isEmpty()) {
				return false;
			}
			return contains(data, root);
		}
		//if Object o is not of type E, then it cannot possibly be in the BST
		catch (ClassCastException e){
			return false;
		}
	}
	
	/**
	 * Private recursive method that checks if the BST contains the specified object
	 * @param data the data we want to find
	 * @param node the node that we are currently checking against in the tree 
	 * @return true if the BST contains the specified object o, 
	 * and false if it does not contain o
	 */
	private boolean contains(E data, Node<E> node) {
		if (node != null) {
			if (data.compareTo(node.data) == 0) {
				return true;
			}
			if (data.compareTo(node.data) < 0) {
				return contains(data, node.left);
			}
			if (data.compareTo(node.data) > 0) {
				return contains(data, node.right);
			}
		}
		return false;
	}
	
	/**
	 * Checks if this BST contains all of the data in the specified collection
	 * @return true if it contains all the data, and return false if it does not 
	 */
	public boolean containsAll(Collection<?> theCollection) {
		for (Object o : theCollection) { //for each object in the collection
			if (!contains(o)) { //if o is not in it, return false
				return false; 
			}
		}
		return true; //it contains everything
	}
	
	/**
	 * Returns the hash code value for this BST
	 */
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * This method returns a new instance of an iterator which performs an preorder traversal
	 * @return a new instance of an iterator which performs an preorder traversal
	 */
	public Iterator<E> preorderIterator() {
		return new preorderIterator();
	}
	
	/**
	 * This method returns a new instance of an iterator which performs an postorder traversal
	 * @return a new instance of an iterator which performs an postorder traversal
	 */
	public Iterator<E> postorderIterator() {
		return new postorderIterator();
	}
	
	/**
	 * This method returns a new instance of an iterator which performs an inorder traversal
	 * @return a new instance of an iterator which performs an inorder traversal
	 */
	public Iterator<E> iterator() {
		return new inorderIterator();
	}
	
	/**
	 * Removes the specified object from the BST if present
	 * @return true if the object is found and removed, 
	 * and return false if the object is not found and removed
	 */
	public boolean remove(Object o) {
		try {
			E data = (E)o; // try to cast from object to E
			
			//you can't remove something that is not in the tree!
			if (!contains(data)) {
				return false;
			}
			
			root = remove(data, root);
			
			//successfully removed! 
			return true;
		}
		
		/* if the object is not the same type as the tree,
		 * it cannot and will not be in the tree! 
		 */
		catch (ClassCastException e) { 
			return false;
		}
	}
	
	/**
	 * Private recursive method to zigzag through the tree,
	 * and find the node to be removed. Once found, this method
	 * calls the removeIt method on the node, to remove it from the BST
	 * @param data the data in the node to be removed 
	 * @param node the current node we are at and examining in the tree
	 * @return the node to be removed from the BST
	 */
	private Node<E> remove(E data, Node<E> node) {
	
		if (node != null) {
			//found what we want to remove!
			if (data.compareTo(node.data) == 0) {
				return removeIt(node);
			}
			//the node we want to remove is less than the node we are currently at
			//so go check the left child
			if (data.compareTo(node.data) < 0) {
				if (node.left != null) {
					node.left = remove(data, node.left);
				}
			}
			//the node we want to remove is greater that the node we are currently at
			//so go check the right child
			if (data.compareTo(node.data) > 0) {
				if (node.right != null) {
					node.right = remove(data, node.right);
				}
			}
		}
		return node; //node is null
	}
	
	/**
	 * Method to remove the identified node from the tree
	 * Checks how many children the node to be removed has, and then handles them accordingly.
	 * @param node the node to be removed
	 * @return the child node to take its place, or null if it has 0 children
	 */
	private Node<E> removeIt(Node<E> node) {
		//0 children, just remove
		if (node.left == null && node.right == null) {
			size--;
			return null;
		}
		
		//1 child, hand it over
		else if (node.left == null && node.right != null) {
			size--;
			return node.right;
		}
		else if (node.right == null && node.left != null) {
			size--;
			return node.left;
		}
		
		//2 children, one step to left and slide all the way to the right
		else {
			E copyOfData = findSmallestSuccessor(node.right);
			node.data = copyOfData; //copy in the successor's data
			
			/* Now there is another copy of this value in the leftmost right child, 
			 * so traverse to it and remove it.
			 * 
			 * We call remove with the copyOfData and node.right as parameters,
			 * so that we can start looking for this duplicate value in the right branch,
			 * and not accidently remove the value we just placed!
			 * 
			 * Note that we do not write size-- here, because that will happen when we call
			 * remove() to get rid of the extra node, and we don't want the tree to be 
			 * decremented twice!
			 */
			node.right = remove(copyOfData, node.right);
			return node; //finally, return this node back to its proper branch
		}
	}

	/**
	 * In cases where the node to be removed has 2 children, 
	 * this method finds the leftmost child in the node's right subtree and makes it the successor
	 * @param node the right child of the node to be removed
	 * @return the leftmost right child of the node we are removing
	 */
	private E findSmallestSuccessor(Node<E> node) {
		if (node == null) {
			throw new NullPointerException("findSmallestSuccessor cannot be called with a null node.");
		}
		
 		while (node.left != null) {
			node = node.left;
		}
 		
		return node.data; //this node is the leftmost right child, the smallest value greater than the node we are replacing
	}
	
	/**
	 * This method is unsupported by the BST class
	 * @throws UnsupportedOperationException if the method is invoked
	 */
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException("Invalid operation.");
	}

	/**
	 * This method is unsupported by the BST class
	 * @throws UnsupportedOperationException if the method is invoked
	 */
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException("Invalid operation.");
	}
	
	
	/**
	 * This method calls the recursive method to print the inorder traversal of the tree
	 */
	public void inorder() {
		inorder(root);
	}
	
	
	/**
	 * Private recursive method to print an inorder traversal of the tree
	 */
	private void inorder(Node<E> node) {
		if (node != null) {
			inorder(node.left);
			System.out.println(node);
			inorder(node.right);
		}
	}
	
	
	/**
	 * Method to print out the tree in a tree like shape,
	 * showing the position of the nodes and their children
	 * 
	 * @author Professor Klukowska
	 * @return the string representation of the tree in a tree shape
	 */
	public String toStringTreeFormat() {
		StringBuilder s = new StringBuilder();
		preOrderPrint(root, 0, s);
		return s.toString();
	}
	
	
	/**
	 * Private method to help build up the tree shape into the toStringTreeFormat() method
	 * @param tree the node of the tree to add to the structure
	 * @param level the current level within the tree structure
	 * @param output the string representation which is being added to, to build up the tree structure
	 * @author Professor Klukowska
	 */
	private void preOrderPrint(Node<E> tree, int level, StringBuilder output) {
		if (tree != null) {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append(tree.data);
			preOrderPrint(tree.left, level + 1, output);
			preOrderPrint(tree.right , level + 1, output);
		}
		// uncomment the part below to show "null children" in the output
		else {
			String spaces = "\n";
			if (level > 0) {
				for (int i = 0; i < level - 1; i++)
					spaces += "   ";
				spaces += "|--";
			}
			output.append(spaces);
			output.append("null");
		}
	}
	
	
	/**
	 * This class implements Iterator<E>, and is instantiated when the preorderIterator() is called
	 * it returns an iterator which traverses the tree using an preorder traversal.
	 * @author atara
	 */
	private class preorderIterator implements Iterator<E> {
		//instance variables
		private ArrayList<E> storage; //ArrayList to store all of the data in the tree
		private int storageIndex; //the current index in storage
		
		/**
		 * Constructs a new iterator, to traverse in preorder
		 * Creates and puts all of the tree's data in an arraylist by calling the myPreorderIterator method
		 */
		public preorderIterator() {
			storage = new ArrayList<>();
			storageIndex = 0;
			myPreorderIterator(root, storage);
		}
		
		/**
		 * Private recursive method to fill the storage list with the tree's data the preorder traversal.
		 * @param node the node we are traversing
		 * @param storage the arraylist that is being filled with the data from the tree
		 */
		private void myPreorderIterator(Node<E> node, ArrayList<E> storage) {
			if (node != null) {
				storage.add(node.data);
				myPreorderIterator(node.left, storage);
				myPreorderIterator(node.right, storage);
			}
		}
		
		/**
		 * This method checks if there is another node in the tree to iterate.
		 * It returns true when the index we are at is less than the amount of data in the list, 
		 * meaning we still have more data to traverse
		 * @return true if there is more data to traverse, false if there is no more data to traverse
		 */
		public boolean hasNext() {
			if (storageIndex < storage.size()) {
				return true;
			}
			return false;
		}

		/**
		 * This method advances the iterator to the next node in the tree.
		 * @return the data from the next node in the tree
		 */
		public E next() {
			return storage.get(storageIndex++);
		}
		
		/**
		 * This is an unsupported remove() method of the iterator
		 * @throws UnsupportedOperationException when this method is called because it is unsupported by this program
		 */
		public void remove() {
			throw new UnsupportedOperationException("Invalid operation.");
		}
	} //end of nested preorder iterator class
	
	
	/**
	 * This class implements Iterator<E>, and is instantiated when the postorderIterator() is called
	 * it returns an iterator which traverses the tree using an postorder traversal.
	 * @author atara
	 */
	private class postorderIterator implements Iterator<E> {
		//instance variables
		private ArrayList<E> storage; //ArrayList to store all of the data in the tree
		private int storageIndex; //the current index in storage
		
		/**
		 * Constructs a new treeIterator, to traverse in postorder
		 * Creates and puts all of the tree's data in an arraylist by calling the myPostorderIterator method
		 */
		public postorderIterator() {
			storage = new ArrayList<>();
			storageIndex = 0;
			myPostorderIterator(root, storage);
		}
		
		/**
		 * Private recursive method to fill the storage list with the tree's data in postorder traversal.
		 * @param node the node we are traversing
		 * @param storage the arraylist that is being filled with the data from the tree
		 */
		private void myPostorderIterator(Node<E> node, ArrayList<E> storage) {
			if (node != null) {
				myPostorderIterator(node.left, storage);
				myPostorderIterator(node.right, storage);
				storage.add(node.data);
			}
		}
		
		/**
		 * This method checks if there is another node in the tree to iterate.
		 * It returns true when the index we are at is less than the amount of data in the list, 
		 * meaning we still have more data to traverse
		 * @return true if there is more data to traverse, false if there is no more data to traverse
		 */
		public boolean hasNext() {
			if (storageIndex < storage.size()) {
				return true;
			}
			return false;
		}

		/**
		 * This method advances the iterator to the next node in the tree.
		 * @return the data from the next node in the tree
		 */
		public E next() {
			return storage.get(storageIndex++);
		}
		
		/**
		 * This is an unsupported remove() method of the iterator
		 * @throws UnsupportedOperationException when this method is called because it is unsupported by this program
		 */
		public void remove() {
			throw new UnsupportedOperationException("Invalid operation.");
		}
	} //end of nested postorder iterator class
	
	
	/**
	 * This class implements Iterator<E>, and is instantiated when the iterator() is called
	 * it returns an iterator which traverses the tree using an inorder traversal.
	 * @author atara
	 */
	private class inorderIterator implements Iterator<E> {
		//instance variables
		private ArrayList<E> storage; //ArrayList to store all of the data in the tree
		private int storageIndex; //the current index in storage
		
		/**
		 * Constructs a new treeIterator, to traverse inorder
		 * Creates and puts all of the tree's data in an arraylist by calling the inorderIterator method
		 */
		public inorderIterator() {
			storage = new ArrayList<>();
			storageIndex = 0;
			myInorderIterator(root, storage);
		}
		
		/**
		 * Private recursive method to fill the storage list with the tree's data inorder.
		 * @param node the node we are traversing
		 * @param storage the arraylist that is being filled with the data from the tree
		 */
		private void myInorderIterator(Node<E> node, ArrayList<E> storage) {
			if (node != null) {
				myInorderIterator(node.left, storage);
				storage.add(node.data);
				myInorderIterator(node.right, storage);
			}
		}
		
		/**
		 * This method checks if there is another node in the tree to iterate.
		 * It returns true when the index we are at is less than the amount of data in the list, 
		 * meaning we still have more data to traverse
		 * @return true if there are more nodes to traverse, false if there are no more to traverse
		 */
		public boolean hasNext() {
			if (storageIndex < storage.size()) {
				return true;
			}
			return false;
		}

		/**
		 * This method advances the iterator to the next node in the tree.
		 * @return the data from the netx node in the tree
		 */
		public E next() {
			return storage.get(storageIndex++);
		}
		
		/**
		 * This is an unsupported remove() method of the iterator
		 * @throws UnsupportedOperationException when this method is called because it is unsupported by this program
		 */
		public void remove() {
			throw new UnsupportedOperationException("Invalid operation.");
		}
	} //end of nested inorder iterator class

	
	/**
	 * This is a private, static, generic nested class which represents a node of a binary search tree.
	 * Each node contains its data, as well as a reference to its left and right child in the tree. 
	 */
	private static class Node<E extends Comparable<E>> implements Comparable<Node<E>> {
			//instance variables
			protected E data = null; //generic type field to store the node's data
			protected Node<E> left = null; //reference to left child
			protected Node<E> right = null; //reference to right child
			
			/**
			 * Constructs a new BST node with the specified data and references
			 * to the left and right children as its parameters.
			 * @param d the data to be stored in this node. Generic.
			 * @param l the reference to the left child of the node
			 * @param r the reference to the right child of the node
			 */
			protected Node(E d, Node<E> l, Node<E> r) {
				data = d;
				left = l;
				right = r;
			}
			
			/**
			 * Compares the data of this node to the data of another node
			 * @param other the other node whose data we are comparing to 
			 */
			@Override
			public int compareTo(Node<E> other) {
				return this.data.compareTo(other.data);
			}
			
			/**
			 * Returns the string format of the data in this node
			 */
			@Override
			public String toString() {
				return data.toString();
			}
		} //end of nested node class
		
}//end of BST class
