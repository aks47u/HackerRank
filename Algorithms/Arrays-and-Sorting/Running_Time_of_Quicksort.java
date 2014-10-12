package Algorithms_Arrays_and_Sorting;

import java.util.Scanner;

public class Running_Time_of_Quicksort {
	private static long answer = 0;
	private static long swaps = 0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];
		int[] arr = new int[n];
		
		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
			arr[i] = ar[i];
		}
		
		mergeSort(arr);
		quickSort(ar, 0, ar.length - 1);
		System.out.println(answer - swaps);
		scn.close();
	}

	private static void swap(int[] ar, int i, int j) {
		swaps++;
		int temp = ar[i];
		ar[i] = ar[j];
		ar[j] = temp;
	}

	private static int Partition(int[] ar, int start, int end) {
		int pivot = ar[end];
		int i = start;
		int j = start;

		while (i < end) {
			if (ar[i] < pivot) {
				swap(ar, i, j);
				i++;
				j++;
			} else {
				i++;
			}
		}

		swap(ar, j, end);

		return j;
	}

	private static void quickSort(int[] ar, int start, int end) {
		if (start >= end) {
			return;
		}

		int pivot = Partition(ar, start, end);
		quickSort(ar, start, pivot - 1);
		quickSort(ar, pivot + 1, end);
	}

	private static int[] Merge(int[] ar1, int[] ar2) {
		int m = ar1.length;
		int n = ar2.length;
		int point1 = 0, point2 = 0;
		int index_result = 0;
		int[] result = new int[m + n];

		while (point1 < m && point2 < n) {
			if (ar1[point1] < ar2[point2]) {
				result[index_result] = ar1[point1];
				point1++;
				index_result++;
			} else if (ar1[point1] > ar2[point2]) {
				answer += m - point1;
				result[index_result] = ar2[point2];
				index_result++;
				point2++;
			} else {
				result[index_result] = ar1[point1];
				index_result++;
				point1++;
			}
		}

		while (point1 < m) {
			result[index_result] = ar1[point1];
			index_result++;
			point1++;
		}

		while (point2 < n) {
			answer += m - point1;
			result[index_result] = ar2[point2];
			index_result++;
			point2++;
		}

		return result;
	}

	private static int[] mergeSort(int[] ar) {
		int n = ar.length;

		if (n <= 1) {
			return ar;
		}

		int mid = n / 2;
		int[] ar1 = new int[mid];
		int[] ar2 = new int[n - mid];
		System.arraycopy(ar, 0, ar1, 0, mid);
		System.arraycopy(ar, mid, ar2, 0, n - mid);
		int[] sorted_ar1 = mergeSort(ar1);
		int[] sorted_ar2 = mergeSort(ar2);
		int[] result = Merge(sorted_ar1, sorted_ar2);

		return result;
	}
}
