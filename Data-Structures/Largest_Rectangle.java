import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Largest_Rectangle {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in), 64 * 1024);
		final int N = Integer.parseInt(br.readLine().trim(), 10);
		final String[] data = br.readLine().trim().split(" ");
		final long[] hist = new long[N];

		for (int i = 0; i < N; i++) {
			final long v = Long.parseLong(data[i], 10);
			hist[i] = v;
		}

		long res0 = 0L;

		for (int i = 0; i < N; i++) {
			int idx0 = i;

			for (; idx0 >= 1; idx0--) {
				if (hist[idx0 - 1] < hist[i]) {
					break;
				}
			}

			int idx1 = i;

			for (; idx1 < hist.length - 1; idx1++) {
				if (hist[idx1 + 1] < hist[i]) {
					break;
				}
			}

			final long area = hist[i] * (idx1 - idx0 + 1);

			if (area > res0) {
				res0 = area;
			}
		}

		System.out.println(res0);
		br.close();
		br = null;
	}
}
