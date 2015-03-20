package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Sherlock_and_Cost {
	public static void main(String[] args) throws IOException {
		BufferedReader scn = new BufferedReader(
				new InputStreamReader(System.in));

		for (int T = Integer.parseInt(scn.readLine()); T > 0; T--) {
			int n = Integer.parseInt(scn.readLine());
			StringTokenizer str = new StringTokenizer(scn.readLine());
			int[] b = new int[n + 1];

			for (int i = 1; i <= n; i++) {
				b[i] = Integer.parseInt(str.nextToken());
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

		scn.close();
	}
}
