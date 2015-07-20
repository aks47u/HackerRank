import java.util.Arrays;
import java.util.Scanner;

public class Merging_Communities {
	private static int[] parent;
	private static int[] size;

	public static void main(String[] args) throws Exception {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int q = scn.nextInt();
		parent = new int[n];

		for (int i = 0; i < n; i++) {
			parent[i] = i;
		}

		size = new int[n];
		Arrays.fill(size, 1);

		while (q-- != 0) {
			String type = scn.next();

			if (type.equals("Q")) {
				System.out.println(size[findSet(scn.nextInt() - 1)]);
			} else {
				union(scn.nextInt() - 1, scn.nextInt() - 1);
			}
		}
	}

	private static int findSet(int x) {
		return parent[x] == x ? x : (parent[x] = findSet(parent[x]));
	}

	private static void union(int x, int y) {
		if (findSet(x) != findSet(y)) {
			size[findSet(x)] = size[findSet(y)] = size[findSet(x)]
					+ size[findSet(y)];
		}

		parent[findSet(x)] = findSet(y);
	}
}
