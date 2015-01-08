package Algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bus_Station {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		short[] groups = new short[N];
		N = 0;

		for (String str : br.readLine().split(" ")) {
			groups[N++] = Short.parseShort(str);
		}

		int[] sums = new int[N];
		sums[0] = groups[0];

		for (int i = 1; i < N; ++i) {
			sums[i] = groups[i] + sums[i - 1];
		}

		List<Integer> sizes = new ArrayList<Integer>();

		for (int i = 0; i < N; ++i) {
			if (sums[N - 1] % sums[i] == 0) {
				int j = i;

				for (int x = sums[i]; j >= 0; j = Arrays.binarySearch(sums, j,
						N, x += sums[i])) {
				}

				if (~j == N) {
					sizes.add(sums[i]);
				}
			}
		}

		StringBuffer sb = new StringBuffer();

		for (int size : sizes) {
			sb.append(size + " ");
		}

		System.out.print(sb);
	}
}
