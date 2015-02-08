package Algorithms;

import java.util.Scanner;

public class Chief_Hopper {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		@SuppressWarnings("unused")
		String s = in.nextLine();
		int[] heights = new int[n];

		for (int i = 0; i < n; i++) {
			heights[i] = in.nextInt();
		}

		in.close();
		System.out.println(calcMinEnergy(heights));
	}

	public static long calcMinEnergy(int[] heights) {
		if (heights.length < 1) {
			return 0;
		}

		long energy = 0;

		for (int i = 0; i < heights.length; i++) {
			long tmp = energy + heights[heights.length - 1 - i];
			int one = (int) (tmp % 2);
			energy = tmp / 2 + one;
		}

		return energy;
	}
}
