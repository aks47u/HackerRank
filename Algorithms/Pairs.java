package Algorithms;

import java.util.Arrays;
import java.util.Scanner;

public class Pairs {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		int[] arr = new int[N];
		int K = scn.nextInt();

		for (int i = 0; i < N; i++) {
			arr[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(arr);
		int res = 0;

		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				if (arr[j] - arr[i] > K) {
					break;
				} else if (arr[j] - arr[i] == K) {
					res++;
				}
			}
		}

		System.out.println(res);
	}
}
