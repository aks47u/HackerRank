import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Bricks_Game {
	private static int n;
	private static int[] a;
	private static Long[] f, g;

	public static void main(String[] args) throws Exception {
		BufferedReader scn = new BufferedReader(
				new InputStreamReader(System.in));
		StringTokenizer token = new StringTokenizer(scn.readLine());

		for (int T = Integer.parseInt(token.nextToken()); T > 0; T--) {
			n = Integer.parseInt(scn.readLine());
			a = new int[n];
			token = new StringTokenizer(scn.readLine());

			for (int i = 0; i < n; i++) {
				a[i] = Integer.parseInt(token.nextToken());
			}

			f = new Long[n];
			g = new Long[n];
			System.out.println(F(0));
		}

		scn.close();
	}

	private static long F(int i) {
		if (i >= n) {
			return 0;
		}

		if (f[i] != null) {
			return f[i];
		}

		f[i] = 0L;
		f[i] = Math.max(f[i], a[i] + G(i + 1));

		if (i + 1 < n) {
			f[i] = Math.max(f[i], a[i] + a[i + 1] + G(i + 2));
		}

		if (i + 2 < n) {
			f[i] = Math.max(f[i], a[i] + a[i + 1] + a[i + 2] + G(i + 3));
		}

		return f[i];
	}

	private static long G(int i) {
		if (i >= n) {
			return 0;
		}

		if (g[i] != null) {
			return g[i];
		}

		g[i] = Long.MAX_VALUE;
		g[i] = Math.min(g[i], F(i + 1));
		g[i] = Math.min(g[i], F(i + 2));
		g[i] = Math.min(g[i], F(i + 3));

		return g[i];
	}
}
