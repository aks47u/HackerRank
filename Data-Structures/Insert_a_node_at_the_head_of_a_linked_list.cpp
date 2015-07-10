#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* Insert(Node *head, int x) {
	Node *newHead = new Node;
	newHead->data = x;
	newHead->next = head;
	head = newHead;

	return head;
}
