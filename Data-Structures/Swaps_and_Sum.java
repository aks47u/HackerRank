import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Random;

public class Swaps_and_Sum {
	static int size(Node x) {
		return x == null ? 0 : x.size;
	}

	static long sum(Node x) {
		return x == null ? 0 : x.sum;
	}

	static class Node {
		final static Random rnd = new Random(42);
		Node l, r;
		int depth;
		int size;
		long val, sum;

		Node(long val) {
			depth = rnd.nextInt();
			this.val = val;
			rehash();
		}

		void rehash() {
			size = 1;
			sum = val;

			if (l != null) {
				size += l.size;
				sum += l.sum;
			}

			if (r != null) {
				size += r.size;
				sum += r.sum;
			}
		}
	}

	static Node[] split(Node x, int left) {
		if (x == null) {
			return new Node[2];
		}

		Node[] p;

		if (left <= size(x.l)) {
			p = split(x.l, left);
			x.l = p[1];
			p[1] = x;
		} else {
			p = split(x.r, left - size(x.l) - 1);
			x.r = p[0];
			p[0] = x;
		}

		x.rehash();

		return p;
	}

	static Node[] splitAt(Node x, int... at) {
		Node[] ret = new Node[at.length + 1];

		for (int i = at.length - 1; i >= 0; --i) {
			Node[] tmp = split(x, at[i]);
			ret[i + 1] = tmp[1];
			x = tmp[0];
		}

		ret[0] = x;

		return ret;
	}

	static Node merge(Node l, Node r) {
		if (l == null) {
			return r;
		}

		if (r == null) {
			return l;
		}

		if (l.depth > r.depth) {
			r.l = merge(l, r.l);
			r.rehash();

			return r;
		} else {
			l.r = merge(l.r, r);
			l.rehash();

			return l;
		}
	}

	static Node mergeAll(Node... nodes) {
		Node ret = null;

		for (Node node : nodes) {
			ret = merge(ret, node);
		}

		return ret;
	}

	public static void solve(Input in, PrintWriter out) throws IOException {
		int n = in.nextInt();
		int q = in.nextInt();
		Node even = null, odd = null;

		for (int i = 0; i < n; ++i) {
			if (i % 2 == 0) {
				even = merge(even, new Node(in.nextLong()));
			} else {
				odd = merge(odd, new Node(in.nextLong()));
			}
		}

		for (int it = 0; it < q; ++it) {
			int type = in.nextInt();
			int l = in.nextInt() - 1;
			int r = in.nextInt();
			Node[] splitEven = splitAt(even, (l + 1) / 2, (r + 1) / 2);
			Node[] splitOdd = splitAt(odd, l / 2, r / 2);

			if (type == 1) {
				if (splitEven[1].size != (r - l) / 2
						|| splitOdd[1].size != (r - l) / 2) {
					throw new AssertionError();
				}

				even = mergeAll(splitEven[0], splitOdd[1], splitEven[2]);
				odd = mergeAll(splitOdd[0], splitEven[1], splitOdd[2]);
			} else {
				out.println(sum(splitEven[1]) + sum(splitOdd[1]));
				even = mergeAll(splitEven);
				odd = mergeAll(splitOdd);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter(System.out);
		solve(new Input(new BufferedReader(new InputStreamReader(System.in))),
				out);
		out.close();
	}

	static class Input {
		BufferedReader in;
		StringBuilder sb = new StringBuilder();

		public Input(BufferedReader in) {
			this.in = in;
		}

		public Input(String s) {
			this.in = new BufferedReader(new StringReader(s));
		}

		public String next() throws IOException {
			sb.setLength(0);

			while (true) {
				int c = in.read();

				if (c == -1) {
					return null;
				}

				if (" \n\r\t".indexOf(c) == -1) {
					sb.append((char) c);
					break;
				}
			}

			while (true) {
				int c = in.read();

				if (c == -1 || " \n\r\t".indexOf(c) != -1) {
					break;
				}

				sb.append((char) c);
			}

			return sb.toString();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
	}
}
