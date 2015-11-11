package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Building_a_List {
	private static BufferedReader br;
	private static PrintWriter out;
	private static String input = "";
	private static StringBuilder sb = new StringBuilder();
	private static StringTokenizer st;

	public static void main(String[] args) {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = null;
		out = new PrintWriter(System.out);

		try {
			solve();
			br.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		out.close();
	}

	private static void solve() throws NumberFormatException, IOException {
		int T = nextInt();

		while (T-- > 0) {
			nextInt();
			input = nextToken();
			combine(0);
		}
	}

	private static void combine(int start) {
		for (int i = start; i < input.length(); ++i) {
			sb.append(input.charAt(i));

			if (!sb.equals("")) {
				System.out.println(sb);
			}

			if (i < input.length()) {
				combine(i + 1);
			}

			sb.setLength(sb.length() - 1);
		}
	}

	private static int nextInt() throws NumberFormatException, IOException {
		return Integer.parseInt(nextToken());
	}

	private static String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}

		return st.nextToken();
	}
}
