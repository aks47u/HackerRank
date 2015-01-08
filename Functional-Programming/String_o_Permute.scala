import java.util.Scanner

object Solution {
	def main(args: Array[String]) {
		val scn = new Scanner(System.in)
		val T = scn.nextInt()

		for (i <- 0 until T) {
			val str = scn.next()
			var j = 0

			while (j < str.length - 1) {
				System.out.print(str.charAt(j + 1))
				System.out.print(str.charAt(j))
				j += 2
			}
			
			println()
		}
		
		scn.close()
	}
}
