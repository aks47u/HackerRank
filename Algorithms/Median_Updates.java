import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Median_Updates {
	static class Foo58 {
		void main() {
			BufferedReader br = null;

			try {
				br = new BufferedReader(new InputStreamReader(System.in));
				int N = Integer.parseInt(br.readLine().trim());

				while (N-- > 0) {
					String[] s = br.readLine().trim().split(" ");
					char ch = s[0].charAt(0);
					int x = Integer.parseInt(s[1].trim());
					String res = null;

					if (ch == 'a') {
						res = add(x);
					} else {
						res = remove(x);
					}

					System.out.println(res);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		static class Key implements Comparable<Key> {
			int x, ind;

			public Key(int x, int ind) {
				this.x = x;
				this.ind = ind;
			}

			public int compareTo(Key b) {
				if (x != b.x) {
					return x < b.x ? -1 : 1;
				}

				return ind - b.ind;
			}

			public String toString() {
				return x + ", " + ind;
			}
		}

		static class Aug implements Augment<Aug> {
			int size;

			public void maintain(Aug a, Aug b) {
				size = 1;

				if (a != null) {
					size += a.size;
				}

				if (b != null) {
					size += b.size;
				}
			}
		}

		TreeMapAug<Key, Object, Aug> tree = new TreeMapAug<Key, Object, Aug>();
		HashMap<Integer, ArrayList<Key>> map = new HashMap<Integer, ArrayList<Key>>();
		int index = 0;

		String median() {
			int size = tree.size();

			if (size % 2 == 1) {
				return "" + select((size + 1) / 2);
			}

			int a = select(size / 2);
			int b = select(size / 2 + 1);
			long val = (long) a + b;

			if (val % 2 == 0) {
				return "" + val / 2;
			} else if (val > 0) {
				return "" + val / 2 + ".5";
			} else {
				return "-" + (-((val + 1) / 2)) + ".5";
			}
		}

		int select(int k) {
			TreeMapAug.Node<Key, Object, Aug> root = tree.getRoot();
			Key key = select(k, root);
			tree.get(key);

			return key.x;
		}

		Key select(int k, TreeMapAug.Node<Key, Object, Aug> root) {
			while (true) {
				int r = 1;

				if (root.left != null) {
					r += root.left.aug.size;
				}

				if (k == r) {
					return root.key;
				} else if (k < r) {
					root = root.left;
				} else {
					k -= r;
					root = root.right;
				}
			}
		}

		String add(int x) {
			int ind = index++;
			Key key = new Key(x, ind);
			tree.put(key, null, new Aug());
			ArrayList<Key> list = map.get(x);

			if (list == null) {
				list = new ArrayList<Key>();
				map.put(x, list);
			}

			list.add(key);

			return median();
		}

		String remove(int x) {
			ArrayList<Key> list = map.get(x);

			if (list == null || list.size() == 0) {
				return "Wrong!";
			}

			Key key = list.remove(list.size() - 1);
			tree.remove(key);

			if (tree.size() == 0) {
				return "Wrong!";
			}

			return median();
		}
	}

	public static void main(String[] args) {
		Foo58 foo = new Foo58();
		foo.main();
	}
}

interface Augment<T> {
	public void maintain(T a, T b);
}

class TreeMapAug<K, V, A> {
	public static class Node<K, V, A> {
		public K key;
		public V data;
		public A aug;
		public Node<K, V, A> left;
		public Node<K, V, A> right;
		public Node<K, V, A> parent;

		public Node(K key, V data, A aug) {
			this.key = key;
			this.data = data;
			this.aug = aug;
		}

		public String toString() {
			String res = "" + key + " aug: " + aug + " parent: ";

			if (parent == null) {
				res += " null";
			} else {
				res += parent.key;
			}

			res += " left: ";

			if (left == null) {
				res += " null";
			} else {
				res += left.key;
			}

			res += " right: ";

			if (right == null) {
				res += " null";
			} else {
				res += right.key;
			}

			return res;
		}
	}

	public Node<K, V, A> root;
	private int size;

	public Node<K, V, A> getRoot() {
		return root;
	}

	@SuppressWarnings("unchecked")
	private static <A> Augment<A> toAugment(A obj) {
		return (Augment<A>) obj;
	}

	@SuppressWarnings("unchecked")
	private static <K> Comparable<K> toComparable(K obj) {
		return (Comparable<K>) obj;
	}

	void splay(Node<K, V, A> x) {
		while (x != null && x != root) {
			if (x.parent == root) {
				if (root.left == x) {
					rightRotate(root);
				} else {
					leftRotate(root);
				}

				return;
			}

			Node<K, V, A> p = x.parent;
			Node<K, V, A> gg = p.parent;

			if (p.left == x && gg.left == p) {
				rightRotate(gg);
				rightRotate(p);
			} else if (p.right == x && gg.right == p) {
				leftRotate(gg);
				leftRotate(p);
			} else if (p.right == x && gg.left == p) {
				leftRotate(p);
				rightRotate(gg);
			} else {
				rightRotate(p);
				leftRotate(gg);
			}
		}
	}

	private Node<K, V, A> find(K key) {
		Node<K, V, A> q = null, p = root;

		if (root == null) {
			return null;
		}

		while (p != null) {
			q = p;
			int res = toComparable(key).compareTo(p.key);

			if (res == 0) {
				return p;
			} else if (res < 0) {
				p = p.left;
			} else {
				p = p.right;
			}
		}

		return q;
	}

	public V get(K key) {
		Node<K, V, A> res = find(key);

		if (res == null || toComparable(res.key).compareTo(key) != 0) {
			return null;
		}

		splay(res);

		return res.data;
	}

	public void put(K key, V data, A aug) {
		Node<K, V, A> res = find(key);
		Node<K, V, A> node = new Node<K, V, A>(key, data, aug);

		if (res == null) {
			root = node;
			size = 1;
			toAugment(node.aug).maintain(null, null);

			return;
		}

		int cmp = toComparable(key).compareTo(res.key);

		if (cmp == 0) {
			res.data = data;
			node = res;
		} else if (cmp < 0) {
			res.left = node;
			node.parent = res;
			size++;
		} else {
			res.right = node;
			node.parent = res;
			size++;
		}

		splay(node);
	}

	public V remove(K key) {
		Node<K, V, A> res = find(key);

		if (res == null) {
			return null;
		}

		int cmp = toComparable(key).compareTo(res.key);

		if (cmp != 0) {
			return null;
		}

		V data = res.data;
		size--;
		splay(res);

		if (root.left == null) {
			root = root.right;

			if (root != null) {
				root.parent = null;
			}
		} else if (root.right == null) {
			root = root.left;

			if (root != null) {
				root.parent = null;
			}
		} else {
			Node<K, V, A> left = root.left, right = root.right;
			root = left;
			left.parent = null;

			while (left.right != null) {
				left = left.right;
			}

			splay(left);
			root.right = right;
			right.parent = root;
			A rootleft = null;

			if (root.left != null) {
				rootleft = root.left.aug;
			}

			if (root.aug != null) {
				toAugment(root.aug).maintain(rootleft, root.right.aug);
			}
		}

		return data;
	}

	public A getRootAug() {
		if (root == null) {
			return null;
		}

		return root.aug;
	}

	public boolean contains(K key) {
		Node<K, V, A> res = find(key);

		if (res == null || toComparable(key).compareTo(res.key) != 0) {
			return false;
		}

		return true;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return size;
	}

	void rightRotate(Node<K, V, A> x) {
		Node<K, V, A> y = x.left;

		if (y.right != null) {
			y.right.parent = x;
		}

		x.left = y.right;
		y.parent = x.parent;

		if (x.parent != null) {
			if (x.parent.left == x) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		} else {
			root = y;
		}

		x.parent = y;
		y.right = x;
		A leftx = null;

		if (x.left != null) {
			leftx = x.left.aug;
		}

		A rightx = null;

		if (x.right != null) {
			rightx = x.right.aug;
		}

		if (x.aug != null) {
			toAugment(x.aug).maintain(leftx, rightx);
		}

		A lefty = null;

		if (y.left != null) {
			lefty = y.left.aug;
		}

		if (y.aug != null) {
			toAugment(y.aug).maintain(lefty, x.aug);
		}
	}

	void leftRotate(Node<K, V, A> x) {
		Node<K, V, A> y = x.right;

		if (y.left != null) {
			y.left.parent = x;
		}

		x.right = y.left;
		y.parent = x.parent;

		if (x.parent != null) {
			if (x.parent.left == x) {
				x.parent.left = y;
			} else {
				x.parent.right = y;
			}
		} else {
			root = y;
		}

		x.parent = y;
		y.left = x;
		A leftx = null;

		if (x.left != null) {
			leftx = x.left.aug;
		}

		A rightx = null;

		if (x.right != null) {
			rightx = x.right.aug;
		}

		if (x.aug != null) {
			toAugment(x.aug).maintain(leftx, rightx);
		}

		A righty = null;

		if (y.right != null) {
			righty = y.right.aug;
		}

		if (y.aug != null) {
			toAugment(y.aug).maintain(x.aug, righty);
		}
	}

	public void inorder() {
		inorder(root);
	}

	private void inorder(Node<K, V, A> root) {
		if (root != null) {
			inorder(root.left);
			System.out.println(root);
			inorder(root.right);
		}
	}

	public void preorder() {
		preorder(root);
	}

	private void preorder(Node<K, V, A> root) {
		if (root != null) {
			System.out.println(root);
			preorder(root.left);
			preorder(root.right);
		}
	}
}
