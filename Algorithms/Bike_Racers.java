import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Bike_Racers {
	static BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));
	static StringBuilder out = new StringBuilder();
	private static Node source;
	private static Node sink;
	private static Node[] bikers;
	private static Node[] bikes;

	public static void main(String[] args) throws IOException {
		String line = in.readLine();
		String[] data = line.split("\\s+");
		int numBikers = Integer.parseInt(data[0]);
		int numBikes = Integer.parseInt(data[1]);
		int numRequired = Integer.parseInt(data[2]);
		source = new Node();
		sink = new Node(true);
		bikers = new Node[numBikers];
		bikes = new Node[numBikes];
		Coordinate[] bikerPos = new Coordinate[numBikers];

		for (int i = 0; i < numBikers; i++) {
			bikers[i] = new Node();
			source.addConnection(bikers[i]);
			line = in.readLine();
			data = line.split("\\s+");
			bikerPos[i] = new Coordinate(Integer.parseInt(data[0]),
					Integer.parseInt(data[1]));
		}

		ArrayList<BikerBikeDistance> bbd = new ArrayList<>();

		for (int j = 0; j < numBikes; j++) {
			bikes[j] = new Node();
			bikes[j].addConnection(sink);
			line = in.readLine();
			data = line.split("\\s+");
			int bx = Integer.parseInt(data[0]);
			int by = Integer.parseInt(data[1]);

			for (int i = 0; i < numBikers; i++) {
				bbd.add(new BikerBikeDistance(i, j, getCost(bx, by,
						bikerPos[i].x, bikerPos[i].y)));
			}
		}

		Collections.sort(bbd);
		int total = 0;
		long dist = 0;

		for (int i = 0; total < numRequired; i++) {
			BikerBikeDistance cbbd = bbd.get(i);
			dist = cbbd.cost;
			bikers[cbbd.biker].addConnection(bikes[cbbd.bike]);

			if (source.dfsAndReverse(i)) {
				total++;
			}
		}

		System.out.println(dist);
	}

	private static long getCost(long x1, long y1, long x2, long y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	private static class Coordinate {
		final int x;
		final int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private static class BikerBikeDistance implements
	Comparable<BikerBikeDistance> {
		final int biker;
		final int bike;
		final long cost;
		String name;

		public BikerBikeDistance(int biker, int bike, long cost) {
			this.biker = biker;
			this.bike = bike;
			this.cost = cost;
		}

		@Override
		public int compareTo(BikerBikeDistance o) {
			if (cost < o.cost) {
				return -1;
			}

			if (cost > o.cost) {
				return 1;
			}

			return 0;
		}
	}

	private static class Node {
		private LinkedList<Node> connections;
		private int visitedNum;
		private boolean isTerminus;

		public Node() {
			connections = new LinkedList<Node>();
			visitedNum = -999;
			isTerminus = false;
		}

		public Node(boolean terminus) {
			connections = new LinkedList<Node>();
			visitedNum = -999;
			isTerminus = terminus;
		}

		public int getVisited() {
			return visitedNum;
		}

		public void addConnection(Node n) {
			connections.add(n);
		}

		public boolean dfsAndReverse(int v) {
			if (isTerminus) {
				return true;
			}

			visitedNum = v;
			Iterator<Node> i = connections.iterator();

			while (i.hasNext()) {
				Node n = i.next();

				if (n.getVisited() != v) {
					if (n.dfsAndReverse(v)) {
						n.addConnection(this);
						i.remove();

						return true;
					}
				}
			}

			return false;
		}
	}
}
