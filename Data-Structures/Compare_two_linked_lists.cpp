#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

int CompareLists(Node *headA, Node* headB) {
	if (headA == NULL && headB == NULL) {
		return 1;
	}

	if ((headA == NULL && headB != NULL) || (headA != NULL && headB == NULL)) {
		return 0;
	}

	while (headA->data == headB->data) {
		headA = headA->next;
		headB = headB->next;

		if (headA == NULL || headB == NULL) {
			break;
		}
	}

	if (headA == NULL && headB == NULL) {
		return 1;
	} else {
		return 0;
	}
}
