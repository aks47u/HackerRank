import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Xoring_Ninja {
	private static long MOD = 1000000007;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in), 1024 * 1024 * 2);
		int T = Integer.parseInt(br.readLine());
		long[] pow = new long[100001];
		pow[0] = 1;

		for (int i = 1; i < 100001; i++) {
			pow[i] = (pow[i - 1] << 1) % MOD;
		}

		StringBuilder sb = new StringBuilder();

		while (T-- > 0) {
			int n = Integer.parseInt(br.readLine());
			int[] v = readArrayLine(br.readLine(), n);
			int x = 0;

			for (int a : v) {
				x |= a;
			}

			long result = 0;

			for (int i = 0; i < 32; i++) {
				long val = 1L << i;

				if ((x & (1 << i)) != 0) {
					result += (val * pow[n - 1]) % MOD;
					result %= MOD;
				}
			}

			sb.append(result + "\n");
		}

		System.out.println(sb.toString());
	}

	private static int[] readArrayLine(String line, int n) {
		int[] ret = new int[n];
		int start = 0;
		int end = line.indexOf(' ', start);

		for (int i = 0; i < n; i++) {
			if (end > 0) {
				ret[i] = Integer.parseInt(line.substring(start, end));
			} else {
				ret[i] = Integer.parseInt(line.substring(start));
			}

			start = end + 1;
			end = line.indexOf(' ', start);
		}

		return ret;
	}
}
