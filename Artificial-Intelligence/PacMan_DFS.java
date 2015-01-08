package Artificial_Intelligence_A_Search;

import java.util.Scanner;
import java.util.Stack;

public class PacMan_DFS {
	private static Stack<String> current_path = new Stack<String>();
	private static Stack<String> final_path = new Stack<String>();
	private static boolean[][] traveled;
	private static boolean found = false;

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int pacman_x = scn.nextInt();
		int pacman_y = scn.nextInt();
		int food_x = scn.nextInt();
		int food_y = scn.nextInt();
		int x = scn.nextInt();
		int y = scn.nextInt();
		String[] grid = new String[x];

		for (int i = 0; i < x; i++) {
			grid[i] = scn.next();
		}

		traveled = new boolean[x][y];
		dfs(x, y, pacman_x, pacman_y, food_x, food_y, grid);
		String[] curr_path = current_path.toArray(new String[0]);
		String[] fin_path = final_path.toArray(new String[0]);
		System.out.println(curr_path.length);

		for (int i = 0; i < curr_path.length; i++) {
			System.out.println(curr_path[i]);
		}

		System.out.println(fin_path.length - 1);

		for (int i = fin_path.length; i > 0; i--) {
			System.out.println(fin_path[i - 1]);
		}

		scn.close();
	}

	private static boolean dfs(int x, int y, int pacman_x, int pacman_y,
			int food_x, int food_y, String[] grid) {
		traveled[pacman_x][pacman_y] = true;
		Stack<String> total = new Stack<String>();

		if (pacman_x == food_x && pacman_y == food_y) {
			current_path.add(pacman_x + " " + pacman_y);
			final_path.add(pacman_x + " " + pacman_y);

			return true;
		}

		if (found == false) {
			if (pacman_x - 1 >= 0 && grid[pacman_x - 1].charAt(pacman_y) != '%'
					&& traveled[pacman_x - 1][pacman_y] == false) {
				total.add("UP");
				traveled[pacman_x - 1][pacman_y] = true;
			}

			if (pacman_y - 1 >= 0 && grid[pacman_x].charAt(pacman_y - 1) != '%'
					&& traveled[pacman_x][pacman_y - 1] == false) {
				total.add("LEFT");
				traveled[pacman_x][pacman_y - 1] = true;
			}

			if (pacman_y + 1 <= y && grid[pacman_x].charAt(pacman_y + 1) != '%'
					&& traveled[pacman_x][pacman_y + 1] == false) {
				total.add("RIGHT");
				traveled[pacman_x][pacman_y + 1] = true;
			}

			if (pacman_x + 1 <= x && grid[pacman_x + 1].charAt(pacman_y) != '%'
					&& traveled[pacman_x + 1][pacman_y] == false) {
				total.add("DOWN");
				traveled[pacman_x + 1][pacman_y] = true;
			}

			if (!total.empty()) {
				current_path.add(pacman_x + " " + pacman_y);
				String direction = total.pop(); // Get the first node

				if (direction.contains("DOWN") && found == false) {
					found = dfs(x, y, pacman_x + 1, pacman_y, food_x, food_y,
							grid);

					if (found == true) {
						final_path.add(pacman_x + " " + pacman_y);
						total = new Stack<String>();
					}

					if (!total.empty() && found == false) {
						direction = total.pop();
					}
				}

				if (direction.contains("RIGHT") && found == false) {
					found = dfs(x, y, pacman_x, pacman_y + 1, food_x, food_y,
							grid);

					if (found == true) {
						final_path.add(pacman_x + " " + pacman_y);
						total = new Stack<String>();
					}

					if (!total.empty() && found == false) {
						direction = total.pop();
					}
				}

				if (direction.contains("LEFT") && found == false) {
					found = dfs(x, y, pacman_x, pacman_y - 1, food_x, food_y,
							grid);

					if (found == true) {
						final_path.add(pacman_x + " " + pacman_y);
						total = new Stack<String>();
					}

					if (!total.empty() && found == false) {
						direction = total.pop();
					}
				}

				if (direction.contains("UP") && found == false) {
					found = dfs(x, y, pacman_x - 1, pacman_y, food_x, food_y,
							grid);

					if (found == true) {
						final_path.add(pacman_x + " " + pacman_y);
						total = new Stack<String>();
					}
				}
			} else {
				current_path.add(pacman_x + " " + pacman_y);

				return false;
			}
		}

		return found;
	}
}
