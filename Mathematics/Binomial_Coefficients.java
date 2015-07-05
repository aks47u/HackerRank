import java.math.BigInteger;
import java.util.Scanner;

public class Binomial_Coefficients {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			BigInteger n = scn.nextBigInteger();
			BigInteger p = scn.nextBigInteger();

			if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)) {
				System.out.println(0);
				continue;
			}

			BigInteger cb = n;
			BigInteger ans = BigInteger.ONE;

			while (!cb.equals(BigInteger.ZERO)) {
				ans = ans.multiply(cb.mod(p).add(BigInteger.ONE));
				cb = cb.divide(p);
			}

			System.out.println(n.add(BigInteger.ONE).subtract(ans));
		}

		scn.close();
	}
}
