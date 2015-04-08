import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Digital_Camera_Autodetect_Day_or_Night {
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		long sum = 0;
		long count = 0;

		while (true) {
			String s = reader.readLine();

			if (s == null || s.trim().length() == 0) {
				break;
			}

			StringTokenizer tokenizer = new StringTokenizer(s, " \n\t\r,");

			while (tokenizer.hasMoreTokens()) {
				int value = Integer.parseInt(tokenizer.nextToken());
				sum += value;
				count++;
			}
		}

		double average = sum / count;
		System.out.println(average < 80 ? "night" : "day");
	}
}
