import java.util.Arrays;
import java.util.Scanner;

public class Find_the_Median {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int N = scn.nextInt();
		int[] nums = new int[N];

		for (int i = 0; i < N; i++) {
			nums[i] = scn.nextInt();
		}

		scn.close();
		Arrays.sort(nums);

		if (N % 2 == 0) {
			int total = nums[N / 2] + nums[(N / 2) + 1];
			System.out.println(total / 2.0);
		} else {
			System.out.println(nums[N / 2]);
		}
	}
}
