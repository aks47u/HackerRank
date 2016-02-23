import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class The_crazy_helix {
	private static Reader in;
	private static PrintWriter out;

	private static class Node {
		int id, size;
		Node left;
		Node right;
		Node parent;
		boolean flip;

		public Node(int id) {
			this.id = id;
			this.size = 1;
			left = null;
			right = null;
			parent = null;
		}

		public String toString() {
			return id + " " + size;
		}
	}

	private static void push(Node x) {
		if (!x.flip) {
			return;
		}

		x.flip = false;
		Node t = x.left;
		x.left = x.right;
		x.right = t;

		if (x.left != null) {
			x.left.flip = !x.left.flip;
		}

		if (x.right != null) {
			x.right.flip = !x.right.flip;
		}

		join(x);
	}

	private static boolean isRoot(Node x) {
		return x.parent == null;
	}

	private static void connect(Node ch, Node p, boolean leftChild) {
		if (leftChild) {
			p.left = ch;
		} else {
			p.right = ch;
		}

		join(p);

		if (ch != null) {
			ch.parent = p;
		}
	}

	private static void rotate(Node x) {
		Node p = x.parent;
		Node g = p.parent;
		boolean isRootP = isRoot(p);
		boolean leftChildX = (x == p.left);
		Node next = leftChildX ? x.right : x.left;
		connect(next, p, leftChildX);
		connect(p, x, !leftChildX);

		if (!isRootP) {
			connect(x, g, p == g.left);
		} else {
			x.parent = g;
		}
	}

	private static void splay(Node x) {
		while (!isRoot(x)) {
			Node p = x.parent;
			Node g = p.parent;

			if (!isRoot(p)) {
				push(g);
			}

			push(p);
			push(x);

			if (!isRoot(p)) {
				rotate((x == p.left) == (p == g.left) ? p : x);
			}

			rotate(x);
		}

		push(x);
		root = x;
	}

	private static Node cutLeft(Node x) {
		Node ret = x.left;

		if (ret != null) {
			x.left.parent = null;
			x.left = null;
			join(x);
		}

		return ret;
	}

	private static Node cutRight(Node x) {
		Node ret = x.right;

		if (ret != null) {
			x.right.parent = null;
			x.right = null;
			join(x);
		}

		return ret;
	}

	private static void join(Node x) {
		x.size = (x.left == null ? 0 : x.left.size)
				+ (x.right == null ? 0 : x.right.size) + 1;
	}

	public static void main(String[] args) throws IOException {
		in = new Reader();
		out = new PrintWriter(System.out, true);
		int N = in.nextInt(), Q = in.nextInt();
		initTree(N);

		while (Q-- > 0) {
			int cmd = in.nextInt();

			switch (cmd) {
				case 1: {
					int a = in.nextInt(), b = in.nextInt();
					flip(a, b);
					break;
				}
				case 2: {
					int a = in.nextInt();
					out.printf("element %d is at position %d\n", a, getPosition(a));
					break;
				}
				case 3: {
					int a = in.nextInt();
					out.printf("element at position %d is %d\n", a, getElement(a));
					break;
				}
			}
		}

		out.close();
		System.exit(0);
	}

	private static Node getElementAtPosition(Node start, int a) {
		Node cur = start;

		while (a > 0) {
			push(cur);
			int sz = (cur.left == null ? 0 : cur.left.size);

			if (a <= sz) {
				cur = cur.left;
				continue;
			}

			a -= sz + 1;

			if (a == 0) {
				break;
			}

			cur = cur.right;
		}

		splay(cur);

		return cur;
	}

	private static int getElement(int a) {
		return getElementAtPosition(root, a).id;
	}

	private static int getPosition(int a) {
		splay(nodes[a]);

		return (nodes[a].left == null ? 0 : nodes[a].left.size) + 1;
	}

	private static void flip(int a, int b) {
		if (a == b) {
			return;
		}

		Node right = getElementAtPosition(root, b);
		Node ra = cutRight(right);
		Node left = getElementAtPosition(root, a);
		Node la = cutLeft(left);
		splay(left);
		left.flip = !left.flip;
		push(left);
		connect(ra, left, false);
		splay(right);
		connect(la, right, true);
	}

	private static void initTree(int n) {
		nodes = new Node[n + 1];

		for (int i = 1; i <= n; i++) {
			nodes[i] = new Node(i);
		}

		root = initRec(1, n);
	}

	private static Node initRec(int start, int end) {
		if (start == end) {
			return nodes[start];
		}

		int mid = (start + end) >> 1;
		Node x = nodes[mid];

		if (start <= mid - 1) {
			connect(initRec(start, mid - 1), x, true);
		}

		if (mid + 1 <= end) {
			connect(initRec(mid + 1, end), x, false);
		}

		return x;
	}

	private static Node[] nodes;
	private static Node root;

	private static class Reader {
		final private int BUFFER_SIZE = 1 << 16;
		private DataInputStream din;
		private byte[] buffer;
		private int bufferPointer, bytesRead;

		public Reader() {
			din = new DataInputStream(System.in);
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public Reader(String file_name) throws IOException {
			din = new DataInputStream(new FileInputStream(file_name));
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[1024];
			int cnt = 0, c;

			while ((c = read()) != -1) {
				if (c == '\n') {
					break;
				}

				buf[cnt++] = (byte) c;
			}

			return new String(buf, 0, cnt);
		}

		public int nextInt() throws IOException {
			int ret = 0;
			byte c = read();

			while (c <= ' ') {
				c = read();
			}

			boolean neg = (c == '-');

			if (neg) {
				c = read();
			}

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (neg) {
				return -ret;
			}

			return ret;
		}

		public long nextLong() throws IOException {
			long ret = 0;
			byte c = read();

			while (c <= ' ') {
				c = read();
			}

			boolean neg = (c == '-');

			if (neg) {
				c = read();
			}

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (neg) {
				return -ret;
			}

			return ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = read();

			while (c <= ' ') {
				c = read();
			}

			boolean neg = (c == '-');

			if (neg) {
				c = read();
			}

			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');

			if (c == '.') {
				while ((c = read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}

			if (neg) {
				return -ret;
			}

			return ret;
		}

		private void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);

			if (bytesRead == -1) {
				buffer[0] = -1;
			}
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead) {
				fillBuffer();
			}

			return buffer[bufferPointer++];
		}

		public void close() throws IOException {
			if (din == null) {
				return;
			}

			din.close();
		}
	}
}
