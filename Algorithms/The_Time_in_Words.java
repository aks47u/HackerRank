import java.util.Scanner;

public class The_Time_in_Words {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int H = scn.nextInt();
		int M = scn.nextInt();
		scn.close();
		String[] words = { "", "one", "two", "three", "four", "five", "six",
				"seven", "eight", "nine", "ten", "eleven", "twelve",
				"thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
				"eighteen", "nineteen", "twenty", "twenty one", "twenty two",
				"twenty three", "twenty four", "twenty five", "twenty six",
				"twenty seven", "twenty eight", "twenty nine" };
		String plu, a;

		if (M == 1 || M == 59) {
			plu = "minute";
		} else {
			plu = "minutes";
		}

		if (H == 12) {
			a = words[1];
		} else {
			a = words[H + 1];
		}

		if (M == 0) {
			System.out.println(words[H] + " o' clock");
		} else if (M == 15) {
			System.out.println("quarter past " + words[H]);
		} else if (M == 30) {
			System.out.println("half past " + words[H]);
		} else if (M == 45) {
			System.out.println("quarter to " + a);
		} else if (M < 30) {
			System.out.println(words[M] + " " + plu + " past " + words[H]);
		} else {
			System.out.println(words[60 - M] + " " + plu + " to " + a);
		}
	}
}
