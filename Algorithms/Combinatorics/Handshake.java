package Algorithms_Combinatorics;

import java.util.Scanner;

public class Handshake {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			int people = scn.nextInt();
			System.out.println((int) (people * (people - 1)) / 2);
		}

		scn.close();
	}
}
