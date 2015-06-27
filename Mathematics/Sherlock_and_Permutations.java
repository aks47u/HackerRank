import java.math.BigInteger;
import java.util.Scanner;

public class Sherlock_and_Permutations {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			int N = scn.nextInt();
			int M = scn.nextInt();
			BigInteger top = factorial(N + M - 1);
			BigInteger bottom1 = factorial(M - 1);
			BigInteger bottom2 = factorial(N);
			BigInteger bottom = bottom1.multiply(bottom2);
			BigInteger res = top.divide(bottom);
			res = res.mod(BigInteger.valueOf(1000000007));
			System.out.println(res);
		}

		scn.close();
	}

	private static BigInteger factorial(int n) {
		BigInteger fact = BigInteger.ONE;

		for (int i = 1; i <= n; i++) {
			fact = fact.multiply(BigInteger.valueOf(i));
		}

		return fact;
	}
}
