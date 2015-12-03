package Algorithms;

import java.util.Scanner;

public class Handshake {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		while (T-- > 0) {
			int people = scn.nextInt();
			System.out.println((int) (people * (people - 1)) / 2);
		}

		scn.close();
	}
}
