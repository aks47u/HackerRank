import java.util.Scanner;

public class Java_2D_Array {
	public static void main(String[] args) {
		int limit = 6;
		Scanner scn = new Scanner(System.in);
		int[][] hourglasses = new int[limit][limit];

		for (int i = 0; i < limit; i++) {
			for (int j = 0; j < limit; j++) {
				hourglasses[i][j] = scn.nextInt();
			}
		}

		scn.close();
		int largest = Integer.MIN_VALUE;

		for (int i = 0; i <= limit / 2; i++) {
			for (int j = 0; j <= limit / 2; j++) {
				int current = hourglasses[i][j] + hourglasses[i][j + 1]
						+ hourglasses[i][j + 2] + hourglasses[i + 1][j + 1]
								+ hourglasses[i + 2][j] + hourglasses[i + 2][j + 1]
										+ hourglasses[i + 2][j + 2];

				if (current > largest) {
					largest = current;
				}
			}
		}

		System.out.println(largest);
	}
}
