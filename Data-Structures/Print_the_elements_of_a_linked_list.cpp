void Print(Node *head) {
	if (head != NULL) {
		while (head->next != NULL) {
			cout << head->data << endl;
			head = head->next;
		}

		cout << head->data << endl;
	}
}
