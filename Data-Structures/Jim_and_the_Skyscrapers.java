import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Jim_and_the_Skyscrapers {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";

	static void solve() {
		int n = ni();
		int[] h = na(n);
		int[][] hi = new int[n][];

		for (int i = 0; i < n; i++) {
			hi[i] = new int[] { h[i], i };
		}

		Arrays.sort(hi, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				if (a[0] != b[0]) {
					return a[0] - b[0];
				}

				return a[1] - b[1];
			}
		});

		LST lst = new LST(n);

		for (int i = 0; i < n; i++) {
			lst.set(i);
		}

		long ret = 0;

		for (int i = 0; i < n;) {
			int j = i;

			while (j < n && hi[j][0] == hi[i][0]) {
				j++;
			}

			int pre = -2;
			int ct = 0;

			for (int k = i; k < j; k++) {
				lst.unset(hi[k][1]);
				int prev = lst.prev(hi[k][1]);

				if (pre < prev) {
					ct = 0;
				}

				ret += ct;
				ct++;
				pre = hi[k][1];
			}

			i = j;
		}

		out.println(ret * 2L);
	}

	public static class LST {
		public long[][] set;
		public int n;

		public LST(int n) {
			this.n = n;
			int d = 1;

			for (int m = n; m > 1; m >>>= 6, d++)
				;

			set = new long[d][];

			for (int i = 0, m = n >>> 6; i < d; i++, m >>>= 6) {
				set[i] = new long[m + 1];
			}
		}

		public LST set(int l, int r) {
			if (0 <= l && l <= r && r <= n) {
				setRange(r);
				unsetRange(l - 1);
			}

			return this;
		}

		public LST setRange(int r) {
			for (int i = 0; i < set.length; i++, r >>>= 6) {
				for (int j = 0; j < r >>> 6; j++) {
					set[i][j] = -1;
				}

				set[i][r >>> 6] |= (1L << r + 1) - 1;
			}

			return this;
		}

		public LST unsetRange(int r) {
			if (r >= 0) {
				for (int i = 0; i < set.length; i++, r >>>= 6) {
					for (int j = 0; j < r >>> 6; j++) {
						set[i][j] = 0;
					}

					set[i][r >>> 6] &= ~((1L << r + 1) - 1);
				}
			}

			return this;
		}

		public LST set(int pos) {
			if (pos >= 0 && pos < n) {
				for (int i = 0; i < set.length; i++, pos >>>= 6) {
					set[i][pos >>> 6] |= 1L << pos;
				}
			}

			return this;
		}

		public LST unset(int pos) {
			if (pos >= 0 && pos < n) {
				for (int i = 0; i < set.length
						&& (i == 0 || set[i - 1][pos] == 0L); i++, pos >>>= 6) {
					set[i][pos >>> 6] &= ~(1L << pos);
				}
			}

			return this;
		}

		public boolean get(int pos) {
			return pos >= 0 && pos < n && set[0][pos >>> 6] << ~pos < 0;
		}

		public int prev(int pos) {
			for (int i = 0; i < set.length && pos >= 0; i++, pos >>>= 6, pos--) {
				int pre = prev(set[i][pos >>> 6], pos & 63);

				if (pre != -1) {
					pos = pos >>> 6 << 6 | pre;

					while (i > 0) {
						pos = pos << 6 | 63
								- Long.numberOfLeadingZeros(set[--i][pos]);
					}

					return pos;
				}
			}

			return -1;
		}

		public int next(int pos) {
			for (int i = 0; i < set.length && pos >>> 6 < set[i].length; i++, pos >>>= 6, pos++) {
				int nex = next(set[i][pos >>> 6], pos & 63);

				if (nex != -1) {
					pos = pos >>> 6 << 6 | nex;

					while (i > 0) {
						pos = pos << 6
								| Long.numberOfTrailingZeros(set[--i][pos]);
					}

					return pos;
				}
			}

			return -1;
		}

		private static int prev(long set, int n) {
			long h = Long.highestOneBit(set << ~n);

			if (h == 0L) {
				return -1;
			}

			return Long.numberOfTrailingZeros(h) - (63 - n);
		}

		private static int next(long set, int n) {
			long h = Long.lowestOneBit(set >>> n);

			if (h == 0L) {
				return -1;
			}

			return Long.numberOfTrailingZeros(h) + n;
		}
	}

	public static void main(String[] args) throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(
				INPUT.getBytes());
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}

	private static byte[] inbuf = new byte[1024];
	static int lenbuf = 0, ptrbuf = 0;

	private static int readByte() {
		if (lenbuf == -1) {
			throw new InputMismatchException();
		}

		if (ptrbuf >= lenbuf) {
			ptrbuf = 0;

			try {
				lenbuf = is.read(inbuf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}

			if (lenbuf <= 0) {
				return -1;
			}
		}

		return inbuf[ptrbuf++];
	}

	private static int[] na(int n) {
		int[] a = new int[n];

		for (int i = 0; i < n; i++) {
			a[i] = ni();
		}

		return a;
	}

	private static int ni() {
		int num = 0, b;
		boolean minus = false;

		while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;

		if (b == '-') {
			minus = true;
			b = readByte();
		}

		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}

			b = readByte();
		}
	}
}
