package Algorithms_Dynamic_Programming;

import java.util.Scanner;

public class Travel_around_the_world {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		long C = scn.nextLong();
		long[] a = new long[2 * n];
		long[] b = new long[2 * n];

		for (int i = 0; i < n; i++) {
			long x = scn.nextLong();
			x = Math.min(C, x);
			a[i] = x;
			a[i + n] = x;
		}

		for (int i = 0; i < n; i++) {
			long x = scn.nextLong();
			b[i] = x;
			b[i + n] = x;
		}

		boolean[] invalid = new boolean[n];
		scn.close();

		for (int i = 2 * n - 1; i >= n; i--) {
			if (invalid[i % n]) {
				continue;
			}

			if (b[i] > C) {
				System.out.println(0);
				return;
			}

			if (b[i] > a[i]) {
				invalid[i % n] = true;
				long diff = b[i] - a[i];
				int nexti = i - 1;

				while (diff > 0 && nexti >= 0) {
					diff += b[nexti];

					if (diff > C) {
						System.out.println(0);
						return;
					}

					diff -= a[nexti];

					if (diff > 0) {
						invalid[nexti % n] = true;
					}

					nexti--;
				}
			}
		}

		int total = 0;

		for (int i = 0; i < n; i++) {
			if (!invalid[i]) {
				total++;
			}
		}

		System.out.println(total);
	}
}
