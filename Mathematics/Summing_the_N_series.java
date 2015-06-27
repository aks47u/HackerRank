import java.math.BigInteger;
import java.util.Scanner;

public class Summing_the_N_series {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();
		BigInteger n = BigInteger.ZERO;
		BigInteger nn = BigInteger.ZERO;
		BigInteger mod = BigInteger.valueOf(1000000007);
		BigInteger result = BigInteger.ZERO;

		for (int i = 0; i < T; i++) {
			n = scn.nextBigInteger();
			nn = n.multiply(n);
			result = nn.mod(mod);
			System.out.println(result);
		}

		scn.close();
	}
}
