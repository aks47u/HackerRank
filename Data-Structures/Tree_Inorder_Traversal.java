void Inorder(Node root) {
    if (root == null) {
        return;
    }

    Inorder(root.left);
    System.out.print(root.data + " ");
    Inorder(root.right);
}
