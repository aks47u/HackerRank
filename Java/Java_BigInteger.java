import java.math.BigInteger;
import java.util.Scanner;

public class Java_BigInteger {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		BigInteger a = scn.nextBigInteger();
		BigInteger b = scn.nextBigInteger();
		scn.close();
		System.out.println(a.add(b));
		System.out.println(a.multiply(b));
	}
}
