package Algorithms;

import java.util.Scanner;

public class Sherlock_and_The_Beast {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		for (int i = 0; i < t; i++) {
			String s = "";
			int n = scn.nextInt();

			for (int j = n; j >= 0; j--) {
				if (j % 3 == 0 && (n - j) % 5 == 0) {
					s = repeat("5", j) + repeat("3", n - j);
					break;
				}
			}

			if (s == "") {
				System.out.println("-1");
			} else {
				System.out.println(s);
			}
		}
		
		scn.close();
	}

	private static String repeat(String str, int times) {
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < times; i++) {
			ret.append(str);
		}

		return ret.toString();
	}
}
