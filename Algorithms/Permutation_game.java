import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.StringTokenizer;

public class Permutation_game {
	private void solution() throws IOException {
		int ts = in.nextInt();

		while (ts-- > 0) {
			int n = in.nextInt();
			Boolean[] dp = new Boolean[1 << n];
			int[] a = new int[n];

			for (int i = 0; i < n; ++i) {
				a[i] = in.nextInt();
			}

			out.println(isWin(0, a, dp) ? "Alice" : "Bob");
		}
	}

	private boolean isWin(int mask, int[] a, Boolean[] dp) {
		if (dp[mask] != null) {
			return dp[mask];
		}

		boolean loose = true;
		int last = -1;

		for (int i = 0; i < a.length; ++i) {
			if (((mask >> i) & 1) == 0) {
				if (a[i] <= last) {
					loose = false;
					break;
				}

				last = a[i];
			}
		}

		if (loose) {
			return dp[mask] = false;
		}

		boolean res = false;

		for (int next = 0; next < a.length; ++next) {
			if (((mask >> next) & 1) == 0) {
				res |= isWin(mask ^ (1 << next), a, dp) ^ true;
			}
		}

		return dp[mask] = res;
	}

	public void run() {
		try {
			solution();
			in.reader.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private class Scanner {
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

	public static void main(String[] args) throws IOException {
		new Permutation_game().run();
	}

	Scanner in = new Scanner(new InputStreamReader(System.in));
	PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(System.out)));
}
