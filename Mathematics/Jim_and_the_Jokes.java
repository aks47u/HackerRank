import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Jim_and_the_Jokes {
	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter out;

	public static void main(String[] args) {
		new Jim_and_the_Jokes().run();
	}

	public void solve() throws IOException {
		int N = nextInt();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < N; i++) {
			int M = nextInt();
			int D = nextInt();
			boolean OK = true;
			int value = 0;
			int index = 0;

			while (D > 0) {
				int c = D % 10;

				if (c >= M) {
					OK = false;
				}

				value += Math.pow(M, index) * c;
				D /= 10;
				index++;
			}

			if (!OK) {
				continue;
			}

			if (!map.containsKey(value)) {
				map.put(value, 1);
			} else {
				map.put(value, map.get(value) + 1);
			}
		}

		long ans = 0;

		for (int key : map.keySet()) {
			long size = map.get(key);
			ans += (size * (size - 1)) / 2;
		}

		out.println(ans);
	}

	public void run() {
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));
			tokenizer = null;
			out = new PrintWriter(System.out);
			solve();
			reader.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	String nextToken() throws IOException {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			tokenizer = new StringTokenizer(reader.readLine());
		}

		return tokenizer.nextToken();
	}
}
