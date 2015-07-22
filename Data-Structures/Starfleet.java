import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Starfleet {
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	private static StringBuilder out = new StringBuilder();
	private static ArrayList<Integer> allYs = new ArrayList<Integer>();
	private static ArrayList<Integer> yStart = new ArrayList<Integer>();
	private static ArrayList<Integer> yEnd = new ArrayList<Integer>();

	public static void main(String[] args) throws IOException {
		String line = in.readLine();
		String[] data = line.split(" ");
		int numShips = Integer.parseInt(data[0]);
		int numQueries = Integer.parseInt(data[1]);
		HashMap<Integer, Integer> foundFrequencies = new HashMap<Integer, Integer>();
		PosFreqPair[] ships = new PosFreqPair[numShips];

		for (int i = 0; i < numShips; i++) {
			line = in.readLine();
			data = line.split(" ");
			ships[i] = new PosFreqPair();
			ships[i].pos = Integer.parseInt(data[1]);
			ships[i].freq = Integer.parseInt(data[2]);

			if (!foundFrequencies.containsKey(ships[i].freq)) {
				foundFrequencies.put(ships[i].freq, foundFrequencies.size());
			}
		}

		Arrays.sort(ships);
		int[] shipFreqMap = new int[numShips];
		int[] freqCount = new int[numShips];
		ArrayList<ArrayList<Integer>> freqPos = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < foundFrequencies.size(); i++) {
			freqPos.add(new ArrayList<Integer>());
		}

		for (int i = 0; i < numShips; i++) {
			if (i == 0) {
				allYs.add(ships[i].pos);
				yStart.add(0);
			} else {
				if (ships[i].pos != allYs.get(allYs.size() - 1)) {
					allYs.add(ships[i].pos);
					yEnd.add(i - 1);
					yStart.add(i);
				}

				if (i == numShips - 1) {
					yEnd.add(i);
				}
			}

			shipFreqMap[i] = foundFrequencies.get(ships[i].freq);
			freqCount[i] = freqPos.get(shipFreqMap[i]).size();
			freqPos.get(shipFreqMap[i]).add(i);
		}

		int groupSize = (int) Math.sqrt(numShips);
		int numGroups = numShips / groupSize;
		int[][] spanMaxFreq = new int[numGroups][numGroups];

		for (int i = 0; i < numGroups; i++) {
			int currMax = 0;
			int[] currCount = new int[freqPos.size()];
			int k = i * groupSize;

			for (int j = i; j < numGroups; j++) {
				while (k < (j + 1) * groupSize) {
					currCount[shipFreqMap[k]]++;
					currMax = Math.max(currMax, currCount[shipFreqMap[k]]);
					k++;
				}

				spanMaxFreq[i][j] = currMax;
			}
		}

		for (int q = 0; q < numQueries; q++) {
			line = in.readLine();
			data = line.split(" ");
			int minY = Integer.parseInt(data[1]);
			int maxY = Integer.parseInt(data[0]);
			int minIndex = findMinimumBound(minY);
			int maxIndex = findMaximumBound(maxY);

			if (minIndex > maxIndex || minIndex == -1 || maxIndex == -1) {
				out.append(0 + "\n");
			} else {
				int minSpan = (minIndex + groupSize - 1) / groupSize;
				int maxSpan = (maxIndex + 1) / groupSize - 1;
				int bestFrequency = 0;

				if (minSpan <= maxSpan) {
					bestFrequency = spanMaxFreq[minSpan][maxSpan];
				}

				int prefixEnd = minSpan * groupSize - 1;
				int suffixStart = (maxSpan + 1) * groupSize;

				if (suffixStart <= prefixEnd) {
					prefixEnd = maxIndex;
					suffixStart = maxIndex + 1;
				}

				for (int i = minIndex; i <= prefixEnd; i++) {
					int myFreq = shipFreqMap[i];

					if (freqCount[i] == 0
							|| freqPos.get(myFreq).get(freqCount[i] - 1) < minIndex) {
						while (freqCount[i] + bestFrequency < freqPos.get(
								myFreq).size()
								&& freqPos.get(myFreq).get(
										freqCount[i] + bestFrequency) <= maxIndex) {
							bestFrequency++;
						}
					}
				}

				for (int i = suffixStart; i <= maxIndex; i++) {
					int myFreq = shipFreqMap[i];

					if (freqCount[i] == freqPos.get(myFreq).size() - 1
							|| freqPos.get(myFreq).get(freqCount[i] + 1) > maxIndex) {
						while (freqCount[i] - bestFrequency >= 0
								&& freqPos.get(myFreq).get(
										freqCount[i] - bestFrequency) >= minIndex) {
							bestFrequency++;
						}
					}
				}

				out.append(bestFrequency + "\n");
			}
		}

		System.out.print(out);
	}

	private static int findMinimumBound(int y) {
		return findMinimumBound(y, 0, allYs.size() - 1);
	}

	private static int findMinimumBound(int y, int i, int j) {
		if (j < i) {
			if (i >= allYs.size()) {
				return -1;
			}

			return yStart.get(i);
		}

		if (i == j) {
			if (allYs.get(i) >= y) {
				return yStart.get(i);
			} else {
				if (i == allYs.size() - 1) {
					return -1;
				}

				return yStart.get(i + 1);
			}
		}

		int avg = (i + j) / 2;

		if (allYs.get(avg) == y) {
			return yStart.get(avg);
		} else if (allYs.get(avg) > y) {
			return findMinimumBound(y, i, avg - 1);
		} else {
			return findMinimumBound(y, avg + 1, j);
		}
	}

	private static int findMaximumBound(int y) {
		return findMaximumBound(y, 0, allYs.size() - 1);
	}

	private static int findMaximumBound(int y, int i, int j) {
		if (i > j) {
			if (j < 0) {
				return -1;
			}

			return yEnd.get(j);
		}

		if (i == j) {
			if (allYs.get(i) <= y) {
				return yEnd.get(i);
			} else {
				if (i == 0) {
					return -1;
				}

				return yEnd.get(i - 1);
			}
		}

		int avg = (i + j) / 2;

		if (allYs.get(avg) == y) {
			return yEnd.get(avg);
		} else if (allYs.get(avg) > y) {
			return findMaximumBound(y, i, avg - 1);
		} else {
			return findMaximumBound(y, avg + 1, j);
		}
	}

	private static class PosFreqPair implements Comparable<PosFreqPair> {
		int pos, freq;

		@Override
		public int compareTo(PosFreqPair o) {
			return pos - o.pos;
		}
	}
}
