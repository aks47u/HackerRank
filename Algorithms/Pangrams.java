import java.io.BufferedReader;
package Algorithms;

import java.io.IOException;
import java.io.InputStreamReader;

public class Pangrams {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] C = br.readLine().toLowerCase().toCharArray();
		int N = C.length;
		boolean isPangram = false;
		int ALPHA_LEN = 'z' - 'a' + 1;

		if (N >= ALPHA_LEN) {
			int count = 0;
			boolean[] alpha = new boolean[ALPHA_LEN];

			for (int i = N - 1; i >= 0; --i) {
				char c = C[i];

				if (c >= 'a' && c <= 'z' && !alpha[c -= 'a']) {
					alpha[c] = true;

					if (++count >= ALPHA_LEN) {
						isPangram = true;
						break;
					}
				}
			}
		}

		System.out.print(isPangram ? "pangram" : "not pangram");
	}
}
