#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

int HasCycle(Node* head) {
	Node * p = head;
	Node * q = head;

	while (q != NULL) {
		p = p->next;
		q = q->next;

		if (q == NULL) {
			return 0;
		}

		q = q->next;

		if (p == q) {
			return 1;
		}
	}

	return 0;
}
