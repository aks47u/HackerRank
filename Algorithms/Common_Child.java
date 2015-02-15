package Algorithms;

import java.util.Scanner;

public class Common_Child {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		char[] x = scn.next().toCharArray();
		char[] y = scn.next().toCharArray();
		scn.close();
		int[][] a = new int[x.length + 1][];
		int[][] dir = new int[x.length + 1][];

		for (int i = 0; i < a.length; i++) {
			a[i] = new int[y.length + 1];
			dir[i] = new int[y.length + 1];
		}

		for (int i = 1; i < x.length + 1; i++) {
			for (int j = 1; j < a[0].length; j++) {
				if (x[i - 1] == y[j - 1]) {
					a[i][j] = a[i - 1][j - 1] + 1;
					dir[i][j] = 1;
				} else {
					if (a[i - 1][j] > a[i][j - 1]) {
						a[i][j] = a[i - 1][j];
						dir[i][j] = 3;
					} else {
						a[i][j] = a[i][j - 1];
						dir[i][j] = 2;
					}
				}
			}
		}

		int row = a.length - 1;
		int col = a[0].length - 1;
		System.out.println(a[row][col]);
	}
}
