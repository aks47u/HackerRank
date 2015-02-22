package Algorithms;

import java.util.Scanner;

public class The_Grid_Search {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int set = 0; set < T; set++) {
			int R = scn.nextInt();
			int C = scn.nextInt();
			scn.nextLine();

			String[] grid = new String[R];

			for (int i = 0; i < R; i++) {
				grid[i] = scn.nextLine();
			}

			int r = scn.nextInt();
			int c = scn.nextInt();
			scn.nextLine();
			String[] subgrid = new String[r];

			for (int i = 0; i < r; i++) {
				subgrid[i] = scn.nextLine();
			}

			boolean found = false;

			for (int i = 0; !found && i < R - r + 1; i++) {
				for (int j = 0; !found && j < C - c + 1; j++) {
					if (subgrid[0].equals(grid[i].substring(j, j + c))) {
						System.err.println("We found a substring at row=" + i
								+ ", col=" + j);
						found = true;

						for (int k = i + 1; found && k < r + i; k++) {
							System.err.println("  The substring = "
									+ grid[k].substring(j, j + c));
							found &= subgrid[k - i].equals(grid[k].substring(j,
									j + c));
						}
					}
				}
			}

			System.out.println(found ? "YES" : "NO");
		}

		scn.close();
	}
}
