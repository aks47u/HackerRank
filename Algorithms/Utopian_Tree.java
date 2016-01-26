package Algorithms;

import java.util.Scanner;

public class Utopian_Tree {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		while (T-- > 0) {
			System.out.println(UtopianHeight(scn.nextInt()));
		}

		scn.close();
	}

	private static int UtopianHeight(int n) {
		int height = 1;

		if (n <= 0) {
			return 1;
		}

		for (int i = 1; i <= n; i++) {
			if (i % 2 != 0) {
				height *= 2;
			} else {
				height++;
			}
		}
		
		return height;
	}
}
