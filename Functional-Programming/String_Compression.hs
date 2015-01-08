import qualified Data.List as L

main :: IO()
main = do
	msg <- getLine
	putStrLn $ L.intercalate "" $ map encode $ L.group msg
	where
		encode x
			| length x == 1 = x
			| otherwise = (head x) : (show $ length x)
