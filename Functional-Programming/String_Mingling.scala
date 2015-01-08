import java.util.Scanner

object Solution {
	def main(args: Array[String]) {
		val scn = new Scanner(System.in)
		val P = scn.next()
		val Q = scn.next()
		scn.close()
		
		for (i <- 0 until P.length) {
			System.out.print(P.charAt(i))
			System.out.print(Q.charAt(i))
		}
	}
}
