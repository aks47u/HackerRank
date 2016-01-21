package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class The_Full_Counting_Sort {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int s = scn.nextInt();
		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		HashMap<Integer, ArrayList<Integer>> index_Map = new HashMap<Integer, ArrayList<Integer>>();
		int[] count = new int[100];

		for (int i = 0; i < s; i++) {
			int key = scn.nextInt();
			count[key]++;
			String value = scn.next();

			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
				index_Map.put(key, new ArrayList<Integer>());
			}

			index_Map.get(key).add(i);
			map.get(key).add(value);
		}

		scn.close();
		int mid = s / 2;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < 100; i++) {
			if (map.containsKey(i)) {
				for (int j = 0; j < map.get(i).size(); j++) {
					int index = index_Map.get(i).get(j);
					String string = map.get(i).get(j);

					if (index < mid) {
						sb.append("-").append(" ");
					} else {
						sb.append(string).append(" ");
					}
				}
			}
		}

		System.out.println(sb.toString());
	}
}
