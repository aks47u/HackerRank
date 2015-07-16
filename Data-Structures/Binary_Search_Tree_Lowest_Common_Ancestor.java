static Node lca(Node root, int v1, int v2) {
	Queue<String> v1path = new LinkedList<String>();
	Queue<String> v2path = new LinkedList<String>();
	String left = "l";
	String right = "r";
	Node rootcopy1 = new Node();
	rootcopy1 = root;
	Node rootcopy2 = new Node();
	rootcopy2 = root;

	while (rootcopy1 != null) {
		if (v1 == rootcopy1.data) {
			break;
		} else if (v1 < rootcopy1.data) {
			v1path.add(left);
			rootcopy1 = rootcopy1.left;
		} else {
			v1path.add(right);
			rootcopy1 = rootcopy1.right;
		}
	}

	while (rootcopy2 != null) {
		if (v2 == rootcopy2.data) {
			break;
		} else if (v2 < rootcopy2.data) {
			v2path.add(left);
			rootcopy2 = rootcopy2.left;
		} else {
			v2path.add(right);
			rootcopy2 = rootcopy2.right;
		}
	}

	rootcopy1 = root;

	while (v1path.peek() != null && v2path.peek() != null) {
		if (v1path.peek() == v2path.peek()) {
			if (v1path.peek() == "l") {
				v1path.remove();
				v2path.remove();
				rootcopy1 = rootcopy1.left;
			} else {
				v1path.remove();
				v2path.remove();
				rootcopy1 = rootcopy1.right;
			}
		} else {
			break;
		}
	}

	return rootcopy1;
}
