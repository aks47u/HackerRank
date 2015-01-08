package Algorithms;

import java.util.Scanner;

public class Stock_Maximize {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int numOfTestCase = scn.nextInt();

		for (int i = 0; i < numOfTestCase; i++) {
			int n = scn.nextInt();
			long profit = 0;
			int[] stockPrice = new int[n];

			for (int j = 0; j < n; j++) {
				stockPrice[j] = scn.nextInt();
			}

			int currMax = Integer.MIN_VALUE;

			for (int j = n - 1; j >= 0; j--) {
				if (currMax < stockPrice[j]) {
					currMax = stockPrice[j];
				}

				profit += (currMax - stockPrice[j]);
			}

			System.out.println(profit);
		}

		scn.close();
	}
}
