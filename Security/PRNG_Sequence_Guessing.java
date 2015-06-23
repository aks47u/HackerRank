import java.util.Random;
import java.util.Scanner;

public class PRNG_Sequence_Guessing {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt(), i = 0;
		int[] a = new int[10];

		for (; n > 0; n--) {
			int x = scn.nextInt(), y = scn.nextInt();

			for (i = 0; i < 10; i++) {
				a[i] = scn.nextInt();
			}

			for (; x <= y; x++) {
				Random rnd = new Random(x);

				for (i = 0; i < 10; i++) {
					if (a[i] != rnd.nextInt(1000)) {
						break;
					}
				}

				if (i == 10) {
					System.out.print(x);

					for (i = 0; i < 10; i++) {
						System.out.print(" " + rnd.nextInt(1000));
					}

					System.out.println();
					break;
				}
			}
		}

		scn.close();
	}
}
