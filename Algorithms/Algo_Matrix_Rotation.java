import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

public class Solution {
	private StreamTokenizer in;
	private PrintWriter out;

	class Edge {
		public int v;
		public int u;
		public int cost;

		public Edge(int v, int u, int cost) {
			this.v = v;
			this.u = u;
			this.cost = cost;
		}
	}

	private int nextInt() throws IOException {
		in.nextToken();

		return (int) in.nval;
	}

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private void run() throws IOException {
		in = new StreamTokenizer(new BufferedReader(new InputStreamReader(
				System.in)));
		out = new PrintWriter(new OutputStreamWriter(System.out));
		int n = nextInt();
		int m = nextInt();
		int[][] a = new int[n][m];
		int[][] b = new int[n][m];
		int k = nextInt();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = nextInt();
			}
		}

		int x0 = 0;
		int y0 = 0;
		int xn = n - 1;
		int yn = m - 1;

		while (x0 <= xn && y0 <= yn) {
			List<Point> points = new ArrayList<Point>();

			for (int i = x0; i <= xn; i++) {
				points.add(new Point(i, y0));
			}

			for (int i = y0 + 1; i <= yn - 1; i++) {
				points.add(new Point(xn, i));
			}

			for (int i = xn; i >= x0; i--) {
				points.add(new Point(i, yn));
			}

			for (int i = yn - 1; i >= y0 + 1; i--) {
				points.add(new Point(x0, i));
			}

			int size = points.size();
			int shift = k % size;

			for (int i = 0; i < size; i++) {
				int p = i;
				int q = (size + i - shift) % size;
				Point point1 = points.get(p);
				Point point2 = points.get(q);
				b[point1.x][point1.y] = a[point2.x][point2.y];
			}

			x0++;
			y0++;
			xn--;
			yn--;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				out.print(b[i][j] + " ");
			}

			out.println();
		}

		out.flush();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new Solution().run();
	}
}
