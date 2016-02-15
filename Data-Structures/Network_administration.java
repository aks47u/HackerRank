import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

public class Network_administration {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		NetworkAdministration solver = new NetworkAdministration();
		solver.solve(1, in, out);
		out.close();
	}
}

class NetworkAdministration {
	public void solve(int testNumber, InputReader in, PrintWriter out) {
		int S = in.nextInt();
		int L = in.nextInt();
		int A = in.nextInt();
		int T = in.nextInt();
		LinkCutTree.Node[][] lct = new LinkCutTree.Node[A][S];
		byte[][] deg = new byte[A][S];
		Info[] infos = new Info[L];

		for (int i = 0; i < L; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			int a = in.nextInt() - 1;
			LinkCutTree.Node edgeNode = new LinkCutTree.Node(0);
			
			if (lct[a][x] == null) {
				lct[a][x] = new LinkCutTree.Node(0);
			}

			if (lct[a][y] == null) {
				lct[a][y] = new LinkCutTree.Node(0);
			}

			LinkCutTree.link(lct[a][x], edgeNode);
			LinkCutTree.link(lct[a][y], edgeNode);
			++deg[a][x];
			++deg[a][y];
			infos[i] = new Info(a, edgeNode, edge(x, y));
		}

		Arrays.sort(infos, new Comparator<Info>() {
			public int compare(Info a, Info b) {
				return Long.compare(a.edge, b.edge);
			}
		});

		long[] edges = new long[L];

		for (int i = 0; i < L; i++) {
			edges[i] = infos[i].edge;
		}

		for (int i = 0; i < T; i++) {
			int t = in.nextInt();
			int a = in.nextInt() - 1;
			int b = in.nextInt() - 1;

			if (t == 1) {
				int admin = in.nextInt() - 1;
				long e = edge(a, b);
				int index = Arrays.binarySearch(edges, e);
				Info info = index < 0 ? null : infos[index];
				Integer prevAdmin = info == null ? null : info.admin;

				if (prevAdmin == null) {
					out.println("Wrong link");
				} else if (prevAdmin == admin) {
					out.println("Already controlled link");
				} else if (deg[admin][a] == 2 || deg[admin][b] == 2) {
					out.println("Server overload");
				} else if (lct[admin][a] != null && lct[admin][b] != null
						&& LinkCutTree.connected(lct[admin][a], lct[admin][b])) {
					out.println("Network redundancy");
				} else {
					out.println("Assignment done");
					--deg[prevAdmin][a];
					--deg[prevAdmin][b];
					++deg[admin][a];
					++deg[admin][b];
					LinkCutTree.Node edgeNode = info.edgeNode;
					LinkCutTree.cut(lct[prevAdmin][a], edgeNode);
					LinkCutTree.cut(lct[prevAdmin][b], edgeNode);

					if (lct[admin][a] == null) {
						lct[admin][a] = new LinkCutTree.Node(0);
					}

					if (lct[admin][b] == null) {
						lct[admin][b] = new LinkCutTree.Node(0);
					}

					LinkCutTree.link(lct[admin][a], edgeNode);
					LinkCutTree.link(lct[admin][b], edgeNode);
					info.admin = admin;
				}
			} else if (t == 2) {
				int x = in.nextInt();
				LinkCutTree.Node edgeNode = infos[Arrays.binarySearch(edges,
						edge(a, b))].edgeNode;
				LinkCutTree.modify(edgeNode, edgeNode, x);
			} else {
				int admin = in.nextInt() - 1;

				if (lct[admin][a] == null || lct[admin][b] == null
						|| !LinkCutTree.connected(lct[admin][a], lct[admin][b])) {
					out.println("No connection");
				} else {
					int res = LinkCutTree.query(lct[admin][a], lct[admin][b]);
					out.println(res + " security devices placed");
				}
			}
		}
	}

	static long edge(int u, int v) {
		return ((long) Math.min(u, v) << 32) + Math.max(u, v);
	}

	static class Info {
		int admin;
		LinkCutTree.Node edgeNode;
		long edge;

