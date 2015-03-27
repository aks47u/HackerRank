import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class String_Transmission {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		int mod = 1000000007;

		for (int test = 0; test < T; test++) {
			String[] line = in.readLine().split(" ");
			int N = Integer.parseInt(line[0]);
			int K = Integer.parseInt(line[1]);
			char[] characters = in.readLine().toCharArray();
			int[] arr = new int[N];

			for (int i = 0; i < N; i++) {
				arr[i] = characters[i] - '0';
			}

			int ans = 0;
			int[] mult = new int[N + 1];
			mult[N] = 1;

			for (int m = N - 1; m >= 1; m--) {
				if (N % m != 0) {
					continue;
				}

				for (int q = m + 1; q <= N; q++) {
					if (N % q == 0 && q % m == 0) {
						mult[m] -= mult[q];
					}
				}
			}

			for (int m = 1; m <= N; m++) {
				if (N % m != 0) {
					continue;
				}

				int[] c = new int[m];

				for (int i = 0; i < N / m; i++) {
					for (int j = 0; j < m; j++) {
						c[j] += arr[i * m + j];
					}
				}

				int[][] res = new int[m + 1][K + 1];
				res[0][0] = 1;

				for (int i = 1; i <= m; i++) {
					for (int k = 0; k <= K; k++) {
						if (res[i - 1][k] == 0) {
							continue;
						}

						if (k + c[i - 1] <= K) {
							res[i][k + c[i - 1]] += res[i - 1][k];

							if (res[i][k + c[i - 1]] > mod) {
								res[i][k + c[i - 1]] %= mod;
							}
						}

						if (k + (N / m - c[i - 1]) <= K) {
							res[i][k + (N / m - c[i - 1])] += res[i - 1][k];

							if (res[i][k + (N / m - c[i - 1])] > mod) {
								res[i][k + (N / m - c[i - 1])] %= mod;
							}
						}
					}
				}

				int x = 0;

				for (int k = 0; k <= K; k++) {
					x += res[m][k];

					if (x > mod) {
						x %= mod;
					}
				}

				ans += mult[m] * x;
				ans %= mod;

				if (ans < 0) {
					ans += mod;
				}
			}

			System.out.println(ans);
		}
	}
}
