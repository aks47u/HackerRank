import java.util.Arrays;
import java.util.Scanner;

public class Flowers {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int k = scn.nextInt();
		long[] a = new long[n];

		for (int i = 0; i < n; i++) {
			a[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(a);
		long result = 0;

		for (int i = n - 1; i >= 0; i--) {
			result += a[i] * ((n - i - 1) / k + 1);
		}

		System.out.println(result);
	}
}
