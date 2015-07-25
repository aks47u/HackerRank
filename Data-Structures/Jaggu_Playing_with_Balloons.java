import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

public class Jaggu_Playing_with_Balloons {
	public static void solve(Input in, PrintWriter out) throws IOException {
		int n = 1000000;
		int[] next = new int[n];
		int[] next2 = new int[n];

		for (int i = n - 1; i >= 0; --i) {
			next[i] = i | (i + 1);

			if (next[i] >= n) {
				next2[i] = next[i] - n;
			} else {
				next2[i] = next2[next[i]];
			}
		}

		long[] f = new long[n];
		int qs = in.nextInt();

		for (int q = 0; q < qs; ++q) {
			if (in.next().equals("U")) {
				int pos = in.nextInt() - 1;
				long m = in.nextLong();
				int add = in.nextInt();

				for (int it = 0; it < 50; ++it) {
					int x = (pos + it * add) % n;
					int incs = 1000;

					while (incs > 0) {
						if (next2[x] == x) {
							inc(x, m * incs, f);
							incs = 0;
						} else {
							inc(x, m, f);
							--incs;
							x = next2[x];
						}
					}
				}
			} else {
				int pos1 = in.nextInt() - 1;
				int pos2 = in.nextInt() - 1;
				out.println(get(pos2, f) - get(pos1 - 1, f));
			}
		}
	}

	private static long get(int x, long[] f) {
		long r = 0;

		while (x >= 0) {
			r += f[x];
			x = (x & (x + 1)) - 1;
		}

		return r;
	}

	private static void inc(int x, long m, long[] f) {
		long add = m;

		while (x < f.length) {
			f[x] += add;
			add += m;
			x = x | (x + 1);
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

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
	}
}
