package Algorithms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class Palindromes {
	private static InputStream is;
	private static PrintWriter out;
	private static String INPUT = "";

	public static void main(String[] args) throws Exception {
		is = System.in;
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}

	private static int enc(int[] a) {
		int n = a.length;
		int m = (n + 1) / 2;
		int[] x = new int[m];

		for (int i = 0, j = n - 1; i <= j; i++, j--) {
			if (i == j) {
				x[i] = a[i];
			} else {
				x[i] = (a[i] < a[j] ? a[i] * m + a[j] : a[j] * m + a[i]);
			}
		}

		int h = 0;

		if (n % 2 == 1) {
			Arrays.sort(x, 0, m - 1);

			for (int i = 0; i < m - 1; i++) {
				h = h * m * m + x[i];
			}

			h = h * m + x[m - 1];
		} else {
			Arrays.sort(x);

			for (int i = 0; i < m; i++) {
				h = h * m * m + x[i];
			}
		}

		return h;
	}

	private static int[] dec(int h, int n) {
		int[] a = new int[n];

		if (n % 2 == 0) {
			int m = n / 2;

			for (int i = 0; i < m; i++) {
				int x = h % (m * m);
				a[n - 1 - (m - 1 - i)] = x % m;
				a[m - 1 - i] = x / m;
				h /= m * m;
			}
		} else {
			int m = (n + 1) / 2;
			a[m - 1] = h % m;
			h /= m;

			for (int i = 0; i < m - 1; i++) {
				int x = h % (m * m);
				a[n - 1 - (m - 2 - i)] = x % m;
				a[m - 2 - i] = x / m;
				h /= m * m;
			}
		}

		return a;
	}

	private static void solve() {
		for (int T = ni(); T >= 1; T--) {
			char[] s = ns(8);
			int[] ct = new int[26];

			for (char c : s) {
				ct[c - 'a']++;
			}

			int p = 0;

			for (int i = 0; i < 26; i++) {
				if (ct[i] > 0) {
					ct[i] = p++;
				}
			}

			int[] a = new int[s.length];

			for (int i = 0; i < s.length; i++) {
				a[i] = ct[s[i] - 'a'];
			}

			int n = a.length;
			int[] map = new int[65536];
			Arrays.fill(map, -1);
			int[] q = new int[25];
			int r = 1;
			q[0] = enc(a);
			map[enc(a)] = 0;
			double[][] MB = new double[25][25];
			double inv = 1. / (n * (n - 1) / 2);

			for (int z = 0; z < r; z++) {
				int[] cur = dec(q[z], n);

				for (int i = 0; i < n; i++) {
					for (int j = i + 1; j < n; j++) {
						int d = cur[i];
						cur[i] = cur[j];
						cur[j] = d;
						int e = enc(cur);

						if (map[e] == -1) {
							map[e] = r;
							q[r++] = e;
						}

						MB[map[e]][z] += inv;
						d = cur[i];
						cur[i] = cur[j];
						cur[j] = d;
					}
				}
			}

			int tar = -1;

			outer: for (int z = 0; z < r; z++) {
				int[] d = dec(q[z], n);

				for (int i = 0, j = n - 1; i < j; i++, j--) {
					if (d[i] != d[j]) {
						continue outer;
					}
				}

				tar = z;
				break;
			}

			double[][] M = new double[r][r];

			for (int i = 0; i < r; i++) {
				for (int j = 0; j < r; j++) {
					if (j != tar) {
						M[i][j] = MB[i][j];
					}
				}
			}

			double[] v = new double[r];
			v[0] = 1;
			out.printf("%.4f\n", E(M, v)[tar] - 1);
		}
	}

	private static double[] E(double[][] T, double[] v) {
		int n = T.length;
		double[][] U = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				U[i][j] = (i == j ? 1 : 0) - T[i][j];
			}
		}

		return gaussianElimination(p2(U), v);
	}

	private static double[][] p2(double[][] A) {
		int n = A.length;
		double[][] C = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double sum = 0;

				for (int k = 0; k < n; k++) {
					sum += A[i][k] * A[k][j];
				}

				C[i][j] = sum;
			}
		}

		return C;
	}

	private static double[] gaussianElimination(double[][] a, double[] c) {
		int n = a.length;

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				a[i][j] /= a[i][i];
			}

			c[i] /= a[i][i];
			a[i][i] = 1.0;

			for (int j = i + 1; j < n; j++) {
				for (int k = i + 1; k < n; k++) {
					a[j][k] -= a[j][i] * a[i][k];
				}

				c[j] -= a[j][i] * c[i];
				a[j][i] = 0.0;
			}
		}

		for (int i = n - 1; i >= 0; i--) {
			for (int j = i - 1; j >= 0; j--) {
				c[j] -= a[j][i] * c[i];
			}
		}

		return c;
	}

	private static char[] ns(int n) {
		char[] buf = new char[n];

		try {
			int b = 0, p = 0;

			while ((b = is.read()) != -1
					&& (b == ' ' || b == '\r' || b == '\n'))
				;

			if (b == -1) {
				return null;
			}

			buf[p++] = (char) b;

			while (p < n) {
				b = is.read();

				if (b == -1 || b == ' ' || b == '\r' || b == '\n') {
					break;
				}

				buf[p++] = (char) b;
			}

			return Arrays.copyOf(buf, p);
		} catch (IOException ioe) {
		}

		return null;
	}

	private static int ni() {
		try {
			int num = 0;
			boolean minus = false;

			while ((num = is.read()) != -1
					&& !((num >= '0' && num <= '9') || num == '-'))
				;

			if (num == '-') {
				num = 0;
				minus = true;
			} else {
				num -= '0';
			}

			while (true) {
				int b = is.read();

				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
			}
		} catch (IOException ioe) {
		}

		return -1;
	}
}
