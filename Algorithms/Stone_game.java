import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Stone_game {
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
		int n = ni();
		int[] a = na(n);
		int mod = 1000000007;
		long ret = 0;
		int w = 0;

		while (true) {
			int max = 0;
			int maxi = -1;

			for (int i = 0; i < n; i++) {
				if (a[i] > max) {
					max = a[i];
					maxi = i;
				}
			}

			if (max == 0) {
				if (w == 0) {
					ret++;
				}

				break;
			}

			int h = Integer.highestOneBit(max);

			if (w == 0 || w == h) {
				long[][][] dp = new long[n + 1][2][2];
				dp[0][w == 0 ? 0 : 1][0] = 1;

				for (int i = 0; i < n; i++) {
					if (i == maxi) {
						for (int j = 0; j < 2; j++) {
							for (int k = 0; k < 2; k++) {
								dp[i + 1][j][k] = dp[i][j][k];
							}
						}
					} else {
						if (a[i] < h) {
							dp[i + 1][0][0] = dp[i][0][0] * (a[i]) % mod;
							dp[i + 1][1][0] = dp[i][1][0] * (a[i]) % mod;
							dp[i + 1][0][1] = (dp[i][0][1] * (a[i] + 1) + dp[i][0][0])
									% mod;
							dp[i + 1][1][1] = (dp[i][1][1] * (a[i] + 1) + dp[i][1][0])
									% mod;
						} else {
							dp[i + 1][0][0] = (dp[i][0][0] * h + dp[i][1][0]
									* (a[i] - h))
									% mod;
							dp[i + 1][1][0] = (dp[i][1][0] * h + dp[i][0][0]
									* (a[i] - h))
									% mod;
							dp[i + 1][0][1] = (dp[i][0][1] * h + dp[i][1][1]
									* (a[i] - h + 1) + dp[i][1][0])
									% mod;
							dp[i + 1][1][1] = (dp[i][1][1] * h + dp[i][0][1]
									* (a[i] - h + 1) + dp[i][0][0])
									% mod;
						}
					}
				}

				ret += dp[n][0][1];
			}

			a[maxi] -= h;
			w ^= h;
		}

		out.println(ret % mod);
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
