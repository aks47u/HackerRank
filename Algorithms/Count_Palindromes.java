import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class Count_Palindromes {
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
		for (int T = ni(); T >= 1; T--) {
			long K = nl();
			long start = Math.max(0, (long) Math.sqrt(2 * K) - 10);
			int len = 0;
			long i;

			for (i = start;; i++) {
				if ((i + 1) * (i + 2) / 2 > K) {
					break;
				}
			}

			K -= i * (i + 1) / 2;
			len += i;
			int step = 0;

			while (true) {
				if (K == 0) {
					break;
				}

				if (step == 0) {
					for (i = 1;; i++) {
						if ((i + 1) * (i + 2) / 2 > K) {
							break;
						}
					}

					len += i;
					K -= i * (i + 1) / 2;
				} else {
					if (K <= 2) {
						len++;
						break;
					}

					for (i = 1;; i++) {
						if ((i + 2) * (i + 3) / 2 - 1 > K) {
							break;
						}
					}

					len += i;
					K -= (i + 1) * (i + 2) / 2 - 1;
				}

				step++;
			}

			out.println(len);
		}
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
