import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Rooted_Tree {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		RootedTree solver = new RootedTree();
		solver.solve(1, in, out);
		out.close();
	}
}

class RootedTree {
	private static final long MOD = (long) (1e9 + 7);
	long reverse = (MOD + 1) / 2;
	private DFSOrder order;
	private LCA lca;
	private SumIntervalTree base;
	private SumIntervalTree linear;
	private SumIntervalTree squared;

	public void solve(int testNumber, InputReader in, OutputWriter out) {
		int count = in.readInt();
		int queryCount = in.readInt();
		int root = in.readInt() - 1;
		int[] from = new int[count - 1];
		int[] to = new int[count - 1];
		IOUtils.readIntArrays(in, from, to);
		MiscUtils.decreaseByOne(from, to);
		BidirectionalGraph tree = BidirectionalGraph.createGraph(count, from,
				to);
		order = new DFSOrder(tree, root);
		lca = new LCA(tree, root);
		base = new SumIntervalTree(count);
		linear = new SumIntervalTree(count);
		squared = new SumIntervalTree(count);
		int[] parent = new int[count];
		dfs(root, -1, parent, tree);

		for (int i = 0; i < queryCount; i++) {
			char type = in.readCharacter();

			if (type == 'Q') {
				int a = in.readInt() - 1;
				int b = in.readInt() - 1;
				int cLCA = lca.getLCA(a, b);
				long result = value(a) + value(b) - value(cLCA)
						- value(parent[cLCA]);
				result %= MOD;

				if (result < 0) {
					result += MOD;
				}

				out.printLine(result);
			} else {
				int node = in.readInt() - 1;
				long v = in.readInt();
				long k = in.readInt();
				long d = lca.getLevel(node);
				int start = order.position[node];
				int end = order.end[node];
				base.update(start, end,
						2 * (-v * d + v + k * (d * (d - 1) / 2)));
				linear.update(start, end, 2 * v - k * (2 * d - 1));
				squared.update(start, end, k);
			}
		}
	}

	private long value(int node) {
		if (node == -1) {
			return 0;
		}

		int level = lca.getLevel(node);
		node = order.position[node];
		long result = (base.query(node, node) + level
				* linear.query(node, node) % MOD + level * level % MOD
				* squared.query(node, node) % MOD)
				% MOD;
		result *= reverse;
		result %= MOD;

		return result;
	}

	private void dfs(int current, int last, int[] parent,
			BidirectionalGraph graph) {
		parent[current] = last;

		for (int i = graph.firstOutbound(current); i != -1; i = graph
				.nextOutbound(i)) {
			int next = graph.destination(i);

			if (next != last) {
				dfs(next, current, parent, graph);
			}
		}
	}

	static class SumIntervalTree extends LongIntervalTree {
		public SumIntervalTree(int size) {
			super(size);
		}

		protected long joinValue(long left, long right) {
			return (left + right) % MOD;
		}

		protected long joinDelta(long was, long delta) {
			return (was + delta) % MOD;
		}

		protected long accumulate(long value, long delta, int length) {
			return (value + delta * length) % MOD;
		}

		protected long neutralValue() {
			return 0;
		}

