import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Find_Strings {
	public static void main(String[] args) {
		new Find_Strings().solveProblem();
		out.close();
	}

	static Scanner in = new Scanner(new InputStreamReader(System.in));
	static PrintStream out = new PrintStream(new BufferedOutputStream(
			System.out));
	int[] maxx;
	String s = "";

	public void solveProblem() {
		int n = in.nextInt();
		in.nextLine();
		String[] sn = new String[n];

		for (int i = 0; i < n; i++) {
			sn[i] = in.nextLine() + "A";
			s += sn[i];
		}

		T = s.toCharArray();
		maxx = new int[T.length];
		int som = 0;

		for (int i = 0; i < n; i++) {
			int nu = sn[i].length();

			for (int j = som; j < som + nu; j++) {
				maxx[j] = som + nu;
			}

			som += nu;
		}

		this.n = T.length;
		constructSA();
		computeLCP();
		int q = in.nextInt();
		in.nextLine();

		for (int i = 0; i < q; i++) {
			losOp(in.nextLong());
		}
	}

	void losOp(long k) {
		int start = 0;

		for (int i = 0; i < n; i++) {
			int ind = SA[i];
			start = LCP[i];
			long aantal = Math.max(0, maxx[ind] - 1 - ind - start);

			if (T[ind] != 'A' && aantal >= k) {
				out.println(s.substring(ind, (int) (ind + start + k)));

				return;
			} else if (T[ind] != 'A') {
				k -= aantal;
			}
		}

		out.println("INVALID");
	}

	int maxlen = 100010;
	int n;
	char[] T;
	int[] RA = new int[maxlen];
	int[] RATemp = new int[maxlen];
	int[] SA = new int[maxlen];
	int[] SATemp = new int[maxlen];
	int[] c = new int[maxlen];

	void constructSA() {
		for (int i = 0; i < n; i++) {
			RA[i] = T[i] - '.';
			SA[i] = i;
		}

		for (int k = 1; k < n; k <<= 1) {
			countingSort(k);
			countingSort(0);
			RATemp[SA[0]] = 1;
			int r = 1;

			for (int i = 1; i < n; i++) {
				RATemp[SA[i]] = (RA[SA[i]] == RA[SA[i - 1]] && RA[SA[i] + k] == RA[SA[i - 1]
						+ k]) ? r : ++r;
			}

			RA = RATemp.clone();
		}
	}

	void countingSort(int k) {
		int sum = 0;
		int maxi = Math.max(300, n);
		Arrays.fill(c, 0);

		for (int i = 0; i < n; i++) {
			c[(i + k) < n ? RA[i + k] : 0]++;
		}

		for (int i = 0; i <= maxi; i++) {
			int t = c[i];
			c[i] = sum;
			sum += t;
		}

		for (int i = 0; i < n; i++) {
			SATemp[c[(SA[i] + k) < n ? RA[SA[i] + k] : 0]++] = SA[i];
		}

		SA = SATemp.clone();
	}

	int[] Phi;
	int[] LCP;
	int max = 0;

	void computeLCP() {
		LCP = new int[n];
		Phi = new int[n];
		int[] PLCP = new int[n];
		Phi[SA[0]] = -1;

		for (int i = 1; i < n; i++) {
			Phi[SA[i]] = SA[i - 1];
		}

		int L = 0;

		for (int i = 0; i < n; i++) {
			if (Phi[i] == -1) {
				PLCP[i] = 0;
				continue;
			}

			while (i + L < n && Phi[i] + L < n && T[i + L] == T[Phi[i] + L]) {
				L++;
			}

			max = Math.max(max, L);
			PLCP[i] = L;
			L = Math.max(L - 1, 0);
		}

		for (int i = 1; i < n; i++) {
			LCP[i] = PLCP[SA[i]];
		}
	}
}