		public Info(int admin, LinkCutTree.Node edgeNode, long edge) {
			this.admin = admin;
			this.edgeNode = edgeNode;
			this.edge = edge;
		}
	}

	static class LinkCutTree {
		static int queryOperation(int leftValue, int rightValue) {
			return leftValue + rightValue;
		}

		static int getNeutralValue() {
			return 0;
		}

		public static class Node {
			int nodeValue;
			int subTreeValue;
			int size;
			boolean revert;
			Node left;
			Node right;
			Node parent;

			Node(int value) {
				nodeValue = value;
				subTreeValue = value;
				size = 1;
			}

			boolean isRoot() {
				return parent == null
						|| (parent.left != this && parent.right != this);
			}

			void push() {
				if (revert) {
					revert = false;
					Node t = left;
					left = right;
					right = t;

					if (left != null) {
						left.revert = !left.revert;
					}

					if (right != null) {
						right.revert = !right.revert;
					}
				}
			}

			void update() {
				subTreeValue = queryOperation(
						queryOperation(getSubTreeValue(left), nodeValue),
						getSubTreeValue(right));
				size = 1 + getSize(left) + getSize(right);
			}
		}

		static int getSize(Node root) {
			return root == null ? 0 : root.size;
		}

		static int getSubTreeValue(Node root) {
			return root == null ? getNeutralValue() : root.subTreeValue;
		}

		static void connect(Node ch, Node p, Boolean isLeftChild) {
			if (ch != null) {
				ch.parent = p;
			}

			if (isLeftChild != null) {
				if (isLeftChild) {
					p.left = ch;
				} else {
					p.right = ch;
				}
			}
		}

		static void rotate(Node x) {
			Node p = x.parent;
			Node g = p.parent;
			boolean isRootP = p.isRoot();
			boolean leftChildX = (x == p.left);
			connect(leftChildX ? x.right : x.left, p, leftChildX);
			connect(p, x, !leftChildX);
			connect(x, g, isRootP ? null : p == g.left);
			p.update();
		}

		static void splay(Node x) {
			while (!x.isRoot()) {
				Node p = x.parent;
				Node g = p.parent;

				if (!p.isRoot()) {
					g.push();
				}

				p.push();
				x.push();

				if (!p.isRoot()) {
					rotate((x == p.left) == (p == g.left) ? p : x);
				}

				rotate(x);
			}

			x.push();
			x.update();
		}

		static Node expose(Node x) {
			Node last = null;

			for (Node y = x; y != null; y = y.parent) {
				splay(y);
				y.left = last;
				last = y;
			}

			splay(x);

			return last;
		}

		public static void makeRoot(Node x) {
			expose(x);
			x.revert = !x.revert;
		}

		public static boolean connected(Node x, Node y) {
			if (x == y) {
				return true;
			}

			expose(x);
			expose(y);

			return x.parent != null;
		}

		public static void link(Node x, Node y) {
			makeRoot(x);
			x.parent = y;
		}

		public static void cut(Node x, Node y) {
			makeRoot(x);
			expose(y);
			y.right.parent = null;
			y.right = null;
		}

		public static int query(Node from, Node to) {
			makeRoot(from);
			expose(to);

			return getSubTreeValue(to);
		}

		public static void modify(Node from, Node to, int delta) {
			makeRoot(from);
			expose(to);
			to.nodeValue = delta;
		}
	}
}

class InputReader {
	final InputStream is;
	final byte[] buf = new byte[1024];
	int pos;
	int size;

	public InputReader(InputStream is) {
		this.is = is;
	}

	public int nextInt() {
		int c = read();

		while (isWhitespace(c)) {
			c = read();
		}

		int sign = 1;

		if (c == '-') {
			sign = -1;
			c = read();
		}

		int res = 0;

		do {
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}

			res = res * 10 + c - '0';
			c = read();
		} while (!isWhitespace(c));

		return res * sign;
	}

	int read() {
		if (size == -1) {
			throw new InputMismatchException();
		}

		if (pos >= size) {
			pos = 0;

			try {
				size = is.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}

			if (size <= 0) {
				return -1;
			}
		}

		return buf[pos++] & 255;
	}

	static boolean isWhitespace(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}
}
