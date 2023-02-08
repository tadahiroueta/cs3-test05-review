import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class Node<T> { //not an inner class for ease of testing
	T       data;
	Node<T> left;
	Node<T> right;

	public Node(T data) {
		this(data, null, null);
	}

	public Node(T data, Node<T> left, Node<T> right) {
		this.data  = data;
		this.left  = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return "" + this.data;
	}
}

public class BinaryTree<T>
{
	private Node<T> overallRoot;
	
	/** Construct an empty tree */
	public BinaryTree() { }

	/** Construct a binary tree given a pre-built tree */
	public BinaryTree(Node<T> overallRoot) {
		this.overallRoot = overallRoot;
	}
	
	public Node<T> getRoot() { return this.overallRoot; }
	
	public void reflect() { reflect(overallRoot); }

	private void reflect(Node<T> root) {
		if (root == null) return;

		Node<T> temporary = root.left;
		root.left = root.right;
		root.right = temporary;

		reflect(root.left); 
		reflect(root.right);
	}

	public Node<String> build(int levels, String s) {
		if (levels <= 0) return null;

		Node<String> root = new Node<String>(s);
		
		root.left = build(--levels, s);
		root.right = build(levels, s);
		
		return root;
	}

	public void save(String filename) {
		try {
			PrintWriter out = new PrintWriter(new File(filename));
			save(out, overallRoot);
			out.close();
		} catch (IOException e) {
			System.out.println("Error saving file: " + e.getMessage());
		}
	}

	private void save(PrintWriter out, Node<T> root) {
		if (root == null) {
			out.println("$");
			return;
		}

		out.println(root.data);
		save(out, root.left);
		save(out, root.right);
	}

	public Node<String> load(String filename) {
		try {
			Scanner in = new Scanner(new File(filename));
			Node<String> root = load(in);
			in.close();
			return root;
		} 
		catch (IOException e) { System.out.println("Error loading file: " + e.getMessage()); }

		return null;
	}

	private Node<String> load(Scanner in) {
		if (!in.hasNext()) return null;

		Node<String> root = new Node<String>(in.next());
		root.left = load(in);
		root.right = load(in);

		return root;
	}
}
