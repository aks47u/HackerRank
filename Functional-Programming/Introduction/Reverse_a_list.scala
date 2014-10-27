def f(arr: List[Int]): List[Int] = {
    return arr.zipWithIndex.sortWith((a, b) => a._2 > b._2).map((x) => x._1);
}
