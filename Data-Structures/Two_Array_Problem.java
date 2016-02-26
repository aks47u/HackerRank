import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.StringTokenizer;

public class Two_Array_Problem {
	private static int MAX = 1111111;

	private static class Node {
		Node child[] = new Node[2], parent;
		boolean reverse;
		int value, size;
	}

	private static class SplayTree {
		public static final Node nil;

		static {
			nil = new Node();
			nil.child[0] = nil.child[1] = nil.parent = nil;
			nil.reverse = false;
			nil.value = nil.size = 0;
		}

		private Node root = nil;

		public SplayTree(int a[]) {
			root = buildTree(a, 0, a.length - 1);
		}

		private Node buildTree(int a[], int l, int r) {
			if (l > r) {
				return nil;
			}

			int mid = (l + r) >> 1;
			Node x = new Node();
			x.value = a[mid];
			x.parent = nil;
			x.reverse = false;
			setLink(x, buildTree(a, l, mid - 1), 0);
			setLink(x, buildTree(a, mid + 1, r), 1);
			update(x);

			return x;
		}

		private void update(Node x) {
			x.size = x.child[0].size + x.child[1].size + 1;
		}

		private void pushDown(Node x) {
			if (x != nil) {
				if (x.reverse) {
					Node tmp = x.child[0];
					x.child[0] = x.child[1];
					x.child[1] = tmp;
					x.reverse = false;
					x.child[0].reverse = !x.child[0].reverse;
					x.child[1].reverse = !x.child[1].reverse;
				}
			}
		}

		private static void setLink(Node parent, Node child, int d) {
			parent.child[d] = child;
			child.parent = parent;
		}

		private int getDir(Node parent, Node child) {
			return parent.child[0] == child ? 0 : 1;
		}

		private void rotate(Node x, int d) {
			Node y = x.child[d], z = x.parent;
			setLink(x, y.child[d ^ 1], d);
			setLink(y, x, d ^ 1);
			setLink(z, y, getDir(z, x));
			update(x);
			update(y);
		}

		private void splay(Node x) {
			while (x.parent != nil) {
				Node y = x.parent, z = y.parent;
				int dy = getDir(y, x), dz = getDir(z, y);

				if (z == nil) {
					rotate(y, dy);
				} else if (dy == dz) {
					rotate(z, dz);
					rotate(y, dy);
				} else {
					rotate(y, dy);
					rotate(z, dz);
				}
			}

			root = x;
		}

		public void reverse(int right) {
			if (right <= 1) {
				return;
			}

			if (right == root.size) {
				root.reverse = !root.reverse;
			} else {
				accessByPos(right + 1);
				root.child[0].reverse = !root.child[0].reverse;
			}
		}

		public void reverse(int left, int right) {
			if (left >= right) {
				return;
			}

			reverse(right);
			reverse(right - left + 1);
			reverse(right);
		}

		public void swap(int left1, int right1, int left2, int right2) {
			reverse(left1, right1);
			reverse(left2, right2);
			reverse(right1 + 1, left2 - 1);
			reverse(left1, right2);
		}

		private void dfs(Node root, int[] buffer) {
			if (root == nil) {
				return;
			}

			pushDown(root);
			dfs(root.child[0], buffer);
			buffer[++buffer[0]] = root.value;
			dfs(root.child[1], buffer);
		}

		public void collect(int left, int right, int[] buffer) {
			buffer[0] = 0;

			if (left == 1 && right == root.size) {
				dfs(root, buffer);
			} else {
				reverse(right);
				Node x = accessByPos(right - left + 2).child[0];
				dfs(x, buffer);
				reverse(right);
			}
		}

		public Node accessByPos(int pos) {
			assert(pos >= 1 && pos <= root.size);
			Node x = root;
			pushDown(x);

			while (x.child[0].size + 1 != pos) {
				if (pos < x.child[0].size + 1) {
					x = x.child[0];
				} else {
					pos -= x.child[0].size + 1;
					x = x.child[1];
				}

				pushDown(x);
			}

			splay(x);

			return x;
		}

		public void dfs(Node root) {
			if (root == nil) {
				return;
			}

			pushDown(root);
			dfs(root.child[0]);
			System.out.print(root.value + " ");
			dfs(root.child[1]);
		}
	}

	private static int n, m;
	private static int[] a;
	private static int[] buffer2 = new int[MAX];
	private static int[] buffer1 = new int[MAX];
	private static Random rand = new Random();
	private static double[][] p = new double[MAX][2];
	private static final double eps = 1E-8;

	private static double dis(double[] a, double[] b) {
		return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
	}

