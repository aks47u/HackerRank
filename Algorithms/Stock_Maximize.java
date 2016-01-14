package Algorithms;

import java.util.Scanner;

public class Stock_Maximize {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		while (T-- > 0) {
			int n = scn.nextInt();
			long profit = 0;
			int[] stockPrice = new int[n];

			for (int i = 0; i < n; i++) {
				stockPrice[i] = scn.nextInt();
			}

			int currMax = Integer.MIN_VALUE;

			for (int i = n - 1; i >= 0; i--) {
				if (currMax < stockPrice[i]) {
					currMax = stockPrice[i];
				}

				profit += (currMax - stockPrice[i]);
			}

			System.out.println(profit);
		}

		scn.close();
	}
}
