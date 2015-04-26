import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;

public class Two_Two {
	static class Node {
		Node[] edges = new Node[10];
		Node slink;
		int weight;
	}

	public static void solve(Input in, PrintWriter out) throws IOException {
		Node root = new Node();

		for (int pow = 0; pow <= 800; ++pow) {
			String s = BigInteger.ONE.shiftLeft(pow).toString();
			Node cur = root;

			for (char c : s.toCharArray()) {
				if (cur.edges[c - '0'] == null) {
					cur.edges[c - '0'] = new Node();
				}

				cur = cur.edges[c - '0'];
			}

			cur.weight++;
		}

		ArrayList<Node> q = new ArrayList<Node>();
		q.add(root);

		for (int it = 0; it < q.size(); ++it) {
			Node n = q.get(it);

			for (int i = 0; i < 10; ++i) {
				Node n1 = n.edges[i];

				if (n1 == null) {
					continue;
				}

				q.add(n1);
				n1.slink = n.slink;

				while (n1.slink != null && n1.slink.edges[i] == null) {
					n1.slink = n1.slink.slink;
				}

				if (n1.slink == null) {
					n1.slink = root;
				} else {
					n1.slink = n1.slink.edges[i];
				}
			}
		}

		for (Node n : q) {
			if (n.slink != null) {
				n.weight += n.slink.weight;
			}
		}

		for (Node n : q) {
			for (int i = 0; i < 10; ++i) {
				if (n.edges[i] == null) {
					if (n.slink == null) {
						n.edges[i] = root;
					} else {
						n.edges[i] = n.slink.edges[i];
					}
				}
			}
		}

		int tests = in.nextInt();

		for (int test = 0; test < tests; ++test) {
			String s = in.next();
			int ans = 0;
			Node cur = root;

			for (char c : s.toCharArray()) {
				cur = cur.edges[c - '0'];
				ans += cur.weight;
			}

			out.println(ans);
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
	}
}
