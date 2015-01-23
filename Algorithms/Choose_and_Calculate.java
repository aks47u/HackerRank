package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Choose_and_Calculate {
	private static long mod = 1000000007;
	private static long[] fact, invfact;

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		int max = 100001;
		fact = new long[max];
		invfact = new long[max];
		fact[0] = invfact[0] = 1;

		for (int i = 1; i < max; i++) {
			fact[i] = (fact[i - 1] * i) % mod;
			invfact[i] = inv(fact[i], mod);
		}

		StringTokenizer st = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st
				.nextToken());
		int[] arr = new int[N];
		st = new StringTokenizer(in.readLine());

		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		Arrays.sort(arr);
		long res = 0;

		for (int i = 0; i < N; i++) {
			res += mod - ((arr[i] * comb(N - i - 1, K - 1)) % mod);

			if (res >= mod) {
				res -= mod;
			}
		}

		for (int i = N - 1; i >= 0; i--) {
			res += (arr[i] * comb(i, K - 1)) % mod;

			if (res >= mod) {
				res -= mod;
			}
		}

		out.println(res);
		out.close();
	}

	private static long inv(long N, long M) {
		long x = 0, lastx = 1, y = 1, lasty = 0, q, t, a = N, b = M;

		while (b != 0) {
			q = a / b;
			t = a % b;
			a = b;
			b = t;
			t = x;
			x = lastx - q * x;
			lastx = t;
			t = y;
			y = lasty - q * y;
			lasty = t;
		}

		return (lastx + M) % M;
	}

	private static long comb(int n, int k) {
		if (k > n) {
			return 0;
		}

		long res = (fact[n] * invfact[k]) % mod;

		return (res * invfact[n - k]) % mod;
	}
}
