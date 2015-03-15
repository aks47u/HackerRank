import java.util.Scanner;

public class Sam_and_substrings {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String number = scn.next();
		scn.close();
		int l = number.length();
		char[] nArray = number.toCharArray();
		long ones = 1, result = 0, mod = 1000000007;

		for (int i = l - 1; i >= 0; i--) {
			int digit = nArray[i] - '0';
			long tmp = (ones * digit * (i + 1)) % mod;
			result = (result + tmp) % mod;
			ones = (ones * 10 + 1) % mod;
		}

		System.out.print(result);
	}
}
