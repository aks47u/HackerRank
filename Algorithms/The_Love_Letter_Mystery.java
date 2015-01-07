package Algorithms;

import java.util.Scanner;

public class The_Love_Letter_Mystery {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int numTestCases = scn.nextInt();
		
		while (numTestCases > 0) {
			String word = scn.next();
			System.out.println(getNumRotations(word));
			--numTestCases;
		}
		
		scn.close();
	}

	private static int getNumRotations(String word) {
		char[] wordArr = word.toCharArray();
		int count = 0;
		int j = word.length() - 1;
		
		for (int i = 0; i < wordArr.length / 2; i++, j--) {
			count += Math.abs((int) (wordArr[i] - wordArr[j]));
		}
		
		return count;
	}
}
