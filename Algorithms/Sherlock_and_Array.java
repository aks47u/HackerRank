package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sherlock_and_Array {
	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (byte T = Byte.parseByte(br.readLine()); T > 0; --T) {
			int N = Integer.parseInt(br.readLine());
			short[] A = new short[N];
			int i = 0;

			for (String s : br.readLine().split(" ")) {
				A[i++] = Short.parseShort(s);
			}

			int sum;
			int[] S = new int[N];
			sum = 0;

			for (i = 0; i < N; ++i) {
				S[i] = sum += A[i];
			}

			sum = 0;

			for (i = N - 1; i >= 0; --i) {
				S[i] -= sum += A[i];
			}

			sb.append((Arrays.binarySearch(S, 0) < 0) ? "NO\n" : "YES\n");
		}

		System.out.print(sb);
	}
}
