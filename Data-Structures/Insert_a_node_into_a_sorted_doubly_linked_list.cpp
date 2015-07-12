#include <cmath>
#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* SortedInsert(Node *head, int data) {
	Node *cur = new Node();
	cur->data = data;
	cur->next = cur->prev = NULL;

	if (head == NULL) {
		return cur;
	}

	Node *pos = head;

	while (pos != NULL && pos->data <= data) {
		pos = pos->next;
	}

	if (pos == NULL) {
		Node *tail = head;

		while (tail->next != NULL) {
			tail = tail->next;
		}

		tail->next = cur;
		cur->prev = tail;

		return head;
	}

	Node *pre = pos->prev;

	if (pre == NULL) {
		cur->next = head;
		head->prev = cur;

		return cur;
	}

	pre->next = cur;
	cur->prev = pre;
	cur->next = pos;
	pos->prev = cur;

	return head;
}
