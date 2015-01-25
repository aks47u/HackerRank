package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Counter_game {
	private static BufferedReader reader;
	private static StringTokenizer tokenizer = null;
	private static PrintWriter writer;
	private static BigInteger two = new BigInteger("2");

	public static void main(String[] args) throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		writer = new PrintWriter(System.out);
		cherry();
		reader.close();
		writer.close();
	}

	private static String nextToken() throws IOException {
		while (tokenizer == null || (!tokenizer.hasMoreTokens())) {
			tokenizer = new StringTokenizer(reader.readLine());
		}
		
		return tokenizer.nextToken();
	}

	private static int nextInt() throws NumberFormatException, IOException {
		return Integer.parseInt(nextToken());
	}

	private static boolean isWin(BigInteger bi) {
		if (bi.equals(BigInteger.ONE)) {
			return false;
		}
		
		if (bi.bitCount() == 1) {
			return !isWin(bi.divide(two));
		} else {
			return !isWin(bi.clearBit(bi.bitLength() - 1));
		}
	}

	private static void cherry() throws NumberFormatException, IOException {
		int T = nextInt();
		
		for (int t = 0; t < T; t++) {
			BigInteger bi = new BigInteger(nextToken());
			writer.println(isWin(bi) ? "Louise" : "Richard");
		}
	}
}
