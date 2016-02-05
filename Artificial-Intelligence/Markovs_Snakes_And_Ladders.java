import java.util.ArrayList;
import java.util.Scanner;

public class Markovs_Snakes_And_Ladders {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int boards = scn.nextInt();
		scn.nextLine();

		for (int b = 0; b < boards; b++) {
			ArrayList<Integer> probs = setDieProbs(scn.nextLine());
			int ladderAndSnakeCount = setEventCount(scn.nextLine());
			int[][] events = setEvents(scn.nextLine(), scn.nextLine(),
					ladderAndSnakeCount);
			System.out.println(findEstimatedNumberOfTurns(probs, events));
		}
		
		scn.close();
	}

	private static int findEstimatedNumberOfTurns(ArrayList<Integer> probs,
			int[][] events) {
		int turnTotal = 0;
		int simCount = 0;

		for (int i = 0; i < 5000; i++) {
			int turns = simulateGame(probs, events);

			if (turns < 1001) {
				turnTotal += turns;
				simCount++;
			}
		}

		if (simCount > 0) {
			return turnTotal / simCount;
		} else {
			return 0;
		}
	}

	private static int simulateGame(ArrayList<Integer> probs, int[][] events) {
		int turns = 0;
		int currentSquare = 1;
		boolean gameFinished = false;

		while (!gameFinished && turns <= 1001) {
			turns++;
			int roll = rollDie(probs);

			if (currentSquare + roll <= 100) {
				currentSquare += roll;
			}

			currentSquare = processEvent(currentSquare, events);

			if (currentSquare == 100) {
				gameFinished = true;
			}
		}

		return turns;
	}

	private static int processEvent(int currentSquare, int[][] events) {
		int square = currentSquare;

		for (int i = 0; i < events.length; i++) {
			if (events[i][0] == square) {
				square = events[i][1];
			}
		}

		return square;
	}

	private static int rollDie(ArrayList<Integer> probs) {
		if (probs.size() != 6) {
			return 0;
		}

		int randInt = (int) (Math.random() * 100) + 1;

		for (int i = 0; i < 6; i++) {
			if (randInt < probs.get(i)) {
				return i + 1;
			}
		}

		return 0;
	}

	private static ArrayList<Integer> setDieProbs(String probList) {
		ArrayList<Integer> probs = new ArrayList<Integer>();
		String[] probStrings = probList.split(",");

		for (int i = 0; i < probStrings.length; i++) {
			int prob = (int) (Double.parseDouble(probStrings[i]) * 100);

			if (i > 0) {
				prob += probs.get(i - 1);
			}

			probs.add(prob);
		}

		return probs;
	}

	private static int setEventCount(String countList) {
		int count = 0;
		String[] countStrings = countList.split(",");

		for (int i = 0; i < countStrings.length; i++) {
			count += Integer.valueOf(countStrings[i]).intValue();
		}

		return count;
	}

	private static int[][] setEvents(String ladderList, String snakeList,
			int eventCount) {
		int[][] events = new int[eventCount][2];
		int index = 0;
		@SuppressWarnings("resource")
		Scanner ladderScan = new Scanner(ladderList);

		while (ladderScan.hasNext()) {
			String[] ladder = ladderScan.next().split(",");
			events[index][0] = Integer.valueOf(ladder[0]).intValue();
			events[index][1] = Integer.valueOf(ladder[1]).intValue();
			index++;
		}

		@SuppressWarnings("resource")
		Scanner snakeScan = new Scanner(snakeList);

		while (snakeScan.hasNext()) {
			String[] snake = snakeScan.next().split(",");
			events[index][0] = Integer.valueOf(snake[0]).intValue();
			events[index][1] = Integer.valueOf(snake[1]).intValue();
			index++;
		}

		return events;
	}
}
