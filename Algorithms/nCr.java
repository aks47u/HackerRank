package Algorithms;

import java.util.HashMap;
import java.util.Scanner;

public class nCr {
	private static int[][] mod11;
	private static int[][] mod13;
	private static int[][] mod37;
	private static int[][] mod27;
	private static HashMap<Integer, int[][]> map;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		StringBuilder output = new StringBuilder();
		int t = scn.nextInt();
		precompute();

		while (t-- > 0) {
			int n = scn.nextInt();
			int r = scn.nextInt();
			output.append(choose(n, r));
			output.append("\n");
		}

		scn.close();
		System.out.print(output);
	}

	private static int choose(int n, int r) {
		int count3 = countFactors(n, r, 3);
		int count11 = countFactors(n, r, 11);
		int count13 = countFactors(n, r, 13);
		int count37 = countFactors(n, r, 37);

		if (count3 >= 3) {
			count3 = 3;
		}

		int modulo27 = (count3 == 3 ? 0 : factorial(n, r, 27));

		while (count3-- > 0) {
			modulo27 *= 3;
		}

		int modulo11 = (count11 != 0 ? 0 : factorial(n, r, 11));
		int modulo13 = (count13 != 0 ? 0 : factorial(n, r, 13));
		int modulo37 = (count37 != 0 ? 0 : factorial(n, r, 37));
		int N = 27 * 11 * 13 * 37;
		int x = modulo27 * (N / 27) * modularInversion(N / 27, 27) + modulo11
				* (N / 11) * modularInversion(N / 11, 11) + modulo13 * (N / 13)
				* modularInversion(N / 13, 13) + modulo37 * (N / 37)
				* modularInversion(N / 37, 37);
		x %= N;

		return x;
	}

	private static int modularInversion(int a, int mod) {
		a %= mod;
		int[] coeffs = extendedEuclidean(a, mod);

		if (coeffs[0] < 0) {
			coeffs[0] += mod;
		}

		return coeffs[0];
	}

	private static int[] extendedEuclidean(int a, int b) {
		if (a % b == 0) {
			return new int[] { 0, 1 };
		}

		int[] ee = extendedEuclidean(b, a % b);

		return new int[] { ee[1], ee[0] - ee[1] * (a / b) };
	}

	private static int factorial(int n, int r, int prime) {
		int result = factorialMod(n, prime, 0) * factorialInverse(n - r, prime)
				* factorialInverse(r, prime);
		result %= prime;

		return result;
	}

	private static int factorialInverse(int n, int prime) {
		return factorialMod(n, prime, 1);
	}

	private static int factorialMod(int n, int prime, int bit) {
		int ret = 1;
		int[][] modprime = map.get(prime); // dat prime better be there. "prime"
		int div = prime;

		if (div == 27) {
			div = 3;
		}

		int q = n / prime;
		int r = n % prime;
		n /= div;

		while (!(q == 0 && r == 0)) {
			ret *= modpow(modprime[prime - 1][bit], q, prime);
			ret *= modprime[r][bit];
			ret %= prime;
			r = n % prime;
			q = n / prime;
			n /= div;
		}

		ret *= modprime[r][bit];

		return ret % prime;
	}

	private static int modpow(int base, int exp, int mod) {
		base %= mod;
		int ret = 1;
		int bit = 1;
		int mult = base;

		while (bit <= exp) {
			if ((bit & exp) != 0) {
				ret *= mult;
				ret %= mod;
			}

			mult *= mult;
			mult %= mod;
			bit <<= 1;
		}

		return ret;
	}

	private static int countFactors(int n, int r, int prime) {
		return countFactors(n, prime) - countFactors(n - r, prime)
				- countFactors(r, prime);
	}

	private static int countFactors(int n, int prime) {
		int total = 0;

		while (n >= prime) {
			n /= prime;
			total += n;
		}

		return total;
	}

	private static void precompute() {
		mod11 = new int[11][2];
		mod13 = new int[13][2];
		mod37 = new int[37][2];
		mod27 = new int[27][2];
		mod11[0][0] = mod11[0][1] = mod13[0][0] = mod13[0][1] = 1;
		mod37[0][0] = mod37[0][1] = mod27[0][0] = mod27[0][1] = 1;
		int[] temp = getInverses(11);

		for (int i = 1; i < 11; ++i) {
			mod11[i][0] = (mod11[i - 1][0] * i) % 11;
			mod11[i][1] = temp[mod11[i][0]];
		}

		temp = getInverses(13);

		for (int i = 1; i < 13; ++i) {
			mod13[i][0] = (mod13[i - 1][0] * i) % 13;
			mod13[i][1] = temp[mod13[i][0]];
		}

		temp = getInverses(37);

		for (int i = 1; i < 37; ++i) {
			mod37[i][0] = (mod37[i - 1][0] * i) % 37;
			mod37[i][1] = temp[mod37[i][0]];
		}

		temp = getInverses(27);

		for (int i = 1; i < 27; ++i) {
			int tempi = i;

			if (tempi % 3 == 0) {
				tempi = 1;
			}

			mod27[i][0] = (mod27[i - 1][0] * tempi) % 27;
			mod27[i][1] = temp[mod27[i][0]];
		}

		map = new HashMap<Integer, int[][]>();
		map.put(11, mod11);
		map.put(13, mod13);
		map.put(27, mod27);
		map.put(37, mod37);
	}

	private static int[] getInverses(int prime) {
		int[] ret = new int[prime];

		for (int i = 1; i < ret.length; ++i) {
			for (int j = 1; j < ret.length; ++j) {
				if (i * j % ret.length == 1) {
					ret[i] = j;
					break;
				}
			}
		}

		return ret;
	}
}
