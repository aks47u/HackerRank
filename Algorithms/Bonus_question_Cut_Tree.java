import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Bonus_question_Cut_Tree {
	private static long[][] rooted = new long[55][55];
	private static int[] size = new int[55];
	private static int[] outDegree = new int[55];
	@SuppressWarnings("unchecked")
	private static ArrayList<Integer>[] tree = new ArrayList[55];
	private static int n, k;

	public static void main(String[] args) {
		Scanner scn = new Scanner(new BufferedInputStream(System.in));
		n = scn.nextInt();
		k = scn.nextInt();

		for (int i = 0; i < n; i++) {
			tree[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < n - 1; i++) {
			int u = scn.nextInt() - 1;
			int v = scn.nextInt() - 1;
			tree[u].add(v);
			tree[v].add(u);
		}

		scn.close();
		dfs(0, -1);
		long ans = 0;

		for (int i = 0; i < n; i++) {
			if (i == 0) {
				for (int j = 0; j <= k; j++) {
					ans += rooted[i][j];
				}
			} else {
				for (int j = 1; j <= k; j++) {
					ans += rooted[i][j - 1];
				}
			}
		}

		System.out.println(ans + 1);
	}

	private static void dfs(int u, int p) {
		size[u] = 1;

		for (int v : tree[u]) {
			if (v == p) {
				continue;
			}

			dfs(v, u);
			outDegree[u]++;
			size[u] += size[v];
		}

		long[] pre = rooted[u];
		rooted[u][0] = 1;

		for (int v : tree[u]) {
			if (v == p) {
				continue;
			}

			rooted[u] = new long[55];

			for (int i = 0; i <= k; i++) {
				if (i >= 1) {
					rooted[u][i] += pre[i - 1];
				}

				for (int j = 0; j <= i; j++) {
					rooted[u][i] += pre[i - j] * rooted[v][j];
				}
			}

			pre = rooted[u];
		}
	}
}
