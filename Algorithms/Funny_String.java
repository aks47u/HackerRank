import java.util.Scanner;

public class Funny_String {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			String S = scn.next();
			int N = S.length();
			boolean fun = true;

			for (int j = 1; j < N; j++) {
				int sdiff = Math.abs(S.charAt(j) - S.charAt(j - 1));
				int rdiff = Math.abs(S.charAt(N - j) - S.charAt(N - j - 1));

				if (!(sdiff == rdiff)) {
					fun = false;
					break;
				}
			}

			System.out.println(fun ? "Funny" : "Not Funny");
		}

		scn.close();
	}
}
