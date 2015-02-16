package Algorithms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Circle_City {
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
		int[] primes = sieveEratosthenes(200000);

		for (int T = ni(); T >= 1; T--) {
			int r = ni(), K = ni();
			int[][] f = factor(r, primes);
			long need = 4;

			for (int[] q : f) {
				if ((q[0] & 3) == 1) {
					need *= q[1] + 1;
				} else if ((q[0] & 3) == 3 && q[1] % 2 == 1) {
					need *= 0;
				}
			}

			out.println(K >= need ? "possible" : "impossible");
		}
	}

	private static int[][] factor(int n, int[] primes) {
		int[][] ret = new int[9][2];
		int rp = 0;

		for (int p : primes) {
			if (p * p > n) {
				break;
			}

			int i;

			for (i = 0; n % p == 0; n /= p, i++)
				;

			if (i > 0) {
				ret[rp][0] = p;
				ret[rp][1] = i;
				rp++;
			}
		}

		if (n != 1) {
			ret[rp][0] = n;
			ret[rp][1] = 1;
			rp++;
		}

		return Arrays.copyOf(ret, rp);
	}

	private static int[] sieveEratosthenes(int n) {
		if (n <= 32) {
			int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };

			for (int i = 0; i < primes.length; i++) {
				if (n < primes[i]) {
					return Arrays.copyOf(primes, i);
				}
			}

			return primes;
		}

		int u = n + 32;
		double lu = Math.log(u);
		int[] ret = new int[(int) (u / lu + u / lu / lu * 1.5)];
		ret[0] = 2;
		int pos = 1;
		int[] isp = new int[(n + 1) / 32 / 2 + 1];
		int sup = (n + 1) / 32 / 2 + 1;
		int[] tprimes = { 3, 5, 7, 11, 13, 17, 19, 23, 29, 31 };

		for (int tp : tprimes) {
			ret[pos++] = tp;
			int[] ptn = new int[tp];

			for (int i = (tp - 3) / 2; i < tp << 5; i += tp) {
				ptn[i >> 5] |= 1 << (i & 31);
			}

			for (int i = 0; i < tp; i++) {
				for (int j = i; j < sup; j += tp) {
					isp[j] |= ptn[i];
				}
			}
		}

		int[] magic = { 0, 1, 23, 2, 29, 24, 19, 3, 30, 27, 25, 11, 20, 8, 4,
				13, 31, 22, 28, 18, 26, 10, 7, 12, 21, 17, 9, 6, 16, 5, 15, 14 };
		int h = n / 2;

		for (int i = 0; i < sup; i++) {
			for (int j = ~isp[i]; j != 0; j &= j - 1) {
				int pp = i << 5 | magic[(j & -j) * 0x076be629 >>> 27];
				int p = 2 * pp + 3;

				if (p > n) {
					break;
				}

				ret[pos++] = p;

				for (int q = pp; q <= h; q += p) {
					isp[q >> 5] |= 1 << (q & 31);
				}
			}
		}

		return Arrays.copyOf(ret, pos);
	}

	private static byte[] inbuf = new byte[1024];
	private static int lenbuf = 0, ptrbuf = 0;

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
