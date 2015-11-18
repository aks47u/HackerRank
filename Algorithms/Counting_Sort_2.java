package Algorithms;

import java.util.Scanner;

public class Counting_Sort_2 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] count = new int[100];

		while (n-- > 0) {
			count[scn.nextInt()]++;
		}

		scn.close();

		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count[i]; j++) {
				System.out.print(i + " ");
			}
		}
	}
}
