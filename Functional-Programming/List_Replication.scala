def f(num : Int, arr : List[Int]) : List[Int] = {
	val out : Array[Int] = new Array[Int](num*arr.length);
	
	for(i <- 0 until arr.length) {
		val offset : Int = i*num;
		
		for(n <- 0 until num) {
			out(offset + n) = arr(i);
		}
	}
	
	return out.toList;
}
