package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game_Of_Rotation {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		N = 0;

		for (String s : br.readLine().split(" ")) {
			A[N++] = Integer.parseInt(s);
		}

		long sum = 0;

		for (int v : A) {
			sum += v;
		}

		long curPmean = 0;

		for (int i = 0; i < N; ++i) {
			curPmean += ((long) A[i]) * (i + 1);
		}

		long maxPmean = curPmean;

		for (int i = 1; i < N; ++i) {
			curPmean = curPmean - sum + ((long) A[i - 1]) * N;

			if (curPmean > maxPmean) {
				maxPmean = curPmean;
			}
		}

		System.out.print(maxPmean);
	}
}
