import java.util.Scanner;

public class Java_Loops {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int t = scn.nextInt();

		for (int i = 0; i < t; i++) {
			int a = scn.nextInt();
			int b = scn.nextInt();
			int n = scn.nextInt();
			long total = a;

			for (int j = 0; j < n; j++) {
				total += (Math.pow(2, j) * b);
				System.out.print(total + " ");
			}

			System.out.println();
		}

		scn.close();
	}
}
