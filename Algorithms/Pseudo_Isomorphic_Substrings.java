import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution implements Runnable {
	static Random RND = new Random(7777L);
	static int MAX_LENGTH = 100000;
	static int ALPHA_SIZE = 26;
	static int HASH_BASE = BigInteger.probablePrime(17, RND).intValue();
	static int[] HASH_BASE_POWS = new int[MAX_LENGTH * 2];

	static {
		HASH_BASE_POWS[0] = 1;

		for (int i = 1; i < HASH_BASE_POWS.length; i++) {
			HASH_BASE_POWS[i] = HASH_BASE_POWS[i - 1] * HASH_BASE;
		}
	}

	char[] s;
	int length;
	int[][] charMaps;
	int[][] charPerms;
	int[][] distributedHashes;
	int[] hashPrefixes;
	Map<Long, Integer> lcpCache = new HashMap<Long, Integer>();

	void solve() throws IOException {
		s = new StringBuilder(in.readLine()).reverse().toString().toCharArray();
		length = s.length;
		charMaps = getCharMaps(s);
		charPerms = getCharPermutations(charMaps);
		distributedHashes = getDistributedHashes(s);
		hashPrefixes = precalcHashPrefixes(charPerms, distributedHashes);
		Integer[] suffixArray = getSuffixArray();
		int[] suffixIndex = new int[length];

		for (int i = 0; i < length; i++) {
			suffixIndex[suffixArray[i]] = i;
		}

		NavigableSet<Integer> viewedSuffixes = new TreeSet<Integer>(
				new Comparator<Integer>() {
					@Override
					public int compare(Integer pos1, Integer pos2) {
						return suffixIndex[pos1] - suffixIndex[pos2];
					}
				});

		long[] counts = new long[length + 1];

		for (int pos = length - 1; pos >= 0; pos--) {
			long intersectionSize = 0L;

			{
				Integer neigbourPos = viewedSuffixes.lower(pos);

				if (neigbourPos != null) {
					intersectionSize = Math.max(intersectionSize,
							lcp(pos, neigbourPos));
				}
			}

			{
				Integer neigbourPos = viewedSuffixes.higher(pos);

				if (neigbourPos != null) {
					intersectionSize = Math.max(intersectionSize,
							lcp(pos, neigbourPos));
				}
			}

			counts[pos] = counts[pos + 1] + length - pos - intersectionSize;
			viewedSuffixes.add(pos);
		}

		for (int i = 0; i < length; i++) {
			out.println(counts[length - 1 - i]);
		}
	}

	Integer[] getSuffixArray() {
		Integer[] suffixArray = new Integer[length];

		for (int i = 0; i < length; i++) {
			suffixArray[i] = i;
		}

		Arrays.sort(suffixArray, new Comparator<Integer>() {
			@Override
			public int compare(Integer pos1, Integer pos2) {
				if (pos1.equals(pos2)) {
					return 0;
				}

				int lcp = lcp(pos1, pos2);

				if (lcp == length - pos1) {
					return -1;
				}

				if (lcp == length - pos2) {
					return +1;
				}

				return charMaps[pos1][s[pos1 + lcp] - 'a']
						- charMaps[pos2][s[pos2 + lcp] - 'a'];
			}
		});

		return suffixArray;
	}

	int lcp(int pos1, int pos2) {
		if (pos1 > pos2) {
			return lcp(pos2, pos1);
		}

		int leftBound = 0;
		int rightBound = length - pos2;
		int lcp = naiveLcp(pos1, pos2, Math.min(120, rightBound));

		if (lcp == -1) {
			leftBound = Math.min(15, rightBound);

			while (leftBound <= rightBound) {
				int middlePoint = (leftBound + rightBound) >> 1;

				if (equals(pos1, pos2, middlePoint)) {
					lcp = Math.max(lcp, middlePoint);
					leftBound = middlePoint + 1;
				} else {
					rightBound = middlePoint - 1;
				}
			}
		}

		return lcp;
	}

	int naiveLcp(int pos1, int pos2, int len) {
		int[] map1 = charMaps[pos1];
		int[] map2 = charMaps[pos2];

		for (int i = 0; i < len; i++) {
			if (map1[s[pos1 + i] - 'a'] != map2[s[pos2 + i] - 'a']) {
				return i;
			}
		}

		return -1;
	}

	boolean equals(int pos1, int pos2, int length) {
		if (pos1 > pos2) {
			return equals(pos2, pos1, length);
		}

		int hash1 = hash(pos1, length);
		int hash2 = hash(pos2, length);
		int hashAlingmentPower = HASH_BASE_POWS[pos2 - pos1];

		return hashAlingmentPower * hash1 == hash2;
	}

	int hash(int pos, int length) {
		int[] perm = charPerms[pos];
		int[] hashes = distributedHashes[pos + length];
		int hash = -hashPrefixes[pos];

		for (int rank = 0; rank < perm.length; rank++) {
			hash += hashes[perm[rank]] * rank;
		}

		return hash;
	}

	static int[][] getCharMaps(char[] s) {
		int length = s.length;
		int[][] linksToNext = getLinksToNext(s);
		int[][] maps = new int[length][ALPHA_SIZE];

		for (int offset = 0; offset < length; offset++) {
			int[] map = maps[offset];
			Arrays.fill(map, -1);
			int mapped = 0;
			map[s[offset] - 'a'] = mapped++;

			for (int pos = offset; pos < length;) {
				int nextPos = length;
				int nextChar = -1;

				for (int ch = 0; ch < ALPHA_SIZE; ch++) {
					if (map[ch] == -1) {
						if (nextPos > linksToNext[pos][ch]) {
							nextPos = linksToNext[pos][ch];
							nextChar = ch;
						}
					}
				}

				if (nextChar == -1) {
					break;
				}

				map[nextChar] = mapped++;
				pos = nextPos;
			}
		}

		return maps;
	}

	static int[][] getLinksToNext(char[] s) {
		int length = s.length;
		int[][] linksToNext = new int[length][ALPHA_SIZE];

		for (int[] row : linksToNext) {
			Arrays.fill(row, length);
		}

		for (int i = length - 2; i >= 0; i--) {
			System.arraycopy(linksToNext[i + 1], 0, linksToNext[i], 0,
					ALPHA_SIZE);
			linksToNext[i][s[i + 1] - 'a'] = i + 1;
		}

		return linksToNext;
	}

	static int[][] getDistributedHashes(char[] s) {
		int length = s.length;
		int[][] distributedHashes = new int[length + 1][ALPHA_SIZE];

		for (int i = 0; i < length; i++) {
			System.arraycopy(distributedHashes[i], 0, distributedHashes[i + 1],
					0, ALPHA_SIZE);
			distributedHashes[i + 1][s[i] - 'a'] += HASH_BASE_POWS[i];
		}

		return distributedHashes;
	}

	static int[][] getCharPermutations(int[][] charMaps) {
		int lenght = charMaps.length;
		int[][] charPerms = new int[lenght][];

		for (int pos = 0; pos < lenght; pos++) {
			charPerms[pos] = getCharPermutation(charMaps[pos]);
		}

		return charPerms;
	}

	static int[] getCharPermutation(int[] map) {
		int[] permutation = new int[ALPHA_SIZE];
		int last = 0;

		for (int ch = 0; ch < ALPHA_SIZE; ch++) {
			if (map[ch] != -1) {
				permutation[map[ch]] = ch;
				last = Math.max(last, map[ch]);
			}
		}

		return Arrays.copyOf(permutation, last + 1);
	}

	static int[] precalcHashPrefixes(int[][] charPerms,
			int[][] distributedHashes) {
		int length = charPerms.length;
		int[] hashPreffixes = new int[length];

		for (int pos = 0; pos < length; pos++) {
			int[] hashes = distributedHashes[pos];
			int[] perm = charPerms[pos];

			for (int rank = 0; rank < charPerms[pos].length; rank++) {
				hashPreffixes[pos] += hashes[perm[rank]] * rank;
			}
		}

		return hashPreffixes;
	}

	static Solution INSTANCE = new Solution();
	static boolean WRITE_LOG = true;
	static long STACK_SIZE = -1;

	public static void main(String[] args) throws IOException {
		try {
			if (STACK_SIZE < 0L) {
				INSTANCE.run();
			} else {
				new Thread(null, INSTANCE, "Solver", 1L << 24).start();
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(999);
		}
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(System.out);
			solve();
			out.close();
			in.close();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(999);
		}
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer st = new StringTokenizer("");

	String nextToken() throws IOException {
		while (!st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine());
		}

		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}

	int[] nextIntArray(int size) throws IOException {
		int[] ret = new int[size];

		for (int i = 0; i < size; i++) {
			ret[i] = nextInt();
		}

		return ret;
	}

	long[] nextLongArray(int size) throws IOException {
		long[] ret = new long[size];

		for (int i = 0; i < size; i++) {
			ret[i] = nextLong();
		}

		return ret;
	}

	double[] nextDoubleArray(int size) throws IOException {
		double[] ret = new double[size];

		for (int i = 0; i < size; i++) {
			ret[i] = nextDouble();
		}

		return ret;
	}

	String nextLine() throws IOException {
		st = new StringTokenizer("");

		return in.readLine();
	}

	boolean isEof() throws IOException {
		while (!st.hasMoreTokens()) {
			String s = in.readLine();

			if (s == null) {
				return true;
			}

			st = new StringTokenizer(s);
		}

		return false;
	}

	void printRepeat(String s, int count) {
		for (int i = 0; i < count; i++) {
			out.print(s);
		}
	}

	void printArray(int[] array) {
		if (array == null || array.length == 0) {
			return;
		}

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				out.print(' ');
			}

			out.print(array[i]);
		}

		out.println();
	}

	void printArray(long[] array) {
		if (array == null || array.length == 0) {
			return;
		}

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				out.print(' ');
			}

			out.print(array[i]);
		}

		out.println();
	}

	void printArray(double[] array) {
		if (array == null || array.length == 0) {
			return;
		}

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				out.print(' ');
			}

			out.print(array[i]);
		}

		out.println();
	}

	void printArray(double[] array, String spec) {
		if (array == null || array.length == 0) {
			return;
		}

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				out.print(' ');
			}

			out.printf(Locale.US, spec, array[i]);
		}

		out.println();
	}

	void printArray(Object[] array) {
		if (array == null || array.length == 0) {
			return;
		}

		boolean blank = false;

		for (Object x : array) {
			if (blank) {
				out.print(' ');
			} else {
				blank = true;
			}

			out.print(x);
		}

		out.println();
	}

	@SuppressWarnings("rawtypes")
	void printCollection(Collection collection) {
		if (collection == null || collection.isEmpty()) {
			return;
		}

		boolean blank = false;

		for (Object x : collection) {
			if (blank) {
				out.print(' ');
			} else {
				blank = true;
			}

			out.print(x);
		}

		out.println();
	}

	static void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	static void swap(long[] a, int i, int j) {
		long tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	static void swap(double[] a, int i, int j) {
		double tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}

	static void shuffle(int[] a, int from, int to) {
		for (int i = from; i < to; i++) {
			swap(a, i, RND.nextInt(a.length));
		}
	}

	static void shuffle(long[] a, int from, int to) {
		for (int i = from; i < to; i++) {
			swap(a, i, RND.nextInt(a.length));
		}
	}

	static void shuffle(double[] a, int from, int to) {
		for (int i = from; i < to; i++) {
			swap(a, i, RND.nextInt(a.length));
		}
	}

	static void shuffle(int[] a) {
		if (a == null) {
			return;
		}

		shuffle(a, 0, a.length);
	}

	static void shuffle(long[] a) {
		if (a == null) {
			return;
		}

		shuffle(a, 0, a.length);
	}

	static void shuffle(double[] a) {
		if (a == null) {
			return;
		}

		shuffle(a, 0, a.length);
	}

	static long[] getPartialSums(int[] a) {
		long[] sums = new long[a.length + 1];

		for (int i = 0; i < a.length; i++) {
			sums[i + 1] = sums[i] + a[i];
		}

		return sums;
	}

	static long[] getPartialSums(long[] a) {
		long[] sums = new long[a.length + 1];

		for (int i = 0; i < a.length; i++) {
			sums[i + 1] = sums[i] + a[i];
		}

		return sums;
	}

	static int[] getOrderedSet(int[] a) {
		int[] set = Arrays.copyOf(a, a.length);

		if (a.length == 0) {
			return set;
		}

		shuffle(set);
		Arrays.sort(set);
		int k = 1;
		int prev = set[0];

		for (int i = 1; i < a.length; i++) {
			if (prev != set[i]) {
				set[k++] = prev = set[i];
			}
		}

		return Arrays.copyOf(set, k);
	}

	static long[] getOrderedSet(long[] a) {
		long[] set = Arrays.copyOf(a, a.length);

		if (a.length == 0) {
			return set;
		}

		shuffle(set);
		Arrays.sort(set);
		int k = 1;
		long prev = set[0];

		for (int i = 1; i < a.length; i++) {
			if (prev != set[i]) {
				set[k++] = prev = set[i];
			}
		}

		return Arrays.copyOf(set, k);
	}

	static int gcd(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);

		while (x > 0 && y > 0) {
			if (x > y) {
				x %= y;
			} else {
				y %= x;
			}
		}

		return x + y;
	}

	static long gcd(long x, long y) {
		x = Math.abs(x);
		y = Math.abs(y);

		while (x > 0 && y > 0) {
			if (x > y) {
				x %= y;
			} else {
				y %= x;
			}
		}

		return x + y;
	}
}
