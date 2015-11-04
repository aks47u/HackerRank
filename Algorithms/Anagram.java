package Algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Anagram {
	private static BufferedReader br;
	private static StringTokenizer st;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		InputStream input = System.in;
		PrintStream output = System.out;
		br = new BufferedReader(new InputStreamReader(input));
		out = new PrintWriter(output);
		solve();
		out.close();
	}

	private static void solve() throws IOException {
		int tests = nextInt();

		while (tests-- > 0) {
			String s = nextToken();
			int answer = solve(s);
			out.println(answer);
		}
	}

	private static int solve(String s) {
		if ((s.length() & 1) != 0) {
			return -1;
		}

		int k = s.length() >> 1;
		char[] c1 = s.substring(0, k).toCharArray();
		char[] c2 = s.substring(k, 2 * k).toCharArray();
		int[] cnt1 = get(c1);
		int[] cnt2 = get(c2);
		int result = 0;

		for (int i = 0; i < 256; i++) {
			result += Math.abs(cnt1[i] - cnt2[i]);
		}

		return result >> 1;
	}

	private static int[] get(char[] c1) {
		int[] ret = new int[256];

		for (char cc : c1) {
			++ret[cc];
		}

		return ret;
	}

	private static int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	private static String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			String line = br.readLine();

			if (line == null) {
				return null;
			}

			st = new StringTokenizer(line);
		}

		return st.nextToken();
	}
}
