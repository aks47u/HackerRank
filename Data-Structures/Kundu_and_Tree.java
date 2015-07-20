import java.util.ArrayList;
import java.util.Scanner;

public class Kundu_and_Tree {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] edges = new ArrayList[N];

		for (int i = 0; i < N; ++i) {
			edges[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < N - 1; ++i) {
			int a = scn.nextInt() - 1;
			int b = scn.nextInt() - 1;

			if (scn.next().equals("b")) {
				edges[a].add(b);
				edges[b].add(a);
			}
		}

		scn.close();
		boolean[] col = new boolean[N];
		long c1 = 0, c2 = 0, c3 = 0;

		for (int i = 0; i < N; ++i) {
			if (!col[i]) {
				int c = dfs(i, edges, col);
				c3 += c * c2;
				c2 += c * c1;
				c1 += c;
			}
		}

		System.out.println(c3 % 1000000007);
	}

	private static int dfs(int i, ArrayList<Integer>[] edges, boolean[] col) {
		if (col[i]) {
			return 0;
		}

		col[i] = true;
		int r = 1;

		for (int j : edges[i]) {
			r += dfs(j, edges, col);
		}

		return r;
	}
}
