import java.math.BigInteger;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			long B = scn.nextInt();
			long W = scn.nextInt();
			long X = scn.nextInt();
			long Y = scn.nextInt();
			long Z = scn.nextInt();
			BigInteger price = BigInteger.ZERO;

			if (X + Z <= Y) {
				price = BigInteger.valueOf(((B + W) * X) + (W * Z));
			} else if (Y + Z <= X) {
				price = BigInteger.valueOf(((B + W) * Y) + (B * Z));
			} else {
				price = BigInteger.valueOf((B * X) + (W * Y));
			}

			System.out.println(price);
		}

		scn.close();
	}
}
