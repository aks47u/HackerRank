package Artificial_Intelligence_Bot_Building;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battleship_1_Player {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		@SuppressWarnings("unused")
		String ten = scn.nextLine();
		String[] board = new String[10];
		@SuppressWarnings("unused")
		boolean printed = false;

		for (int i = 0; i < 10; i++) {
			board[i] = scn.nextLine();
		}

		File fileName = new File("myfile.txt");

		if (!fileName.exists()) {
			try {
				fileName.createNewFile();
				FileWriter fileWrite = new FileWriter(fileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
				bufferedWriter.write("4 4");
				bufferedWriter.close();
				System.out.println("4 4");
			} catch (IOException e) {
			}
		} else {
			try {
				byte[] buffer = new byte[100];
				FileInputStream inputStream = new FileInputStream(fileName);
				@SuppressWarnings("unused")
				int readLines = -1;
				String last_move = "";

				while ((readLines = inputStream.read(buffer)) != -1) {
					last_move = new String(buffer);
				}

				inputStream.close();
				int x = Character.getNumericValue(last_move.charAt(0));
				int y = Character.getNumericValue(last_move.charAt(2));

				if (board[x].charAt(y) == 'h') {
					if (x + 1 < 10 && board[x + 1].charAt(y) == 'h') {
						for (int i = 1; i < 9 - x; i++) {
							if (x - i >= 0 && board[x - i].charAt(y) == '-') {
								x = x - i;

								break;
							} else if (x - i >= 0
									&& board[x - i].charAt(y) == 'm') {
								break;
							}
						}

						for (int i = 1; i < 9 - x; i++) {
							if (x + i < 10 && board[x + i].charAt(y) == '-') {
								x = x + i;

								break;
							} else if (x + i < 10
									&& board[x + i].charAt(y) == 'm') {
								break;
							}
						}
					} else if (x - 1 >= 0 && board[x - 1].charAt(y) == 'h') {
						for (int i = 1; i < 9 - x; i++) {
							if (x - i >= 0 && board[x - i].charAt(y) == '-') {
								x = x - i;

								break;
							} else if (x - i >= 0
									&& board[x - i].charAt(y) == 'm') {
								break;
							}
						}

						for (int i = 1; i < 9 - x; i++) {
							if (x + i < 10 && board[x + i].charAt(y) == '-') {
								x = x + i;

								break;
							} else if (x + i < 10
									&& board[x + i].charAt(y) == 'm') {
								break;
							}
						}
					} else if (y + 1 < 10 && board[x].charAt(y + 1) == 'h') {
						for (int i = 1; i < 9 - y; i++) {
							if (y - i >= 0 && board[x].charAt(y - i) == '-') {
								y = y - i;

								break;
							} else if (y - i >= 0
									&& board[x].charAt(y - i) == 'm') {
								break;
							}
						}

						for (int i = 1; i < 9 - y; i++) {
							if (y + i < 10 && board[x].charAt(y + i) == '-') {
								y = y + i;

								break;
							} else if (y + i < 10
									&& board[x].charAt(y + i) == 'm') {
								break;
							}
						}
					} else if (y - 1 >= 0 && board[x].charAt(y - 1) == 'h') {
						for (int i = 1; i < 9 - y; i++) {
							if (y - i >= 0 && board[x].charAt(y - i) == '-') {
								y = y - i;

								break;
							} else if (y - i >= 0
									&& board[x].charAt(y - i) == 'm') {
								break;
							}
						}

						for (int i = 1; i < 9 - y; i++) {
							if (y + i < 10 && board[x].charAt(y + i) == '-') {
								y = y + i;

								break;
							} else if (y + i < 10
									&& board[x].charAt(y + i) == 'm') {
								break;
							}
						}
					} else {
						ArrayList<Integer> moves = getRemainingHit(board);
						x = moves.get(0);
						y = moves.get(1);
					}
				} else {
					ArrayList<Integer> moves = getRemainingHit(board);
					x = moves.get(0);
					y = moves.get(1);
				}

				while (board[x].charAt(y) != '-') {
					ArrayList<Integer> moves = getRandom(board);
					x = moves.get(0);
					y = moves.get(1);
				}

				fileName.createNewFile();
				FileWriter fileWrite = new FileWriter(fileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
				bufferedWriter.write(x + " " + y);
				bufferedWriter.close();
				System.out.println(x + " " + y);
			} catch (IOException e) {
			}
		}
	}

	private static ArrayList<Integer> getRemainingHit(String[] board) {
		ArrayList<Integer> to_return = new ArrayList<Integer>();
		boolean added = false;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (board[i].charAt(j) == 'h') {
					if (i - 1 >= 0 && board[i - 1].charAt(j) == '-') {
						to_return.add(i - 1);
						to_return.add(j);
						added = true;
					} else if (i + 1 < 10 && board[i + 1].charAt(j) == '-') {
						to_return.add(i + 1);
						to_return.add(j);
						added = true;
					} else if (j - 1 >= 0 && board[i].charAt(j - 1) == '-') {
						to_return.add(i);
						to_return.add(j - 1);
						added = true;
					} else if (j + 1 < 10 && board[i].charAt(j + 1) == '-') {
						to_return.add(i);
						to_return.add(j + 1);
						added = true;
					}
				}

				if (added) {
					return to_return;
				}
			}
		}

		return getRandom(board);
	}

	private static ArrayList<Integer> getRandom(String[] board) {
		ArrayList<Integer> to_return = new ArrayList<Integer>();
		Random ran = new Random();
		int x = 0;
		int y = 0;

		do {
			x = ran.nextInt(10);
			y = ran.nextInt(10);
		} while (board[x].charAt(y) != '-');

		to_return.add(x);
		to_return.add(y);

		return to_return;
	}
}
