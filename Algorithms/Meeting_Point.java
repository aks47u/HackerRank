import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Meeting_Point {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String input = reader.readLine();
		Integer N = Integer.valueOf(input.trim());
		List<Point> points = new LinkedList<Point>();
		Point centroid = new Point();
		centroid.x = 0;
		centroid.y = 0;

		for (int i = 0; i < N; i++) {
			input = reader.readLine();
			String[] parts = input.trim().split(" ");
			Point p = new Point();
			p.x = Long.valueOf(parts[0]);
			p.y = Long.valueOf(parts[1]);
			centroid.x += p.x;
			centroid.y += p.y;
			points.add(p);
		}

		centroid.x /= points.size();
		centroid.y /= points.size();
		Point centerPoint = null;
		double centerPointDist = Double.MAX_VALUE;

		for (Point p : points) {
			double dist = dist(centroid, p);

			if (dist < centerPointDist) {
				centerPoint = p;
				centerPointDist = dist;
			}
		}

		long totalDist = 0L;

		for (Point p : points) {
			totalDist += specialDistance(p, centerPoint);
		}

		System.out.println(totalDist);
	}

	private static double dist(Point p1, Point p2) {
		double ret = 0.0;

		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	private static long specialDistance(Point p1, Point p2) {
		long width = Math.abs(p1.x - p2.x);
		long height = Math.abs(p1.y - p2.y);

		return Math.abs(width - height) + Math.min(width, height);
	}

	private static class Point {
		long x;
		long y;
	}
}
