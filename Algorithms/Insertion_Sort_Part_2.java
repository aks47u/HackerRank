package Algorithms;

import java.util.Scanner;

public class Insertion_Sort_Part_2 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];

		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
		}

		scn.close();
		insertionSort(ar);
	}

	private static void insertionSort(int[] ar) {
		int index = 0;

		for (int i = 1; i < ar.length; i++) {
			index++;
			insertionSort2(ar, index);
		}
	}

	private static void insertionSort2(int[] ar, int index) {
		for (int i = 0; i <= index; i++) {
			if (ar[i] > ar[index]) {
				int temp = 0;
				temp = ar[index];
				ar[index] = ar[i];
				ar[i] = temp;
			} else {
				continue;
			}
		}

		printArray(ar);
	}

	private static void printArray(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}

		System.out.println("");
	}
}
