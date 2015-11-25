import java.util.Scanner;

public class Encryption {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		String s = scn.next();
		scn.close();
		int wid, len;
		int l = s.length();
		double f = Math.sqrt(l);
		int test = (int) f;

		if (test * test == l) {
			wid = test;
			len = test;
		} else {
			wid = test;
			len = test + 1;

			if (wid * len < l) {
				wid++;
			}
		}

		int a = 0;
		char[][] arr = new char[wid][len];

		for (int i = 0; i < wid; i++) {
			for (int j = 0; j < len; j++) {
				if (a == s.length()) {
					arr[i][j] = ' ';
				} else {
					arr[i][j] = s.charAt(a++);
				}
			}

			if (a == s.length()) {
				break;
			}
		}

		String temp = "";
		boolean go = false;

		for (int i = 0; i < len; i++) {
			for (int j = 0; j < wid; j++) {
				if (!(arr[j][i] == ' ')) {
					temp = temp + arr[j][i];
					go = true;
				}
			}

			if (go) {
				temp += " ";
			}

			go = false;
		}

		System.out.println(temp);
	}
}
