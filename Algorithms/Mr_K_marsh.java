package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Mr_K_marsh implements Runnable {
	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(null, new Mr_K_marsh(), "", 256 * (1L << 20)).start();
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			solve();
			in.close();
			out.close();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	String readString() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}

		return tok.nextToken();
	}

	int readInt() throws IOException {
		return Integer.parseInt(readString());
	}

	long readLong() throws IOException {
		return Long.parseLong(readString());
	}

	double readDouble() throws IOException {
		return Double.parseDouble(readString());
	}

	void solve() throws IOException {
		int m = readInt();
		int n = readInt();
		char[][] a = new char[m][n];

		for (int i = 0; i < m; i++) {
			a[i] = in.readLine().toCharArray();
		}

		int[][] sumX = new int[m][n];

		for (int j = 0; j < n; j++) {
			sumX[0][j] = (a[0][j] == '.' ? 0 : 1);

			for (int i = 1; i < m; i++) {
				sumX[i][j] = (a[i][j] == '.' ? 0 : 1) + sumX[i - 1][j];
			}
		}

		int result = 0;

		for (int x1 = 0; x1 < m; x1++) {
			for (int x2 = x1 + 1; x2 < m; x2++) {
				int previousOpenPlace = -1;

				for (int y = 0; y < n; y++) {
					if (a[x1][y] == '.' && a[x2][y] == '.') {
						if (sumX[x2][y] - (x1 == 0 ? 0 : sumX[x1 - 1][y]) == 0) {
							if (previousOpenPlace == -1) {
								previousOpenPlace = y;
							} else {
								result = Math
										.max(result,
												perimeter(x1, x2,
														previousOpenPlace, y));
							}
						}
					} else {
						previousOpenPlace = -1;
					}
				}
			}
		}

		out.println((result > 0 ? result : "impossible"));
	}

	int perimeter(int x1, int x2, int y1, int y2) {
		return 2 * ((x2 - x1) + (y2 - y1));
	}
}
