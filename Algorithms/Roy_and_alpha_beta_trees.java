import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;

public class Roy_and_alpha_beta_trees {
	private static long MOD = 1000000009;

	public static void solve(Input in, PrintWriter out) throws IOException {
		int maxn = 150;
		long[] c = new long[maxn + 1];
		long[][][] d = new long[2][maxn + 1][];
		d[0][0] = d[1][0] = new long[0];
		c[0] = 1;

		for (int i = 1; i <= maxn; ++i) {
			d[0][i] = new long[i];
			d[1][i] = new long[i];

			for (int j = 0; j < i; ++j) {
				long add = c[j] * c[i - j - 1];
				c[i] = (c[i] + add) % MOD;
				d[0][i][j] = (d[0][i][j] + add) % MOD;

				for (int t = 0; t < j; ++t) {
					for (int l = 0; l < 2; ++l) {
						d[l][i][t] = (d[l][i][t] + d[1 - l][j][t]
								* c[i - j - 1])
								% MOD;
					}
				}

				for (int t = 0; t < i - j - 1; ++t) {
					for (int l = 0; l < 2; ++l) {
						d[l][i][j + 1 + t] = (d[l][i][j + 1 + t] + d[1 - l][i
						                                                    - j - 1][t]
						                                                    		* c[j])
						                                                    		% MOD;
					}
				}
			}

			for (int j = 0; j < i; ++j) {
				if ((d[0][i][j] + d[1][i][j]) % MOD != c[i]) {
					throw new AssertionError();
				}
			}
		}

		int tests = in.nextInt();

		for (int test = 0; test < tests; ++test) {
			int n = in.nextInt();
			long a = in.nextLong();
			long b = MOD - in.nextLong();
			long[] xs = new long[n];

			for (int i = 0; i < n; ++i) {
				xs[i] = in.nextLong();
			}

			Arrays.sort(xs);
			long ans = 0;

			for (int i = 0; i < n; ++i) {
				long xa = xs[i] * a % MOD;
				long xb = xs[i] * b % MOD;
				ans = (ans + d[0][n][i] * xa + d[1][n][i] * xb) % MOD;
			}

			out.println(ans);
		}
	}

	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter(System.out);
		solve(new Input(new BufferedReader(new InputStreamReader(System.in))),
				out);
		out.close();
	}

	static class Input {
		BufferedReader in;
		StringBuilder sb = new StringBuilder();

		public Input(BufferedReader in) {
			this.in = in;
		}

		public Input(String s) {
			this.in = new BufferedReader(new StringReader(s));
		}

		public String next() throws IOException {
			sb.setLength(0);

			while (true) {
				int c = in.read();

				if (c == -1) {
					return null;
				}

				if (" \n\r\t".indexOf(c) == -1) {
					sb.append((char) c);
					break;
				}
			}

			while (true) {
				int c = in.read();

				if (c == -1 || " \n\r\t".indexOf(c) != -1) {
					break;
				}

				sb.append((char) c);
			}

			return sb.toString();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
