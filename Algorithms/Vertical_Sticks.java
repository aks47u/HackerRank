import java.util.Arrays;
import java.util.Scanner;

public class Vertical_Sticks {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		while (T-- > 0) {
			int numbers = scn.nextInt();
			int[] data = new int[numbers];

			for (int i = 0; i < numbers; ++i) {
				data[i] = scn.nextInt();
			}

			Arrays.sort(data);
			double answer = 0.0;

			for (int i = 0; i < numbers; ++i) {
				int totalSmaller = i;

				for (int j = i - 1; j >= 0; --j) {
					if (data[j] == data[i]) {
						--totalSmaller;
					} else {
						break;
					}
				}

				for (int j = 1; j <= numbers; ++j) {
					int smaller = totalSmaller;
					double thisValue = 0.0;
					double probContinue = 1;

					for (int k = j - 1; k >= 0; --k) {
						if (smaller == 0 || k == 0) {
							thisValue += probContinue * (j - k);
							break;
						}

						int numbersLeft = numbers - (j - k - 1) - 1;
						double probThisOneNotSmaller = 1 - (1.0 * smaller / numbersLeft);
						thisValue += (j - k) * probContinue
								* probThisOneNotSmaller;
						--smaller;
						probContinue *= (1 - probThisOneNotSmaller);
					}

					answer += thisValue / numbers;
				}
			}

			System.out.printf("%.2f\n", answer);
		}

		scn.close();
	}
}
