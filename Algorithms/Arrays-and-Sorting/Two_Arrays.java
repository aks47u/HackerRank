package Algorithms_Arrays_and_Sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Two_Arrays {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(in.readLine());

		for (int i = 0; i < T; i++) {
			String[] line = in.readLine().split(" ");
			@SuppressWarnings("unused")
			int n = Integer.parseInt(line[0]);
			int k = Integer.parseInt(line[1]);
			ArrayList<Integer> a = new ArrayList<Integer>();
			ArrayList<Integer> b = new ArrayList<Integer>();
			String[] stringArray = in.readLine().split(" ");

			for (int j = 0; j < stringArray.length; j++) {
				a.add(Integer.parseInt(stringArray[j]));
			}

			stringArray = in.readLine().split(" ");

			for (int j = 0; j < stringArray.length; j++) {
				b.add(Integer.parseInt(stringArray[j]));
			}

			Collections.sort(a);
			Collections.sort(b);
			System.out.println(isTwoArray(a, b, k));
		}
	}

	private static String isTwoArray(ArrayList<Integer> a,
			ArrayList<Integer> b, int k) {
		String result = "NO";
		int flag = 0;

		for (int i = 0; i < a.size(); i++) {
			int numRequired = k - a.get(i);

			if (b.contains(numRequired)) {
				b.remove(b.indexOf(numRequired));
				result = "YES";
				continue;
			} else {
				for (int j = 0; j < b.size(); j++) {
					if (Math.abs(b.get(j) + a.get(i)) >= k) {
						b.remove(j);
						result = "YES";
						flag = 1;
						break;
					} else
						flag = 0;
				}

				if (flag == 0) {
					result = "NO";
					break;
				}
			}
		}

		return result;
	}
}
