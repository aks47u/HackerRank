package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class K_Candy_Store {
	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter out;

	public static void main(String[] args) {
		new K_Candy_Store().run();
	}

	public BigInteger factorial(BigInteger n) {
		if (n.compareTo(BigInteger.ONE) == 0
				|| n.compareTo(BigInteger.ZERO) == 0) {
			return BigInteger.ONE;
		} else {
			return (n.multiply(factorial(n.subtract(BigInteger.ONE))));
		}
	}

	public void solve() throws IOException {
		int T = nextInt();

		for (int count = 0; count < T; count++) {
			int N = nextInt();
			int K = nextInt();
			BigInteger nCk = BigInteger.ONE;
			nCk = factorial(new BigInteger((N + K - 1) + "")).divide(
					factorial(new BigInteger(K + "")).multiply(
							factorial(BigInteger.valueOf(N - 1))));

			if (nCk.toString().length() > 9) {
				System.out.println(new BigInteger(nCk.toString().substring(
						nCk.toString().length() - 9, nCk.toString().length())));
			} else {
				System.out.println(nCk);
			}
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
