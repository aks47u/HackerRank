package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sherlock_and_Cost {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());

		while (T-- > 0) {
			int n = Integer.parseInt(br.readLine());
			StringTokenizer s = new StringTokenizer(br.readLine());
			int[] b = new int[n + 1];

			for (int i = 1; i <= n; i++) {
				b[i] = Integer.parseInt(s.nextToken());
			}

			int[][] f = new int[n + 1][2];

			for (int i = 2; i <= n; i++) {
				int cb = f[i - 1][0] + Math.abs(1 - 1);
				cb = Math.max(cb, f[i - 1][1] + Math.abs(1 - b[i - 1]));
				f[i][0] = cb;
				f[i][1] = f[i - 1][0] + Math.abs(b[i] - 1);
				f[i][1] = Math.max(f[i][1],
						f[i - 1][1] + Math.abs(b[i] - b[i - 1]));
			}

			System.out.println(Math.max(f[n][0], f[n][1]));
		}

		br.close();
	}
}
