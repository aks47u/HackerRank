def f(coefficients:List[Int],powers:List[Int],x:Double):Double = {
  (0 until coefficients.length).map((i) => coefficients(i)*math.pow(x, powers(i))).sum;
}

def area(coefficients:List[Int],powers:List[Int],x:Double):Double = {
  math.Pi*math.pow(f(coefficients, powers, x), 2);
}

def summation(func:(List[Int],List[Int],Double)=>Double,upperLimit:Int,lowerLimit:Int,coefficients:List[Int],powers:List[Int]):Double = {
  (lowerLimit*1d to upperLimit*1d by 0.001d).map(0.001d*func(coefficients,powers,_)).sum;
}
