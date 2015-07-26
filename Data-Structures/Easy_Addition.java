import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Easy_Addition {
	static InputStream is;
	static PrintWriter out;
	static String INPUT = "";

	public static long invl(long a, long mod) {
		long b = mod;
		long p = 1, q = 0;

		while (b > 0) {
			long c = a / b;
			long d;
			d = a;
			a = b;
			b = d % b;
			d = p;
			p = q;
			q = d - c * q;
		}

		return p < 0 ? p + mod : p;
	}

	public static void solve() {
		int mod = 1000000007;
		int n = ni(), R = ni();
		int IR = (int) invl(R, mod);
		int[] from = new int[n - 1];
		int[] to = new int[n - 1];

		for (int i = 0; i < n - 1; i++) {
			from[i] = ni() - 1;
			to[i] = ni() - 1;
		}

		int[][] g = packU(n, from, to);
		int[][] pars = parents3(g, 0);
		int[] par = pars[0], ord = pars[1], dep = pars[2];
		int[][] spar = logstepParents(par);
		long[] w = new long[n];
		long[] wz = new long[n];
		long[] wzz = new long[n];
		long[] x = new long[n];
		long[] xz = new long[n];
		long[] xzz = new long[n];
		long[] pr = new long[n + 1];
		long[] pir = new long[n + 1];
		pr[0] = 1;
		pir[0] = 1;

		for (int i = 1; i <= n; i++) {
			pr[i] = pr[i - 1] * R % mod;
			pir[i] = pir[i - 1] * IR % mod;
		}

		int U = ni(), Q = ni();

		for (int i = 0; i < U; i++) {
			long a1 = ni(), d1 = ni();
			long a2 = ni(), d2 = ni();
			int a = ni() - 1, b = ni() - 1;
			int lca = lca2(a, b, spar, dep);
			long p0 = a1 * a2 % mod;
			long p1 = (d1 * a2 + d2 * a1) % mod;
			long p2 = d1 * d2 % mod;
			w[a] += p0;
			w[a] %= mod;
			wz[a] += p1;
			wz[a] %= mod;
			wzz[a] += p2;
			wzz[a] %= mod;

			if (par[lca] != -1) {
				int pl = par[lca];
				int dal = dep[a] - dep[pl];
				long rd = pr[dal];
				w[pl] -= (p0 + p1 * dal + p2 * dal % mod * dal) % mod * rd
						% mod;

				if (w[pl] < 0) {
					w[pl] += mod;
				}

				wz[pl] -= (p1 + 2 * p2 * dal) % mod * rd % mod;

				if (wz[pl] < 0) {
					wz[pl] += mod;
				}

				wzz[pl] -= p2 * rd % mod;

				if (wzz[pl] < 0) {
					wzz[pl] += mod;
				}
			}

			int dab = dep[a] + dep[b] - 2 * dep[lca];
			long rx = pr[dab];
			long px = (p0 + p1 * dab + p2 * dab % mod * dab) % mod * rx % mod;
			long pxz = (p1 + 2 * p2 * dab) % mod * rx % mod;
			long pxzz = p2 * rx % mod;
			x[b] += px;
			x[b] %= mod;
			xz[b] += pxz;
			xz[b] %= mod;
			xzz[b] += pxzz;
			xzz[b] %= mod;

			{
				int dal = dep[a] - dep[lca];
				long rd = pr[dal];
				x[lca] -= (p0 + p1 * dal + p2 * dal % mod * dal) % mod * rd
						% mod;

				if (w[lca] < 0) {
					x[lca] += mod;
				}

				xz[lca] -= (p1 + 2 * p2 * dal) % mod * rd % mod;

				if (wz[lca] < 0) {
					xz[lca] += mod;
				}

				xzz[lca] -= p2 * rd % mod;

				if (xzz[lca] < 0) {
					xzz[lca] += mod;
				}
			}
		}

		for (int i = n - 1; i >= 1; i--) {
			int cur = ord[i];
			int p = par[cur];
			w[p] += w[cur] * R + wz[cur] * R + wzz[cur] * R;
			w[p] %= mod;
			wz[p] += wz[cur] * R + 2L * wzz[cur] * R;
			wz[p] %= mod;
			wzz[p] += wzz[cur] * R;
			wzz[p] %= mod;
			x[p] += x[cur] * IR - xz[cur] * IR + xzz[cur] * IR;
			x[p] %= mod;
			xz[p] += xz[cur] * IR - 2L * xzz[cur] * IR;
			xz[p] %= mod;
			xzz[p] += xzz[cur] * IR;
			xzz[p] %= mod;
		}

		long[] s = new long[n];

		for (int i = 0; i < n; i++) {
			int cur = ord[i];
			int p = par[cur];

			if (p != -1) {
				s[cur] = (s[p] + w[cur] + x[cur]) % mod;
			} else {
				s[cur] = (w[cur] + x[cur]) % mod;
			}
		}

		for (int i = 0; i < Q; i++) {
			int u = ni() - 1, v = ni() - 1;
			int lca = lca2(u, v, spar, dep);
			long ret = s[u] + s[v] - s[lca];

			if (par[lca] != -1) {
				ret -= s[par[lca]];
			}

			ret %= mod;

			if (ret < 0) {
				ret += mod;
			}

			out.println(ret);
		}
	}

	public static long pow(long a, long n, long mod) {
		long ret = 1;
		int x = 63 - Long.numberOfLeadingZeros(n);

		for (; x >= 0; x--) {
			ret = ret * ret % mod;

			if (n << 63 - x < 0) {
				ret = ret * a % mod;
			}
		}

		return ret;
	}

	public static int lca2(int a, int b, int[][] spar, int[] depth) {
		if (depth[a] < depth[b]) {
			b = ancestor(b, depth[b] - depth[a], spar);
		} else if (depth[a] > depth[b]) {
			a = ancestor(a, depth[a] - depth[b], spar);
		}

		if (a == b) {
			return a;
		}

		int sa = a, sb = b;

		for (int low = 0, high = depth[a], t = Integer.highestOneBit(high), k = Integer
				.numberOfTrailingZeros(t); t > 0; t >>>= 1, k--) {
			if ((low ^ high) >= t) {
				if (spar[k][sa] != spar[k][sb]) {
					low |= t;
					sa = spar[k][sa];
					sb = spar[k][sb];
				} else {
					high = low | t - 1;
				}
			}
		}

		return spar[0][sa];
	}

	protected static int ancestor(int a, int m, int[][] spar) {
		for (int i = 0; m > 0 && a != -1; m >>>= 1, i++) {
			if ((m & 1) == 1) {
				a = spar[i][a];
			}
		}

		return a;
	}

	public static int[][] logstepParents(int[] par) {
		int n = par.length;
		int m = Integer.numberOfTrailingZeros(Integer.highestOneBit(n - 1)) + 1;
		int[][] pars = new int[m][n];
		pars[0] = par;

		for (int j = 1; j < m; j++) {
			for (int i = 0; i < n; i++) {
				pars[j][i] = pars[j - 1][i] == -1 ? -1
						: pars[j - 1][pars[j - 1][i]];
			}
		}

		return pars;
	}

	public static int[][] parents3(int[][] g, int root) {
		int n = g.length;
		int[] par = new int[n];
		Arrays.fill(par, -1);
		int[] depth = new int[n];
		depth[0] = 0;
		int[] q = new int[n];
		q[0] = root;

		for (int p = 0, r = 1; p < r; p++) {
			int cur = q[p];

			for (int nex : g[cur]) {
				if (par[cur] != nex) {
					q[r++] = nex;
					par[nex] = cur;
					depth[nex] = depth[cur] + 1;
				}
			}
		}

		return new int[][] { par, q, depth };
	}

	static int[][] packU(int n, int[] from, int[] to) {
		int[][] g = new int[n][];
		int[] p = new int[n];

		for (int f : from) {
			p[f]++;
		}

		for (int t : to) {
			p[t]++;
		}

		for (int i = 0; i < n; i++) {
			g[i] = new int[p[i]];
		}

		for (int i = 0; i < from.length; i++) {
			g[from[i]][--p[from[i]]] = to[i];
			g[to[i]][--p[to[i]]] = from[i];
		}

		return g;
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
