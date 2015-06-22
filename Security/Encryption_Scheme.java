import java.util.Scanner;

public class Encryption_Scheme {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		scn.close();
		int res = 1;

		for (int i = 1; i <= n; i++) {
			res *= i;
		}

		System.out.println(res);
	}
}
