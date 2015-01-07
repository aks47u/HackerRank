package Algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Filling_Jars {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String[] line = br.readLine().split(" ");
			int N = Integer.parseInt(line[0]);
			int M = Integer.parseInt(line[1]);
			BigInteger noOfCandy = BigInteger.ZERO;
			BigInteger average = BigInteger.ZERO;

			for (int i = 0; i < M; i++) {
				String[] lines = br.readLine().split(" ");
				int a = Integer.parseInt(lines[0]);
				int b = Integer.parseInt(lines[1]);
				int k = Integer.parseInt(lines[2]);
				noOfCandy = noOfCandy.add(noOfCandies(a, b, k));
			}

			average = noOfCandy.divide(BigInteger.valueOf(N));
			System.out.println(average);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	private static BigInteger noOfCandies(int a, int b, int k) {
		BigInteger noOfChocolates = BigInteger.ONE;
		long difference = (b - a + 1);
		noOfChocolates = noOfChocolates
				.multiply(BigInteger.valueOf(difference));

		return noOfChocolates = noOfChocolates.multiply(BigInteger.valueOf(k));
	}
}
