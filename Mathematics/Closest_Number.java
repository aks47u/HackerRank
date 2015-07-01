import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Closest_Number {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int x, a, b;
		long storage;
		String[] values = new String[3];
		int testc = Integer.parseInt(br.readLine());

		while (testc > 0) {
			values = br.readLine().split(" ");
			a = Integer.parseInt(values[0]);
			b = Integer.parseInt(values[1]);
			x = Integer.parseInt(values[2]);
			storage = (long) Math.pow(a, b);

			if (storage % x > x / 2) {
				System.out.println(storage + x - storage % x);
			} else {
				System.out.println(storage - storage % x);
			}

			testc--;
		}
	}
}
