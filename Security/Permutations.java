import java.util.Scanner;

public class Permutations {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] f = new int[n];

		for (int i = 0; i < n; i++) {
			f[i] = scn.nextInt();
		}

		scn.close();

		for (int i = 0; i < n; i++) {
			System.out.println(f[f[i] - 1]);
		}
	}
}
