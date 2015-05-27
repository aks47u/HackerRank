import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Java_strings {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String str = scn.next();
		int k = scn.nextInt();
		scn.close();
		ArrayList<String> subs = new ArrayList<String>();

		for (int i = 0; i < str.length() - k + 1; i++) {
			subs.add(str.substring(i, i + k));
		}

		String[] sub = new String[subs.size()];
		subs.toArray(sub);
		Arrays.sort(sub);
		System.out.println(sub[0]);
		System.out.println(sub[sub.length - 1]);
	}
}
