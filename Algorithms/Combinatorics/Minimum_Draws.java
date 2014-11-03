package Algorithms_Combinatorics;

import java.util.Scanner;

public class Minimum_Draws {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			int noOfPairs = scn.nextInt();
			System.out.println(minimumDraw(noOfPairs));
		}

		scn.close();
	}

	private static int minimumDraw(int noOfPairs) {
		return (noOfPairs + 1);
	}
}
