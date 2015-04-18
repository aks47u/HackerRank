import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class Hyper_Strings {
	private static int MOD = 1000000007;
	private static String[] arr, pat;
	private static int N, M;
	private static ArrayList<String>[] endXpat;

	public static void main(String[] args) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			String[] s = br.readLine().trim().split(" ");
			N = Integer.parseInt(s[0].trim());
			M = Integer.parseInt(s[1].trim());
			arr = new String[N];
			int count = 0;

			for (int i = 0; i < N; i++) {
				String str = br.readLine().trim();

				if (str.length() == 0) {
					continue;
				}

				arr[count++] = str;
			}

			if (count != N) {
				N = count;
				arr = Arrays.copyOf(arr, N);
			}

			fillSet();
			int res = doit();
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static int doit() {
		int size = pat.length;
		int[][] dp = new int[M + 1][size];
		int[][] dp2 = new int[M + 1][10];
		int[][] dp3 = new int[M + 1][10];
		Arrays.fill(dp3[0], 1);

		for (int m = 1; m <= M; m++) {
			for (int i = 0; i < size; i++) {
				if (pat[i].length() > m) {
					continue;
				}

				String curr = pat[i];
				dp[m][i] = dp3[m - curr.length()][curr.charAt(0) - 'a'];
			}

			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < size; j++) {
					String curr = pat[j];

					if (curr.charAt(curr.length() - 1) - 'a' == i) {
						dp2[m][i] = (dp2[m][i] + dp[m][j]) % MOD;
					}
				}
			}

			for (int i = 0; i < 10; i++) {
				for (int j = i; j < 10; j++) {
					dp3[m][i] = (dp3[m][i] + dp2[m][j]) % MOD;
				}
			}
		}

		int res = 0;

		for (int m = 0; m <= M; m++) {
			res = (res + dp3[m][0]) % MOD;
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	private static void fillSet() {
		Arrays.sort(arr);
		TreeSet<String> set = new TreeSet<String>();
		set.add("");
		char[] store = new char[10];

		for (int len = 1; len <= 10; len++) {
			fillSet(store, 0, 'a' - 1, len, set);
		}

		set.remove("");
		int n = set.size();
		pat = new String[n];
		int index = 0;

		for (String str : set) {
			pat[index++] = str;
		}

		endXpat = new ArrayList[10];

		for (int i = 0; i < 10; i++) {
			endXpat[i] = new ArrayList<String>();
		}

		for (String str : pat) {
			endXpat[str.charAt(str.length() - 1) - 'a'].add(str);
		}
	}

	private static void fillSet(char[] store, int index, int last, int len,
			TreeSet<String> set) {
		if (index == len) {
			String curr = new String(Arrays.copyOf(store, len));

			for (String pre : arr) {
				if (pre.length() <= curr.length() && curr.indexOf(pre) == 0
						&& set.contains(curr.substring(pre.length()))) {
					set.add(curr);
					break;
				}
			}

			return;
		}

		for (int i = last + 1; i <= 'j'; i++) {
			store[index] = (char) i;
			fillSet(store, index + 1, i, len, set);
		}
	}
}
