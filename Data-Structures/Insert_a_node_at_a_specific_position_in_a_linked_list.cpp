#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* InsertNth(Node *head, int data, int position) {
	Node *node = new Node();
	node->data = data;
	node->next = NULL;
	Node *temp = head;

	if (position == 0) {
		node->next = head;
		head = node;
	} else {
		for (int i = 1; i < position; i++) {
			temp = temp->next;
		}

		node->next = temp->next;
		temp->next = node;
	}

	return head;
}
