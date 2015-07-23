import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Coloring_Tree {
	private final IOFast io = new IOFast();

	public void run() throws IOException {
		int TEST_CASE = 1;

		while (TEST_CASE-- != 0) {
			int n = io.nextInt();
			int m = io.nextInt();
			int r = io.nextInt() - 1;
			SimpleAdjListGraph g = new SimpleAdjListGraph(n, 2 * n);

			for (int i = 0; i < n - 1; i++) {
				int s = io.nextInt() - 1;
				int t = io.nextInt() - 1;
				g.addEdge(s, t);
				g.addEdge(t, s);
			}

			int[] ans = new int[n];
			@SuppressWarnings("unchecked")
			TreeSet<Integer>[] ts = new TreeSet[n];

			for (int i = 0; i < n; i++) {
				ts[i] = new TreeSet<>();
				ts[i].add(io.nextInt());
			}

			{
				final int[] cnt = new int[2 * g.head.length + 10];
				final int[] st = new int[2 * g.head.length + 10];
				int sp = 0;
				st[sp++] = r;

				while (sp != 0) {
					final int v = st[--sp];

					if (cnt[v]++ == 0) {
						st[sp++] = v;

						for (int e = g.head[v]; e != -1; e = g.next[e]) {
							final int u = g.to[e];

							if (cnt[u] == 0) {
								st[sp++] = u;
							}
						}
					} else {
						for (int e = g.head[v]; e != -1; e = g.next[e]) {
							final int u = g.to[e];

							if (cnt[u] == 2) {
								if (ts[u].size() > ts[v].size()) {
									ts[u].addAll(ts[v]);
									ts[v] = ts[u];
								} else {
									ts[v].addAll(ts[u]);
								}

								ts[u] = null;
							}
						}

						ans[v] = ts[v].size();
					}
				}
			}

			for (int i = 0; i < m; i++) {
				io.out.println(ans[io.nextInt() - 1]);
			}
		}
	}

	static class SimpleAdjListGraph {
		int m, V, E;
		int[] head, next, to;

		public SimpleAdjListGraph(int V, int E) {
			head = new int[V];
			next = new int[E];
			to = new int[E];
			this.V = V;
			this.E = E;
			clear();
		}

		public void clear() {
			m = 0;
			Arrays.fill(head, -1);
		}

		public void addEdge(int s, int t) {
			next[m] = head[s];
			head[s] = m;
			to[m++] = t;
		}
	}

	static int gcd(int n, int r) {
		return r == 0 ? n : gcd(r, n % r);
	}

	static long gcd(long n, long r) {
		return r == 0 ? n : gcd(r, n % r);
	}

	static <T> void swap(T[] x, int i, int j) {
		T t = x[i];
		x[i] = x[j];
		x[j] = t;
	}

	static void swap(int[] x, int i, int j) {
		int t = x[i];
		x[i] = x[j];
		x[j] = t;
	}

	static void radixSort(int[] xs) {
		int[] cnt = new int[(1 << 16) + 1];
		int[] ys = new int[xs.length];

		for (int j = 0; j <= 16; j += 16) {
			Arrays.fill(cnt, 0);

			for (int x : xs) {
				cnt[(x >> j & 0xFFFF) + 1]++;
			}

			for (int i = 1; i < cnt.length; i++) {
				cnt[i] += cnt[i - 1];
			}

			for (int x : xs) {
				ys[cnt[x >> j & 0xFFFF]++] = x;
			}

			{
				final int[] t = xs;
				xs = ys;
				ys = t;
			}
		}
	}

	static void radixSort(long[] xs) {
		int[] cnt = new int[(1 << 16) + 1];
		long[] ys = new long[xs.length];

		for (int j = 0; j <= 48; j += 16) {
			Arrays.fill(cnt, 0);

			for (long x : xs) {
				cnt[(int) (x >> j & 0xFFFF) + 1]++;
			}

			for (int i = 1; i < cnt.length; i++) {
				cnt[i] += cnt[i - 1];
			}

			for (long x : xs) {
				ys[cnt[(int) (x >> j & 0xFFFF)]++] = x;
			}

			{
				final long[] t = xs;
				xs = ys;
				ys = t;
			}
		}
	}

	static void arrayIntSort(int[][] x, int... keys) {
		Arrays.sort(x, new ArrayIntsComparator(keys));
	}

	static class ArrayIntsComparator implements Comparator<int[]> {
		final int[] KEY;

		public ArrayIntsComparator(int... key) {
			KEY = key;
		}

		@Override
		public int compare(int[] o1, int[] o2) {
			for (int k : KEY) {
				if (o1[k] != o2[k]) {
					return o1[k] - o2[k];
				}
			}

			return 0;
		}
	}

	static class ArrayIntComparator implements Comparator<int[]> {
		final int KEY;

		public ArrayIntComparator(int key) {
			KEY = key;
		}

		@Override
		public int compare(int[] o1, int[] o2) {
			return o1[KEY] - o2[KEY];
		}
	}

	void main() throws IOException {
		try {
			run();
		} catch (EndOfFileRuntimeException e) {
		}

		io.out.flush();
	}

	public static void main(String[] args) throws IOException {
		new Coloring_Tree().main();
	}

	static class EndOfFileRuntimeException extends RuntimeException {
		private static final long serialVersionUID = -8565341110209207657L;
	}

	static public class IOFast {
		private BufferedReader in = new BufferedReader(new InputStreamReader(
				System.in));
		private PrintWriter out = new PrintWriter(System.out);

		void setFileIO(String ins, String outs) throws IOException {
			out.flush();
			out.close();
			in.close();
			in = new BufferedReader(new FileReader(ins));
			out = new PrintWriter(new FileWriter(outs));
			System.err.println("reading from " + ins);
		}

		private static int pos, readLen;
		private static final char[] buffer = new char[1024 * 8];
		private static char[] str = new char[500 * 8 * 2];
		private static boolean[] isDigit = new boolean[256];
		private static boolean[] isSpace = new boolean[256];
		private static boolean[] isLineSep = new boolean[256];

		static {
			for (int i = 0; i < 10; i++) {
				isDigit['0' + i] = true;
			}

			isDigit['-'] = true;
			isSpace[' '] = isSpace['\r'] = isSpace['\n'] = isSpace['\t'] = true;
			isLineSep['\r'] = isLineSep['\n'] = true;
		}

		public int read() throws IOException {
			if (pos >= readLen) {
				pos = 0;
				readLen = in.read(buffer);

				if (readLen <= 0) {
					throw new EndOfFileRuntimeException();
				}
			}

			return buffer[pos++];
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(nextString());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(nextString());
		}

		public char nextChar() throws IOException {
			while (true) {
				final int c = read();

				if (!isSpace[c]) {
					return (char) c;
				}
			}
		}

		int reads(int len, boolean[] accept) throws IOException {
			try {
				while (true) {
					final int c = read();

					if (accept[c]) {
						break;
					}

					if (str.length == len) {
						char[] rep = new char[str.length * 3 / 2];
						System.arraycopy(str, 0, rep, 0, str.length);
						str = rep;
					}

					str[len++] = (char) c;
				}
			} catch (EndOfFileRuntimeException e) {
				;
			}

			return len;
		}

		int reads(char[] cs, int len, boolean[] accept) throws IOException {
			try {
				while (true) {
					final int c = read();

					if (accept[c]) {
						break;
					}

					cs[len++] = (char) c;
				}
			} catch (EndOfFileRuntimeException e) {
				;
			}

			return len;
		}

		public char[] nextLine() throws IOException {
			int len = 0;
			str[len++] = nextChar();
			len = reads(len, isLineSep);

			try {
				if (str[len - 1] == '\r') {
					len--;
					read();
				}
			} catch (EndOfFileRuntimeException e) {
				;
			}

			return Arrays.copyOf(str, len);
		}

		public String nextString() throws IOException {
			return new String(next());
		}

		public char[] next() throws IOException {
			int len = 0;
			str[len++] = nextChar();
			len = reads(len, isSpace);

			return Arrays.copyOf(str, len);
		}

		public int next(char[] cs) throws IOException {
			int len = 0;
			cs[len++] = nextChar();
			len = reads(cs, len, isSpace);

			return len;
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(nextString());
		}

		public long[] nextLongArray(final int n) throws IOException {
			final long[] res = new long[n];

			for (int i = 0; i < n; i++) {
				res[i] = nextLong();
			}

			return res;
		}

		public int[] nextIntArray(final int n) throws IOException {
			final int[] res = new int[n];

			for (int i = 0; i < n; i++) {
				res[i] = nextInt();
			}

			return res;
		}

		public int[][] nextIntArray2D(final int n, final int k)
				throws IOException {
			final int[][] res = new int[n][];

			for (int i = 0; i < n; i++) {
				res[i] = nextIntArray(k);
			}

			return res;
		}

		public int[][] nextIntArray2DWithIndex(final int n, final int k)
				throws IOException {
			final int[][] res = new int[n][k + 1];

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < k; j++) {
					res[i][j] = nextInt();
				}

				res[i][k] = i;
			}

			return res;
		}

		public double[] nextDoubleArray(final int n) throws IOException {
			final double[] res = new double[n];

			for (int i = 0; i < n; i++) {
				res[i] = nextDouble();
			}

			return res;
		}
	}
}
