package Algorithms;

import java.util.Scanner;

public class Sherlock_and_Squares {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();
		int[] sq = new int[31622];

		for (int i = 1; i <= 31622; i++) {
			sq[i - 1] = i * i;
		}

		for (int i = 0; i < t; i++) {
			int count = 0;
			int a = scn.nextInt();
			int b = scn.nextInt();

			for (int j = 0; j < 31622; j++) {
				if (sq[j] >= a && sq[j] <= b) {
					count++;
				}
			}

			System.out.println(count);
		}

		scn.close();
	}
}
