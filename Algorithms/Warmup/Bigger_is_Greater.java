package Algorithms_Warmup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Bigger_is_Greater {
	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int T = Integer.parseInt(br.readLine()); T > 0; --T) {
			char[] w = br.readLine().toCharArray();
			int n = w.length;
			int i;

			for (i = n - 2; i >= 0 && w[i] >= w[i + 1]; --i) {
			}

			if (i < 0) {
				sb.append("no answer\n");
				continue;
			}

			int minI = i + 1;

			for (int j = i + 2; j < n; ++j) {
				if (w[j] > w[i] && w[j] < w[minI]) {
					minI = j;
				}
			}

			char t = w[minI];
			w[minI] = w[i];
			w[i] = t;
			Arrays.sort(w, i + 1, n);
			sb.append(w).append("\n");
		}

		System.out.println(sb);
	}
}
