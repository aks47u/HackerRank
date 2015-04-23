import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class Save_Humanity {
	BufferedReader in;
	PrintWriter out;
	StringTokenizer tokenizer = new StringTokenizer("");

	public static void main(String[] args) {
		new Save_Humanity().run();
	}

	char[] t, p;

	private void solve() throws IOException {
		int tc = nextInt();

		for (int tcIdx = 0; tcIdx < tc; tcIdx++) {
			t = nextToken().toCharArray();
			p = nextToken().toCharArray();
			List<Integer> answerFast = solveFast();
			print(answerFast);
		}
	}

	Random r = new Random(123456789L);

	char[] gen(int len, int letters) {
		char[] s = new char[len];

		for (int i = 0; i < len; i++) {
			s[i] = (char) ('a' + r.nextInt(letters));
		}

		return s;
	}

	private void print(List<Integer> answerNaive) {
		boolean first = true;

		for (int v : answerNaive) {
			if (!first) {
				out.print(' ');
			}

			out.print(v);
			first = false;
		}

		out.println();
	}

	private List<Integer> solveFast() {
		char[] pt = concat(p, t);
		int[] z = calcZ(pt);
		char[] ptRev = concatRev(p, t);
		int[] zRev = calcZ(ptRev);
		List<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i <= t.length - p.length; i++) {
			int commonPrefix = z[p.length + i + 1];
			boolean match = false;

			if (commonPrefix >= p.length) {
				match = true;
			} else if (commonPrefix == p.length - 1) {
				match = true;
			} else {
				int len = p.length - commonPrefix - 1;
				int nI = i + p.length - 1;
				nI = t.length - nI - 1;
				nI += p.length + 1;

				if (zRev[nI] >= len) {
					match = true;
				}
			}

			if (match) {
				result.add(i);
			}
		}

		return result;
	}

	private char[] concat(char[] p, char[] t) {
		char[] result;
		StringBuilder ptBuf = new StringBuilder();
		ptBuf.append(p);
		ptBuf.append('#');
		ptBuf.append(t);
		result = ptBuf.toString().toCharArray();

		return result;
	}

	private char[] concatRev(char[] p, char[] t) {
		char[] result;
		StringBuilder ptBuf = new StringBuilder();
		ptBuf.append(t);
		ptBuf.append('#');
		ptBuf.append(p);
		result = ptBuf.reverse().toString().toCharArray();

		return result;
	}

	private int[] calcZ(char[] s) {
		int[] z = new int[s.length];

		for (int i = 1, l = 0, r = 0; i < s.length; ++i) {
			if (i <= r) {
				z[i] = Math.min(r - i + 1, z[i - l]);
			}

			while (i + z[i] < s.length && s[z[i]] == s[i + z[i]]) {
				z[i]++;
			}

			if (i + z[i] - 1 > r) {
				l = i;
				r = i + z[i] - 1;
			}
		}

		return z;
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					System.out)));
			solve();
			in.close();
			out.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String nextToken() throws IOException {
		while (!tokenizer.hasMoreTokens()) {
			String line = in.readLine();

			if (line == null) {
				return null;
			}

			tokenizer = new StringTokenizer(line);
		}

		return tokenizer.nextToken();
	}

	private int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
}
