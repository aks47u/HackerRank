#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* Reverse(Node *head) {
	if (head->next == NULL) {
		return head;
	}

	Node * curr = head;
	Node * prev = NULL;
	Node * q;

	while (curr != NULL) {
		q = curr->next;
		curr->next = prev;
		prev = curr;
		curr = q;
	}

	return prev;
}
