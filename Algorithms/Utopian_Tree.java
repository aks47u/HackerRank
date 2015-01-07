package Algorithms;

import java.util.Scanner;

public class Utopian_Tree {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int noOfTestCases = scn.nextInt();

		for (int i = 0; i < noOfTestCases; i++) {
			int noOfCycles = scn.nextInt();
			System.out.println(UtopianHeight(noOfCycles));
		}

		scn.close();
	}

	private static int UtopianHeight(int noOfCycles) {
		int height = 1;

		if (noOfCycles <= 0) {
			return 1;
		}

		for (int i = 1; i <= noOfCycles; i++) {
			if (i % 2 != 0) {
				height *= 2;
			} else {
				height++;
			}
		}
		
		return height;
	}
}
