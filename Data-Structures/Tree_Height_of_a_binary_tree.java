int height(Node root) {
	if (root == null) {
		return 0;
	} else {
		return 1 + Math.max(height(root.left), height(root.right));
	}
}
