package Algorithms;

import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			long n = scn.nextLong();
			long m = scn.nextLong();
			long m1 = m - 1;
			long ans = (((m1 * m) / 2) * (n / m))
					+ ((n % m) * ((n % m) + 1) / 2);
			System.out.println(ans);
		}
		
		scn.close();
	}
}
