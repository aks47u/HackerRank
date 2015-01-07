package Algorithms;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Manasa_and_Stones {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		for (int i = 0; i < t; i++) {
			Set<Long> answer = ManasaandStones(scn.nextLong(), scn.nextLong(),
					scn.nextLong());
			Iterator<Long> iterator = answer.iterator();

			while (iterator.hasNext()) {
				System.out.printf("%d ", iterator.next());
			}

			System.out.println();
		}

		scn.close();
	}

	private static Set<Long> ManasaandStones(long n, long a, long b) {
		if (a > b) {
			long temp = b;
			b = a;
			a = temp;
		}

		Set<Long> hs = new TreeSet<Long>();

		for (int i = 0; i <= n - 1; i++) {
			hs.add(i * b + (n - 1 - i) * a);
		}

		return hs;
	}
}
