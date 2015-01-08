package Algorithms;

import java.util.Arrays;
import java.util.Scanner;

public class Mark_and_Toys {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int k = scn.nextInt();
		int arr[] = new int[n];

		for (int i = 0; i < n; i++) {
			arr[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(arr);
		int count = 0, sum = 0;

		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];

			if (sum > k) {
				break;
			} else {
				count++;
			}
		}

		System.out.println(count);
	}
}
