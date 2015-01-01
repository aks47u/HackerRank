package Algorithms_Arrays_and_Sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Make_it_Anagram {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] A = br.readLine().toCharArray();
		char[] B = br.readLine().toCharArray();
		int[] diffs = new int['z' - 'a' + 1];
		
		for (char c : A) {
			diffs[c - 'a']++;
		}
		
		for (char c : B) {
			diffs[c - 'a']--;
		}
		
		int sum = 0;
		
		for (int diff : diffs) {
			sum += (diff < 0) ? -diff : diff;
		}

		System.out.print(sum);
	}
}
