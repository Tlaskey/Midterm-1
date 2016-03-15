class ThreadedBinarySearchTree {
	ThreadedBinaryNode root;
	ThreadedBinaryNode current;
	boolean directionRight = false;
	boolean directionLeft = false;

	public ThreadedBinarySearchTree() {
		// virtual root node
		root = new ThreadedBinaryNode(999);
		root.lBit = 0;
		root.rBit = 1;
		root.left = root.right = root;
	}

	public static void main(String[] args) {
		ThreadedBinarySearchTree tree = new ThreadedBinarySearchTree();
		tree.insert(35);
		tree.insert(20);
		tree.insert(25);
		tree.insert(40);
		tree.insert(15);
		tree.inOrder();
		System.out.println("-----");
		tree.remove(25);
		tree.inOrder();
	}
	
//	public ThreadedBinaryNode search(int number){
//		current = root.left;
//		while(current != null){
//			if(number > current.element){
//				current = current.right;
//			}
//			else if(number < current.element){
//				current = current.left;
//			}
//			else{
//				break;
//			}
//		}
//		return current;
//	}
	
	// Prints starting at root.left which is our real root node.
	public void inOrder() {
		current = root.left;
		while (current.lBit == 1) {
			current = current.left;
		}
		while (current != root) {
			System.out.println("--> " + current.element);
			current = nextInOrder(current);
		}
	}

	public ThreadedBinaryNode nextInOrder(ThreadedBinaryNode next) {
		// returns the inOrder successor of the node because rBit is 0.
		if (next.rBit == 0) {
			return next.right;
		}
		// Else go to the right
		next = next.right;
		// while lBit has a child, go left
		while (next.lBit == 1) {
			next = next.left;
		}
		return next;
	}

	public void remove(int element) {
		ThreadedBinaryNode node = root.left;
		ThreadedBinaryNode parent = root;
		//Finds the element to be removed.
		for (;;) {
			if (node.element < element) {
				if (node.rBit == 0)
					return; // not found
				parent = node;
				node = node.right;
			} else if (node.element > element) {
				if (node.lBit == 0)
					return; // not found
				parent = node;
				node = node.left;
			} else {
				break; // found the element to be removed
			}
		}
		//Removes the target node.
		ThreadedBinaryNode remove = node;
		if (node.rBit == 1 && node.lBit == 1) { // node has two children
			parent = node;
			// find largest ThreadedBinaryNode at left child
			remove = node.left;
			while (remove.rBit == 1) {
				parent = remove;
				remove = remove.right;
			}
			node.element = remove.element; 
		}
		if (parent.element >= remove.element) {
			if (remove.rBit == 0 && remove.lBit == 0) {
				// p
				// /
				// t
				parent.left = remove.left;
				parent.lBit = 0;
			} else if (remove.rBit == 0) {
				// p
				// /
				// t
				// /
				// t.left
				ThreadedBinaryNode largest = remove.left;
				while (largest.rBit == 1) {
					largest = largest.right;
				}
				largest.right = parent;
				parent.left = remove.left;
			} else {
				// p
				// /
				// t
				// \
				// t.right
				ThreadedBinaryNode smallest = remove.right;
				while (smallest.lBit == 1) {
					smallest = smallest.left;
				}
				smallest.left = remove.left;
				parent.left = remove.right;
			}
		} else {
			if (remove.rBit == 0 && remove.lBit == 0) {
				// p
				// \
				// t
				parent.right = remove.right;
				parent.rBit = 0;
			} else if (remove.rBit == 0) {
				// p
				// \
				// t
				// /
				// t.left
				ThreadedBinaryNode largest = remove.left;
				while (largest.rBit == 1) {
					largest = largest.right;
				}
				largest.right = remove.right;
				parent.right = remove.left;
			} else {
				// p
				// \
				// t
				// \
				// t.right
				ThreadedBinaryNode smallest = remove.right;
				while (smallest.lBit == 1) {
					smallest = smallest.left;
				}
				smallest.left = parent;
				parent.right = remove.right;
			}
		}
	}

	public void insert(int number) {
		ThreadedBinaryNode r = root.left;
		// Finds where the number should be inserted.
		for (;;) {
			if (number > r.element) {
				if (r.rBit == 0)
					break;
				r = r.right;
			} else if (number < r.element) {
				if (r.lBit == 0)
					break;
				r = r.left;
			} else {
				return;
			}
		}
		// Creates the node and inserts.
		ThreadedBinaryNode newNode = new ThreadedBinaryNode(0);
		newNode.element = number;
		newNode.rBit = newNode.lBit = 0;
		if (r.element < number) {
			newNode.right = r.right;
			newNode.left = r;
			r.right = newNode;
			r.rBit = 1;
		} else {
			newNode.right = r;
			newNode.left = r.left;
			r.left = newNode;
			r.lBit = 1;
		}
	}

	// Node with smallest element that is larger than node.
	public ThreadedBinaryNode findSuccessor(ThreadedBinaryNode node) {
		ThreadedBinaryNode current = node;
		ThreadedBinaryNode root2 = root.left;
		ThreadedBinaryNode succ = new ThreadedBinaryNode(0);
		if (current.right != null) {
			succ = findMin(current.right);
		} else {
			while (root2 != null) {
				if (current.element < root2.element) {
					succ = root2;
					root2 = root2.left;
				} else if (current.element > root2.element) {
					root2 = root2.right;
				} else {
					break;
				}
			}
		}
		return succ;
	}

	// Node with largest element in tree that is smaller than node.
	public ThreadedBinaryNode findPredecessor(ThreadedBinaryNode node) {
		ThreadedBinaryNode current = node;
		ThreadedBinaryNode root2 = root.left;
		ThreadedBinaryNode pred = new ThreadedBinaryNode(0);
		if (current.left != null) {
			return pred = findMax(current.left);
		} else {
			while (root2 != null) {
				if (current.element > root2.element) {
					pred = root2;
					root2 = root2.right;
				} else if (current.element < root2.element) {
					root2 = root2.left;
				} else {
					break;
				}
			}
			return pred;
		}
	}

	public ThreadedBinaryNode findMin(ThreadedBinaryNode node) {
		if (node == null) {
			return null;
		} else if (node.left == null) {
			return node;
		}
		return findMin(node.left);
	}

	public ThreadedBinaryNode findMax(ThreadedBinaryNode node) {
		if (node != null) {
			while (node.right != null) {
				node = node.right;
			}
		}
		return node;
	}
}

class ThreadedBinaryNode {
	int element;
	ThreadedBinaryNode left;
	ThreadedBinaryNode right;
	// If lBit or rBit is 1 it has a child node. If it has 0 it is connected to
	// a thread.
	// node.lBit = 0 means that node.left points to its inOrder predecessor
	// node.rBit = 0 means that node.right points to its inOrder successor
	int lBit;
	int rBit;

	ThreadedBinaryNode(int number) {
		element = number;
	}
}