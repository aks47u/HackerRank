import java.util.Scanner;

public class Bijective_Functions {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		boolean[] bijective = new boolean[n];
		boolean ok = true;

		for (int i = 0; i < n; i++) {
			int v = scn.nextInt();

			if (bijective[v - 1]) {
				ok = false;
				break;
			}

			bijective[v - 1] = true;
		}

		scn.close();
		System.out.print(ok ? "YES" : "NO");
	}
}
