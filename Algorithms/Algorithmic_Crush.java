package Algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Algorithmic_Crush {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		int N = Integer.parseInt(input[0]);
		int M = Integer.parseInt(input[1]);
		SegmentTree tree = new SegmentTree(new int[N]);

		for (int m = 0; m < M; ++m) {
			input = br.readLine().split(" ");
			int A = Integer.parseInt(input[0]);
			int B = Integer.parseInt(input[1]);
			int K = Integer.parseInt(input[2]);
			tree.update(A - 1, B - 1, K);
		}

		System.out.print(tree.max());
	}

	private static class SegmentTree {
		private int length;
		private long[] data;
		private long[] lazy;

		public SegmentTree(int[] arr) {
			init(arr);
		}

		private void init(int[] arr) {
			this.length = arr.length;
			int height = (int) Math.ceil(Math.log(this.length) / Math.log(2));
			int size = (1 << (height + 1)) - 1;
			this.data = new long[size];
			this.lazy = new long[size];
			build(this.data, arr, 0, this.length - 1, 0);
		}

		private static long build(long[] data, int[] arr, int min, int max,
				int i) {
			if (min == max) {
				return data[i] = arr[min];
			}

			int mid = getMid(min, max);

			return data[i] = getMax(build(data, arr, min, mid, getLeft(i)),
					build(data, arr, mid + 1, max, getRight(i)));
		}

		public long max() {
			return max(0, this.length - 1);
		}

		public long max(int ql, int qr) {
			return max(this.data, this.lazy, ql, qr, 0, this.length - 1, 0);
		}

		private static long max(long[] arr, long[] lazy, int ql, int qr,
				int min, int max, int i) {
			if (lazy[i] != 0L) {
				arr[i] += lazy[i];

				if (min != max) {
					lazy[getLeft(i)] += lazy[i];
					lazy[getRight(i)] += lazy[i];
				}

				lazy[i] = 0L;
			}

			if (max < ql || min > qr) {
				return 0L;
			}

			if (min >= ql && max <= qr) {
				return arr[i];
			}

			int mid = getMid(min, max);

			return getMax(max(arr, lazy, ql, qr, min, mid, getLeft(i)),
					max(arr, lazy, ql, qr, mid + 1, max, getRight(i)));
		}

		public void update(int ql, int qr, int val) {
			update(this.data, this.lazy, ql, qr, 0, this.length - 1, val, 0);
		}

		public long update(long[] arr, long[] lazy, int ql, int qr, int min,
				int max, int val, int i) {
			if (lazy[i] != 0L) {
				arr[i] += lazy[i];

				if (min != max) {
					lazy[getLeft(i)] += lazy[i];
					lazy[getRight(i)] += lazy[i];
				}

				lazy[i] = 0L;
			}

			if (max < ql || min > qr) {
				return arr[i];
			}

			if (min >= ql && max <= qr) {
				if (min != max) {
					lazy[getLeft(i)] += val;
					lazy[getRight(i)] += val;
				}

				return arr[i] += val;
			}

			int mid = getMid(min, max);

			return arr[i] = getMax(
					update(arr, lazy, ql, qr, min, mid, val, getLeft(i)),
					update(arr, lazy, ql, qr, mid + 1, max, val, getRight(i)));
		}

		private static long getMax(long a, long b) {
			return (a > b) ? a : b;
		}

		private static int getMid(int min, int max) {
			return min + ((max - min) >> 1);
		}

		private static int getLeft(int i) {
			return (i << 1) + 1;
		}

		private static int getRight(int i) {
			return (i + 1) << 1;
		}

		public String toString() {
			return Arrays.toString(this.data);
		}
	}
}
