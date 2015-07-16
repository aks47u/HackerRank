static Node Insert(Node root, int value) {
	if (root == null) {
		root = new Node();
		root.data = value;
	} else if (root.data > value) {
		if (root.left == null) {
			Node left = new Node();
			left.data = value;
			root.left = left;
		} else {
			Insert(root.left, value);
		}
	} else {
		if (root.right == null) {
			Node right = new Node();
			right.data = value;
			root.right = right;
		} else {
			Insert(root.right, value);
		}
	}

	return root;
}
