package Algorithms;

import java.util.Scanner;

public class Sherlock_and_GCD {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		while (t-- > 0) {
			int N = scn.nextInt();
			int[] a = new int[N];
			int gc = 0;

			for (int j = 0; j < N; j++) {
				a[j] = scn.nextInt();
				gc = gcd(gc, a[j]);
			}

			System.out.println((gc == 1 ? "YES" : "NO"));
		}

		scn.close();
	}

	private static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
}
