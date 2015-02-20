package Algorithms;

import java.util.Scanner;

public class Missing_Numbers {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] A = new int[10001];

		for (int i = 0; i < n; i++) {
			A[scn.nextInt()]++;
		}

		int m = scn.nextInt();
		int[] B = new int[10001];

		for (int i = 0; i < m; i++) {
			B[scn.nextInt()]++;
		}

		scn.close();

		for (int i = 0; i < A.length; i++) {
			if (A[i] < B[i]) {
				System.out.print(i + " ");
			}
		}
	}
}
