import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Reverse_Shuffle_Merge {
	private static BufferedReader in;
	private static PrintWriter out;
	private static StringTokenizer st;

	public static void main(String[] args) {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			solve();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void solve() throws IOException {
		int tests = 1;

		for (int test = 0; test < tests; test++) {
			solveOne();
		}
	}

	private static void solveOne() throws IOException {
		String s = nextToken();
		s = reverseString(s);
		int alphaSize = 26;
		int[] count = new int[alphaSize];

		for (int i = 0; i < s.length(); i++) {
			++count[s.charAt(i) - 'a'];
		}

		int needLength = 0;

		for (int i = 0; i < alphaSize; i++) {
			if (count[i] % 2 != 0) {
				throw new AssertionError();
			}

			count[i] /= 2;
			needLength += count[i];
		}

		StringBuilder result = new StringBuilder();
		int[][] counts = new int[s.length()][alphaSize];

		for (int i = s.length() - 1; i >= 0; i--) {
			for (int j = 0; j < alphaSize; j++) {
				counts[i][j] = (i + 1 == s.length() ? 0 : counts[i + 1][j]);
			}

			counts[i][s.charAt(i) - 'a']++;
		}

		int leftPointer = 0;

		for (int it = 0; it < needLength; it++) {
			int resultIndex = -1;

			for (int i = leftPointer; i < s.length(); i++) {
				if (count[s.charAt(i) - 'a'] > 0) {
					if (resultIndex == -1
							|| s.charAt(i) < s.charAt(resultIndex)) {
						if (isOk(count, counts[i])) {
							resultIndex = i;
						}
					}
				}
			}

			result.append(s.charAt(resultIndex));
			--count[s.charAt(resultIndex) - 'a'];
			leftPointer = resultIndex + 1;
		}

		out.println(result);
	}

	private static boolean isOk(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] > b[i]) {
				return false;
			}
		}

		return true;
	}

	private static String reverseString(String s) {
		return new StringBuilder(s).reverse().toString();
	}

	private static String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			String line = in.readLine();

			if (line == null) {
				return null;
			}

			st = new StringTokenizer(line);
		}

		return st.nextToken();
	}
}
