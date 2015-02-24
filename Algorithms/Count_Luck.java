package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Count_Luck {
	private static BufferedReader br;
	private static StringTokenizer st;
	private static PrintWriter out;

	public static void main(String[] args) throws IOException {
		InputStream input = System.in;
		PrintStream output = System.out;
		br = new BufferedReader(new InputStreamReader(input));
		out = new PrintWriter(output);
		solve();
		out.close();
	}

	private static void solve() throws IOException {
		int tc = nextInt();
		int[] dx = { 0, 0, 1, -1 };
		int[] dy = { 1, -1, 0, 0 };

		while (tc-- > 0) {
			int rows = nextInt();
			int cols = nextInt();
			char[][] grid = new char[rows][];

			for (int i = 0; i < rows; i++) {
				grid[i] = nextToken().toCharArray();
			}

			int k = nextInt();
			int start = find(grid, 'M');
			int finish = find(grid, '*');
			int[] goTo = new int[rows * cols];
			Arrays.fill(goTo, -1);
			int[] queue = new int[rows * cols];
			int qh = 0, qt = 0;
			queue[qt++] = finish;
			goTo[finish] = finish;

			while (qh < qt) {
				int u = queue[qh++];
				int r = u / cols, c = u % cols;

				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dx[dir];
					int nc = c + dy[dir];

					if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
						continue;
					}

					if (grid[nr][nc] == 'X') {
						continue;
					}

					int v = nr * cols + nc;

					if (goTo[v] < 0) {
						goTo[v] = u;
						queue[qt++] = v;
					}
				}
			}

			if (goTo[start] < 0) {
				out.println("Oops!");
				continue;
			}

			int wand = 0;

			for (int i = start; i != finish; i = goTo[i]) {
				int possibleMoves = 0;
				int r = i / cols, c = i % cols;

				for (int dir = 0; dir < 4; dir++) {
					int nr = r + dx[dir];
					int nc = c + dy[dir];

					if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
						continue;
					}

					if (grid[nr][nc] == 'X') {
						continue;
					}

					++possibleMoves;
				}

				if (possibleMoves > 2 || (i == start && possibleMoves > 1)) {
					++wand;
				}
			}

			out.println(wand == k ? "Impressed" : "Oops!");
		}
	}

	private static int find(char[][] grid, char c) {
		int rows = grid.length;
		int cols = grid[0].length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j] == c) {
					return i * cols + j;
				}
			}
		}

		return -1;
	}

	private static int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	private static String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			String line = br.readLine();

			if (line == null) {
				return null;
			}

			st = new StringTokenizer(line);
		}

		return st.nextToken();
	}
}
