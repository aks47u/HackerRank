package Algorithms;

import java.util.Scanner;

public class Halloween_party {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();
		
		for (int i = 0; i < t; i++) {
			int k = scn.nextInt();
			System.out.println(noOfChocolates(k));
		}
		
		scn.close();
	}
	
	private static long noOfChocolates(int k) {
		long noOfChocolates = 0;
		noOfChocolates = (long) (k / 2) * (k - (long) (k / 2));
		
		return noOfChocolates;
	}
}
