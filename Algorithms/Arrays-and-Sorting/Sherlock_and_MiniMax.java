package Algorithms_Arrays_and_Sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sherlock_and_MiniMax {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		byte N = Byte.parseByte(br.readLine());
		int[] A = new int[N];
		String[] input = br.readLine().split(" ");

		for (byte i = 0; i < N; ++i) {
			A[i] = Integer.parseInt(input[i]);
		}

		input = br.readLine().split(" ");
		int P = Integer.parseInt(input[0]);
		int Q = Integer.parseInt(input[1]);
		int maxMin = solve(A, N, P, Q);
		System.out.print(maxMin);
	}

	private static int solve(int[] A, int N, int P, int Q) {
		if (P == Q) {
			return P;
		}

		Arrays.sort(A);
		N = unDup(A);
		int i = Arrays.binarySearch(A, 0, N, P);
		int j = Arrays.binarySearch(A, 0, N, Q);

		if (i == j) {
			i = ~i;

			if (i == 0) {
				return P;
			}

			if (i == N) {
				return Q;
			}

			int mid = A[i - 1] + ((A[i] - A[i - 1]) >> 1);

			return (P > mid) ? P : (Q < mid) ? Q : mid;
		}

		int curMaxMin;
		int maxMin = 0;
		int maxMinVal = 0;

		if (i < 0) {
			if (((i = ~i) == 0 || P > A[i - 1] + ((A[i] - A[i - 1]) >> 1))) {
				maxMin = A[i] - P;
				maxMinVal = P;
			} else {
				--i;
			}
		}

		if (j < 0 && ((j = ~j) == N || Q < A[j - 1] + ((A[j] - A[j - 1]) >> 1))) {
			curMaxMin = Q - A[j - 1];

			if (curMaxMin > maxMin) {
				maxMin = curMaxMin;
				maxMinVal = Q;
			}

			--j;
		}

		while (i < j) {
			curMaxMin = (A[i + 1] - A[i]) >> 1;

		if (curMaxMin > maxMin || (curMaxMin == maxMin && maxMinVal == Q)) {
			maxMin = curMaxMin;
			maxMinVal = A[i] + curMaxMin;
		}

		++i;
		}

		return maxMinVal;
	}

	private static int unDup(int[] A) {
		int N = A.length;

		if (N < 2) {
			return N;
		}

		int numUniqs = 1;

		for (int i = 1; i < N; ++i) {
			if (A[i] != A[i - 1]) {
				A[numUniqs++] = A[i];
			}
		}

		return numUniqs;
	}
}
