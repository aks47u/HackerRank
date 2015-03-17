import java.util.Arrays;
import java.util.Scanner;

public class Lego_Blocks {
	int md = 1000000007;
	int[][] ways = new int[1001][1001];
	int[][] waysRestrict = new int[1001][1001];

	public static void main(String[] args) {
		Lego_Blocks o = new Lego_Blocks();
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();

		for (int i = 0; i < n; i++) {
			int a, b;
			a = scn.nextInt();
			b = scn.nextInt();
			System.out.println(o.solveRestrict(a, b));
		}

		scn.close();
	}

	public Lego_Blocks() {
		for (int[] w : ways) {
			Arrays.fill(w, -1);
		}

		for (int[] w : waysRestrict) {
			Arrays.fill(w, -1);
		}
	}

	public int solve(int n, int m) {
		if (ways[n][m] != -1) {
			return ways[n][m];
		}

		long ans;

		if (m == 1) {
			ans = 1;
		} else if (n == 1) {
			if (m <= 4) {
				ans = 2 * solve(1, m - 1);
			} else {
				ans = solve(1, m - 1);
				ans = (ans + solve(1, m - 2)) % md;
				ans = (ans + solve(1, m - 3)) % md;
				ans = (ans + solve(1, m - 4)) % md;
			}
		} else {
			ans = 1;
			int one = solve(1, m);

			for (int i = 0; i < n; i++) {
				ans = (ans * one) % md;
			}
		}

		ways[n][m] = (int) ans;

		return ways[n][m];
	}

	public int solveRestrict(int n, int m) {
		if (waysRestrict[n][m] != -1) {
			return waysRestrict[n][m];
		}

		long ans;

		if (m == 1) {
			ans = 1;
		} else {
			ans = solve(n, m);

			for (int i = 1; i < m; i++) {
				long a = solve(n, i);
				a = (a * solveRestrict(n, m - i)) % md;
				ans -= a;

				if (ans < 0) {
					ans += md;
				}
			}
		}

		waysRestrict[n][m] = (int) ans;

		return waysRestrict[n][m];
	}
}
