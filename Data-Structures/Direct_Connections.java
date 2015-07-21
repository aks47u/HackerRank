import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Direct_Connections {
	private static int MOD = 1000000007;
	private static int[] distances, populations;
	private static int N, SIZE;
	private static long[] pos;
	private static int[] pla;
	private static Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();

	public static void main(String[] args) throws IOException {
		String[] temp;
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		line = br.readLine();
		N = Integer.parseInt(line);

		for (int i = 0; i < N; i++) {
			line = br.readLine();
			SIZE = Integer.parseInt(line);
			distances = new int[SIZE];
			populations = new int[SIZE];
			pos = buildFenwickTreeLong(SIZE);
			pla = buildFenwickTreeInt(SIZE);
			line = br.readLine();
			temp = line.split(" ");

			for (int j = 0; j < SIZE; j++) {
				distances[j] = Integer.parseInt(temp[j]);
			}

			line = br.readLine();
			temp = line.split(" ");

			for (int k = 0; k < SIZE; k++) {
				populations[k] = Integer.parseInt(temp[k]);
			}

			doMapping(distances);
			compute(distances, populations);
		}
	}

	private static int[] buildFenwickTreeInt(int size) {
		int[] fenwickTree = new int[size + 1];

		for (int i = 1; i <= size; i++) {
			int idx = i;

			do {
				fenwickTree[idx] += 0;
				idx += (idx & -idx);
			} while (idx <= size && idx > 0);
		}

		return fenwickTree;
	}

	private static long[] buildFenwickTreeLong(int size) {
		long[] fenwickTree = new long[size + 1];

		for (int i = 1; i <= size; i++) {
			int idx = i;

			do {
				fenwickTree[idx] += 0;
				idx += (idx & -idx);
			} while (idx <= size && idx > 0);
		}

		return fenwickTree;
	}

	private static long readValueInt(int idx, int[] tree) {
		long sum = 0;

		while (idx > 0) {
			sum = sum + tree[idx];
			int x = idx & -idx;
			idx = idx - x;
		}

		return sum;
	}

	private static long readValueLong(int idx, long[] tree) {
		long sum = 0;

		while (idx > 0) {
			sum = sum + tree[idx];
			int x = idx & -idx;
			idx = idx - x;
		}

		return sum;
	}

	private static void updateValueInt(int idx, int val, int[] tree, int max) {
		while (idx <= max) {
			tree[idx] += val;
			idx += (idx & -idx);
		}
	}

	private static void updateValueLong(int idx, long val, long[] tree, int max) {
		while (idx <= max) {
			tree[idx] += val;
			idx += (idx & -idx);
		}
	}

	private static void compute(int[] dist, int[] popul) {
		long counter = 0;
		long bcount, acount;
		long bsum, asum;
		Map<Integer, List<Integer>> map = new TreeMap<Integer, List<Integer>>();

		for (int i = 0; i < dist.length; i++) {
			if (!map.containsKey(popul[i])) {
				map.put(popul[i], new ArrayList<Integer>());
				map.get(popul[i]).add(dist[i]);
			} else {
				map.get(popul[i]).add(dist[i]);
			}
		}

		Set<Integer> keys = map.keySet();
		Iterator<Integer> it = keys.iterator();
		List<Integer> list;
		int next, position;
		long coordinates;

		while (it.hasNext()) {
			next = it.next();
			list = map.get(next);

			for (int i = 0; i < list.size(); i++) {
				coordinates = list.get(i);
				position = mapping.get(list.get(i));
				bcount = readValueInt(position, pla);
				acount = readValueInt(SIZE, pla) - bcount;
				bsum = readValueLong(position, pos);
				asum = readValueLong(SIZE, pos) - bsum;
				counter += (((bcount * coordinates) - bsum) * next)
						+ ((asum - (acount * coordinates)) * next);

				if (counter > MOD) {
					counter %= MOD;
				}

				updateValueLong(position, coordinates, pos, SIZE);
				updateValueInt(position, 1, pla, SIZE);
			}
		}

		System.out.println(counter);
	}

	private static void doMapping(int[] input) {
		int[] copy = new int[input.length];

		for (int i = 0; i < input.length; i++) {
			copy[i] = input[i];
		}

		Arrays.sort(copy);

		for (int i = 0; i < copy.length; i++) {
			mapping.put(copy[i], i + 1);
		}
	}
}
