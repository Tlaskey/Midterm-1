
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.Stack;

public class BinaryExpressionTree {
	public static <E> void main(String[] args) {
		//Input: 1 3 + 4 * 5 + which means ((1+3) * 4) + 5
		//Output: (5+(4*(3+1))) InOrder traversal printing
		Character[] l1 = { '1', '3', '+', '4', '*', '5', '+' };
		postFixToExpressionTree(l1);
	}
	//Converts a postfix expression to a binary expression tree.
	//Only works with a correct postfix expression.
	//Prints the tree using InOrder traversal. (Left, Node, Right)
	public static <E> void postFixToExpressionTree(Character[] list) {
		Stack<BinaryTree<E>> s1 = new Stack<>();
		for (int i = 0; i < list.length; i++) {
			//If the character is a number, create a new tree and push it on the stack.
			if (Character.isDigit(list[i])) {
				BinaryTree<E> t1 = new BinaryTree<>();
				t1.addNode((E) list[i]);
				s1.push(t1);
			//Else (The character is an operator) pop two numbers off stack 
			//and add them to tree.
			} else {
				BinaryTree<E> t2 = new BinaryTree<>();
				t2.addNode((E) list[i]);
				if (!s1.empty()) {
					t2.addTree(s1.pop());
					t2.addTree(s1.pop());
					s1.push(t2);
				}
			}
		}
		BinaryTree<E> temp = s1.pop();
		traversePrintBinaryTree(temp.root);
	}

	// InOrder traversal and printing.
	public static <E> void traversePrintBinaryTree(BinaryNode<E> root) {
		if (root.left != null) {
			System.out.print("(");
			traversePrintBinaryTree(root.left);
		}
		System.out.print(root.element + "");
		if (root.right != null) {
			traversePrintBinaryTree(root.right);
			System.out.print(")");
		}
	}
}

class BinaryTree<E> {
	BinaryNode<E> root;
	public void addTree(BinaryTree<E> tree) {
		BinaryNode<E> position = root;
		if (tree.root.left == null && tree.root.right == null) {
			this.addNode(tree.root.element);
		} else {
			if (position.left == null) {
				position.left = tree.root;
				position.left.left = tree.root.left;
				position.left.right = tree.root.right;
			} else {
				position.right = tree.root;
				position.right.left = tree.root.left;
				position.right.right = tree.root.right;
			}
		}
	}

	public void addNode(E element) {
		BinaryNode<E> newNode = new BinaryNode<E>(element);
		if (root == null) {
			setRoot(newNode);
		} else {
			BinaryNode<E> position = root;

			BinaryNode<E> parent;
			while (true) {
				parent = position;
				position = position.left;

				if (position == null) {
					parent.left = newNode;
					return;
				} else {
					parent.right = newNode;
					return;
				}
			}
		}
	}

	public void setRoot(BinaryNode<E> newNode) {
		root = newNode;
	}

	public BinaryNode<E> getRoot() {
		return root;
	}

}

class BinaryNode<E> {
	E element;
	BinaryNode<E> left;
	BinaryNode<E> right;

	public BinaryNode(E element) {
		setData(element);
	}

	public void setData(E element) {
		this.element = element;
	}

	public E getData() {
		return this.element;
	}
}
