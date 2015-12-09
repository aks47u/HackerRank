package Algorithms;

import java.util.Scanner;

public class Maximizing_XOR {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		short l = scn.nextShort();
		short r = scn.nextShort();
		short max = 0;

		for (int a = l; a <= r; a++) {
			for (int b = a; b <= r; b++) {
				int curr = a ^ b;

				if (curr > max) {
					max = (short) curr;
				}
			}
		}

		scn.close();
		System.out.println(max);
	}
}
