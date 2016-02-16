import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Recalling_Early_Days_GP_with_Trees {
	private static InputStream is;
	private static PrintWriter out;
	private static String INPUT = "";
	private static byte[] inbuf = new byte[1024];
	private static int lenbuf = 0, ptrbuf = 0;

	public static void main(String[] args) throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(
				INPUT.getBytes());
		out = new PrintWriter(System.out);
		solve();
		out.flush();
	}

	private static void solve() {
		int mod = 100711433;
		int n = ni();
		long R = ni();
		int[] from = new int[n - 1];
		int[] to = new int[n - 1];

		for (int i = 0; i < n - 1; i++) {
			from[i] = ni() - 1;
			to[i] = ni() - 1;
		}

		int[][] g = packU(n, from, to);
		int[] up = new int[n];
		int[] down = new int[n];
		int[][] pars = parents3(g, 0);
		int[] par = pars[0], ord = pars[1], dep = pars[2];
		int[][] spar = logstepParents(par);
		int u = ni();
		int Q = ni();

		if (R % mod != 0) {
			for (int i = 0; i < u; i++) {
				int f = ni() - 1, t = ni() - 1, x = ni();
				int lca = lca2(f, t, spar, dep);
				up[f] += x;

				if (up[f] >= mod) {
					up[f] -= mod;
				}

				int inter = (int) (x * pow(R, dep[f] - dep[lca], mod) % mod);

				if (par[lca] != -1) {
					int xin = (int) (inter * R % mod);
					up[par[lca]] += mod - xin;

					if (up[par[lca]] >= mod) {
						up[par[lca]] -= mod;
					}
				}

				int last = (int) (inter * pow(R, dep[t] - dep[lca], mod) % mod);
				down[lca] += mod - inter;

				if (down[lca] >= mod) {
					down[lca] -= mod;
				}

				down[t] += last;

				if (down[t] >= mod) {
					down[t] -= mod;
				}
			}

			for (int i = n - 1; i >= 0; i--) {
				int cur = ord[i];
				int under = 0;

				for (int e : g[cur]) {
					if (e != par[cur]) {
						under += up[e];

						if (under >= mod) {
							under -= mod;
						}
					}
				}

				up[cur] = (int) ((up[cur] + under * R) % mod);
			}

			long IR = invl(R, mod);

			for (int i = n - 1; i >= 0; i--) {
				int cur = ord[i];
				int under = 0;

				for (int e : g[cur]) {
					if (e != par[cur]) {
						under += down[e];

						if (under >= mod) {
							under -= mod;
						}
					}
				}

				down[cur] = (int) ((down[cur] + under * IR) % mod);
			}

			for (int i = 0; i < n; i++) {
				int cur = ord[i];
				up[cur] += down[cur];

				if (up[cur] >= mod) {
					up[cur] -= mod;
				}

				for (int e : g[cur]) {
					if (e != par[cur]) {
						up[e] += up[cur];

						if (up[e] >= mod) {
							up[e] -= mod;
						}
					}
				}
			}
		} else {
			for (int i = 0; i < u; i++) {
				@SuppressWarnings("unused")
				int f = ni() - 1, t = ni() - 1, x = ni();
				up[f] += x;

				if (up[f] >= mod) {
					up[f] -= mod;
				}
			}

			for (int i = 0; i < n; i++) {
				int cur = ord[i];

				for (int e : g[cur]) {
					if (e != par[cur]) {
						up[e] += up[cur];

						if (up[e] >= mod) {
							up[e] -= mod;
						}
					}
				}
			}
		}

		for (int i = 0; i < Q; i++) {
			int f = ni() - 1, t = ni() - 1;
			int lca = lca2(f, t, spar, dep);
			long ret = up[f] + up[t] + mod - up[lca];

			if (par[lca] != -1) {
				ret += mod - up[par[lca]];
			}

			out.println(ret % mod);
		}
	}

	private static long invl(long a, long mod) {
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

	private static long pow(long a, long n, long mod) {
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

	private static int lca2(int a, int b, int[][] spar, int[] depth) {
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

	private static int ancestor(int a, int m, int[][] spar) {
		for (int i = 0; m > 0 && a != -1; m >>>= 1, i++) {
			if ((m & 1) == 1) {
				a = spar[i][a];
			}
		}

		return a;
	}

	private static int[][] logstepParents(int[] par) {
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

	private static int[][] parents3(int[][] g, int root) {
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

	private static int[][] packU(int n, int[] from, int[] to) {
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