	private static void circumcenter(double[] a, double[] b, double[] c, double[] res) {
		double a1 = b[0] - a[0], b1 = b[1] - a[1], c1 = (a1 * a1 + b1 * b1) / 2;
		double a2 = c[0] - a[0], b2 = c[1] - a[1], c2 = (a2 * a2 + b2 * b2) / 2;
		double d = a1 * b2 - a2 * b1;
		res[0] = a[0] + (c1 * b2 - c2 * b1) / d;
		res[1] = a[1] + (a1 * c2 - a2 * c1) / d;
	}

	private static double minDisk(int[] x, int[] y) {
		for (int n = x[0]; n >= 1; n--) {
			int i = rand.nextInt(n) + 1;
			int tmp = x[i];
			x[i] = x[n];
			x[n] = tmp;
			tmp = y[i];
			y[i] = y[n];
			y[n] = tmp;
		}

		for (int i = 1; i <= x[0]; i++) {
			p[i][0] = x[i];
			p[i][1] = y[i];
		}

		double[] c = p[1].clone();
		double r = 0;

		for (int i = 2; i <= x[0]; i++) {
			if (dis(p[i], c) > r + eps) {
				c[0] = p[i][0];
				c[1] = p[i][1];
				r = 0;

				for (int j = 1; j < i; j++) {
					if (dis(p[j], c) > r + eps) {
						c[0] = (p[i][0] + p[j][0]) / 2;
						c[1] = (p[i][1] + p[j][1]) / 2;
						r = dis(p[j], c);

						for (int k = 1; k < j; k++) {
							if (dis(p[k], c) > r + eps) {
								circumcenter(p[i], p[j], p[k], c);
								r = dis(p[i], c);
							}
						}
					}
				}
			}
		}

		return r;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter cout = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer token = new StringTokenizer(cin.readLine());
		n = Integer.parseInt(token.nextToken());
		m = Integer.parseInt(token.nextToken());
		a = new int[2 * n];
		int index = 0;
		token = new StringTokenizer(cin.readLine());

		for (int i = 0; i < n; i++) {
			a[index++] = Integer.parseInt(token.nextToken());
		}

		token = new StringTokenizer(cin.readLine());

		for (int i = 0; i < n; i++) {
			a[index++] = Integer.parseInt(token.nextToken());
		}

		SplayTree tree = new SplayTree(a);

		for (; m > 0; m--) {
			token = new StringTokenizer(cin.readLine());
			int op = Integer.parseInt(token.nextToken());

			if (op == 1) {
				int id = Integer.parseInt(token.nextToken());
				int left = Integer.parseInt(token.nextToken());
				int right = Integer.parseInt(token.nextToken());

				if (id == 1) {
					left += n;
					right += n;
				}

				tree.reverse(left, right);
			} else if (op == 2) {
				int id = Integer.parseInt(token.nextToken());
				int left1 = Integer.parseInt(token.nextToken());
				int right1 = Integer.parseInt(token.nextToken());
				int left2 = Integer.parseInt(token.nextToken());
				int right2 = Integer.parseInt(token.nextToken());

				if (id == 1) {
					left1 += n;
					right1 += n;
					left2 += n;
					right2 += n;
				}

				tree.swap(left1, right1, left2, right2);
			} else if (op == 3) {
				int left = Integer.parseInt(token.nextToken());
				int right = Integer.parseInt(token.nextToken());
				tree.swap(left, right, left + n, right + n);
			} else if (op == 4) {
				int left = Integer.parseInt(token.nextToken());
				int right = Integer.parseInt(token.nextToken());
				tree.collect(left, right, buffer1);
				tree.collect(left + n, right + n, buffer2);
				double r = minDisk(buffer1, buffer2);
				cout.write(String.format("%.2f%n", r));
			}
		}

		cin.close();
		cout.close();
	}

	private static class Pair<U extends Comparable<U>, V extends Comparable<V>> implements Comparable<Pair<U, V>> {
		final U _1;
		final V _2;

		private Pair(U key, V val) {
			this._1 = key;
			this._2 = val;
		}

		public static <U extends Comparable<U>, V extends Comparable<V>> Pair<U, V> instanceOf(U _1, V _2) {
			return new Pair<U, V>(_1, _2);
		}

		@Override
		public String toString() {
			return _1 + " " + _2;
		}

		@Override
		public int hashCode() {
			int res = 17;
			res = res * 31 + _1.hashCode();
			res = res * 31 + _2.hashCode();

			return res;
		}

		@Override
		public int compareTo(Pair<U, V> that) {
			int res = this._1.compareTo(that._1);

			if (res < 0 || res > 0) {
				return res;
			} else {
				return this._2.compareTo(that._2);
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof Pair)) {
				return false;
			}

			Pair<?, ?> that = (Pair<?, ?>) obj;

			return _1.equals(that._1) && _2.equals(that._2);
		}
	}
}
