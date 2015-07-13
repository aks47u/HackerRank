void top_view(Node root) {
	if (root == null) {
		return;
		}
	
	Stack st = new Stack();
	int size = 0;
	int arr[] = new int[100];
	Node Left = null, Right = null;
	Left = root.left;
	Right = root.right;

	while (Right != null) {
		arr[size] = Right.data;
		size++;
		Right = Right.right;
		
		if (Right == null) {
			for (int i = size - 1; i >= 0; i--) {
				st.push(arr[i]);
			}
		}
	}

	st.push(root.data);
	
	while (Left != null) {
		st.push(Left.data);
		Left = Left.left;
	}
	
	while (st.isEmpty() != true) {
		System.out.print(st.pop() + " ");
	}
}
