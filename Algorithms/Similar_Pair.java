package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Similar_Pair implements Runnable {
	BufferedReader in;
	PrintWriter out;
	StringTokenizer tok = new StringTokenizer("");

	public static void main(String[] args) {
		new Thread(null, new Similar_Pair(), "", 256 * (1L << 20)).start();
	}

	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			solve();
			in.close();
			out.close();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	String readString() throws IOException {
		while (!tok.hasMoreTokens()) {
			tok = new StringTokenizer(in.readLine());
		}

		return tok.nextToken();
	}

	int readInt() throws IOException {
		return Integer.parseInt(readString());
	}

	long readLong() throws IOException {
		return Long.parseLong(readString());
	}

	double readDouble() throws IOException {
		return Double.parseDouble(readString());
	}

	Edge[] first;
	FenwickTree sum;
	long result;

	void solve() throws IOException {
		int n = readInt();
		int k = readInt();
		first = new Edge[n];
		boolean[] root = new boolean[n];
		Arrays.fill(root, true);

		for (int i = 0; i < n - 1; i++) {
			int from = readInt() - 1;
			int to = readInt() - 1;
			root[to] = false;
			first[from] = new Edge(from, to, first[from]);
		}

		sum = new FenwickTree(n);
		result = 0;

		for (int i = 0; i < n; i++) {
			if (root[i]) {
				dfs(i, k);
				break;
			}
		}

		out.println(result);
	}

	void dfs(int x, int k) {
		result += sum.find(x + k) - sum.find(x - k - 1);
		sum.increase(x, +1);

		for (Edge edge = first[x]; edge != null; edge = edge.next) {
			dfs(edge.b, k);
		}

		sum.increase(x, -1);
	}

	class Edge {
		int a;
		int b;
		Edge next;

		Edge(int a, int b, Edge next) {
			this.a = a;
			this.b = b;
			this.next = next;
		}
	}

	class FenwickTree {
		private int[] sum;

		FenwickTree(int size) {
			sum = new int[size + 10];
		}

		private int prev(int x) {
			return x & (x - 1);
		}

		private int next(int x) {
			return 2 * x - prev(x);
		}

		void increase(int id, int value) {
			id++;

			while (id < sum.length) {
				sum[id] += value;
				id = next(id);
			}
		}

		long find(int id) {
			id++;
			id = Math.min(sum.length - 1, id);
			long res = 0;

			if (id <= 0) {
				return 0;
			}

			while (id > 0) {
				res += sum[id];
				id = prev(id);
			}

			return res;
		}
	}
}
