import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class Morgan_and_a_String {
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
			char[] s = ns().toCharArray();
			char[] t = ns().toCharArray();
			int p = 0, q = 0, r = 0;
			char[] ret = new char[s.length + t.length];

			outer: while (r < ret.length) {
				if (q == t.length) {
					ret[r++] = s[p++];
				} else if (p == s.length) {
					ret[r++] = t[q++];
				} else if (s[p] < t[q]) {
					ret[r++] = s[p++];
				} else if (s[p] > t[q]) {
					ret[r++] = t[q++];
				} else {
					int i = p, j = q;

					for (; i < s.length && j < t.length; i++, j++) {
						if (s[i] < t[j]) {
							for (int k = p, o = k; k < i && s[k] == s[o]; k++) {
								ret[r++] = s[p++];
							}

							continue outer;
						}

						if (s[i] > t[j]) {
							for (int k = q, o = k; k < j && t[k] == t[o]; k++) {
								ret[r++] = t[q++];
							}

							continue outer;
						}
					}

					if (i == s.length) {
						for (int k = q, o = k; k < j && t[k] == t[o]; k++) {
							ret[r++] = t[q++];
						}

						continue outer;
					}

					if (j == t.length) {
						for (int k = p, o = k; k < i && s[k] == s[o]; k++) {
							ret[r++] = s[p++];
						}

						continue outer;
					}

					throw new RuntimeException();
				}
			}

			out.println(new String(ret));
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

	private static boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private static int skip() {
		int b;

		while ((b = readByte()) != -1 && isSpaceChar(b))
			;

		return b;
	}

	private static String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();

		while (!(isSpaceChar(b))) {
			sb.appendCodePoint(b);
			b = readByte();
		}

		return sb.toString();
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
