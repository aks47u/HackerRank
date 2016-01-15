import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

public class String_Function_Calculation {
	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter(System.out);
		solve(new Input(new BufferedReader(new InputStreamReader(System.in))),
				out);
		out.close();
	}

	private static void solve(Input in, PrintWriter out) throws IOException {
		String s = in.next();
		SuffixAutomata a = new SuffixAutomata(s);
		out.println(a.root.dp());
	}

	private static class SuffixAutomata {
		static class Vertex {
			Vertex suffixLink = null;
			Vertex[] edges;
			int log = 0;
			int terminals;
			boolean visited;

			public Vertex(Vertex o, int log) {
				edges = o.edges.clone();
				this.log = log;
			}

			public Vertex(int log) {
				edges = new Vertex[26];
				this.log = log;
			}

			long dp() {
				if (visited) {
					return 0;
				}

				visited = true;
				long r = 0;

				for (Vertex v : edges) {
					if (v != null) {
						r = Math.max(r, v.dp());
						terminals += v.terminals;
					}
				}

				return Math.max(r, 1L * log * terminals);
			}
		}

		Vertex root, last;

		public SuffixAutomata(String str) {
			last = root = new Vertex(0);

			for (int i = 0; i < str.length(); i++) {
				addChar(str.charAt(i));
			}

			addTerm();
		}

		private void addChar(char c) {
			Vertex cur = last;
			last = new Vertex(cur.log + 1);

			while (cur != null && cur.edges[c - 'a'] == null) {
				cur.edges[c - 'a'] = last;
				cur = cur.suffixLink;
			}

			if (cur != null) {
				Vertex q = cur.edges[c - 'a'];

				if (q.log == cur.log + 1) {
					last.suffixLink = q;
				} else {
					Vertex r = new Vertex(q, cur.log + 1);
					r.suffixLink = q.suffixLink;
					q.suffixLink = r;
					last.suffixLink = r;

					while (cur != null) {
						if (cur.edges[c - 'a'] == q) {
							cur.edges[c - 'a'] = r;
						} else {
							break;
						}

						cur = cur.suffixLink;
					}
				}
			} else {
				last.suffixLink = root;
			}
		}

		private void addTerm() {
			Vertex cur = last;

			while (cur != null) {
				cur.terminals++;
				cur = cur.suffixLink;
			}
		}
	}

	private static class Input {
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
	}
}
