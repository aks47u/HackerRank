import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Library_Query {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(in.readLine());

		for (int i = 0; i < t; i++) {
			int n = Integer.parseInt(in.readLine());
			Fenwick[] trees = new Fenwick[1001];

			for (int j = 1; j < trees.length; j++) {
				trees[j] = new Fenwick(10000);
			}

			int[] shelf = new int[n + 1];
			String[] cur = in.readLine().split(" ");

			for (int j = 1; j <= n; j++) {
				shelf[j] = Integer.parseInt(cur[j - 1]);
				trees[shelf[j]].update(j, 1);
			}

			int q = Integer.parseInt(in.readLine());

			for (int j = 0; j < q; j++) {
				String[] c = in.readLine().split(" ");

				if (c[0].equals("0")) {
					int x = Integer.parseInt(c[1]), y = Integer.parseInt(c[2]), k = Integer
							.parseInt(c[3]);
					int ct = 0;

					do {
						ct++;
						k -= trees[ct].rangeQuery(x, y);
					} while (k > 0);

					System.out.println(ct);
				} else {
					int x = Integer.parseInt(c[1]);
					int v = Integer.parseInt(c[2]);
					int pv = shelf[x];
					trees[v].update(x, 1);
					trees[pv].update(x, -1);
					shelf[x] = v;
				}
			}
		}
	}

	private static class Fenwick {
		short[] tree;

		public Fenwick(int n) {
			tree = new short[n + 1];
		}

		public int query(int idx) {
			int sum = 0;

			while (idx > 0) {
				sum += tree[idx];
				idx -= (idx & -idx);
			}

			return sum;
		}

		public int rangeQuery(int left, int right) {
			return query(right) - query(left - 1);
		}

		void update(int idx, int val) {
			while (idx < tree.length) {
				tree[idx] += (short) val;
				idx += (idx & -idx);
			}
		}
	}
}
