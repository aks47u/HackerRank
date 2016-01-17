import java.util.HashMap;
import java.util.Scanner;

public class Swap_Nodes_Algo {
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		Node root = buildTree(in);
		int t = in.nextInt();

		while (t-- > 0) {
			swapKDevisible(root, in.nextInt());
			inOrderTraversal(root);
			System.out.println();
		}
	}

	private static void swapKDevisible(Node nd, int k) {
		if (nd == null) {
			return;
		}

		if (nd.depth % k == 0) {
			swapChildren(nd);
		}

		swapKDevisible(nd.left, k);
		swapKDevisible(nd.right, k);
	}

	private static Node buildTree(Scanner sc) {
		HashMap<Integer, Node> cache = new HashMap<Integer, Node>();
		Node root = new Node(null, null, 1, 1);
		cache.put(1, root);
		int len = sc.nextInt();

		for (int i = 0; i < len; ++i) {
			int ind1 = sc.nextInt();
			int ind2 = sc.nextInt();
			Node parent = cache.get(i + 1);
			Node left = buildNewNode(ind1, parent.depth + 1);
			Node right = buildNewNode(ind2, parent.depth + 1);

			if (ind1 != -1) {
				cache.put(ind1, left);
			}

			if (ind2 != -1) {
				cache.put(ind2, right);
			}

			parent.left = left;
			parent.right = right;
		}

		return root;
	}

	private static Node buildNewNode(int data, int depth) {
		if (data == -1) {
			return null;
		}

		return new Node(null, null, data, depth);
	}

	private static void swapChildren(Node nd) {
		if (nd == null) {
			return;
		}

		Node t = nd.right;
		nd.right = nd.left;
		nd.left = t;
	}

	private static void inOrderTraversal(Node nd) {
		if (nd == null) {
			return;
		}

		inOrderTraversal(nd.left);
		System.out.print(nd.data + " ");
		inOrderTraversal(nd.right);
	}

	private static class Node {
		public Node left;
		public Node right;
		public int data;
		public int depth;

		public Node(Node left, Node right, int data, int depth) {
			this.left = left;
			this.right = right;
			this.data = data;
			this.depth = depth;
		}
	}
}
