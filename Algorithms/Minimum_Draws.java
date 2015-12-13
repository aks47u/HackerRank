package Algorithms;

import java.util.Scanner;

public class Minimum_Draws {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		while (T-- > 0) {
			System.out.println(minimumDraw(scn.nextInt()));
		}

		scn.close();
	}

	private static int minimumDraw(int noOfPairs) {
		return (noOfPairs + 1);
	}
}
