import java.util.Scanner;

public class Key_Spaces {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String str = scn.next();
		int e = scn.nextInt();
		scn.close();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			sb.append(((str.charAt(i) - '0') + e) % 10);
		}

		System.out.println(sb.toString());
	}
}