		protected long neutralDelta() {
			return 0;
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

class LCA {
	private final long[] order;
	private final int[] position;
	private final IntervalTree lcaTree;
	private final int[] level;

	public LCA(Graph graph) {
		this(graph, 0);
	}

	public LCA(Graph graph, int root) {
		order = new long[2 * graph.vertexCount() - 1];
		position = new int[graph.vertexCount()];
		level = new int[graph.vertexCount()];
		int[] index = new int[graph.vertexCount()];

		for (int i = 0; i < index.length; i++) {
			index[i] = graph.firstOutbound(i);
		}

		int[] last = new int[graph.vertexCount()];
		int[] stack = new int[graph.vertexCount()];
		stack[0] = root;
		int size = 1;
		int j = 0;
		last[root] = -1;
		Arrays.fill(position, -1);

		while (size > 0) {
			int vertex = stack[--size];

			if (position[vertex] == -1) {
				position[vertex] = j;
			}

			order[j++] = vertex;

			if (last[vertex] != -1) {
				level[vertex] = level[last[vertex]] + 1;
			}

			while (index[vertex] != -1
					&& last[vertex] == graph.destination(index[vertex])) {
				index[vertex] = graph.nextOutbound(index[vertex]);
			}

			if (index[vertex] != -1) {
				stack[size++] = vertex;
				stack[size++] = graph.destination(index[vertex]);
				last[graph.destination(index[vertex])] = vertex;
				index[vertex] = graph.nextOutbound(index[vertex]);
			}
		}

		lcaTree = new ReadOnlyIntervalTree(order) {
			@Override
			protected long joinValue(long left, long right) {
				if (left == -1) {
					return right;
				}

				if (right == -1) {
					return left;
				}

				if (level[((int) left)] < level[((int) right)]) {
					return left;
				}

				return right;
			}

			@Override
			protected long neutralValue() {
				return -1;
			}
		};

		lcaTree.init();
	}

	public int getLCA(int first, int second) {
		return (int) lcaTree.query(Math.min(position[first], position[second]),
				Math.max(position[first], position[second]));
	}

	public int getLevel(int vertex) {
		return level[vertex];
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
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void close() {
		writer.close();
	}

	public void printLine(long i) {
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

class BidirectionalGraph extends Graph {
	public int[] transposedEdge;

	public BidirectionalGraph(int vertexCount) {
		this(vertexCount, vertexCount);
	}

	public BidirectionalGraph(int vertexCount, int edgeCapacity) {
		super(vertexCount, 2 * edgeCapacity);
		transposedEdge = new int[2 * edgeCapacity];
	}

	public static BidirectionalGraph createGraph(int vertexCount, int[] from,
			int[] to) {
		BidirectionalGraph graph = new BidirectionalGraph(vertexCount,
				from.length);

		for (int i = 0; i < from.length; i++) {
			graph.addSimpleEdge(from[i], to[i]);
		}

		return graph;
	}

	public int addEdge(int fromID, int toID, long weight, long capacity,
			int reverseEdge) {
		int lastEdgeCount = edgeCount;
		super.addEdge(fromID, toID, weight, capacity, reverseEdge);
		super.addEdge(toID, fromID, weight, capacity, reverseEdge == -1 ? -1
				: reverseEdge + 1);
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

	protected abstract void updatePostProcess(int root, int left, int right,
			int from, int to, long delta, int middle);

	protected abstract void updatePreProcess(int root, int left, int right,
			int from, int to, long delta, int middle);

	protected abstract void updateFull(int root, int left, int right, int from,
			int to, long delta);

	protected abstract long queryPostProcess(int root, int left, int right,
			int from, int to, int middle, long leftResult, long rightResult);

	protected abstract void queryPreProcess(int root, int left, int right,
			int from, int to, int middle);

	protected abstract long queryFull(int root, int left, int right, int from,
			int to);

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

	protected void update(int root, int left, int right, int from, int to,
			long delta) {
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

		return queryPostProcess(root, left, right, from, to, middle,
				leftResult, rightResult);
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

	public int addEdge(int fromID, int toID, long weight, long capacity,
			int reverseEdge) {
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

	public final int addFlowWeightedEdge(int from, int to, long weight,
			long capacity) {
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

	protected void updatePostProcess(int root, int left, int right, int from,
			int to, long delta, int middle) {
		value[root] = joinValue(value[2 * root + 1], value[2 * root + 2]);
	}

	protected void updatePreProcess(int root, int left, int right, int from,
			int to, long delta, int middle) {
		pushDown(root, left, middle, right);
	}

	protected void pushDown(int root, int left, int middle, int right) {
		value[2 * root + 1] = accumulate(value[2 * root + 1], delta[root],
				middle - left + 1);
		value[2 * root + 2] = accumulate(value[2 * root + 2], delta[root],
				right - middle);
		delta[2 * root + 1] = joinDelta(delta[2 * root + 1], delta[root]);
		delta[2 * root + 2] = joinDelta(delta[2 * root + 2], delta[root]);
		delta[root] = neutralDelta();
	}

	protected void updateFull(int root, int left, int right, int from, int to,
			long delta) {
		value[root] = accumulate(value[root], delta, right - left + 1);
		this.delta[root] = joinDelta(this.delta[root], delta);
	}

	protected long queryPostProcess(int root, int left, int right, int from,
			int to, int middle, long leftResult, long rightResult) {
		return joinValue(leftResult, rightResult);
	}

	protected void queryPreProcess(int root, int left, int right, int from,
			int to, int middle) {
		pushDown(root, left, middle, right);
	}

	protected long queryFull(int root, int left, int right, int from, int to) {
		return value[root];
	}

	protected long emptySegmentResult() {
		return neutralValue();
	}
}

abstract class ReadOnlyIntervalTree extends IntervalTree {
	protected long[] value;
	protected long[] array;

	protected ReadOnlyIntervalTree(long[] array) {
		super(array.length, false);
		this.array = array;
		init();
	}

	protected void initData(int size, int nodeCount) {
		value = new long[nodeCount];
	}

	protected void initAfter(int root, int left, int right, int middle) {
		value[root] = joinValue(value[2 * root + 1], value[2 * root + 2]);
	}

	protected void initBefore(int root, int left, int right, int middle) {
	}

	protected void initLeaf(int root, int index) {
		value[root] = array[index];
	}

	protected void updatePostProcess(int root, int left, int right, int from,
			int to, long delta, int middle) {
		throw new UnsupportedOperationException();
	}

	protected void updatePreProcess(int root, int left, int right, int from,
			int to, long delta, int middle) {
		throw new UnsupportedOperationException();
	}

	protected void updateFull(int root, int left, int right, int from, int to,
			long delta) {
		throw new UnsupportedOperationException();
	}

	protected long queryPostProcess(int root, int left, int right, int from,
			int to, int middle, long leftResult, long rightResult) {
		return joinValue(leftResult, rightResult);
	}

	protected void queryPreProcess(int root, int left, int right, int from,
			int to, int middle) {
	}

	protected long queryFull(int root, int left, int right, int from, int to) {
		return value[root];
	}

	protected long emptySegmentResult() {
		return neutralValue();
	}

	public void update(int from, int to, long delta) {
		throw new UnsupportedOperationException();
	}

	protected void update(int root, int left, int right, int from, int to,
			long delta) {
		throw new UnsupportedOperationException();
	}

	protected abstract long neutralValue();

	protected abstract long joinValue(long left, long right);
}

interface Edge {
}
