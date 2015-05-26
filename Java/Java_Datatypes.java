import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();

		for (int i = 0; i < T; i++) {
			long n = 0;
			String sn = scn.next();
			boolean b = true;

			try {
				n = Long.parseLong(sn);
			} catch (Exception e) {
				b = false;
			}

			if (n <= Long.MAX_VALUE && n >= Long.MIN_VALUE && b) {
				System.out.println(sn + " can be fitted in:");
				b = true;
			} else {
				System.out.println(sn + " can't be fitted anywhere.");
				b = false;
			}

			if (b) {
                if(n == 0 || n == 1) {
					System.out.println("* boolean");
				}

				if (n <= Byte.MAX_VALUE && n >= Byte.MIN_VALUE) {
					System.out.println("* byte");
				}

				if (n <= Short.MAX_VALUE && n >= Short.MIN_VALUE) {
					System.out.println("* short");
				}

				if (n <= Integer.MAX_VALUE && n >= Integer.MIN_VALUE) {
					System.out.println("* int");
				}

				if (n <= Long.MAX_VALUE && n >= Long.MIN_VALUE) {
					System.out.println("* long");
				}
			}
		}

		scn.close();
	}
}
