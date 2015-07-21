import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Subsequence_Weighting {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			int N = scn.nextInt();
			int[] as = new int[N];
			int[] ws = new int[N];

			for (int j = 0; j < N; ++j) {
				as[j] = scn.nextInt();
			}

			for (int j = 0; j < N; ++j) {
				ws[j] = scn.nextInt();
			}

			long bestW = solve(as, ws);
			System.out.println(bestW);
		}

		scn.close();
	}

	private static long solve(int[] a, int[] w) {
		int n = a.length;
		long best = 0;
		TreeMap<Integer, Long> map = new TreeMap<Integer, Long>();

		for (int k = 0; k < n; ++k) {
			Entry<Integer, Long> e = map.lowerEntry(a[k]);
			long b = (e == null ? 0 : e.getValue()) + w[k];
			SortedMap<Integer, Long> tail = map.tailMap(a[k]);
			List<Integer> del = new ArrayList<Integer>();

			for (Entry<Integer, Long> x : tail.entrySet()) {
				if (x.getValue().longValue() > b) {
					break;
				}

				del.add(x.getKey());
			}

			for (Integer i : del) {
				map.remove(i);
			}

			if (!map.containsKey(a[k])) {
				map.put(a[k], b);
			}

			if (best < b) {
				best = b;
			}
		}

		return best;
	}
}
