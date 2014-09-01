package Algorithms_Warmup;

import java.util.Scanner;

public class Angry_Children {
	public static void main(String args[]) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int k = scn.nextInt();
		int a[] = new int[n];
		int max = 0, min = a[0];

		for (int i = 0; i < n; i++) {
			a[i] = scn.nextInt();

			if (a[i] > max) {
				max = a[i];
			} else if (a[i] < min) {
				min = a[i];
			}
		}
		
		scn.close();

		if (k == 1) {
			System.out.println(0);
		} else if (k == n) {
			System.out.println(max - min);
		} else {
			int minDifference = max;
			int digits = 0;

			while (max != 0) {
				max /= 10;
				digits++;
			}

			RadixSort.maxPass = (digits % 3 == 0) ? digits / 3 : digits / 3 + 1;
			RadixSort.radixSort(a);

			for (int i = 0; i < a.length; i++) {
				if (i + k - 1 >= a.length) {
					break;
				}

				if ((a[i + k - 1] - a[i]) < minDifference) {
					minDifference = a[i + k - 1] - a[i];
				}
			}

			System.out.println(minDifference);
		}
	}
}

class RadixSort {
	public static int radixQ = 3;
	public static int maxPass = 4;

	public static int[] radixSort(int[] a) {
		Queues q = new Queues();

		for (int i = 0; i < a.length; i++) {
			q.push(new QueueNode(a[i]));
		}

		for (int passes = 1; passes <= maxPass; passes++) {
			Queues digitBucket[] = new Queues[pow(10, radixQ)];

			while (!q.isEmpty()) {
				int key = q.peek();
				int digit = nthDigitFromRight(key, passes);

				if (digitBucket[digit] == null) {
					digitBucket[digit] = new Queues();
				}

				digitBucket[digit].push(q.pop());
			}

			for (int i = 0; i < digitBucket.length; i++) {
				q.merge(digitBucket[i]);
			}
		}

		for (int i = 0; i < a.length; i++) {
			a[i] = q.peek();
			q.pop();
		}

		return a;
	}

	public static int nthDigitFromRight(int digit, int n) {
		int base = pow(10, radixQ);

		while (--n > 0) {
			digit /= base;
		}

		return digit % base;
	}

	public static int pow(int number, int power) {
		int ans = 1;

		while (power > 0) {
			ans *= number;
			power--;
		}

		return ans;
	}
}

class QueueNode {
	int data;
	QueueNode next;

	public QueueNode(int data, QueueNode next) {
		this.data = data;
		this.next = next;
	}

	public QueueNode(int data) {
		this.data = data;
		this.next = null;
	}
}

class Queues {
	QueueNode first;
	QueueNode last;
	int size;

	public Queues() {
		first = null;
		last = null;
		size = 0;
	}

	public void push(QueueNode queueNode) {
		queueNode.next = null;

		if (last == null) {
			last = queueNode;

			if (first == null) {
				first = last;
			}
		} else {
			last.next = queueNode;
			last = queueNode;
		}

		size++;
	}

	public QueueNode pop() {
		if (first != null) {
			QueueNode temp = first;
			first = first.next;
			size--;

			return temp;
		} else {
			return null;
		}
	}

	public int peek() {
		if (first != null) {
			return first.data;
		} else {
			return -1;
		}
	}

	public void printQueue() {
		if (first == null) {
			System.out.println("null");
		} else {
			QueueNode temp = first;

			while (temp != null) {
				System.out.println(temp.data);
				temp = temp.next;
			}
		}
	}

	public boolean isEmpty() {
		return (size == 0 ? true : false);
	}

	public void merge(Queues queue2) {
		if (queue2 != null) {
			if (!queue2.isEmpty()) {
				if (first != null) {
					last.next = queue2.first;
				} else {
					first = queue2.first;
				}

				last = queue2.last;
				size += queue2.size;
			}
		}
	}

	public void reverse() {
		QueueNode curr = first;
		QueueNode temp = null, after = null;

		while (curr != null) {
			after = curr.next;
			curr.next = temp;
			temp = curr;
			curr = after;
		}

		last = first;
		first = temp;
	}
}
