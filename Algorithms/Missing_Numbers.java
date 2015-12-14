package Algorithms;

import java.util.Scanner;

public class Missing_Numbers {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] A = new int[10001];

		while (n-- > 0) {
			A[scn.nextInt()]++;
		}

		int m = scn.nextInt();
		int[] B = new int[10001];

		while (m-- > 0) {
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
