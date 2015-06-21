import java.util.Scanner;

public class Inverse_of_a_function {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		int[] inverse = new int[N];

		for (int i = 0; i < N; i++) {
			int v = scn.nextInt();
			inverse[v - 1] = i + 1;
		}

		scn.close();

		for (int i = 0; i < N; i++) {
			System.out.println(inverse[i]);
		}
	}
}
