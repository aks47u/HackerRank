#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

int FindMergeNode(Node *headA, Node *headB) {
	Node * p = headA;
	Node * q = headB;

	while (p->next != NULL) {
		p = p->next;
		q = headB;

		while (q != NULL) {
			if (p == q) {
				return p->data;
			}

			q = q->next;
		}
	}

	return 0;
}
