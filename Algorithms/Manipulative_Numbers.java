import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Manipulative_Numbers {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int N = Integer.parseInt(s);
		String[] numbers = br.readLine().split(" ");
		int[] A = new int[N];
		int i;

		for (i = 0; i < N; i++) {
			A[i] = Integer.parseInt(numbers[i]);
		}

		int maxK = calMaxK(N, A);
		System.out.println(maxK);
	}

	private static int calMaxK(int N, int[] A) {
		int K = 32;
		int M = N;
		int target = N / 2;
		int i;
		int val;
		Integer intgr;

		while (M > target) {
			--K;
			Hashtable<Integer, Integer> count = new Hashtable<Integer, Integer>();
			M = 0;

			for (i = 0; i < N; i++) {
				intgr = Integer.valueOf(A[i] >> K);
				Integer c = count.get(intgr);
				val = (c == null) ? 1 : c.intValue() + 1;

				if (val > M) {
					M = val;
				}

				count.put(intgr, val);
			}
		}

		return K;
	}
}
