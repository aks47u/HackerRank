import java.io._;
import java.util._;

object Solution {
    def main(args: Array[String]) {
        var N: Int = readLine().toInt;
        var A: Array[Int] = readLine().split(" ").map((x) => x.toInt);
        var sums: Array[Long] = new Array[Long](N);
        
        for(i <- 0 until N) {
            sums(i) = A(i);
        }
        
        Arrays.sort(sums);
        sums = sums.reverse;
        
        for(i <- 1 until N) {
            sums(i) += sums(i-1);
        }

        for(t <- readLine().toInt until 0 by -1) {
            var S: Long = readLine().toLong;
            var i: Int = Arrays.binarySearch(sums, S);
            
            if (i < 0) {
                i = -i - 1;
            }
            
            i += 1;
            
            if (i > N){
                i = -1;
            }
            
            println(i);
        }
    }
}
