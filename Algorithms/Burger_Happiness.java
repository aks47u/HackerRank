import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Burger_Happiness {
	BufferedReader br;
	PrintWriter out;
	StringTokenizer st;
	boolean eof;

	static class Node {
		static final long INF = Long.MAX_VALUE / 4;
		int l, r;
		Node left, right;
		long add;
		long max;

		public Node(int l, int r) {
			this.l = l;
			this.r = r;

			if (r - l > 1) {
				int mid = (l + r) >> 1;
				left = new Node(l, mid);
				right = new Node(mid, r);
			}
		}

		long getMax() {
			return max + add;
		}

		long qMax(int ql, int qr) {
			if (l >= qr || ql >= r) {
				return -INF;
			}

			if (ql <= l && r <= qr) {
				return getMax();
			}

			return Math.max(left.qMax(ql, qr), right.qMax(ql, qr)) + add;
		}

		long get(int pos) {
			if (l == pos && pos + 1 == r) {
				return add;
			}

			return add + (pos < left.r ? left : right).get(pos);
		}

		void qAdd(int ql, int qr, long delta) {
			if (l >= qr || ql >= r) {
				return;
			}

			if (ql <= l && r <= qr) {
				add += delta;

				return;
			}

			left.qAdd(ql, qr, delta);
			right.qAdd(ql, qr, delta);
			max = Math.max(left.getMax(), right.getMax());
		}
	}

	void solve() throws IOException {
		int n = nextInt();
		int[] x = new int[n];
		int[] a = new int[n];
		int[] b = new int[n];

		for (int i = 0; i < n; i++) {
			x[i] = nextInt();
			a[i] = nextInt();
			b[i] = nextInt();
		}

		int[] xx = x.clone();
		Arrays.sort(xx);

		for (int i = 0; i < n; i++) {
			x[i] = Arrays.binarySearch(xx, x[i]);
		}

		long ans = 0;
		Node pref = new Node(0, n);
		Node suff = new Node(0, n);

		for (int i = 0; i < n; i++) {
			int pos = x[i];
			long valL = suff.qMax(0, pos + 1) - suff.get(pos);
			long valR = pref.qMax(pos, n) - pref.get(pos);
			long val = Math.max(valL, valR) + a[i];
			ans = Math.max(ans, val);
			pref.qAdd(pos, n, -b[i]);
			suff.qAdd(0, pos + 1, -b[i]);
			pref.qAdd(pos, pos + 1, val);
			suff.qAdd(pos, pos + 1, val);
		}

		out.println(ans);
	}

	Burger_Happiness() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		solve();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new Burger_Happiness();
	}

	String nextToken() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;

				return null;
			}
		}

		return st.nextToken();
	}

	String nextString() {
		try {
			return br.readLine();
		} catch (IOException e) {
			eof = true;

			return null;
		}
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
}
