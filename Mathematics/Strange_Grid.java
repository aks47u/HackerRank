import java.util.Scanner;

public class Strange_Grid {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		long r = scn.nextLong();
		int c = scn.nextInt();
		scn.close();
		boolean even = true;

		if (r % 2 == 0) {
			even = false;
		}

		r--;
		r /= 2;
		r *= 10;

		if (even) {
			c--;
			c *= 2;
		} else {
			c *= 2;
			c--;
		}

		System.out.println(r + c);
	}
}
