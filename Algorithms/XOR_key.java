import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class XOR_key {
	private static InputStream is;
	private static PrintWriter out;
	private static String INPUT = "";

	public static void main(String[] args) throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(
				INPUT.getBytes());
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}

	private static void solve() {
		for (int T = ni(); T >= 1; T--) {
			int n = ni();
			int Q = ni();
			int[] a = new int[n];

			for (int i = 0; i < n; i++) {
				a[i] = ni();
			}

			SegmentTreeSeq st = new SegmentTreeSeq(a);

			for (int i = 0; i < Q; i++) {
				int y = ni(), p = ni() - 1, q = ni() - 1;
				out.println(st.operate(p, q, y));
			}
		}
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
		} catch (IOException e) {
		}

		return -1;
	}
}

class SegmentTreeSeq {
	public int M, H, N, B;
	public int[][] st;

	public SegmentTreeSeq(int[] a) {
		N = a.length;
		M = Integer.highestOneBit(Math.max(N - 1, 1)) << 2;
		H = M >>> 1;
		B = Integer.numberOfTrailingZeros(H);
		st = new int[M][];

		for (int i = 0; i < H; i++) {
			if (i < N) {
				st[H + i] = new int[] { a[i] * 2 };
			} else {
				st[H + i] = new int[0];
			}
		}

		for (int i = H - 1; i >= 1; i--) {
			int ll = st[2 * i].length;
			int lr = st[2 * i + 1].length;
			st[i] = new int[ll + lr];

			for (int j = 0, k = 0, p = 0; j < ll || k < lr; p++) {
				if (j == ll) {
					st[i][p] = st[2 * i + 1][k++];
				} else if (k == lr) {
					st[i][p] = st[2 * i][j++];
				} else if (st[2 * i][j] < st[2 * i + 1][k]) {
					st[i][p] = st[2 * i][j++];
				} else {
					st[i][p] = st[2 * i + 1][k++];
				}
			}
		}
	}

	public int operate(int f, int t, int a) {
		return operate(f, t, B, a);
	}

	public int operate(int f, int t, int b, int a) {
		if (f >> b << b == f && t + 1 >> b << b == t + 1) {
			int[] ar = st[(H | f) >> b];
			int from = 0, to = ar.length;
			int x = 0;

			for (int i = 14; i >= 0; i--) {
				int mid = Arrays.binarySearch(ar, from, to,
						(x | 1 << i) * 2 - 1);

				if (mid < 0) {
					mid = -mid - 1;
				}

				if (a << 31 - i >= 0) {
					if (mid < to) {
						from = mid;
						x |= 1 << i;
					} else {
						to = mid;
					}
				} else {
					if (from < mid) {
						to = mid;
					} else {
						from = mid;
						x |= 1 << i;
					}
				}
			}

			return (ar[from] / 2) ^ a;
		}

		b--;

		if ((f ^ t) << 31 - b < 0) {
			int w = t >> b << b;

		return Math.max(operate(f, w - 1, b, a), operate(w, t, b, a));
		} else {
			return operate(f, t, b, a);
		}
	}
}
