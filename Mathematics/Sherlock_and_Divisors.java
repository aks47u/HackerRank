import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sherlock_and_Divisors {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int t = 0; t < T; t++) {
			int N = scn.nextInt();

			if (N % 2 != 0) {
				System.out.println(0);
			} else {
				List<Integer> allPrimeFactors = new ArrayList<Integer>();
				SortedSet<Integer> factorSet = new TreeSet<Integer>();

				for (int i = 2; i <= N / i; i++) {
					while (N % i == 0) {
						allPrimeFactors.add(i);
						factorSet.add(i);
						N /= i;
					}
				}

				if (N > 1) {
					allPrimeFactors.add(N);
					factorSet.add(N);
				}

				List<Integer> primeFactorCount = new ArrayList<Integer>();
				List<Integer> uniquePrimeFactors = new ArrayList<Integer>();

				while (!factorSet.isEmpty()) {
					primeFactorCount.add(Collections.frequency(allPrimeFactors,
							factorSet.first()));
					uniquePrimeFactors.add(factorSet.first());
					factorSet.remove(factorSet.first());
				}

				int totalFactors = 1, oddFactors = 1;

				for (int i = 0; i < uniquePrimeFactors.size(); i++) {
					totalFactors *= (primeFactorCount.get(i) + 1);

					if (uniquePrimeFactors.get(i) % 2 != 0) {
						oddFactors *= (primeFactorCount.get(i) + 1);
					}
				}

				System.out.println(totalFactors - oddFactors);
			}
		}

		scn.close();
	}
}
