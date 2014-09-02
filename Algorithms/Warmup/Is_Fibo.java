package Algorithms_Warmup;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Is_Fibo {
	private static ArrayList<String> arlst = new ArrayList<String>();

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();
		preCompute();

		for (int i = 0; i < t; i++) {
			BigInteger number = new BigInteger(scn.next());
			System.out.println(isFibonacci(number));
		}

		scn.close();
	}

	private static void preCompute() {
		arlst.add("0");
		arlst.add("1");

		for (int i = 2; i <= 60; i++) {
			BigInteger fibNum = fibonacci(BigInteger.valueOf(i));
			arlst.add(fibNum.toString());
		}
	}

	private static BigInteger fibonacci(BigInteger number) {
		BigInteger lastFirst = new BigInteger(arlst.get(arlst.size() - 1));
		BigInteger lastSecond = new BigInteger(arlst.get(arlst.size() - 2));
		BigInteger third = lastFirst.add(lastSecond);

		return third;
	}

	private static String isFibonacci(BigInteger n) {
		if (arlst.contains(n.toString())) {
			return "IsFibo";
		} else {
			return "IsNotFibo";
		}
	}
}
