package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sherlock_and_Watson {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		int N = Integer.parseInt(input[0]);
		int K = Integer.parseInt(input[1]);
		short Q = Short.parseShort(input[2]);
		int[] A = new int[N];
		input = br.readLine().split(" ");

		for (int i = 0; i < N; ++i) {
			A[i] = Integer.parseInt(input[i]);
		}

		int offset = N - K % N;
		StringBuffer sb = new StringBuffer();

		for (short q = 0; q < Q; ++q) {
			int X = Integer.parseInt(br.readLine());
			sb.append(A[(X + offset) % N] + "\n");
		}

		System.out.print(sb);
	}
}
