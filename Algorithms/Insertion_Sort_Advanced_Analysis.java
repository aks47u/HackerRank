package Algorithms;

import java.util.Scanner;

public class Insertion_Sort_Advanced_Analysis {
	private static long answer = 0;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int k = 0; k < T; k++) {
			answer = 0;
			int n = scn.nextInt();
			int[] ar = new int[n];

			for (int i = 0; i < n; i++) {
				ar[i] = scn.nextInt();
			}

			mergeSort(ar);
			System.out.println(answer);
		}

		scn.close();
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
