package Algorithms_Warmup;

import java.util.Scanner;

public class Sherlock_and_Queries {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int m = scn.nextInt();
		long[] a = new long[n];
		int[] b = new int[m];
		int[] c = new int[m];

		for (int i = 0; i < n; i++) {
			a[i] = scn.nextInt();
		}

		for (int i = 0; i < m; i++) {
			b[i] = scn.nextInt();
		}

		for (int i = 0; i < m; i++) {
			c[i] = scn.nextInt();
		}

		scn.close();

		for (int i = 0; i < m; i++) {
			for (int j = b[i] - 1; j < n; j += b[i]) {
				a[j] = (a[j] * c[i]) % 1000000007;
			}
		}

		StringBuilder sb = new StringBuilder(n * 11);

		for (long l : a) {
			sb.append(l).append(" ");
		}

		System.out.print(sb.toString());
	}
}
