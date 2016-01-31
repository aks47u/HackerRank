package Artificial_Intelligence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battleship {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String out = scn.nextLine();

		if (out.equals("INIT")) {
			System.out.println("3 0");
			System.out.println("9 9");
			System.out.println("6 4:6 5");
			System.out.println("3 7:4 7");
			System.out.println("7 7:7 9");
			System.out.println("1 5:1 8");
			System.out.println("3 2:7 2");
		} else {
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
					BufferedWriter bufferedWriter = new BufferedWriter(
							fileWrite);
					ArrayList<Integer> moves = getRandom(board);
					int x = moves.get(0);
					int y = moves.get(1);
					bufferedWriter.write(x + " " + y);
					bufferedWriter.close();
					System.out.println(x + " " + y);
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
									x -= i;
									break;
								} else if (x - i >= 0
										&& board[x - i].charAt(y) == 'm') {
									break;
								}
							}

							for (int i = 1; i < 9 - x; i++) {
								if (x + i < 10 && board[x + i].charAt(y) == '-') {
									x += i;
									break;
								} else if (x + i < 10
										&& board[x + i].charAt(y) == 'm') {
									break;
								}
							}
						} else if (x - 1 >= 0 && board[x - 1].charAt(y) == 'h') {
							for (int i = 1; i < 9 - x; i++) {
								if (x - i >= 0 && board[x - i].charAt(y) == '-') {
									x -= i;
									break;
								} else if (x - i >= 0
										&& board[x - i].charAt(y) == 'm') {
									break;
								}
							}

							for (int i = 1; i < 9 - x; i++) {
								if (x + i < 10 && board[x + i].charAt(y) == '-') {
									x += i;
									break;
								} else if (x + i < 10
										&& board[x + i].charAt(y) == 'm') {
									break;
								}
							}
						} else if (y + 1 < 10 && board[x].charAt(y + 1) == 'h') {
							for (int i = 1; i < 9 - y; i++) {
								if (y - i >= 0 && board[x].charAt(y - i) == '-') {
									y -= i;
									break;
								} else if (y - i >= 0
										&& board[x].charAt(y - i) == 'm') {
									break;
								}
							}

							for (int i = 1; i < 9 - y; i++) {
								if (y + i < 10 && board[x].charAt(y + i) == '-') {
									y += i;
									break;
								} else if (y + i < 10
										&& board[x].charAt(y + i) == 'm') {
									break;
								}
							}
						} else if (y - 1 >= 0 && board[x].charAt(y - 1) == 'h') {
							for (int i = 1; i < 9 - y; i++) {
								if (y - i >= 0 && board[x].charAt(y - i) == '-') {
									y -= i;
									break;
								} else if (y - i >= 0
										&& board[x].charAt(y - i) == 'm') {
									break;
								}
							}

							for (int i = 1; i < 9 - y; i++) {
								if (y + i < 10 && board[x].charAt(y + i) == '-') {
									y += i;
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
					BufferedWriter bufferedWriter = new BufferedWriter(
							fileWrite);
					bufferedWriter.write(x + " " + y);
					bufferedWriter.close();
					System.out.println(x + " " + y);
				} catch (IOException e) {
				}
			}
		}
	}

	private static ArrayList<Integer> getRemainingHit(String[] board) {
		ArrayList<Integer> to_return = new ArrayList<Integer>();
		boolean added = false;

		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				if (board[x].charAt(y) == 'h') {
					if (x - 1 >= 0 && board[x - 1].charAt(y) == '-') {
						to_return.add(x - 1);
						to_return.add(y);
						added = true;
					} else if (x + 1 < 10 && board[x + 1].charAt(y) == '-') {
						to_return.add(x + 1);
						to_return.add(y);
						added = true;
					} else if (y - 1 >= 0 && board[x].charAt(y - 1) == '-') {
						to_return.add(x);
						to_return.add(y - 1);
						added = true;
					} else if (y + 1 < 10 && board[x].charAt(y + 1) == '-') {
						to_return.add(x);
						to_return.add(y + 1);
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
