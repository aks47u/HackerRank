package Algorithms;

import java.util.Arrays;
import java.util.Scanner;

public class Closest_Numbers {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] arr = new int[n];
		int minDiff = Integer.MAX_VALUE;

		for (int i = 0; i < n; i++) {
			arr[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(arr);
		String result = "";

		for (int i = 0; i < n - 1; i++) {
			int diff = Math.abs(arr[i] - arr[i + 1]);

			if (diff < minDiff) {
				minDiff = diff;
				result = arr[i] + " " + arr[i + 1] + " ";
			} else if (diff == minDiff) {
				result += arr[i] + " " + arr[i + 1] + " ";
			}
		}

		System.out.println(result);
	}
}
