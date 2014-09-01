package Algorithms_Warmup;

import java.util.Scanner;

public class Find_Digits {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		for (int i = 0; i < t; i++) {
			int num = scn.nextInt();
			System.out.println(findDigits(num));
		}

		scn.close();
	}

	private static int findDigits(int num) {
		int res = 0;
		int number = num;
		String numbers = String.valueOf(num);

		for (int i = 0; i < numbers.length(); i++) {
			int digit = Integer.parseInt(Character.toString(numbers.charAt(i)));

			if (digit == 1) {
				res++;
			} else if (digit > 0 && number % digit == 0) {
				res++;
			}
		}

		return res;
	}
}
