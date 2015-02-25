package Algorithms;

import java.util.ArrayList;
import java.util.Scanner;

public class Cut_the_tree {
	private static int[] a;
	private static ArrayList<Integer>[] e;
	private static int ans = Integer.MAX_VALUE;
	private static int sum = 0;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		a = new int[n];
		e = new ArrayList[n];

		for (int i = 0; i < n; i++) {
			a[i] = scn.nextInt();
			sum += a[i];
			e[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < n - 1; i++) {
			int u = scn.nextInt() - 1;
			int v = scn.nextInt() - 1;
			e[u].add(v);
			e[v].add(u);
		}

		scn.close();
		dfs(0, -1);
		System.out.println(ans);
	}

	private static int dfs(int u, int parent) {
		int acc = a[u];

		for (int v : e[u]) {
			if (v == parent) {
				continue;
			}

			int cb = dfs(v, u);
			acc += cb;
			ans = Math.min(ans, Math.abs(cb - (sum - cb)));
		}

		return acc;
	}
}
