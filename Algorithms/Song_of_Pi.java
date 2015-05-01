import java.util.Scanner;

public class Song_of_Pi {
	public static void main(String[] args) {
		String pi = "31415926535897932384626433833";
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();
		scn.nextLine();

		for (int i = 0; i < T; i++) {
			String[] isPi = scn.nextLine().split(" ");
			boolean b = true;

			for (int j = 0; j < isPi.length; j++) {
				if (isPi[j].length() != Integer.parseInt("" + pi.charAt(j))) {
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
