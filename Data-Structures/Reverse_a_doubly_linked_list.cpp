#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* Reverse(Node* head) {
	if (head == NULL) {
		return NULL;
	}

	Node* p = head;
	Node* q = p->next;

	while (p->next != NULL) {
		q = p->next;
		p->next = p->prev;
		p->prev = q;
		p = p->prev;
	}

	p->next = p->prev;
	p->prev = NULL;

	return p;
}
