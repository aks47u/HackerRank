module Main where

gcd' :: Integral a => a -> a -> a
gcd' x y | x > y = gcd' (x-y) y | x < y = gcd' x (y-x) | x == y = x

main = do
  input <- getLine
  print . uncurry gcd' . listToTuple . convertToInt . words $ input
 where
  listToTuple (x:xs:_) = (x,xs)
  convertToInt = map (read :: String -> Int)
