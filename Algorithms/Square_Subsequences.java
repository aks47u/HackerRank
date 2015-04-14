import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Square_Subsequences {
	private static int MOD = 1000000007;

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			int T = Integer.parseInt(br.readLine());

			while (T-- > 0) {
				System.out.println(count(br.readLine()));
			}
		} catch (Exception e) {
		}
	}

	private static int count(String a, String b) {
		int n = a.length();
		int m = b.length();
		int[][] dp = new int[n + 1][m + 1];
		int[][] sum = new int[n + 1][m + 1];

		for (int i = 0; i <= n; ++i) {
			sum[i][m] = 1;
		}

		for (int j = 0; j <= m; ++j) {
			sum[n][j] = 1;
		}

		for (int i = n - 1; i >= 0; --i) {
			for (int j = m - 1; j >= 0; --j) {
				if (a.charAt(i) == b.charAt(j)) {
					dp[i][j] = sum[i + 1][j + 1];
				}

				sum[i][j] = (sum[i + 1][j] + sum[i][j + 1] - sum[i + 1][j + 1] + dp[i][j])
						% MOD;
			}
		}

		int result = 0;

		for (int i = 0; i < n; ++i) {
			result += dp[i][0];
			result %= MOD;
		}

		return result;
	}

	private static int count(String s) {
		int n = s.length();
		int result = 0;

		for (int i = 1; i < n; ++i) {
			result += count(s.substring(0, i), s.substring(i, n));
			result %= MOD;
		}

		return (result + MOD) % MOD;
	}
}
