package Algorithms_Combinatorics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class Building_a_List {
	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter out;
	private StringBuilder output = new StringBuilder();
	String inputstring = "";

	public static void main(String[] args) {
		new Building_a_List().run();
	}

	private void combine(int start) {
		for (int i = start; i < inputstring.length(); ++i) {
			output.append(inputstring.charAt(i));

			if (!output.equals("")) {
				System.out.println(output);
			}

			if (i < inputstring.length()) {
				combine(i + 1);
			}

			output.setLength(output.length() - 1);
		}
	}

	public void solve() throws IOException {
		int T = nextInt();

		for (int count = 0; count < T; count++) {
			nextInt();
			inputstring = nextToken();
			combine(0);
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

	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	String nextToken() throws IOException {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			tokenizer = new StringTokenizer(reader.readLine());
		}
		
		return tokenizer.nextToken();
	}
}
