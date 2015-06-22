import java.util.Scanner;

public class Message_Space_and_Ciphertext_Space {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String str = scn.nextLine().trim();
		scn.close();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			sb.append(((str.charAt(i) - '0') + 1) % 10);
		}

		System.out.println(sb.toString());
	}
}
