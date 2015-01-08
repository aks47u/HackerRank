package Algorithms;

import java.util.Scanner;

public class Intro_to_Tutorial_Challenges {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int v = scn.nextInt();
		int n = scn.nextInt();

		for (int i = 0; i < n; i++) {
			if (scn.nextInt() == v) {
				System.out.println(i);
				break;
			}
		}

		scn.close();
	}
}
