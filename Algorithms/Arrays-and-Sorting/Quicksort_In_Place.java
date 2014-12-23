package Algorithms_Arrays_and_Sorting;

import java.util.Scanner;

public class Quicksort_In_Place {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		int[] ar = new int[N];

		for (int i = 0; i < N; i++) {
			ar[i] = scn.nextInt();
		}

		scn.close();
		quickSort(ar);
	}

	private static void quickSort(int[] ar) {
		quickSort(ar, 0, ar.length - 1);
	}

	private static void quickSort(int[] ar, int low, int high) {
		if (low >= high) {
			return;
		}

		int p = partition(ar, low, high);
		quickSort(ar, low, p - 1);
		quickSort(ar, p + 1, high);
	}

	private static int partition(int[] ar, int low, int high) {
		int p = ar[high];
		int i = low - 1;

		for (int j = low; j <= high; j++) {
			if (ar[j] < p) {
				i++;
				swap(ar, i, j);
			}
		}

		swap(ar, i + 1, high);
		printArray(ar);

		return i + 1;
	}

	private static void printArray(int[] ar) {
		for (int n : ar) {
			System.out.print(n + " ");
		}

		System.out.println();
	}

	private static void swap(int[] ar, int x, int y) {
		int t = ar[x];
		ar[x] = ar[y];
		ar[y] = t;
	}
}
