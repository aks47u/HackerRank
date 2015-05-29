import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Java_Stack {
	private static Map<Character, Character> brackets = new HashMap<Character, Character>();

	static {
		brackets.put('[', ']');
		brackets.put('{', '}');
		brackets.put('(', ')');
	}

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		while (scn.hasNext()) {
			if (isBalanced(scn.next())) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}
		}

		scn.close();
	}

	private static boolean isBalanced(String str) {
		if ((str.length() % 2) != 0) {
			return false;
		}

		Stack<Character> stack = new Stack<Character>();

		for (int i = 0; i < str.length(); i++) {
			if (brackets.containsKey(str.charAt(i))) {
				stack.push(str.charAt(i));
			} else if (stack.empty()
					|| (str.charAt(i) != brackets.get(stack.pop()))) {
				return false;
			}
		}

		return true;
	}
}
