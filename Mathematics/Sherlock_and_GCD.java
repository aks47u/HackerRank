import java.util.Scanner;

public class Sherlock_and_GCD {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		for (int i = 0; i < t; i++) {
			int N = scn.nextInt();
			int[] a = new int[N];
			int gc = 0;

			for (int j = 0; j < N; j++) {
				a[j] = scn.nextInt();
				gc = gcd(gc, a[j]);
			}

			if (gc == 1) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}

		scn.close();
	}

	private static int gcd(int m, int n) {
		while (n > 0) {
			int tmp = n;
			n = m % n;
			m = tmp;
		}

		return m;
	}
}
