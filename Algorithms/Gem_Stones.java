package Algorithms;

import java.util.HashSet;
import java.util.Scanner;

public class Gem_Stones {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int numTestCases = scn.nextInt();
		HashSet<Character> baseSet = new HashSet<Character>();
		HashSet<Character> currSet = new HashSet<Character>();
		
		for (char c = 'a'; c <= 'z'; c++) {
			baseSet.add(c);
		}
		
		while (numTestCases > 0) {
			currSet.clear();
			char[] currentRockElements = scn.next().toCharArray();
			
			for (int i = 0; i < currentRockElements.length; i++) {
				currSet.add(currentRockElements[i]);
			}
			
			baseSet.retainAll(currSet);
			--numTestCases;
		}
		
		System.out.println(baseSet.size());
		scn.close();
	}
}
