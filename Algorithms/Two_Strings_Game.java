import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Two_Strings_Game {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";

	static class Result {
		int[] sa;
		int[] lcp;
		int[][] branches;
		long[] count;
		int[] zero, one;
		int[] deadline;

		public Result(int[] sa, int[] lcp, int[][] branches, long[] count,
				int[] zero, int[] one, int[] deadline) {
			this.sa = sa;
			this.lcp = lcp;
			this.branches = branches;
			this.count = count;
			this.zero = zero;
			this.one = one;
			this.deadline = deadline;
		}
	}

	static void solve() {
		int n = ni(), m = ni();
		long K = nl();
		char[] a = ns(n);
		char[] b = ns(m);
		Result ra = go(a);
		Result rb = go(b);
		long[] ca = ra.count;
		long[] cb = rb.count;

		if (cb.length < ca.length) {
			cb = Arrays.copyOf(cb, ca.length);
		}

		long totcb = 0;

		for (long v : cb) {
			totcb += v;
		}

		Arrays.sort(ra.branches, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});

		K--;

		{
			long lcount = totcb - cb[ra.branches[0][3]];

			if (K < lcount) {
				int[] resb = kth(rb, K, ra.branches[0][3]);
				out.println("");
				out.println(new String(b, resb[0], resb[1]));

				return;
			} else {
				K -= lcount;
			}
		}

		int bp = 0;
		bp++;

		for (int i = 0; i < n; i++) {
			long lcount = 0;
			lcount += ra.zero[i] * (totcb - cb[0]);
			lcount += ra.one[i] * (totcb - cb[1]);
			int obp = bp;

			while (bp < ra.branches.length && ra.branches[bp][0] == i) {
				lcount += totcb - cb[ra.branches[bp][3]];
				bp++;
			}

			if (K < lcount) {
				int[] row = new int[n - ra.sa[i]];
				Arrays.fill(row, -1);

				for (int j = obp; j < bp; j++) {
					row[ra.branches[j][2] - 1] = ra.branches[j][3];
				}

				for (int j = n - ra.sa[i] - 1; j >= ra.deadline[i] + 1; j--) {
					if (row[j] == -1) {
						if (j == n - ra.sa[i] - 1) {
							row[j] = 0;
						} else {
							row[j] = row[j + 1] > 0 ? 0 : 1;
						}
					}
				}

				for (int j = ra.deadline[i] + 1; j < n - ra.sa[i]; j++) {
					long llcount = totcb - cb[row[j]];

					if (K < llcount) {
						int[] resa = new int[] { ra.sa[i], j + 1 };
						int[] resb = kth(rb, K, row[j]);
						out.println(new String(a, resa[0], resa[1]));
						out.println(new String(b, resb[0], resb[1]));

						return;
					} else {
						K -= llcount;
					}
				}
			} else {
				K -= lcount;
			}
		}

		out.println("no solution");
	}

	static int[] kth(Result rb, long K, int proh) {
		Arrays.sort(rb.branches, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[0] - b[0];
			}
		});

		if (rb.branches[0][3] != proh) {
			if (K == 0) {
				return new int[] { 0, 0 };
			} else {
				K--;
			}
		}

		int n = rb.sa.length;
		int bp = 1;

		for (int i = 0; i < n; i++) {
			long lcount = 0;

			if (proh != 0) {
				lcount += rb.zero[i];
			}

			if (proh != 1) {
				lcount += rb.one[i];
			}

			int obp = bp;

			while (bp < rb.branches.length && rb.branches[bp][0] == i) {
				if (proh != rb.branches[bp][3]) {
					lcount++;
				}

				bp++;
			}

			if (K < lcount) {
				int[] row = new int[n - rb.sa[i]];
				Arrays.fill(row, -1);

				for (int j = obp; j < bp; j++) {
					row[rb.branches[j][2] - 1] = rb.branches[j][3];
				}

				for (int j = n - rb.sa[i] - 1; j >= rb.deadline[i] + 1; j--) {
					if (row[j] == -1) {
						if (j == n - rb.sa[i] - 1) {
							row[j] = 0;
						} else {
							row[j] = row[j + 1] > 0 ? 0 : 1;
						}
					}
				}

				for (int j = rb.deadline[i] + 1; j < n - rb.sa[i]; j++) {
					if (row[j] != proh) {
						if (K == 0) {
							return new int[] { rb.sa[i], j + 1 };
						}

						K--;
					}
				}
			} else {
				K -= lcount;
			}
		}

		return null;
	}

	static Result go(char[] a) {
		int[] sa = suffixsort(a);
		int[] lcp = buildLCP(a, sa);
		int[][] branches = findBranches(lcp);
		LResult lres = countNimber(sa, lcp, branches);

		return new Result(sa, lcp, branches, lres.count, lres.zero, lres.one,
				lres.deadline);
	}

	private static LResult countNimber(int[] sa, int[] lcp, int[][] branches) {
		int n = sa.length;
		int[] zero = new int[n];
		int[] one = new int[n];
		int[] deadline = new int[n];
		Arrays.fill(deadline, -1);
		int[] hs = new int[n];
		int[] nim = new int[n];
		Arrays.fill(nim, -1);
		long[] count = new long[n + 1];

		for (int i = 0; i < n; i++) {
			hs[i] = n - sa[i] + 1;
		}

		int[] alive = new int[n];
		Arrays.fill(alive, 1);
		int[] ftalive = buildFenwick(alive);
		int bp = 0;
		int[] bs2 = new int[n];

		for (int[] branch : branches) {
			int sp = 0;
			int L = branch[0];
			int R = branch[1];
			int h = branch[2];

			if (L == -1) {
				L = 0;
			}

			int bs = 0;

			for (int i = L; i <= R && i >= 0; i = after(ftalive, i)) {
				if (nim[i] >= 0) {
					count[nim[i]]++;
				}

				int bet = hs[i] - h - 1;

				if (nim[i] == 0) {
					count[0] += bet / 2;
					count[1] += (bet + 1) / 2;
					zero[i] += bet / 2;
					one[i] += (bet + 1) / 2;
					bs |= 1 << (bet & 1);
				} else {
					count[0] += (bet + 1) / 2;
					count[1] += bet / 2;
					zero[i] += (bet + 1) / 2;
					one[i] += bet / 2;

					if (bet == 0) {
						if (nim[i] >= 0) {
							if (nim[i] <= 31) {
								bs |= 1 << nim[i];
							} else {
								bs2[sp++] = nim[i];
							}
						}
					} else {
						bs |= 1 << ((bet & 1) ^ 1);
					}
				}

				hs[i] = h;

				if (i > L) {
					alive[i] = 0;
					deadline[i] = h - 1;
					addFenwick(ftalive, i, -1);
				}
			}

			int clus = Integer.numberOfTrailingZeros(~bs);

			if (clus >= 32) {
				Arrays.sort(bs2, 0, sp);
				clus = 32;

				for (int q = 0; q < sp;) {
					if (bs2[q] == clus) {
						while (q < sp && bs2[q] == clus) {
							q++;
						}

						clus++;
					} else {
						break;
					}
				}
			}

			branches[bp++][3] = nim[L] = clus;

			if (branch[0] == -1) {
				count[nim[L]]++;
			}
		}

		return new LResult(count, zero, one, deadline);
	}

	static class LResult {
		long[] count;
		int[] zero, one;
		int[] deadline;

		public LResult(long[] count, int[] zero, int[] one, int[] deadline) {
			this.count = count;
			this.zero = zero;
			this.one = one;
			this.deadline = deadline;
		}
	}

	static int[][] findBranches(int[] a) {
		int n = a.length;
		long[] ap = new long[n];

		for (int i = 0; i < n; i++) {
			ap[i] = (long) (1000000 - a[i]) << 32 | i;
		}

		Arrays.sort(ap);
		int[][] branches = new int[n][];
		int p = 0;
		int[] flag = new int[n];
		Arrays.fill(flag, 1);
		int[] ft = buildFenwick(flag);

		for (int i = 0; i < n; i++) {
			int j;
			int last = (int) ap[i];
			int va = 1000000 - (int) (ap[i] >>> 32);

			for (j = (int) ap[i]; j >= 0 && j < n && flag[j] == 1 && a[j] >= va; j = after(
					ft, j)) {
				last = j;
				flag[j] = 0;
				addFenwick(ft, j, -1);
			}

			if (j == (int) ap[i]) {
				continue;
			}

			branches[p++] = new int[] { before(ft, (int) ap[i]), last, va, -1 };
		}

		return Arrays.copyOf(branches, p);
	}

	public static int sumFenwick(int[] ft, int i) {
		int sum = 0;

		for (i++; i > 0; i -= i & -i) {
			sum += ft[i];
		}

		return sum;
	}

	public static void addFenwick(int[] ft, int i, int v) {
		if (v == 0) {
			return;
		}

		int n = ft.length;

		for (i++; i < n; i += i & -i) {
			ft[i] += v;
		}
	}

	public static int findGFenwick(int[] ft, int v) {
		int i = 0;
		int n = ft.length;

		for (int b = Integer.highestOneBit(n); b != 0 && i < n; b >>= 1) {
			if (i + b < n) {
				int t = i + b;

				if (v >= ft[t]) {
					i = t;
					v -= ft[t];
				}
			}
		}

		return v != 0 ? -(i + 1) : i - 1;
	}

	public static int valFenwick(int[] ft, int i) {
		return sumFenwick(ft, i) - sumFenwick(ft, i - 1);
	}

	public static int[] restoreFenwick(int[] ft) {
		int n = ft.length - 1;
		int[] ret = new int[n];

		for (int i = 0; i < n; i++) {
			ret[i] = sumFenwick(ft, i);
		}

		for (int i = n - 1; i >= 1; i--) {
			ret[i] -= ret[i - 1];
		}

		return ret;
	}

	public static int before(int[] ft, int x) {
		int u = sumFenwick(ft, x - 1);

		if (u == 0) {
			return -1;
		}

		return findGFenwick(ft, u - 1) + 1;
	}

	public static int after(int[] ft, int x) {
		int u = sumFenwick(ft, x);
		int f = findGFenwick(ft, u);

		if (f + 1 >= ft.length - 1) {
			return -1;
		}

		return f + 1;
	}

	public static int[] buildFenwick(int[] a) {
		int n = a.length;
		int[] ft = new int[n + 1];
		System.arraycopy(a, 0, ft, 1, n);

		for (int k = 2, h = 1; k <= n; k *= 2, h *= 2) {
			for (int i = k; i <= n; i += k) {
				ft[i] += ft[i - h];
			}
		}

		return ft;
	}

	public static int[] buildLCP(char[] str, int[] sa) {
		int n = str.length;
		int h = 0;
		int[] lcp = new int[n];
		int[] b = new int[n];

		for (int i = 0; i < n; i++) {
			b[sa[i]] = i;
		}

		for (int i = 0; i < n; i++) {
			if (b[i] > 0) {
				for (int j = sa[b[i] - 1]; j + h < n && i + h < n
						&& str[j + h] == str[i + h]; h++)
					;

				lcp[b[i]] = h;
			} else {
				lcp[b[i]] = 0;
			}

			if (h > 0) {
				h--;
			}
		}

		return lcp;
	}

	private static interface BaseArray {
		public int get(int i);

		public void set(int i, int val);

		public int update(int i, int val);
	}

	private static class CharArray implements BaseArray {
		private char[] m_A = null;
		private int m_pos = 0;

		CharArray(char[] A, int pos) {
			m_A = A;
			m_pos = pos;
		}

		public int get(int i) {
			return m_A[m_pos + i] & 0xffff;
		}

		public void set(int i, int val) {
			m_A[m_pos + i] = (char) (val & 0xffff);
		}

		public int update(int i, int val) {
			return m_A[m_pos + i] += val & 0xffff;
		}
	}

	private static class IntArray implements BaseArray {
		private int[] m_A = null;
		private int m_pos = 0;

		IntArray(int[] A, int pos) {
			m_A = A;
			m_pos = pos;
		}

		public int get(int i) {
			return m_A[m_pos + i];
		}

		public void set(int i, int val) {
			m_A[m_pos + i] = val;
		}

		public int update(int i, int val) {
			return m_A[m_pos + i] += val;
		}
	}

	private static void getCounts(BaseArray T, BaseArray C, int n, int k) {
		int i;

		for (i = 0; i < k; ++i) {
			C.set(i, 0);
		}

		for (i = 0; i < n; ++i) {
			C.update(T.get(i), 1);
		}
	}

	private static void getBuckets(BaseArray C, BaseArray B, int k, boolean end) {
		int i, sum = 0;

		if (end != false) {
			for (i = 0; i < k; ++i) {
				sum += C.get(i);
				B.set(i, sum);
			}
		} else {
			for (i = 0; i < k; ++i) {
				sum += C.get(i);
				B.set(i, sum - C.get(i));
			}
		}
	}

	private static void LMSsort(BaseArray T, int[] SA, BaseArray C,
			BaseArray B, int n, int k) {
		int b, i, j;
		int c0, c1;

		if (C == B) {
			getCounts(T, C, n, k);
		}

		getBuckets(C, B, k, false);
		j = n - 1;
		b = B.get(c1 = T.get(j));
		--j;
		SA[b++] = (T.get(j) < c1) ? ~j : j;

		for (i = 0; i < n; ++i) {
			if (0 < (j = SA[i])) {
				if ((c0 = T.get(j)) != c1) {
					B.set(c1, b);
					b = B.get(c1 = c0);
				}

				--j;
				SA[b++] = (T.get(j) < c1) ? ~j : j;
				SA[i] = 0;
			} else if (j < 0) {
				SA[i] = ~j;
			}
		}

		if (C == B) {
			getCounts(T, C, n, k);
		}

		getBuckets(C, B, k, true);

		for (i = n - 1, b = B.get(c1 = 0); 0 <= i; --i) {
			if (0 < (j = SA[i])) {
				if ((c0 = T.get(j)) != c1) {
					B.set(c1, b);
					b = B.get(c1 = c0);
				}

				--j;
				SA[--b] = (T.get(j) > c1) ? ~(j + 1) : j;
				SA[i] = 0;
			}
		}
	}

	private static int LMSpostproc(BaseArray T, int[] SA, int n, int m) {
		int i, j, p, q, plen, qlen, name;
		int c0, c1;
		boolean diff;

		for (i = 0; (p = SA[i]) < 0; ++i) {
			SA[i] = ~p;
		}

		if (i < m) {
			for (j = i, ++i;; ++i) {
				if ((p = SA[i]) < 0) {
					SA[j++] = ~p;
					SA[i] = 0;

					if (j == m) {
						break;
					}
				}
			}
		}

		i = n - 1;
		j = n - 1;
		c0 = T.get(n - 1);

		do {
			c1 = c0;
		} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));

		for (; 0 <= i;) {
			do {
				c1 = c0;
			} while ((0 <= --i) && ((c0 = T.get(i)) <= c1));

			if (0 <= i) {
				SA[m + ((i + 1) >> 1)] = j - i;
				j = i + 1;

				do {
					c1 = c0;
				} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));
			}
		}

		for (i = 0, name = 0, q = n, qlen = 0; i < m; ++i) {
			p = SA[i];
			plen = SA[m + (p >> 1)];
			diff = true;

			if ((plen == qlen) && ((q + plen) < n)) {
				for (j = 0; (j < plen) && (T.get(p + j) == T.get(q + j)); ++j) {
				}

				if (j == plen) {
					diff = false;
				}
			}

			if (diff != false) {
				++name;
				q = p;
				qlen = plen;
			}

			SA[m + (p >> 1)] = name;
		}

		return name;
	}

	private static void induceSA(BaseArray T, int[] SA, BaseArray C,
			BaseArray B, int n, int k) {
		int b, i, j;
		int c0, c1;

		if (C == B) {
			getCounts(T, C, n, k);
		}
		getBuckets(C, B, k, false);
		j = n - 1;
		b = B.get(c1 = T.get(j));
		SA[b++] = ((0 < j) && (T.get(j - 1) < c1)) ? ~j : j;

		for (i = 0; i < n; ++i) {
			j = SA[i];
			SA[i] = ~j;

			if (0 < j) {
				if ((c0 = T.get(--j)) != c1) {
					B.set(c1, b);
					b = B.get(c1 = c0);
				}

				SA[b++] = ((0 < j) && (T.get(j - 1) < c1)) ? ~j : j;
			}
		}

		if (C == B) {
			getCounts(T, C, n, k);
		}

		getBuckets(C, B, k, true);
		for (i = n - 1, b = B.get(c1 = 0); 0 <= i; --i) {
			if (0 < (j = SA[i])) {
				if ((c0 = T.get(--j)) != c1) {
					B.set(c1, b);
					b = B.get(c1 = c0);
				}

				SA[--b] = ((j == 0) || (T.get(j - 1) > c1)) ? ~j : j;
			} else {
				SA[i] = ~j;
			}
		}
	}

	private static void SA_IS(BaseArray T, int[] SA, int fs, int n, int k) {
		BaseArray C, B, RA;
		int i, j, b, m, p, q, name, newfs;
		int c0, c1;
		int flags = 0;

		if (k <= 256) {
			C = new IntArray(new int[k], 0);

			if (k <= fs) {
				B = new IntArray(SA, n + fs - k);
				flags = 1;
			} else {
				B = new IntArray(new int[k], 0);
				flags = 3;
			}
		} else if (k <= fs) {
			C = new IntArray(SA, n + fs - k);

			if (k <= (fs - k)) {
				B = new IntArray(SA, n + fs - k * 2);
				flags = 0;
			} else if (k <= 1024) {
				B = new IntArray(new int[k], 0);
				flags = 2;
			} else {
				B = C;
				flags = 8;
			}
		} else {
			C = B = new IntArray(new int[k], 0);
			flags = 4 | 8;
		}

		getCounts(T, C, n, k);
		getBuckets(C, B, k, true);

		for (i = 0; i < n; ++i) {
			SA[i] = 0;
		}

		b = -1;
		i = n - 1;
		j = n;
		m = 0;
		c0 = T.get(n - 1);

		do {
			c1 = c0;
		} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));

		for (; 0 <= i;) {
			do {
				c1 = c0;
			} while ((0 <= --i) && ((c0 = T.get(i)) <= c1));

			if (0 <= i) {
				if (0 <= b) {
					SA[b] = j;
				}

				b = B.update(c1, -1);
				j = i;
				++m;

				do {
					c1 = c0;
				} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));
			}
		}

		if (1 < m) {
			LMSsort(T, SA, C, B, n, k);
			name = LMSpostproc(T, SA, n, m);
		} else if (m == 1) {
			SA[b] = j + 1;
			name = 1;
		} else {
			name = 0;
		}

		if (name < m) {
			if ((flags & 4) != 0) {
				C = null;
				B = null;
			}

			if ((flags & 2) != 0) {
				B = null;
			}

			newfs = (n + fs) - (m * 2);

			if ((flags & (1 | 4 | 8)) == 0) {
				if ((k + name) <= newfs) {
					newfs -= k;
				} else {
					flags |= 8;
				}
			}

			for (i = m + (n >> 1) - 1, j = m * 2 + newfs - 1; m <= i; --i) {
				if (SA[i] != 0) {
					SA[j--] = SA[i] - 1;
				}
			}

			RA = new IntArray(SA, m + newfs);
			SA_IS(RA, SA, newfs, m, name);
			RA = null;
			i = n - 1;
			j = m * 2 - 1;
			c0 = T.get(n - 1);

			do {
				c1 = c0;
			} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));

			for (; 0 <= i;) {
				do {
					c1 = c0;
				} while ((0 <= --i) && ((c0 = T.get(i)) <= c1));

				if (0 <= i) {
					SA[j--] = i + 1;

					do {
						c1 = c0;
					} while ((0 <= --i) && ((c0 = T.get(i)) >= c1));
				}
			}

			for (i = 0; i < m; ++i) {
				SA[i] = SA[m + SA[i]];
			}

			if ((flags & 4) != 0) {
				C = B = new IntArray(new int[k], 0);
			}

			if ((flags & 2) != 0) {
				B = new IntArray(new int[k], 0);
			}
		}

		if ((flags & 8) != 0) {
			getCounts(T, C, n, k);
		}

		if (1 < m) {
			getBuckets(C, B, k, true);
			i = m - 1;
			j = n;
			p = SA[m - 1];
			c1 = T.get(p);

			do {
				q = B.get(c0 = c1);

				while (q < j) {
					SA[--j] = 0;
				}

				do {
					SA[--j] = p;

					if (--i < 0) {
						break;
					}

					p = SA[i];
				} while ((c1 = T.get(p)) == c0);
			} while (0 <= i);

			while (0 < j) {
				SA[--j] = 0;
			}
		}

		induceSA(T, SA, C, B, n, k);
		C = null;
		B = null;
	}

	public static int[] suffixsort(char[] T) {
		if (T == null) {
			return null;
		}

		int n = T.length;
		int[] SA = new int[n];

		if (n <= 1) {
			if (n == 1) {
				SA[0] = 0;
			}

			return SA;
		}

		SA_IS(new CharArray(T, 0), SA, 0, n, 65536);

		return SA;
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

	private static boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private static int skip() {
		int b;

		while ((b = readByte()) != -1 && isSpaceChar(b))
			;

		return b;
	}

	private static char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;

		while (p < n && !(isSpaceChar(b))) {
			buf[p++] = (char) b;
			b = readByte();
		}

		return n == p ? buf : Arrays.copyOf(buf, p);
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

	private static long nl() {
		long num = 0;
		int b;
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
