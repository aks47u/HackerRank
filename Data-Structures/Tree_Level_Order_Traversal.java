void LevelOrder(Node root) {
	Node last = root;
	Queue<Node> q1 = new LinkedList<Node>();
	Queue<Node> q2 = new LinkedList<Node>();
	q1.add(root);
	Node cur = null;

	while (!q1.isEmpty() || !q2.isEmpty()) {
		while (!q1.isEmpty()) {
			cur = q1.poll();

			if (cur.left != null) {
				q2.add(cur.left);
			}

			if (cur.right != null) {
				q2.add(cur.right);
			}

			System.out.print(cur.data + " ");
		}

		while (!q2.isEmpty()) {
			cur = q2.poll();

			if (cur.left != null) {
				q1.add(cur.left);
			}

			if (cur.right != null) {
				q1.add(cur.right);
			}

			System.out.print(cur.data + " ");
		}
	}
}
