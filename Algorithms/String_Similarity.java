import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;

public class String_Similarity {
	private static Scanner in = new Scanner(new InputStreamReader(System.in));
	private static PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(System.out)));

	public static void main(String[] args) throws IOException {
		try {
			solve();
			in.reader.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void solve() throws IOException {
		int ts = in.nextInt();

		while (ts-- > 0) {
			String s = in.next();
			int[] z = zFunc(s);
			long res = 0;

			for (int x : z) {
				res += x;
			}

			out.println(res + s.length());
		}
	}

	private static int[] zFunc(String s) {
		int[] res = new int[s.length()];
		int left = 0;
		int right = 0;

		for (int i = 1; i < s.length(); ++i) {
			if (i <= right) {
				res[i] = Math.min(right - i + 1, res[i - left]);
			}

			while (i + res[i] < s.length()
					&& s.charAt(res[i]) == s.charAt(i + res[i])) {
				++res[i];
			}

			if (i + res[i] - 1 > right) {
				left = i;
				right = i + res[i] - 1;
			}
		}

		return res;
	}

	private static class Scanner {
		StringTokenizer tokenizer;
		BufferedReader reader;

		public Scanner(Reader reader) {
			this.reader = new BufferedReader(reader);
			this.tokenizer = new StringTokenizer("");
		}

		public boolean hasNext() throws IOException {
			while (!tokenizer.hasMoreTokens()) {
				String line = reader.readLine();

				if (line == null) {
					return false;
				}

				tokenizer = new StringTokenizer(line);
			}

			return true;
		}

		public String next() throws IOException {
			hasNext();

			return tokenizer.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
