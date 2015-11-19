package Algorithms;

import java.util.Scanner;

public class Counting_Sort_3 {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] count = new int[100];

		while (n-- > 0) {
			String[] words = scn.nextLine().split(" ");

			if (!words[0].equals("")) {
				count[Integer.parseInt(words[0])]++;
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
