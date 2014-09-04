package Algorithms_Arrays_and_Sorting;

import java.util.Arrays;
import java.util.Scanner;

public class Correctness_and_the_Loop_Invariant {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];

		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(ar);

		for (int i = 0; i < n; i++) {
			System.out.print(ar[i] + " ");
		}
	}
}
