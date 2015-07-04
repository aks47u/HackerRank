import java.util.Scanner;

public class Identify_Smith_Numbers {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		scn.close();
		int a = sumDig(N);
		int b = sumPrimeFact(N);

		if (a == b) {
			System.out.print("1");
		} else {
			System.out.print("0");
		}
	}

	private static int sumDig(int n) {
		int s = 0;

		while (n > 0) {
			s += n % 10;
			n /= 10;
		}

		return s;
	}

	private static int sumPrimeFact(int n) {
		int i = 2, sum = 0;

		while (n > 1) {
			if (n % i == 0) {
				sum += sumDig(i);
				n /= i;
			} else {
				i++;
			}
		}

		return sum;
	}
}
