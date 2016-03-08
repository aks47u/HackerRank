import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Minimum_Draws {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException,
	Exception {
		int T = Integer.parseInt(in.readLine());

		while (T-- > 0) {
			int n = Integer.parseInt(in.readLine());
			System.out.println(minimumDraw(n));
		}

		in.close();
	}

	private static int minimumDraw(int noOfPairs) {
		return (noOfPairs + 1);
	}
}
