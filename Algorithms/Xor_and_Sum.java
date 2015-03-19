import java.util.Scanner;

public class Xor_and_Sum {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		char[] a = new StringBuffer(scn.nextLine()).reverse().toString()
				.toCharArray();
		char[] b = new StringBuffer(scn.nextLine()).reverse().toString()
				.toCharArray();
		scn.close();
		System.out.println(xorSum(a, b));
	}

	private static long xorSum(char[] a, char[] b) {
		long mod = 1000000007;
		int n = 314159;
		int length = Math.max(a.length, b.length + n);
		int[] oneCount = new int[b.length + 1];
		oneCount[0] = 0;

		for (int i = 1; i < b.length + 1; i++) {
			char bn = b[i - 1];
			oneCount[i] = oneCount[i - 1];

			if (bn == '1') {
				oneCount[i]++;
			}
		}

		long power = 1;
		long sum = 0;

		for (int i = 0; i < length; i++) {
			int sumAtIndex;
			int count;

			if (i <= n) {
				count = oneCount(oneCount, 0, i + 1);
			} else {
				count = oneCount(oneCount, i - n, oneCount.length - 1);
			}

			if (i >= a.length || a[i] == '0') {
				sumAtIndex = count;
			} else {
				sumAtIndex = n + 1 - count;
			}

			sum = (sum + sumAtIndex * power % mod) % mod;
			power = (power * 2) % mod;
		}

		return sum;
	}

	private static int oneCount(int[] counts, int from, int to) {
		if (to >= counts.length) {
			return oneCount(counts, from, counts.length - 1);
		}

		return counts[to] - counts[from];
	}
}
