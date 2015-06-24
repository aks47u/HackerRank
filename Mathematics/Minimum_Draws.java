import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Minimum_Draws {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException,
	Exception {
		int T = Integer.parseInt(in.readLine());

		for (int i = 0; i < T; i++) {
			int noOfPairs = Integer.parseInt(in.readLine());
			System.out.println(minimumDraw(noOfPairs));
		}

		in.close();
	}

	private static int minimumDraw(int noOfPairs) {
		return (noOfPairs + 1);
	}
}
