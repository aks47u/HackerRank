import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Substring_Diff {
	private static BufferedReader in;
	private static PrintWriter out;
	private static StringTokenizer tokenizer = new StringTokenizer("");
	private static int n, maxMismatch;
	private static char[] a, b;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(new OutputStreamWriter(System.out));
			solve();
			in.close();
			out.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void solve() throws IOException {
		int tc = nextInt();

		for (int tcIdx = 0; tcIdx < tc; tcIdx++) {
			maxMismatch = nextInt();
			a = nextToken().toCharArray();
			b = nextToken().toCharArray();

			if (a.length != b.length) {
				throw new IllegalStateException(
						"Input strings have different length");
			}

			n = a.length;
			out.println(solveFast());
		}
	}

	private static int solveFast() {
		int maxLen = 0;

		for (int i = 0; i < n; i++) {
			int m = solveFastSegment(i, 0);
			maxLen = Math.max(maxLen, m);
		}

		for (int j = 0; j < n; j++) {
			int m = solveFastSegment(0, j);
			maxLen = Math.max(maxLen, m);
		}

		return maxLen;
	}

	private static int solveFastSegment(int i, int j) {
		int len = Math.min(n - i, n - j);
		short[] d = new short[len];

		for (int k = 0; k < len; k++) {
			d[k] = (short) ((a[i + k] != b[j + k]) ? 1 : 0);
		}

		int l = 0;
		int sumMismatch = 0;
		int answer = 0;

		for (int r = 0; r < len; r++) {
			sumMismatch += d[r];

			while (sumMismatch > maxMismatch) {
				sumMismatch -= d[l++];
			}

			answer = Math.max(answer, r - l + 1);
		}

		return answer;
	}

	private static String nextToken() throws IOException {
		while (!tokenizer.hasMoreTokens()) {
			String line = in.readLine();

			if (line == null) {
				return null;
			}

			tokenizer = new StringTokenizer(line);
		}

		return tokenizer.nextToken();
	}

	private static int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
}
