#include <cstdio>
#include <cstdlib>
#include <iostream>

using namespace std;

Node* RemoveDuplicates(Node *head) {
	if (head == NULL || head->next == NULL) {
		return head;
	}

	Node *cur = head;

	while (cur->next != NULL) {
		Node *sec = cur->next;

		while (sec != NULL && sec->data == cur->data) {
			sec = sec->next;
		}

		if (sec != NULL) {
			Node *del = cur->next;

			while (del != sec) {
				Node *_next = del->next;
				delete del;
				del = _next;
			}
		} else if (sec == NULL) {
			Node *del = cur->next;

			while (del != NULL) {
				Node *_next = del->next;
				delete del;
				del = _next;
			}
		}

		cur->next = sec;
		cur = sec;

		if (cur == NULL) {
			break;
		}
	}

	return head;
}
