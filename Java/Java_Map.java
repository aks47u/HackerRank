import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Java_Map {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int n = scn.nextInt();
		scn.nextLine();
		Map<String, String> phonebook = new HashMap<String, String>();

		for (int i = 0; i < n; i++) {
			phonebook.put(scn.nextLine(), scn.nextLine());
		}

		while (scn.hasNextLine()) {
			String query = scn.nextLine();

			if (phonebook.containsKey(query)) {
				System.out.println(query + "=" + phonebook.get(query));
			} else {
				System.out.println("Not found");
			}
		}

		scn.close();
	}
}
