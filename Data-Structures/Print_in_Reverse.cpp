#include <vector>

void ReversePrint(Node *head) {
	vector<int> elements;

	while (head != NULL) {
		elements.push_back(head->data);
		head = head->next;
	}

	for (int i = elements.size() - 1; i >= 0; i--) {
		cout << elements[i] << endl;
	}
}
