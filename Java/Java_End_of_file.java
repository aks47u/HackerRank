import java.util.Scanner;

public class Java_End_of_file {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int counter = 1;

		while (scn.hasNextLine()) {
			System.out.println(counter++ + " " + scn.nextLine());
		}

		scn.close();
	}
}
