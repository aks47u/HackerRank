import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class Solution {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		DynamicSummation solver = new DynamicSummation();
		solver.solve(1, in, out);
		out.close();
	}
}

class DynamicSummation {
	public void solve(int testNumber, InputReader in, OutputWriter out) {
		int count = in.readInt();
		int[] from = new int[count - 1];
		int[] to = new int[count - 1];
		IOUtils.readIntArrays(in, from, to);
		MiscUtils.decreaseByOne(from, to);
		Graph graph = BidirectionalGraph.createGraph(count, from, to);
		DFSOrder order = new DFSOrder((BidirectionalGraph) graph);
		int[] level = new int[count];
		dfs(0, -1, 0, graph, level);
		int[][] parent = new int[20][count];

		for (int i = 1; i < count; i++) {
			for (int j = graph.firstOutbound(i); j != -1; j = graph.nextOutbound(j)) {
				if (level[graph.destination(j)] < level[i]) {
					parent[0][i] = graph.destination(j);
					break;
				}
			}
		}

		for (int i = 1; i < 20; i++) {
			for (int j = 0; j < count; j++) {
				parent[i][j] = parent[i - 1][parent[i - 1][j]];
			}
		}

		int[] primes = IntegerUtils.generatePrimes(102);
		int[] mods = new int[7];

		for (int i = 0; i < 7; i++) {
			mods[i] = 1;

			for (int j = i * 4; j < i * 4 + 4 && j < primes.length; j++) {
				int current = primes[j];

				while (current * primes[j] <= 101) {
					current *= primes[j];
				}

				mods[i] *= current;
			}
		}

		IntervalTree[] trees = new IntervalTree[7];

		for (int i = 0; i < 7; i++) {
			final int mod = mods[i];

			trees[i] = new LongIntervalTree(count) {
				@Override
				protected long joinValue(long left, long right) {
					return (left + right) % mod;
				}

				@Override
				protected long joinDelta(long was, long delta) {
					return (was + delta) % mod;
				}

				@Override
				protected long accumulate(long value, long delta, int length) {
					return (value + delta * length) % mod;
				}

				@Override
				protected long neutralValue() {
					return 0;
				}

				@Override
				protected long neutralDelta() {
					return 0;
				}
			};
		}

		int queryCount = in.readInt();

		for (int i = 0; i < queryCount; i++) {
			char type = in.readCharacter();

			if (type == 'U') {
				int root = in.readInt() - 1;
				int current = in.readInt() - 1;
				long a = in.readLong();
				long b = in.readLong();

				if (root == current) {
					root = current = 0;
				}

				if (order.position[root] < order.position[current] || order.end[root] > order.end[current]
						|| root == current) {
					for (int j = 0; j < 7; j++) {
						int mod = mods[j];
						long value = IntegerUtils.power(a % mod, b, mod) + IntegerUtils.power((a + 1) % mod, b, mod)
						+ IntegerUtils.power((b + 1) % mod, a, mod);
						trees[j].update(order.position[current], order.end[current], value);
					}
				} else {
					int delta = level[root] - level[current] - 1;

					for (int j = 19; j >= 0; j--) {
						if (delta >= (1 << j)) {
							delta -= 1 << j;
							root = parent[j][root];
						}
					}

					for (int j = 0; j < 7; j++) {
						int mod = mods[j];
						long value = (IntegerUtils.power(a % mod, b, mod) + IntegerUtils.power((a + 1) % mod, b, mod)
						+ IntegerUtils.power((b + 1) % mod, a, mod)) % mod;
						trees[j].update(0, order.position[root] - 1, value);
						trees[j].update(order.end[root] + 1, count - 1, value);
					}
				}
			} else {
				int root = in.readInt() - 1;
				int current = in.readInt() - 1;
				int mod = in.readInt();
				int curVal = 0;
				int curMod = 1;

				if (root == current) {
					root = current = 0;
				}

				if (order.position[root] < order.position[current] || order.end[root] > order.end[current]
						|| root == current) {
					for (int j = 0; j < 7; j++) {
						int localMod = IntegerUtils.gcd(mod, mods[j]);

						if (localMod == 1) {
							continue;
						}

						int localVal = (int) (trees[j].query(order.position[current], order.end[current]) % localMod);

						for (int k = 0; k < localMod; k++) {
							if ((curMod * k + curVal) % localMod == localVal) {
								curVal += curMod * k;
								curMod *= localMod;
								break;
							}
						}
					}
				} else {
					int delta = level[root] - level[current] - 1;

					for (int j = 19; j >= 0; j--) {
						if (delta >= (1 << j)) {
							delta -= 1 << j;
							root = parent[j][root];
						}
					}

					for (int j = 0; j < 7; j++) {
						int localMod = IntegerUtils.gcd(mod, mods[j]);

						if (localMod == 1) {
							continue;
						}

						int localVal = (int) ((trees[j].query(0, order.position[root] - 1)
								+ trees[j].query(order.end[root] + 1, count - 1)) % localMod);

						for (int k = 0; k < localMod; k++) {
							if ((curMod * k + curVal) % localMod == localVal) {
								curVal += curMod * k;
								curMod *= localMod;
								break;
							}
						}
					}
				}

				if (curMod != mod) {
					throw new RuntimeException();
				}

				out.printLine(curVal);
			}
		}
	}

