#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

int GetNode(Node *head, int positionFromTail) {
	Node * p = head;
	Node * q = head;
	int diff = positionFromTail;

	while (diff--) {
		q = q->next;
	}

	while (q->next != NULL) {
		q = q->next;
		p = p->next;

	}

	return p->data;
}
