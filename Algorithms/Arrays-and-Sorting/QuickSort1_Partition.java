package Algorithms_Arrays_and_Sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickSort1_Partition {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];

		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
		}

		scn.close();
		int p = ar[0];
		List<Integer> below = new ArrayList<Integer>();
		List<Integer> above = new ArrayList<Integer>();

		for (int i = 1, N = ar.length; i < N; ++i) {
			int v = ar[i];

			if (v < p) {
				below.add(v);
			} else {
				above.add(v);
			}
		}

		int i = 0;

		for (Integer v : below) {
			ar[i++] = v;
		}

		ar[i++] = p;

		for (Integer v : above) {
			ar[i++] = v;
		}

		for (int j : ar) {
			System.out.print(j + " ");
		}
	}
}
