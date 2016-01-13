import java.util.Scanner;

public class Song_of_Pi {
	public static void main(String[] args) {
		String pi = "31415926535897932384626433833";
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();
		scn.nextLine();

		while (T-- > 0) {
			String[] isPi = scn.nextLine().split(" ");
			boolean b = true;

			for (int i = 0; i < isPi.length; i++) {
				if (isPi[i].length() != Integer.parseInt("" + pi.charAt(i))) {
					b = false;
					break;
				}
			}

			if (b) {
				System.out.println("It's a pi song.");
			} else {
				System.out.println("It's not a pi song.");
			}
		}
		
		scn.close();
	}
}
