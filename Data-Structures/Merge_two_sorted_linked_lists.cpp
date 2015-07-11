#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* MergeLists(Node *headA, Node* headB) {
	if (headA == NULL) {
		return headB;
	} else if (headB == NULL) {
		return headA;
	} else {
		if (headA->data < headB->data) {
			Node * restA = headA->next;
			headA->next = MergeLists(restA, headB);

			return headA;
		} else {
			Node * restB = headB->next;
			headB->next = MergeLists(headA, restB);

			return headB;
		}
	}
}
