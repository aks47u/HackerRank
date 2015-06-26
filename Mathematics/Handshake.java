import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Handshake {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException,
	Exception {
		int T = Integer.parseInt(in.readLine());

		for (int i = 0; i < T; i++) {
			int numofPersons = Integer.parseInt(in.readLine());
			System.out.println((int) (numofPersons * (numofPersons - 1)) / 2);
		}

		in.close();
	}
}
