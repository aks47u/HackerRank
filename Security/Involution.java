import java.util.Scanner;

public class Involution {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		int[] f = new int[n];

		for (int i = 0; i < n; i++) {
			f[i] = scn.nextInt();
		}

		scn.close();
		boolean ok = true;

		for (int i = 0; i < n; i++) {
			if (f[f[i] - 1] != i + 1) {
				ok = false;
				break;
			}
		}

		System.out.println(ok ? "YES" : "NO");
	}
}
