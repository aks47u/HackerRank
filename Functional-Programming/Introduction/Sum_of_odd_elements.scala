def f(arr : List[Int]) : Int = {
    return arr.filter(x => (x & 1) == 1).sum
}
