import java.util.Scanner;

public class Restaurant {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			int l = scn.nextInt();
			int b = scn.nextInt();
			int gcd = gcd(l, b);
			int ans = (l / gcd) * (b / gcd);
			System.out.println(ans);
		}

		scn.close();
	}

	private static int gcd(int a, int b) {
		return (a % b == 0) ? b : gcd(b, a % b);
	}
}
