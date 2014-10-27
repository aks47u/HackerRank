import scala.collection.mutable._;

object Solution {
    def main(args: Array[String]) {
        for(t <- readByte() until 0 by -1) {
            val Array(n, k) = readLine().split(" ").map(_.toShort);
            val arr: Array[Int] = readLine().split(" ").map(_.toInt);
            var length: Short = 0;
            var order: Array[Int] = new Array[Int](n);
            val counts: HashMap[Int, Short] = new HashMap[Int, Short]();
            
            for(a <- arr) {
                val count: Short = counts.getOrElse(a, 0);
                counts.put(a, (count+1).toShort);
                
                if (count < 1) {
                    order(length) = a;
                    length = (length+1).toShort;
                }
            }
            
            order = order.slice(0, length).filter(counts.get(_).get >= k);
            
            if (order.length < 1){
                println("-1");
            } else {
                println(order.mkString(" "));
            }
        }
    }
}
