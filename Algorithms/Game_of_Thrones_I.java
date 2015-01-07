package Algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Game_of_Thrones_I {
	public static void main(String[] args) {
		String input = getInput();
		StringTokenizer st = new StringTokenizer(input, " ");
		int[] in = new int[26];

		while (st.hasMoreTokens()) {
			String word = st.nextToken();

			for (int i = 0; i < word.length(); i++) {
				in[word.charAt(i) - '\0' - 97]++;
			}
		}

		int isOdd = 0;
		boolean isOK = false;
		boolean isNotOK = false;

		for (int i = 0; i < in.length; i++) {
			if (in[i] == 1) {
				isOdd++;
			}

			if (in[i] > 1) {
				if (in[i] % 2 == 1) {
					if (isOdd == 1) {
						isNotOK = false;
					} else {
						isOK = true;
						isNotOK = true;
					}
				} else {
					isOK = true;
				}
			}
		}

		if (!isNotOK && isOK) {
			System.out.println("YES");
		} else {
			if (in[0] == 3 && in[1] == 4) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

	private static String getInput() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			return stdin.readLine();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
