package Algorithms;

import java.util.Scanner;

public class Halloween_party {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();
		
		while (T-- > 0) {
			System.out.println(noOfChocolates(scn.nextInt()));
		}
		
		scn.close();
	}
	
	private static long noOfChocolates(int k) {
		long noOfChocolates = 0;
		noOfChocolates = (long) (k / 2) * (k - (long) (k / 2));
		
		return noOfChocolates;
	}
}
