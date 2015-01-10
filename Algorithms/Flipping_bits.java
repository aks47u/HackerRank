package Algorithms;

import java.util.Scanner;

public class Flipping_bits {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			long n = scn.nextLong();
			long flipped = ~n;

			if (flipped < 0) {
				flipped += 4294967296L;
			}

			System.out.println(flipped);
		}

		scn.close();
	}
}
