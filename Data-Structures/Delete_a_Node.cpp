#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* Delete(Node *head, int position) {
	Node * p = head;

	if (position == 0) {
		Node * q = head->next;
		free(p);

		return q;
	}

	while (--position) {
		p = p->next;
	}

	Node * q = p->next;
	p->next = p->next->next;
	free(q);

	return head;
}
