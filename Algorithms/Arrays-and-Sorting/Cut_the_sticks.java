package Algorithms_Arrays_and_Sorting;

import java.util.Scanner;

public class Cut_the_sticks {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] in = new int[n];
		int minimum = Integer.MAX_VALUE;

		for (int i = 0; i < n; i++) {
			in[i] = scn.nextInt();
		}

		scn.close();

		for (int item : in) {
			if (item > 0 && item < minimum) {
				minimum = item;
			}
		}

		while (true) {
			if (onlyZeros(in)) {
				break;
			}

			int cuts = 0;
			int min = Integer.MAX_VALUE;

			for (int item : in) {
				if (item > 0 && item < min) {
					min = item;
				}
			}

			for (int i = 0; i < in.length; i++) {
				if (in[i] <= 0) {
					continue;
				}

				in[i] -= min;
				cuts++;
			}

			System.out.println(cuts);
		}
	}

	private static boolean onlyZeros(int[] in) {
		for (int item : in) {
			if (item > 0) {
				return false;
			}
		}

		return true;
	}
}
