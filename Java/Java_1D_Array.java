import java.util.Scanner;

public class Java_1D_Array {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; ++i) {
			int n = scn.nextInt();
			int arr[] = new int[n];
			int m = scn.nextInt();

			for (int j = 0; j < n; j++) {
				arr[j] = scn.nextInt();
			}

			boolean canWin = jump(arr, n, m, 0);

			if (canWin) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}

		scn.close();
	}

	private static boolean jump(int[] arr, int n, int m, int i) {
		if (i >= n) {
			return true;
		}

		if (arr[i] == 1) {
			return false;
		}

		if (jump(arr, n, m, i + 1)) {
			return true;
		}

		if (jump(arr, n, m, i + m)) {
			return true;
		}

		return false;
	}
}
