package Algorithms;

import java.util.Scanner;

public class Insertion_Sort_Part_1 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];

		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
		}

		scn.close();
		int newvalue = ar[ar.length - 1];

		for (int i = ar.length - 1; i > 0; i--) {
			if (newvalue < ar[i - 1]) {
				ar[i] = ar[i - 1];
				printArray(ar);
			} else {
				ar[i] = newvalue;
				printArray(ar);

				return;
			}
		}

		ar[0] = newvalue;
		printArray(ar);
	}

	private static void printArray(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}

		System.out.println("");
	}
}
