package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Find_Maximum_Index_Product {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		long[] data = new long[n];

		for (int i = 0; i < n; i++) {
			data[i] = Long.parseLong(st.nextToken());
		}

		long max = 0;

		for (int i = 0; i < data.length; i++) {
			int left = 0;

			for (int j = i - 1; j >= 0; j--) {
				if (data[j] > data[i]) {
					left = j + 1;
					break;
				}
			}

			if (left == 0) {
				continue;
			}

			int right = 0;

			for (int j = i + 1; j < data.length; j++) {
				if (data[j] > data[i]) {
					right = j + 1;
					break;
				}
			}

			long ans = (long) left * right;

			if (max < ans) {
				max = ans;
			}
		}

		System.out.println(max);
	}
}
