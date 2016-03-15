public class AVLTree {
	AVLNode root;

	public AVLTree() {
		this(null);
	}

	public AVLTree(AVLNode newNode) {
		root = newNode;
	}

	public void makeEmpty() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void insert(int x) {
		root = insert(x, root);
	}

	public static int findHeightOfTree(AVLNode root) {
		if (root == null) {
			return 0;
		} else {
			return (1 + Math.max(findHeightOfTree(root.left), findHeightOfTree(root.right)));
		}
	}

	public static AVLTree getSubTree(AVLNode node) {
		AVLTree t1 = new AVLTree();
		if (node == null) {
		} else {
			t1.insert(node.element);
			if (node.left != null) {
				AVLTree t2 = getSubTree(node.left);
				t1.insert(t2.root.element);
			}
			if (node.right != null) {
				AVLTree t3 = getSubTree(node.right);
				t1.insert(t3.root.element);
			}
		}
		return t1;
	}

	public static int height(AVLNode node) {
		// This means if node == null it returns -1. If node != null it returns
		// the height of the node.
		return node == null ? -1 : node.height;
	}

	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty Tree");
		} else {
			printTree(root);
		}
	}

	public void printTree(AVLNode node) {
		if (node != null) {
			printTree(node.left);
			System.out.println(node.element);
			printTree(node.right);
		}
	}

	// recursive
	public AVLNode findMin(AVLNode node) {
		if (node == null) {
			return null;
		} else if (node.left == null) {
			return node;
		}
		return findMin(node.left);
	}

	// non recursive
	public AVLNode findMax(AVLNode node) {
		if (node != null) {
			while (node.right != null) {
				node = node.right;
			}
		}
		return node;
	}

	public AVLNode insert(int number, AVLNode node) {
		if (node == null) {
			return new AVLNode(number, null, null);
		}
		if (number < node.element) {
			node.left = insert(number, node.left);
		} else if (number > node.element) {
			node.right = insert(number, node.right);
		} else {
			;
		}
		return balance(node);
	}

	public AVLNode remove(int number, AVLNode node) {
		if (node == null) {
			return node;
		} else {
			if (number < node.element) {
				node.left = remove(number, node.left);
			} else if (number > node.element) {
				node.right = remove(number, node.right);
			} else if (node.right != null && node.left != null) {
				node.element = findMin(node.right).element;
				node.right = remove(node.element, node.right);
			} else {
				node = (node.left != null) ? node.left : node.right;
			}
			return balance(node);
		}
	}

	private static final int ALLOWED_IMBALANCE = 1;

	public AVLNode balance(AVLNode node) {
		if (node == null) {
			return node;
		}
		if (height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {
			if (height(node.left.left) >= height(node.left.right)) {
				node = rotateWithLeftChild(node);
			} else {
				node = doubleWithLeftChild(node);
			}
		} else {
			if (height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {
				if (height(node.right.right) >= height(node.right.left)) {
					node = rotateWithRightChild(node);
				} else {
					node = doubleWithRightChild(node);
				}
			}
		}
		node.height = Math.max(height(node.left), height(node.right)) + 1;
		return node;
	}

	public AVLNode rotateWithLeftChild(AVLNode node2) {
		AVLNode node1 = node2.left;
		node2.left = node1.right;
		node1.right = node2;
		node2.height = Math.max(height(node2.left), height(node2.right));
		node1.height = Math.max((height(node1.left)), height(node1.right));
		return node1;
	}

	public AVLNode rotateWithRightChild(AVLNode node2) {
		AVLNode node1 = node2.right;
		node2.right = node1.left;
		node1.left = node2;
		node2.height = Math.max(height(node2.left), height(node2.right));
		node1.height = Math.max((height(node1.left)), height(node1.right));
		return node1;
	}

	public AVLNode doubleWithLeftChild(AVLNode k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	public AVLNode doubleWithRightChild(AVLNode k3) {
		k3.right = rotateWithLeftChild(k3.right);
		return rotateWithRightChild(k3);
	}

	// Checks if an AVLTree is balanced and that the heights do not differ by
	// more than 1.
	public static boolean isBalanced(AVLTree tree) {
		return tree.isEmpty() || isBalanced(getSubTree(tree.root.left)) && isBalanced(getSubTree(tree.root.right))
				&& Math.abs(findHeightOfTree(getSubTree(tree.root.left).root))
						- Math.abs(findHeightOfTree(getSubTree(tree.root.right).root)) <= 1;
	}

	public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		tree.insert(20);
		tree.insert(22);
		tree.insert(15);
		tree.insert(17);
		tree.insert(18);
		tree.insert(14);
		System.out.println("Entire tree:");
		tree.printTree();
		AVLTree subTreeLeft = AVLTree.getSubTree(tree.root.left);
		AVLTree subTreeRight = AVLTree.getSubTree(tree.root.right);
		System.out.println("Left subtree:");
		subTreeLeft.printTree();
		System.out.println("Right subtree:");
		subTreeRight.printTree();
		System.out.println("Height of the left subtree is: " + findHeightOfTree(subTreeLeft.root));
		System.out.println("Height of the right subtree is: " + findHeightOfTree(subTreeRight.root));
		System.out.println("Height of the original tree is: " + findHeightOfTree(tree.root));
		System.out.println("Is the tree balanced? " + isBalanced(tree));
	}
}

class AVLNode {
	int element;
	AVLNode left;
	AVLNode right;
	int height;

	public AVLNode(int number) {
		this(number, null, null);
	}

	public AVLNode(int number, AVLNode lt, AVLNode rt) {
		element = number;
		left = lt;
		right = rt;
		height = 0;
	}
}
