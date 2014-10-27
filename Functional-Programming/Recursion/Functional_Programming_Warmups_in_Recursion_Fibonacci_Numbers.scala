object Solution {
    def fibonacci(x:Int):Int = {
        if (x < 2){
            if (x < 1){
                return 0;
            }
            
            return 1;
        }
        
        return fib(0, 1, x - 2);
    }

    def fib(prev: Int, cur: Int, count: Int): Int = {
        if (count < 1){
            return cur;
        }
        
        fib(cur, cur+prev, count-1);
    }

    def main(args: Array[String]) {
        println(fibonacci(readInt()))
    }
}
