import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Triplets {
	private StringTokenizer st;
	private BufferedReader in;

	private static class Fenwik {
		long[] f;

		Fenwik(int n) {
			f = new long[n];
		}

		void add(int i, long v) {
			while (i < f.length) {
				f[i] += v;
				i = (i | (i + 1));
			}
		}

		long get(int i) {
			long ret = 0;

			while (i >= 0) {
				ret += f[i];
				i = (i & (i + 1)) - 1;
			}

			return ret;
		}
	}

	public void solve() throws IOException {
		int n = nextInt();
		int[] a = new int[n];

		for (int i = 0; i < n; ++i) {
			a[i] = nextInt();
		}

		int[] b = a.clone();
		Arrays.sort(b);
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i : b) {
			if (!map.containsKey(i)) {
				map.put(i, map.size());
			}
		}

		Fenwik c1 = new Fenwik(map.size());
		Fenwik c2 = new Fenwik(map.size());
		long[] last1 = new long[map.size()];
		long[] last2 = new long[map.size()];
		Arrays.fill(last1, -1);
		long ans = 0;

		for (int i = 0; i < n; ++i) {
			int x = map.get(a[i]);

			if (last1[x] == -1) {
				c1.add(x, 1);
				c2.add(x, last1[x] = c1.get(x - 1));
				ans += last2[x] = c2.get(x - 1);
			} else {
				c2.add(x, c1.get(x - 1) - last1[x]);
				ans += c2.get(x - 1) - last2[x];
			}
		}

		System.out.println(ans);
	}

	public void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		eat("");
		solve();
	}

	void eat(String s) {
		st = new StringTokenizer(s);
	}

	String next() throws IOException {
		while (!st.hasMoreTokens()) {
			String line = in.readLine();

			if (line == null) {
				return null;
			}

			eat(line);
		}

		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

	public static void main(String[] args) throws IOException {
		new Solution().run();
	}
}
