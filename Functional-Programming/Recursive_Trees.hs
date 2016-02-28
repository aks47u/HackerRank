import Data.List 

type Grid c = [[c]]
type Point = (Int, Int)
type Image c = Point -> c
type Tree = (Point, Int)

pointsOfTree :: Tree -> [Point]
pointsOfTree ((basex, basey), h) = trunk ++ left ++ right
    where trunk = [(basex, y) | y <- [basey..(basey+ (half-1))]]
          left = zip [(basex+1)..] [(basey + half)..(basey + half + half - 1)]
          right = zip [(basex-1), (basex-2)..] [(basey + half)..(basey + half + half - 1)]
          half = h `div` 2

allPoints :: [Tree] -> [Point]
allPoints ts = concat (map pointsOfTree ts)

subDivide :: Tree -> [Tree]
subDivide ((basex, basey), h) = [leftTree, rightTree]
    where leftTree = ((basex + half, basey + h), half)
          rightTree = ((basex - half, basey + h), half)
          half = h `div` 2

makeIntGrid :: Int -> Int -> Grid Point
makeIntGrid w h = [[(x,y) | x <- [1..w]] | y <- [h,(h-1)..1]]

charRender :: Grid Char -> IO ()
charRender = putStr . unlines

sample :: Grid Point -> Image Char -> Grid Char
sample grid image = map (map image) grid

treeImage :: Tree -> Point -> Char 
treeImage t = pointImage (pointsOfTree t)

pointImage :: [Point] -> Point -> Char
pointImage ps = \p -> if p `elem` ps then '1' else '_'

collectN :: [Tree] -> Int -> [Tree]
collectN ts n 
    | n <= 1 = ts
    | otherwise = ts ++ collectN result (n-1)
        where result = concat (map subDivide ts)

draw grid render = render grid

tree1 = ((50,1), 32) :: Tree

treelist = tree1 : (subDivide tree1)

solve :: Int -> [Point]
solve n = allPoints (collectN [tree1] n)

figure n = draw (sample (makeIntGrid 100 63) (pointImage (solve n))) charRender

main = do 
  n <- getLine
  figure (read n :: Int)
