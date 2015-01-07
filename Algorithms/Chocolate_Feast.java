package Algorithms;

import java.util.Scanner;

public class Chocolate_Feast {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int numTestCases = scn.nextInt();

		while (numTestCases > 0) {
			int n = scn.nextInt();
			int c = scn.nextInt();
			int m = scn.nextInt();
			String numChocolates = NumberChocolates(n, c, m);
			System.out.println(numChocolates);
			numTestCases--;
		}

		scn.close();
	}

	private static String NumberChocolates(int n, int c, int m) {
		int totalChocolates = 0;
		int baseNumChocolates = n / c;
		totalChocolates += baseNumChocolates;
		int additionalChocolates = baseNumChocolates / m;
		totalChocolates += additionalChocolates;
		int remainingWrappers = baseNumChocolates % m;
		int totalWrappers = remainingWrappers + additionalChocolates;

		while (totalWrappers >= m) {
			baseNumChocolates = totalWrappers / m;
			remainingWrappers = totalWrappers % m;
			totalChocolates += baseNumChocolates;
			totalWrappers = remainingWrappers + baseNumChocolates;
		}

		return "" + totalChocolates;
	}
}
