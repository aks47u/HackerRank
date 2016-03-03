import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Diwali_Lights {
	private static BigInteger ExponentBySquare(long x, long n) {
		BigInteger big = BigInteger.valueOf(x).pow((int) n)
				.subtract(BigInteger.ONE).mod(new BigInteger("100000"));

		return big;
	}

	public static void main(String[] args) throws NumberFormatException,
	Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());

		while (T-- > 0) {
			System.out.println(ExponentBySquare(2, Long.parseLong(in.readLine())).toString());
		}

		in.close();
	}
}
