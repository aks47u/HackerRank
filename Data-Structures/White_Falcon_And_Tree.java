import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class White_Falcon_And_Tree {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		WhiteFalconAndTree solver = new WhiteFalconAndTree();
		solver.solve(1, in, out);
		out.close();
	}
}

class WhiteFalconAndTree {
	static final int MOD = 1000000007;

	public void solve(int testNumber, InputReader in, PrintWriter out) {
		int N = in.nextInt();
		int[] A = new int[N];
		int[] B = new int[N];

		for (int i = 0; i < N; i++) {
			A[i] = in.nextInt();
			B[i] = in.nextInt();
		}

		@SuppressWarnings("unchecked")
		List<Integer>[] tree = new List[N];

		for (int i = 0; i < N; i++) {
			tree[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			int x = in.nextInt() - 1;
			int y = in.nextInt() - 1;
			tree[x].add(y);
			tree[y].add(x);
		}

		HeavyLightDecomposition hldLeft = new HeavyLightDecomposition(tree,
				true);
		HeavyLightDecomposition hldRight = new HeavyLightDecomposition(tree,
				false);

		for (int i = 0; i < N; i++) {
			hldLeft.modify(i, i, ((long) A[i] << 32) + B[i]);
			hldRight.modify(i, i, ((long) A[i] << 32) + B[i]);
		}

		int Q = in.nextInt();

		for (int i = 0; i < Q; i++) {
			int cmd = in.nextInt();

			switch (cmd) {
				case 1: {
					int u = in.nextInt() - 1;
					int v = in.nextInt() - 1;
					long a = in.nextInt();
					long b = in.nextInt();
					hldLeft.modify(u, v, (a << 32) + b);
					hldRight.modify(u, v, (a << 32) + b);
					break;
				}
				case 2: {
					int v = in.nextInt() - 1;
					int u = in.nextInt() - 1;
					int x = in.nextInt();
					long f = query(hldLeft, hldRight, u, v);
					long a = f >>> 32;
					long b = f & 0xFFFFFFFFL;
					out.println((a * x + b) % MOD);
					break;
				}
			}
		}
	}

	static class HeavyLightDecomposition {
		static long modifyOperation(long x, long y) {
			return y;
		}

		static long queryOperation(long leftValue, long rightValue) {
			long a1 = leftValue >>> 32;
		long b1 = leftValue & 0xFFFFFFFFL;
		long a2 = rightValue >>> 32;
			long b2 = rightValue & 0xFFFFFFFFL;
			long a = (int) (a1 * a2 % MOD);
			long b = (int) ((a1 * b2 + b1) % MOD);

			return (a << 32) + b;
		}

		static int pow(int x, int n) {
			int res = 1;

			for (long p = x; n > 0; n >>= 1, p = (p * p) % MOD) {
				if ((n & 1) != 0) {
					res = (int) (res * p % MOD);
				}
			}

			return res;
		}

		static long deltaEffectOnSegment(long delta, int segmentLength) {
			if (delta == getNeutralDelta()) {
				return getNeutralDelta();
			}

			int a = (int) (delta >>> 32);
			int b = (int) (delta & 0xFFFFFFFFL);
			int A = pow(a, segmentLength);
			int B;

			if (a == 1) {
				B = (int) ((long) b * segmentLength % MOD);
			} else {
				B = (int) ((long) ((pow(a, segmentLength) - 1 + MOD) % MOD)
						* pow((a - 1 + MOD) % MOD, MOD - 2) % MOD * b % MOD);
			}

			return ((long) A << 32) + B;
		}

		static long getNeutralDelta() {
			return Long.MAX_VALUE;
		}

		static long getNeutralValue() {
			return 1L << 32;
		}

		static long joinValueWithDelta(long value, long delta) {
			if (delta == getNeutralDelta()) {
				return value;
			}

			return modifyOperation(value, delta);
		}

		static long joinDeltas(long delta1, long delta2) {
			if (delta1 == getNeutralDelta()) {
				return delta2;
			}

			if (delta2 == getNeutralDelta()) {
				return delta1;
			}

			return modifyOperation(delta1, delta2);
		}

		long[][] value, delta;
		int[][] len;
		List<Integer>[] tree;
		boolean leftOrder;
		int[] size, parent, tin, tout;
		int time;
		int[] path, pathSize, pathPos, pathRoot;
		int[] firstPath, lastPath;
		int pathCount;

		public HeavyLightDecomposition(List<Integer>[] tree, boolean leftOrder) {
			this.tree = tree;
			int n = tree.length;
			this.leftOrder = leftOrder;
			size = new int[n];
			parent = new int[n];
			tin = new int[n];
			tout = new int[n];
			calcSizeParentTinTout(0, -1);
			path = new int[n];
			pathSize = new int[n];
			pathPos = new int[n];
			pathRoot = new int[n];
			firstPath = new int[n];
			lastPath = new int[n];
			buildPaths(0, newPath(0));
			value = new long[pathCount][];
			delta = new long[pathCount][];
			len = new int[pathCount][];

			for (int i = 0; i < pathCount; i++) {
				int m = pathSize[i];
				value[i] = new long[2 * m];
				delta[i] = new long[2 * m];
				Arrays.fill(delta[i], getNeutralDelta());
				len[i] = new int[2 * m];
				Arrays.fill(len[i], m, 2 * m, 1);

				for (int j = 2 * m - 1; j > 1; j -= 2) {
					len[i][j >> 1] = len[i][j] + len[i][j ^ 1];
				}
			}
		}

		void calcSizeParentTinTout(int u, int p) {
			tin[u] = time++;
			parent[u] = p;
			size[u] = 1;

			for (int v : tree[u])
				if (v != p) {
					calcSizeParentTinTout(v, u);
					size[u] += size[v];
				}

			tout[u] = time++;
		}

		int newPath(int u) {
			pathRoot[pathCount] = u;

			return pathCount++;
		}

		void buildPaths(int u, int path) {
			firstPath[u] = pathCount;
			this.path[u] = path;
			pathPos[u] = pathSize[path]++;

			for (int v : tree[u]) {
				if (v != parent[u]) {
					buildPaths(v, 2 * size[v] >= size[u] ? path : newPath(v));
				}
			}

			lastPath[u] = pathCount - 1;
		}

		void pushDelta(int path, int i) {
			int d = 0;

			for (; (i >> d) > 0; d++)
				;

			for (d -= 2; d >= 0; d--) {
				int x = i >> d;
				value[path][x >> 1] = joinValueWithDelta0(path, x >> 1);
				delta[path][x] = joinDeltas(delta[path][x], delta[path][x >> 1]);
				delta[path][x ^ 1] = joinDeltas(delta[path][x ^ 1],
						delta[path][x >> 1]);
				delta[path][x >> 1] = getNeutralDelta();
			}
		}

		long joinValueWithDelta0(int path, int i) {
			return joinValueWithDelta(value[path][i],
					deltaEffectOnSegment(delta[path][i], len[path][i]));
		}

		long queryPath(int path, int from, int to) {
			from += value[path].length >> 1;
			to += value[path].length >> 1;
			pushDelta(path, from);
			pushDelta(path, to);
			long res = getNeutralValue();
			List<Integer> list = new ArrayList<>();

			if (leftOrder) {
				for (; from <= to; from = (from + 1) >> 1, to = (to - 1) >> 1) {
					if ((from & 1) != 0) {
						res = queryOperation(res,
								joinValueWithDelta0(path, from));
					}

					if ((to & 1) == 0) {
						list.add(to);
					}
				}

				for (int i = list.size() - 1; i >= 0; i--) {
					res = queryOperation(res,
							joinValueWithDelta0(path, list.get(i)));
				}
			} else {
				for (; from <= to; from = (from + 1) >> 1, to = (to - 1) >> 1) {
					if ((from & 1) != 0) {
						list.add(from);
					}

					if ((to & 1) == 0) {
						res = queryOperation(res, joinValueWithDelta0(path, to));
					}
				}

				for (int i = list.size() - 1; i >= 0; i--) {
					res = queryOperation(res,
							joinValueWithDelta0(path, list.get(i)));
				}
			}

			return res;
		}

		void modifyPath(int path, int from, int to, long delta) {
			from += value[path].length >> 1;
			to += value[path].length >> 1;
			pushDelta(path, from);
			pushDelta(path, to);
			int ta = -1;
			int tb = -1;

			for (; from <= to; from = (from + 1) >> 1, to = (to - 1) >> 1) {
				if ((from & 1) != 0) {
					this.delta[path][from] = joinDeltas(this.delta[path][from],
							delta);

					if (ta == -1) {
						ta = from;
					}
				}

				if ((to & 1) == 0) {
					this.delta[path][to] = joinDeltas(this.delta[path][to],
							delta);

					if (tb == -1) {
						tb = to;
					}
				}
			}

			for (int i = ta; i > 1; i >>= 1) {
				value[path][i >> 1] = queryOperation(
						joinValueWithDelta0(path, leftOrder ^ (i > (i ^ 1)) ? i
								: i ^ 1),
								joinValueWithDelta0(path,
										leftOrder ^ (i > (i ^ 1)) ? i ^ 1 : i));
			}

			for (int i = tb; i > 1; i >>= 1) {
				value[path][i >> 1] = queryOperation(
						joinValueWithDelta0(path, leftOrder ^ (i > (i ^ 1)) ? i
								: i ^ 1),
								joinValueWithDelta0(path,
										leftOrder ^ (i > (i ^ 1)) ? i ^ 1 : i));
			}
		}

		boolean isAncestor(int p, int ch) {
			return tin[p] <= tin[ch] && tout[ch] <= tout[p];
		}

		public void modify(int a, int b, long delta) {
			for (int root; !isAncestor(root = pathRoot[path[a]], b); a = parent[root]) {
				modifyPath(path[a], 0, pathPos[a], delta);
			}

			for (int root; !isAncestor(root = pathRoot[path[b]], a); b = parent[root]) {
				modifyPath(path[b], 0, pathPos[b], delta);
			}

			modifyPath(path[a], Math.min(pathPos[a], pathPos[b]),
					Math.max(pathPos[a], pathPos[b]), delta);
		}
	}

	public static long query(HeavyLightDecomposition hl,
			HeavyLightDecomposition hr, int a, int b) {
		long res = HeavyLightDecomposition.getNeutralValue();

		for (int root; !hr.isAncestor(root = hr.pathRoot[hr.path[a]], b); a = hr.parent[root]) {
			res = HeavyLightDecomposition.queryOperation(res,
					hr.queryPath(hr.path[a], 0, hr.pathPos[a]));
		}

		List<Integer> list = new ArrayList<>();

		for (int root; !hl.isAncestor(root = hl.pathRoot[hl.path[b]], a); b = hl.parent[root]) {
			list.add(b);
		}

		if (hl.pathPos[a] >= hl.pathPos[b]) {
			res = HeavyLightDecomposition.queryOperation(res,
					hr.queryPath(hr.path[a], hr.pathPos[b], hr.pathPos[a]));
		} else {
			res = HeavyLightDecomposition.queryOperation(res,
					hl.queryPath(hl.path[a], hl.pathPos[a], hl.pathPos[b]));
		}

		for (int i = list.size() - 1; i >= 0; i--) {
			res = HeavyLightDecomposition.queryOperation(
					res,
					hl.queryPath(hl.path[list.get(i)], 0,
							hl.pathPos[list.get(i)]));
		}

		return res;
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
