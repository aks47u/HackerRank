package Algorithms;

import java.util.Scanner;

public class Running_Time_of_Algorithms {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] ar = new int[n];
		
		for (int i = 0; i < n; i++) {
			ar[i] = scn.nextInt();
		}
		
		System.out.println(insertionSort(ar));
		scn.close();
	}
	
	private static int insertionSort(int[] a) {
		int noOfShifts = 0;
		
		for (int i = 1; i < a.length; i++) {
			int value = a[i];
			int j = i - 1;
			
			while (j >= 0 && a[j] > value) {
				a[j + 1] = a[j];
				j = j - 1;
				noOfShifts++;
			}
			
			a[j + 1] = value;
		}

		return noOfShifts;
	}
}
