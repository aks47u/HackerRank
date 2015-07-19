import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Find_Median {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		PriorityQueue<Integer> min = new PriorityQueue<Integer>(
				Collections.reverseOrder());
		PriorityQueue<Integer> max = new PriorityQueue<Integer>();
		int a = scn.nextInt();
		System.out.println(String.format("%.1f", (float) a));

		if (n == 1) {
			return;
		}

		int b = scn.nextInt();
		min.add(Math.min(a, b));
		max.add(Math.max(a, b));
		System.out.println(String.format("%.1f",
				((float) (max.peek() + min.peek())) / 2));

		for (int i = 2; i < n; i++) {
			int v = scn.nextInt();

			if (v >= max.peek()) {
				max.add(v);
			} else {
				min.add(v);
			}

			if (Math.abs(max.size() - min.size()) > 1) {
				if (min.size() > max.size()) {
					max.add(min.poll());
				} else {
					min.add(max.poll());
				}
			}

			if (max.size() == min.size()) {
				System.out.println(String.format("%.1f",
						((float) (max.peek() + min.peek())) / 2));
			} else if (max.size() > min.size()) {
				System.out.println(String.format("%.1f", (float) max.peek()));
			} else {
				System.out.println(String.format("%.1f", (float) min.peek()));
			}
		}
	}
}
