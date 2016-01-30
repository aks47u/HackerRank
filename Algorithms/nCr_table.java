package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class nCr_table {
	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter out;

	public static void main(String[] args) {
		new nCr_table().run();
	}

	public void solve() throws IOException {
		int T = nextInt();

		while (T-- > 0) {
			int N = nextInt();
			BigInteger nCk = BigInteger.ONE;
			int i = N;

			for (int j = 0; j <= N; j++) {
				System.out.print(nCk.mod(new BigInteger("1000000000")) + " ");
				nCk = nCk.multiply(new BigInteger((i - j) + "")).divide(
						new BigInteger(((j + 1)) + ""));
			}

			System.out.println();
		}
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			tokenizer = null;
			out = new PrintWriter(System.out);
			solve();
			reader.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	String nextToken() throws IOException {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			tokenizer = new StringTokenizer(reader.readLine());
		}

		return tokenizer.nextToken();
	}
}
