package Algorithms_Arrays_and_Sorting;

import java.util.Scanner;

public class Counting_Sort_3 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] count = new int[100];

		for (int i = 0; i <= n; i++) {
			String[] words = scn.nextLine().split(" ");

			if (!words[0].equals("")) {
				int number = Integer.parseInt(words[0]);
				count[number]++;
			}
		}

		int sum = 0;

		for (int i = 0; i < count.length; i++) {
			sum = 0;

			for (int j = 0; j <= i; j++)
				sum += count[j];

			System.out.print(sum + " ");
		}

		scn.close();
	}
}
