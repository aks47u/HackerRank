package Algorithms_Arrays_and_Sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Cutting_boards {
	private static int MOD = 1000000007;

	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (byte T = Byte.parseByte(br.readLine()); T > 0; --T) {
			String[] input = br.readLine().split(" ");
			int M = Integer.parseInt(input[0]) - 1;
			int N = Integer.parseInt(input[1]) - 1;
			int[] Ys = strToArr(M, br.readLine());
			int[] Xs = strToArr(N, br.readLine());

			Arrays.sort(Xs);
			Arrays.sort(Ys);

			int vSegs = 1;
			int hSegs = 1;
			int y = M - 1;
			int x = N - 1;
			long cost = 0L;

			while (x >= 0 && y >= 0) {
				if (Xs[x] > Ys[y]) {
					++vSegs;
					cost = (cost + ((long) Xs[x--]) * hSegs) % MOD;
				} else {
					++hSegs;
					cost = (cost + ((long) Ys[y--]) * vSegs) % MOD;
				}
			}

			while (x >= 0) {
				cost = (cost + ((long) Xs[x--]) * hSegs) % MOD;
			}

			while (y >= 0) {
				cost = (cost + ((long) Ys[y--]) * vSegs) % MOD;
			}

			sb.append(cost + "\n");
		}

		System.out.print(sb);
	}

	private static int[] strToArr(int n, String line) {
		int[] arr = new int[n];
		String[] Vs = line.split(" ");

		while (n-- > 0) {
			arr[n] = Integer.parseInt(Vs[n]);
		}

		return arr;
	}
}
