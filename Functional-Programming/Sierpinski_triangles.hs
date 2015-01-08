import Control.Monad
import Data.List 

type Grid c = [[c]]
type Point = (Int, Int)
type IsoTriangle = (Point , Int)
type Image c = Point -> c

pointsOfTriangle :: IsoTriangle -> [Point]
pointsOfTriangle (top, h) = pointsAt top h

pointsAt :: Point -> Int -> [Point]
pointsAt (topx, topy) h 
    | h <= 1 = [(topx, topy)]
    | otherwise = current ++ (pointsAt (topx, topy) (h - 1))
        where current = [ (x, topy+(h-1)) | x <- [(topx - (h-1))..(topx + (h-1))]]

allPoints :: [IsoTriangle] -> [Point]
allPoints ts = concat (map pointsOfTriangle ts)

subDivide :: IsoTriangle -> [IsoTriangle]
subDivide ((topx, topy), h) = [first, second, third]
    where first = ((topx, topy), half)
          second = ((topx+half, topy+half), half)
          third = ((topx-half, topy+half), half)
          half = h `div` 2

makeIntGrid :: Int -> Int -> Grid Point
makeIntGrid w h = [[(x,y) | x <- [1..w]] | y <- [1..h]]

charRender :: Grid Char -> IO ()
charRender = putStr . unlines

sample :: Grid Point -> Image Char -> Grid Char
sample grid image = map (map image) grid

triangleImage :: IsoTriangle -> Point -> Char 
triangleImage t = pointImage (pointsOfTriangle t)

pointImage :: [Point] -> Point -> Char
pointImage ps = \p -> if p `elem` ps then '1' else '_'

dummyImage :: Point -> Char
dummyImage = \(x,y) -> if x ==y then ' ' else '*' 

collectN :: [IsoTriangle] -> Int -> [IsoTriangle]
collectN ts n 
    | n == 0 = ts
    | otherwise = collectN result (n-1)
        where result = concat (map subDivide ts)

draw grid render = render grid

triangle1 = ((32,1), 32) :: IsoTriangle

allPs = allPoints (collectN [triangle1] 5)

solve :: Int -> [Point]
solve n = allPoints (collectN [triangle1] n)

figure1 n = draw (sample (makeIntGrid 63 32) (pointImage (solve n))) charRender

main = do 
	n <- getLine
	figure1 (read n ::Int)
