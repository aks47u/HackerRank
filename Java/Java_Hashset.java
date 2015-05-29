import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Java_Hashset {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int T = scn.nextInt();
		Set<Pair<String, String>> setA = new HashSet<Pair<String, String>>();

		for (int i = 0; i < T; i++) {
			String a = scn.next();
			String b = scn.next();
			Pair<String, String> P = new Pair<String, String>(a, b);
			setA.add(P);
			System.out.println(setA.size());
		}
		
		scn.close();
	}
}

class Pair<A, B> {
	private A first;
	private B second;

	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}

	public int hashCode() {
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	public boolean equals(Object other) {
		if (other instanceof Pair) {
			Pair<?, ?> otherPair = (Pair<?, ?>) other;

			return ((this.first == otherPair.first || (this.first != null
					&& otherPair.first != null && this.first
					.equals(otherPair.first))) && (this.second == otherPair.second || (this.second != null
					&& otherPair.second != null && this.second
					.equals(otherPair.second))));
		}

		return false;
	}

	public String toString() {
		return "(" + first + ", " + second + ")";
	}

	public A getFirst() {
		return first;
	}

	public void setFirst(A first) {
		this.first = first;
	}

	public B getSecond() {
		return second;
	}

	public void setSecond(B second) {
		this.second = second;
	}
}
