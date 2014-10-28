package Algorithms_Search;

import java.util.Scanner;

public class Ice_Cream_Parlor {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int k = 0; k < T; k++) {
			int M = scn.nextInt();
			int N = scn.nextInt();
			int[] Cost = new int[N];

			for (int index = 0; index < N; index++) {
				Cost[index] = scn.nextInt();
			}

			for (int i = 0; i < N; i++) {
				for (int j = i; j < N; j++) {
					if (i == j) {
						continue;
					} else {
						if (Cost[i] + Cost[j] > M) {
							continue;
						} else if (Cost[i] + Cost[j] == M) {
							System.out.println((i + 1) + " " + (j + 1));
						}
					}
				}
			}
		}

		scn.close();
	}
}
