import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class String_Modification {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Task solver = new Task();
		solver.solve(1, in, out);
		out.close();
	}
}

class compute {
	static final long MOD = 1000003;
	static long[] Fact, rFact;

	public static void init() {
		Fact = new long[(int) MOD];
		rFact = new long[(int) MOD];
		Fact[0] = rFact[0] = 1;

		for (long i = 1; i < MOD; i++) {
			Fact[((int) i)] = (i * Fact[((int) (i - 1))]) % MOD;
			rFact[((int) i)] = powmod(Fact[((int) i)], MOD - 2);
		}
	}

	public static long ways(long K, long W, long r) {
		return lucas(W - K * (r - 1), r);
	}

	private static long nCr(long n, long k) {
		if (n < 0 || k < 0 || k > n) {
			return 0;
		}

		return (((Fact[(int) n] * rFact[(int) k]) % MOD) * rFact[(int) (n - k)])
				% MOD;
	}

	public static long lucas(long N, long K) {
		if (N < MOD) {
			return nCr(N, K);
		}

		return (nCr(N / MOD, K / MOD) * nCr(N % MOD, K % MOD)) % MOD;
	}

	private static long powmod(long b, long p) {
		long ret = 1, a = b % MOD;

		while (p > 0) {
			if (p % 2 == 1) {
				ret = (ret * a) % MOD;
			}

			a = (a * a) % MOD;
			p >>= 1;
		}

		return ret;
	}
}

class Task {
	final long mod = 1000003;
	int[] howMany;
	int n, k;
	String s;
	int[][] dp;

	int call(int pos, int taken) {
		if (pos >= 26) {
			return 1;
		}

		if (dp[pos][taken] != -1) {
			return dp[pos][taken];
		}

		long ret = call(pos + 1, taken);

		for (int i = 1; i <= howMany[pos]; i++) {
			if (i + taken > n) {
				break;
			}

			long tmp = compute.ways(k, n - taken, i) * call(pos + 1, taken + i);
			tmp %= mod;
			ret += tmp;

			if (ret >= mod) {
				ret -= mod;
			}
		}

		return dp[pos][taken] = (int) ret;
	}

	public void solve(int testNumber, InputReader in, PrintWriter out) {
		compute.init();
		n = in.nextInt();
		k = in.nextInt();
		s = in.next();
		howMany = new int[26];

		for (int i = 0; i < s.length(); i++) {
			howMany[s.charAt(i) - 'A']++;
		}

		dp = new int[26][s.length() + 2];

		for (int i = 0; i < 26; i++) {
			for (int j = 0; j <= s.length() + 1; j++) {
				dp[i][j] = -1;
			}
		}

		out.println(call(0, 0));
	}
}

class InputReader {
	public BufferedReader reader;
	public StringTokenizer tokenizer;

	public InputReader(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream), 32768);
		tokenizer = null;
	}

	public String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return tokenizer.nextToken();
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}
}
