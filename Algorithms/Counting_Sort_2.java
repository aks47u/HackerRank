package Algorithms;

import java.util.Scanner;

public class Counting_Sort_2 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] count = new int[100];

		for (int i = 0; i < n; i++) {
			int number = scn.nextInt();
			count[number]++;
		}

		scn.close();

		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count[i]; j++) {
				System.out.print(i + " ");
			}
		}
	}
}
