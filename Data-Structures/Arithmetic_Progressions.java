import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

final class Operation {
	private static final int FINITE_FIELD_MODULO = 1000003;
	private static final int[] FACTORIALS = new int[FINITE_FIELD_MODULO];

	static {
		FACTORIALS[0] = 1;

		for (int i = 1; i < FINITE_FIELD_MODULO; i++) {
			FACTORIALS[i] = mul(FACTORIALS[i - 1], i);
		}
	}

	public static int pow(int a, long power) {
		int result = 1;
		int e = a;

		while (power > 0) {
			if ((power & 1) > 0) {
				result = Operation.mul(result, e);
			}

			e = Operation.mul(e, e);
			power >>>= 1;
		}

		return result;
	}

	public static int mul(int a, int b) {
		return (int) (((long) a * (long) b) % FINITE_FIELD_MODULO);
	}

	public static int factorial(long n) {
		if (n < FINITE_FIELD_MODULO) {
			return FACTORIALS[(int) n];
		} else {
			return 0;
		}
	}
}

final class ArithmeticProgressionsDataSet {
	private int length;
	private int levels;
	private int[] multiplicators;
	private int[] products;
	private int[] powerSums;
	private long[] powers;
	private int[] leftStack;
	private int[] rightStack;

	public ArithmeticProgressionsDataSet(int size, int[] d, int[] p) {
		length = 1;
		levels = 1;

		while (length <= size) {
			length <<= 1;
			levels++;
		}

		multiplicators = new int[length + size + 1];
		Arrays.fill(multiplicators, 1);
		products = new int[length + size + 1];
		Arrays.fill(products, 1);

		for (int i = 0; i < size; i++) {
			int pi = length + 1 + i;
			multiplicators[pi] = d[i];
			multiplicators[pi >>> 1] = Operation.mul(multiplicators[pi >>> 1],
					multiplicators[pi]);
			products[pi] = Operation.pow(d[i], p[i]);
			products[pi >>> 1] = Operation
					.mul(products[pi >>> 1], products[pi]);
		}

		for (int i = length / 2 - 1; i > 0; i--) {
			multiplicators[i] = Operation.mul(multiplicators[i << 1],
					multiplicators[(i << 1) + 1]);
			products[i] = Operation.mul(products[i << 1],
					products[(i << 1) + 1]);
		}

		powerSums = new int[length + size + 1];
		powers = new long[length + size + 1];
		leftStack = new int[levels];
		rightStack = new int[levels];

		for (int i = 0; i < size; i++) {
			int pi = length + 1 + i;
			powers[pi] = p[i];
			powers[pi >>> 1] += powers[pi];
		}

		for (int i = length / 2 - 1; i > 0; i--) {
			powers[i] = powers[i << 1] + powers[(i << 1) + 1];
		}
	}

	public void incrementPowers(int l, int r, int value) {
		l += length;
		r += length;
		int level = 0;
		long lsum = 0;
		long rsum = 0;
		int lmul = 1;
		int rmul = 1;

		while (l <= r) {
			if ((l & 1) == 1) {
				powerSums[l] += value;
				lsum += (long) value << level;
				lmul = Operation.mul(lmul,
						Operation.pow(multiplicators[l], value));
				l++;
			}

			l >>>= 1;
				powers[l - 1] += lsum;
				products[l - 1] = Operation.mul(products[l - 1], lmul);

				if ((r & 1) == 0) {
					powerSums[r] += value;
					rsum += (long) value << level;
					rmul = Operation.mul(rmul,
							Operation.pow(multiplicators[r], value));
					r--;
				}

				r >>>= 1;
					powers[r + 1] += rsum;
					products[r + 1] = Operation.mul(products[r + 1], rmul);
					level++;
		}

		while (l > 1) {
			l >>>= 1;
			powers[l] += rsum;
			products[l] = Operation.mul(products[l], rmul);
			r >>>= 1;
					powers[r] += lsum;
					products[r] = Operation.mul(products[r], lmul);
		}
	}

	private void fillSumsOfPowersStack(int index, int[] stack) {
		int level = 0;

		while (index > 1) {
			index >>>= 1;
			stack[level++] = index;
		}

		stack[levels - 1] = 0;

		for (level = levels - 2; level >= 0; level--) {
			stack[level] = stack[level + 1] + powerSums[stack[level]];
		}
	}

	private long getPower(int l, int r) {
		long power = 0;
		int level = 0;

		while (l <= r) {
			if ((l & 1) == 1) {
				power += powers[l]
						+ ((long) powerSums[l] + (long) leftStack[level] << level);
				l++;
			}

			l >>>= 1;

						if ((r & 1) == 0) {
							power += powers[r]
									+ ((long) powerSums[r] + (long) rightStack[level] << level);
							r--;
						}

						r >>>= 1;
									level++;
		}

		return power;
	}

	private int getCoefficient(int l, int r) {
		int coef = 1;
		int level = 0;

		while (l <= r) {
			if ((l & 1) == 1) {
				coef = Operation.mul(
						coef,
						Operation.mul(
								products[l],
								Operation.pow(multiplicators[l], powerSums[l]
										+ leftStack[level])));
				l++;
			}

			l >>>= 1;

			if ((r & 1) == 0) {
				coef = Operation.mul(
						coef,
						Operation.mul(
								products[r],
								Operation.pow(multiplicators[r], powerSums[r]
										+ rightStack[level])));
				r--;
			}

			r >>>= 1;
			level++;
		}

		return coef;
	}

	public void queryDiff(int l, int r) {
		l += length;
		r += length;
		fillSumsOfPowersStack(l, leftStack);
		fillSumsOfPowersStack(r, rightStack);
		long power = getPower(l, r);
		int diff = Operation.factorial(power);

		if (diff > 0) {
			diff = Operation.mul(diff, getCoefficient(l, r));
		}

		System.out.println(power + " " + diff);
	}
}

public class Arithmetic_Progressions {
	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in), 64 << 10);
			int n = Integer.parseInt(br.readLine().trim());
			int[] d = new int[n];
			int[] p = new int[n];

			for (int i = 0; i < n; i++) {
				StringTokenizer tokenizer = new StringTokenizer(br.readLine());
				tokenizer.nextToken();
				d[i] = Integer.parseInt(tokenizer.nextToken());
				p[i] = Integer.parseInt(tokenizer.nextToken());
			}

			ArithmeticProgressionsDataSet dataSet = new ArithmeticProgressionsDataSet(
					n, d, p);
			int q = Integer.parseInt(br.readLine().trim());

			for (int i = 0; i < q; i++) {
				StringTokenizer tokenizer = new StringTokenizer(br.readLine());
				int queryType = Integer.parseInt(tokenizer.nextToken());

				switch (queryType) {
				case 0: {
					int l = Integer.parseInt(tokenizer.nextToken());
					int r = Integer.parseInt(tokenizer.nextToken());
					dataSet.queryDiff(l, r);
					break;
				}
				case 1: {
					int l = Integer.parseInt(tokenizer.nextToken());
					int r = Integer.parseInt(tokenizer.nextToken());
					int v = Integer.parseInt(tokenizer.nextToken());
					dataSet.incrementPowers(l, r, v);
					break;
				}
				}
			}
		} catch (Exception e) {
			System.err.println("Error:" + e.getMessage());
		}
	}
}
