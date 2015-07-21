import java.util.Scanner;

public class Cube_Summation {
	private static Scanner scn;
	private static long[][][] matrix;
	private static int N;

	public static void main(String[] args) {
		scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			N = scn.nextInt();
			matrix = new long[101][101][101];
			int M = scn.nextInt();
			long[][][] actual = new long[101][101][101];

			for (int j = 0; j < M; j++) {
				String type = scn.next();

				if (type.equals("UPDATE")) {
					int x = scn.nextInt(), y = scn.nextInt(), z = scn.nextInt();
					long W = scn.nextLong();
					update(x, y, z, W - actual[x][y][z]);
					actual[x][y][z] = W;
				} else {
					int x1 = scn.nextInt(), y1 = scn.nextInt(), z1 = scn
							.nextInt();
					int x2 = scn.nextInt(), y2 = scn.nextInt(), z2 = scn
							.nextInt();
					long v1 = sum(x2, y2, z2) - sum(x1 - 1, y2, z2)
							- sum(x2, y1 - 1, z2) + sum(x1 - 1, y1 - 1, z2);
					long v2 = sum(x2, y2, z1 - 1) - sum(x1 - 1, y2, z1 - 1)
							- sum(x2, y1 - 1, z1 - 1)
							+ sum(x1 - 1, y1 - 1, z1 - 1);
					System.out.println(v1 - v2);
				}
			}
		}
	}

	private static void update(int x, int yy, int zz, long val) {
		while (x <= N) {
			int y = yy;

			while (y <= N) {
				int z = zz;

				while (z <= N) {
					matrix[x][y][z] += val;
					z += (z & -z);
				}

				y += (y & -y);
			}

			x += (x & -x);
		}
	}

	private static long sum(int x, int yy, int zz) {
		long result = 0;

		while (x > 0) {
			int y = yy;

			while (y > 0) {
				int z = zz;

				while (z > 0) {
					result += matrix[x][y][z];
					z -= (z & -z);
				}

				y -= (y & -y);
			}

			x -= (x & -x);
		}

		return result;
	}
}