	private void dfs(int current, int last, int curLevel, Graph graph, int[] level) {
		level[current] = curLevel;

		for (int i = graph.firstOutbound(current); i != -1; i = graph.nextOutbound(i)) {
			int next = graph.destination(i);

			if (next != last) {
				dfs(next, current, curLevel + 1, graph, level);
			}
		}
	}
}

class InputReader {
	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1) {
			throw new InputMismatchException();
		}

		if (curChar >= numChars) {
			curChar = 0;

			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}

			if (numChars <= 0) {
				return -1;
			}
		}

		return buf[curChar++];
	}

	public int readInt() {
		int c = read();

		while (isSpaceChar(c)) {
			c = read();
		}

		int sgn = 1;

		if (c == '-') {
			sgn = -1;
			c = read();
		}

		int res = 0;

		do {
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}

			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));

		return res * sgn;
	}

	public long readLong() {
		int c = read();

		while (isSpaceChar(c)) {
			c = read();
		}

		int sgn = 1;

		if (c == '-') {
			sgn = -1;
			c = read();
		}

		long res = 0;

		do {
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}

			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));

		return res * sgn;
	}

	public boolean isSpaceChar(int c) {
		if (filter != null) {
			return filter.isSpaceChar(c);
		}

		return isWhitespace(c);
	}

	public static boolean isWhitespace(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public char readCharacter() {
		int c = read();

		while (isSpaceChar(c)) {
			c = read();
		}

		return (char) c;
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

class OutputWriter {
	private final PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void close() {
		writer.close();
	}

	public void printLine(int i) {
		writer.println(i);
	}
}

class IOUtils {
	public static void readIntArrays(InputReader in, int[]... arrays) {
		for (int i = 0; i < arrays[0].length; i++) {
			for (int j = 0; j < arrays.length; j++) {
				arrays[j][i] = in.readInt();
			}
		}
	}
}

class MiscUtils {
	public static void decreaseByOne(int[]... arrays) {
		for (int[] array : arrays) {
			for (int i = 0; i < array.length; i++) {
				array[i]--;
			}
		}
	}
}

class Graph {
	public static final int REMOVED_BIT = 0;
	protected int vertexCount;
	protected int edgeCount;
	private int[] firstOutbound;
	private int[] firstInbound;
	private Edge[] edges;
	private int[] nextInbound;
	private int[] nextOutbound;
	private int[] from;
	private int[] to;
	private long[] weight;
	private long[] capacity;
	private int[] reverseEdge;
	private int[] flags;

	public Graph(int vertexCount) {
		this(vertexCount, vertexCount);
	}

	public Graph(int vertexCount, int edgeCapacity) {
		this.vertexCount = vertexCount;
		firstOutbound = new int[vertexCount];
		Arrays.fill(firstOutbound, -1);
		from = new int[edgeCapacity];
		to = new int[edgeCapacity];
		nextOutbound = new int[edgeCapacity];
		flags = new int[edgeCapacity];
	}

	public int addEdge(int fromID, int toID, long weight, long capacity, int reverseEdge) {
		ensureEdgeCapacity(edgeCount + 1);

		if (firstOutbound[fromID] != -1) {
			nextOutbound[edgeCount] = firstOutbound[fromID];
		} else {
			nextOutbound[edgeCount] = -1;
		}

		firstOutbound[fromID] = edgeCount;

		if (firstInbound != null) {
			if (firstInbound[toID] != -1) {
				nextInbound[edgeCount] = firstInbound[toID];
			} else {
				nextInbound[edgeCount] = -1;
			}

			firstInbound[toID] = edgeCount;
		}

		this.from[edgeCount] = fromID;
		this.to[edgeCount] = toID;

		if (capacity != 0) {
			if (this.capacity == null) {
				this.capacity = new long[from.length];
			}

			this.capacity[edgeCount] = capacity;
		}

		if (weight != 0) {
			if (this.weight == null) {
				this.weight = new long[from.length];
			}

			this.weight[edgeCount] = weight;
		}

		if (reverseEdge != -1) {
			if (this.reverseEdge == null) {
				this.reverseEdge = new int[from.length];
				Arrays.fill(this.reverseEdge, 0, edgeCount, -1);
			}

			this.reverseEdge[edgeCount] = reverseEdge;
		}

		if (edges != null) {
			edges[edgeCount] = createEdge(edgeCount);
		}

		return edgeCount++;
	}

	protected final GraphEdge createEdge(int id) {
		return new GraphEdge(id);
	}

	public final int addFlowWeightedEdge(int from, int to, long weight, long capacity) {
		if (capacity == 0) {
			return addEdge(from, to, weight, 0, -1);
		} else {
			int lastEdgeCount = edgeCount;
			addEdge(to, from, -weight, 0, lastEdgeCount + entriesPerEdge());

			return addEdge(from, to, weight, capacity, lastEdgeCount);
		}
	}

	protected int entriesPerEdge() {
		return 1;
	}

	public final int addWeightedEdge(int from, int to, long weight) {
		return addFlowWeightedEdge(from, to, weight, 0);
	}

	public final int addSimpleEdge(int from, int to) {
		return addWeightedEdge(from, to, 0);
	}

	public final int vertexCount() {
		return vertexCount;
	}

	protected final int edgeCapacity() {
		return from.length;
	}

	public final int firstOutbound(int vertex) {
		int id = firstOutbound[vertex];

		while (id != -1 && isRemoved(id)) {
			id = nextOutbound[id];
		}

		return id;
	}

	public final int nextOutbound(int id) {
		id = nextOutbound[id];

		while (id != -1 && isRemoved(id)) {
			id = nextOutbound[id];
		}

		return id;
	}

	public final int destination(int id) {
		return to[id];
	}

	public final boolean flag(int id, int bit) {
		return (flags[id] >> bit & 1) != 0;
	}

	public final boolean isRemoved(int id) {
		return flag(id, REMOVED_BIT);
	}

	protected void ensureEdgeCapacity(int size) {
		if (from.length < size) {
			int newSize = Math.max(size, 2 * from.length);

			if (edges != null) {
				edges = resize(edges, newSize);
			}

			from = resize(from, newSize);
			to = resize(to, newSize);
			nextOutbound = resize(nextOutbound, newSize);

			if (nextInbound != null) {
				nextInbound = resize(nextInbound, newSize);
			}

			if (weight != null) {
				weight = resize(weight, newSize);
			}

			if (capacity != null) {
				capacity = resize(capacity, newSize);
			}

			if (reverseEdge != null) {
				reverseEdge = resize(reverseEdge, newSize);
			}

			flags = resize(flags, newSize);
		}
	}

	protected final int[] resize(int[] array, int size) {
		int[] newArray = new int[size];
		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	private long[] resize(long[] array, int size) {
		long[] newArray = new long[size];
		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	private Edge[] resize(Edge[] array, int size) {
		Edge[] newArray = new Edge[size];
		System.arraycopy(array, 0, newArray, 0, array.length);

		return newArray;
	}

	protected class GraphEdge implements Edge {
		protected int id;

		protected GraphEdge(int id) {
			this.id = id;
		}
	}
}

class BidirectionalGraph extends Graph {
	public int[] transposedEdge;

	public BidirectionalGraph(int vertexCount) {
		this(vertexCount, vertexCount);
	}

	public BidirectionalGraph(int vertexCount, int edgeCapacity) {
		super(vertexCount, 2 * edgeCapacity);
		transposedEdge = new int[2 * edgeCapacity];
	}

	public static BidirectionalGraph createGraph(int vertexCount, int[] from, int[] to) {
		BidirectionalGraph graph = new BidirectionalGraph(vertexCount, from.length);

		for (int i = 0; i < from.length; i++) {
			graph.addSimpleEdge(from[i], to[i]);
		}

		return graph;
	}

	public int addEdge(int fromID, int toID, long weight, long capacity, int reverseEdge) {
		int lastEdgeCount = edgeCount;
		super.addEdge(fromID, toID, weight, capacity, reverseEdge);
		super.addEdge(toID, fromID, weight, capacity, reverseEdge == -1 ? -1 : reverseEdge + 1);
		this.transposedEdge[lastEdgeCount] = lastEdgeCount + 1;
		this.transposedEdge[lastEdgeCount + 1] = lastEdgeCount;

		return lastEdgeCount;
	}

	protected int entriesPerEdge() {
		return 2;
	}

	protected void ensureEdgeCapacity(int size) {
		if (size > edgeCapacity()) {
			super.ensureEdgeCapacity(size);
			transposedEdge = resize(transposedEdge, edgeCapacity());
		}
	}
}

class DFSOrder {
	public final int[] position;
	public final int[] end;

	public DFSOrder(Graph graph) {
		this(graph, 0);
	}

	public DFSOrder(Graph graph, int root) {
		int count = graph.vertexCount();
		position = new int[count];
		end = new int[count];
		int[] edge = new int[count];
		int[] stack = new int[count];

		for (int i = 0; i < count; i++) {
			edge[i] = graph.firstOutbound(i);
		}

		stack[0] = root;
		int size = 1;
		position[root] = 0;
		int index = 0;

		while (size > 0) {
			int current = stack[size - 1];

			if (edge[current] == -1) {
				end[current] = index;
				size--;
			} else {
				int next = graph.destination(edge[current]);
				edge[current] = graph.nextOutbound(edge[current]);
				position[next] = ++index;
				stack[size++] = next;
			}
		}
	}

	public DFSOrder(BidirectionalGraph graph) {
		this(graph, 0);
	}

	public DFSOrder(BidirectionalGraph graph, int root) {
		int count = graph.vertexCount();
		position = new int[count];
		end = new int[count];
		int[] edge = new int[count];
		int[] stack = new int[count];
		int[] last = new int[count];

		for (int i = 0; i < count; i++) {
			edge[i] = graph.firstOutbound(i);
		}

		stack[0] = root;
		last[root] = -1;
		int size = 1;
		position[root] = 0;
		int index = 0;

		while (size > 0) {
			int current = stack[size - 1];

			if (edge[current] == -1) {
				end[current] = index;
				size--;
			} else {
				int next = graph.destination(edge[current]);

				if (next == last[current]) {
					edge[current] = graph.nextOutbound(edge[current]);
					continue;
				}

				edge[current] = graph.nextOutbound(edge[current]);
				position[next] = ++index;
				last[next] = current;
				stack[size++] = next;
			}
		}
	}
}

class IntegerUtils {
	public static int gcd(int a, int b) {
		a = Math.abs(a);
		b = Math.abs(b);

		while (b != 0) {
			int temp = a % b;
			a = b;
			b = temp;
		}

		return a;
	}

	public static int[] generatePrimes(int upTo) {
		int[] isPrime = generateBitPrimalityTable(upTo);
		IntList primes = new IntArrayList();

		for (int i = 0; i < upTo; i++) {
			if ((isPrime[i >> 5] >>> (i & 31) & 1) == 1) {
				primes.add(i);
			}
		}

		return primes.toArray();
	}

	public static int[] generateBitPrimalityTable(int upTo) {
		int[] isPrime = new int[(upTo + 31) >> 5];

		if (upTo < 2) {
			return isPrime;
		}

		Arrays.fill(isPrime, -1);
		isPrime[0] &= -4;

		for (int i = 2; i * i < upTo; i++) {
			if ((isPrime[i >> 5] >>> (i & 31) & 1) == 1) {
				for (int j = i * i; j < upTo; j += i) {
					isPrime[j >> 5] &= -1 - (1 << (j & 31));
				}
			}
		}

		return isPrime;
	}

	public static long power(long base, long exponent, long mod) {
		if (base >= mod) {
			base %= mod;
		}

		if (exponent == 0) {
			return 1 % mod;
		}

		long result = power(base, exponent >> 1, mod);
		result = result * result % mod;

		if ((exponent & 1) != 0) {
			result = result * base % mod;
		}

		return result;
	}
}

abstract class IntervalTree {
	protected int size;

	protected IntervalTree(int size) {
		this(size, true);
	}

	public IntervalTree(int size, boolean shouldInit) {
		this.size = size;
		int nodeCount = Math.max(1, Integer.highestOneBit(size) << 2);
		initData(size, nodeCount);

		if (shouldInit) {
			init();
		}
	}

	protected abstract void initData(int size, int nodeCount);

	protected abstract void initAfter(int root, int left, int right, int middle);

	protected abstract void initBefore(int root, int left, int right, int middle);

	protected abstract void initLeaf(int root, int index);

	protected abstract void updatePostProcess(int root, int left, int right, int from, int to, long delta, int middle);

	protected abstract void updatePreProcess(int root, int left, int right, int from, int to, long delta, int middle);

	protected abstract void updateFull(int root, int left, int right, int from, int to, long delta);

	protected abstract long queryPostProcess(int root, int left, int right, int from, int to, int middle,
			long leftResult, long rightResult);

	protected abstract void queryPreProcess(int root, int left, int right, int from, int to, int middle);

	protected abstract long queryFull(int root, int left, int right, int from, int to);

	protected abstract long emptySegmentResult();

	public void init() {
		if (size == 0) {
			return;
		}

		init(0, 0, size - 1);
	}

	private void init(int root, int left, int right) {
		if (left == right) {
			initLeaf(root, left);
		} else {
			int middle = (left + right) >> 1;
			initBefore(root, left, right, middle);
			init(2 * root + 1, left, middle);
			init(2 * root + 2, middle + 1, right);
			initAfter(root, left, right, middle);
		}
	}

	public void update(int from, int to, long delta) {
		update(0, 0, size - 1, from, to, delta);
	}

	protected void update(int root, int left, int right, int from, int to, long delta) {
		if (left > to || right < from) {
			return;
		}

		if (left >= from && right <= to) {
			updateFull(root, left, right, from, to, delta);

			return;
		}

		int middle = (left + right) >> 1;
		updatePreProcess(root, left, right, from, to, delta, middle);
		update(2 * root + 1, left, middle, from, to, delta);
		update(2 * root + 2, middle + 1, right, from, to, delta);
		updatePostProcess(root, left, right, from, to, delta, middle);
	}

	public long query(int from, int to) {
		return query(0, 0, size - 1, from, to);
	}

	protected long query(int root, int left, int right, int from, int to) {
		if (left > to || right < from) {
			return emptySegmentResult();
		}

		if (left >= from && right <= to) {
			return queryFull(root, left, right, from, to);
		}

		int middle = (left + right) >> 1;
		queryPreProcess(root, left, right, from, to, middle);
		long leftResult = query(2 * root + 1, left, middle, from, to);
		long rightResult = query(2 * root + 2, middle + 1, right, from, to);

		return queryPostProcess(root, left, right, from, to, middle, leftResult, rightResult);
	}
}

abstract class LongIntervalTree extends IntervalTree {
	protected long[] value;
	protected long[] delta;

	protected LongIntervalTree(int size) {
		this(size, true);
	}

	public LongIntervalTree(int size, boolean shouldInit) {
		super(size, shouldInit);
	}

	protected void initData(int size, int nodeCount) {
		value = new long[nodeCount];
		delta = new long[nodeCount];
	}

	protected abstract long joinValue(long left, long right);

	protected abstract long joinDelta(long was, long delta);

	protected abstract long accumulate(long value, long delta, int length);

	protected abstract long neutralValue();

	protected abstract long neutralDelta();

	protected long initValue(int index) {
		return neutralValue();
	}

	protected void initAfter(int root, int left, int right, int middle) {
		value[root] = joinValue(value[2 * root + 1], value[2 * root + 2]);
		delta[root] = neutralDelta();
	}

	protected void initBefore(int root, int left, int right, int middle) {
	}

	protected void initLeaf(int root, int index) {
		value[root] = initValue(index);
		delta[root] = neutralDelta();
	}

	protected void updatePostProcess(int root, int left, int right, int from, int to, long delta, int middle) {
		value[root] = joinValue(value[2 * root + 1], value[2 * root + 2]);
	}

	protected void updatePreProcess(int root, int left, int right, int from, int to, long delta, int middle) {
		pushDown(root, left, middle, right);
	}

	protected void pushDown(int root, int left, int middle, int right) {
		value[2 * root + 1] = accumulate(value[2 * root + 1], delta[root], middle - left + 1);
		value[2 * root + 2] = accumulate(value[2 * root + 2], delta[root], right - middle);
		delta[2 * root + 1] = joinDelta(delta[2 * root + 1], delta[root]);
		delta[2 * root + 2] = joinDelta(delta[2 * root + 2], delta[root]);
		delta[root] = neutralDelta();
	}

	protected void updateFull(int root, int left, int right, int from, int to, long delta) {
		value[root] = accumulate(value[root], delta, right - left + 1);
		this.delta[root] = joinDelta(this.delta[root], delta);
	}

	protected long queryPostProcess(int root, int left, int right, int from, int to, int middle, long leftResult,
			long rightResult) {
		return joinValue(leftResult, rightResult);
	}

	protected void queryPreProcess(int root, int left, int right, int from, int to, int middle) {
		pushDown(root, left, middle, right);
	}

	protected long queryFull(int root, int left, int right, int from, int to) {
		return value[root];
	}

	protected long emptySegmentResult() {
		return neutralValue();
	}
}

abstract class IntCollection {
	public abstract IntIterator iterator();

	public abstract int size();

	public abstract void add(int value);

	public int[] toArray() {
		int size = size();
		int[] array = new int[size];
		int i = 0;

		for (IntIterator iterator = iterator(); iterator.isValid(); iterator.advance()) {
			array[i++] = iterator.value();
		}

		return array;
	}

	public void addAll(IntCollection values) {
		for (IntIterator it = values.iterator(); it.isValid(); it.advance()) {
			add(it.value());
		}
	}
}

interface IntIterator {
	public int value() throws NoSuchElementException;

	public void advance() throws NoSuchElementException;

	public boolean isValid();
}

abstract class IntList extends IntCollection implements Comparable<IntList> {
	public abstract int get(int index);

	public IntIterator iterator() {
		return new IntIterator() {
			private int size = size();
			private int index = 0;

			public int value() throws NoSuchElementException {
				if (!isValid()) {
					throw new NoSuchElementException();
				}

				return get(index);
			}

			public void advance() throws NoSuchElementException {
				if (!isValid()) {
					throw new NoSuchElementException();
				}

				index++;
			}

			public boolean isValid() {
				return index < size;
			}
		};
	}

	public int hashCode() {
		int hashCode = 1;

		for (IntIterator i = iterator(); i.isValid(); i.advance()) {
			hashCode = 31 * hashCode + i.value();
		}

		return hashCode;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof IntList)) {
			return false;
		}

		IntList list = (IntList) obj;

		if (list.size() != size()) {
			return false;
		}

		IntIterator i = iterator();
		IntIterator j = list.iterator();

		while (i.isValid()) {
			if (i.value() != j.value()) {
				return false;
			}

			i.advance();
			j.advance();
		}

		return true;
	}

	public int compareTo(IntList o) {
		IntIterator i = iterator();
		IntIterator j = o.iterator();

		while (true) {
			if (i.isValid()) {
				if (j.isValid()) {
					if (i.value() != j.value()) {
						if (i.value() < j.value()) {
							return -1;
						} else {
							return 1;
						}
					}
				} else {
					return 1;
				}
			} else {
				if (j.isValid()) {
					return -1;
				} else {
					return 0;
				}
			}

			i.advance();
			j.advance();
		}
	}
}

class IntArrayList extends IntList {
	private int[] array;
	private int size;

	public IntArrayList() {
		this(10);
	}

	public IntArrayList(int capacity) {
		array = new int[capacity];
	}

	public IntArrayList(IntList list) {
		this(list.size());
		addAll(list);
	}

	public int get(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return array[index];
	}

	public int size() {
		return size;
	}

	public void add(int value) {
		ensureCapacity(size + 1);
		array[size++] = value;
	}

	public void ensureCapacity(int newCapacity) {
		if (newCapacity > array.length) {
			int[] newArray = new int[Math.max(newCapacity, array.length << 1)];
			System.arraycopy(array, 0, newArray, 0, size);
			array = newArray;
		}
	}

	public int[] toArray() {
		int[] array = new int[size];
		System.arraycopy(this.array, 0, array, 0, size);

		return array;
	}
}

interface Edge {
}
