#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* Insert(Node *head, int data) {
	Node *node = new Node();
	node->data = data;
	node->next = NULL;

	if (head == NULL) {
		return node;
	}

	Node *temp = head;

	while (temp->next != NULL) {
		temp = temp->next;
	}

	temp->next = node;

	return head;
}
