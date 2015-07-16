void decode(String S, Node root) {
	Node temp = root;
	String ans = "";

	for (int i = 0; i < S.length(); i++) {
		if (S.charAt(i) == '0') {
			temp = temp.left;
		} else {
			temp = temp.right;
		}

		if (temp.right == null && temp.left == null) {
			ans += (temp.data);
			temp = root;
		}
	}

	System.out.println(ans);
}
