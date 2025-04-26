import System.Environment (getArgs)

---------------------------------------------------------------------
-- Homework 7 CSV CLI tool
-- Due: 3/27
--
-- One thing Haskell is really good for is file processing.
-- Haskell's tools allow us to write building block that we can
-- use to process larger file.
--
-- In this assignment we're going to build a tool to
-- parse, manipulate, and summarize CSV files.
-- A CSV file has the form
-- text,text,text,text
-- text,text,text,text
-- text,text,text,text
--
-- We will make a couple of assumptions.
-- 1. our CSV files will contain only integers
-- 2. our CSV files will be rectangular
--     That is, each line has the same number of cells
-- 
-- None of the operations we're doing are going to be
-- incredibly novel, but they will show you how
-- we can combine smaller pieces to make larger Haskell programs.
--
-- This will also give you a lot of experience manipulating lists
-- using higher order functions.
--
-- You MAY NOT use recursion on this assignment.
--
-- You can find the code from reading the command line arguments
-- and the CSV in the helper function section
---------------------------------------------------------------------

-------------------------------------------
-- Part 1: reading the CSV file
--
-- We need to read a CSV file before we can do anything.
-- This can be done in 3 steps.
-- 1. split the file into lines
-- 2. split each line into a list of cells
-- 3. convert each cell from a String into an Int
--
-- Useful functions:
--  * splitOn (defined in the helper functions section)
--  * read :: String -> Int
-------------------------------------------

readCSV :: String -> [[Int]]
readCSV input = map (map read . splitOn ',' ) (lines input)

-------------------------------------------
-- Part 2: writing CSV and excel files
--
-- We would also like to write CSV files to standard out.
-- This is the reverse process of reading files.
--
-- For each line, we want to put a comma between every element,
-- then we want to join all of the lines together.
--
-- There are many ways to do this.
-- I found it useful to write a function a function
-- addChar :: Char -> String -> String
-- where addChar ',' "4" "5,6,7" would return "4,5,6,7"
--
-- useful functions
--  * foldr1 :: (a -> b -> b) -> [a] -> b
--  * show :: Int -> String
--
-- A TSV file is a Tab Seperated Value file.
-- It's just like a CSV file except you use tabs.
-- TSV files are nice, because you can copy/paste them into excel.
-------------------------------------------

toCSV :: [[Int]] -> String
toCSV = unlines . map (foldr1 (\x other -> x ++ "," ++ other) . map show)

toTabs :: [[Int]] -> String
toTabs = unlines . map (foldr1 (\x other -> x ++ "\t" ++ other) . map show)

----------------------------------------------
-- Part 3: counting
--
-- In this part we're writing 3 different functions,
-- count, countEvens, countOdds
--
-- First, count n csv should count the number of times
-- n appears in the CSV file.
-- It should then return that as a string.
--
-- countEvens and countOdds shoud report the number
-- of even/odd numbers in the CSV file respectively.
--
-- I found it useful to define a more general function
-- countIf :: (Int -> Bool) -> [[Int]] -> String
-- countIf f csv should return the number of times
-- f was True in CSV, and convert that to a string.
--
-- helpful functions
--  * even :: Int -> Bool
--  * filter :: (a -> Bool) -> [a] -> [a]
--  * show :: Int -> String
--  * length :: [a] -> String
----------------------------------------------

count :: Int -> [[Int]] -> String
count n csv = "Total: " ++ show n ++ ": " ++ show (length (filter (elem n) csv))

countEvens :: [[Int]] -> String
countEvens csv = "Number Evens: " ++ show (length (filter even (concat csv)))

countOdds :: [[Int]] -> String
countOdds csv = "Number Odds: " ++ show (length (filter odd (concat csv)))


-------------------------------------------------------
-- Part 4: summerizing data
--
-- Now that we can count data, we want to actually summarize
-- the data.
-- We have 4 functions here.
-- 1. sum all of the cells in each row
-- 2. sum all of the cells in each column
-- 3. average all of the cells in each row
-- 4. average all of the cells in each column
--
-- useful functions
--  * show :: [Int] -> String
--  * map :: (a -> b) -> [a] -> [b]
--  * foldr :: (a -> b -> b) -> b -> [a] -> b
--  * length :: [a] -> Int
-------------------------------------------------------

sumRows :: [[Int]] -> String
sumRows = unlines . map (("Row Sum: " ++) . show . sum)

sumCols :: [[Int]] -> String
sumCols = ("Column Sum: " ++) . show . map sum . transpose

averageRows :: [[Int]] -> String
averageRows = unlines . map (("Row Average: " ++) . show . (\row -> sum row `div` length row))

averageCols :: [[Int]] -> String
averageCols = ("Column Average: " ++) . show . map (\col -> sum col `div` length col) . transpose

-------------------------------------------------------
-- Part 5: droping
--
-- This time we're just dropping the nth row or column.
--
-- csv =
-- 1,2,3,4,5
-- 2,3,4,5,1
-- 3,4,5,1,2
--
-- dropRow 1 csv =
-- 1,2,3,4,5
-- 3,4,5,1,2
--
-- dropCol 2 csv = 
-- 1,2,4,5
-- 2,3,5,1
-- 3,4,1,2
--
-- helpful functions
--  * take :: Int -> [a] -> [a]
--  * drop :: Int -> [a] -> [a]
--  * (++) :: [a] -> [a] -> [a]
--  * transpose :: [[a]] -> [[a]]
--    defined in the helper function section
-------------------------------------------------------

dropRow :: Int -> [[a]] -> [[a]]
dropRow n rows = take (n - 1) rows ++ drop n rows

dropCol :: Int -> [[a]] -> [[a]]
dropCol n = transpose . dropRow n . transpose

-------------------------------------------------------
-- Part 6: flips and rotations
--
-- Now we want to flip the CSV file, and rotate it.
--
-- examples
-- csv = 
-- 1,2,3
-- 4,5,6
-- 7,8,9
--
-- flipVert csv =
-- 7,8,9
-- 4,5,6
-- 1,2,3
--
-- flipHori csv = 
-- 3,2,1
-- 6,5,4
-- 9,8,7
--
-- rot90 csv = 
-- 7,4,1
-- 8,5,2
-- 9,6,3
--
-- rot180 csv = 
-- 9,8,7
-- 6,5,4
-- 3,2,1
--
-- rot270 csv = 
-- 3,6,9
-- 2,5,8
-- 1,4,7
--
-- This looks really hard, so we're going to cheat by drawing squares.
-- (Ok, technically we're cheating with group theory).
--
-- First, draw a square on a piece of paper,
-- and label the corners A,B,C,D
--
-- A                 B
--  *---------------*
--  |               |
--  |               |
--  |               |
--  |               |
--  |               |
--  *---------------*
-- D                 C
--
-- Now, do all of the operations (including transpose),
-- and write down what square you get.
--
-- Next, try doing one operation then another.
-- For example try transpose, then flipHori,
-- What square do you have?
--
-- Now, let's actually write flipHori and flipVert.
-- Think about what these actually do to our CSV file
-- which is a list of lists.
--
-- Finally, we draw the rest of the owl....
-- I mean, finish the rest of the functions.
--
-- Useful functions
-- * reverse :: [a] -> [a]
-- * map :: (a -> b) -> [a] -> [b]
-- * transpose :: [[a]] -> [[a]]
-------------------------------------------------------

flipVert :: [[a]] -> [[a]]
flipVert = reverse

flipHori :: [[a]] -> [[a]]
flipHori = map reverse

rot90 :: [[a]] -> [[a]]
rot90 = transpose . reverse

rot180 :: [[a]] -> [[a]]
rot180 = reverse . map reverse

rot270 :: [[a]] -> [[a]]
rot270 = reverse . transpose

-------------------------------------------------------
-- Part 7 swaping rows and columns.
-- 
-- In this part I want to write a function that will
-- swap two rows in the CSV file.
--
-- I found it helpful two write a function
-- to split a list into 5 parts
-- split5 :: Int -> Int -> [a] -> ([a],a,[a],a,[a]
-- split5 2 4 [0,1,2,3,4,5,6] returns ([0,1], 2, [3], 4, [5,6])
-- This function returns 
--  * a list before the first index
--  * the element at the first index
--  * a list between the first and second index
--  * the element at the second index
--  * a list after the second index.
-- You will find the splitAt function very helpful here
-- splitAt :: Int -> [a] -> ([a],[a])
-- splits a list at a given index into two lists
-- splitAt 4 [0,1,2,3,4,5,6] = ([0,1,2,3],[4,5,6])
--
-- useful functions
--  * splitAt :: Int -> [a] -> ([a],[a])
--  * ++ :: [a] -> [a] -> [a]
-------------------------------------------------------

swapRow :: Int -> Int -> [[a]] -> [[a]]
swapRow x y = error "swapRow not implemented"

swapCol :: Int -> Int -> [[a]] -> [[a]]
swapCol x y = error "swapCol not implemented"

-------------------------------------------------------
-- Part 8 add rows and colums
-- 
-- This time I want to add one row to another
-- example:
-- csv = 
-- 1,2,3
-- 4,5,6
-- 7,8,9
--
-- addRow 0 2
-- 7,10,12
-- 4,5,6
-- 7,8,9
--
-- I added row 0 and 2 together, and stored it in row 0
-- I did not change row 2.
--
-- helpful functions
--  * zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
--  * ++ :: [a] -> [a] -> [a]
--  * split5
-------------------------------------------------------
addRow :: Int -> Int -> [[Int]] -> [[Int]]
addRow x y = error "addRow not implemented"

addCol :: Int -> Int -> [[Int]] -> [[Int]]
addCol x y = error "addCol not implemented"

-------------------------------------------------------
-- Part 8 scale rows and colums
--
-- Finally, I want to be able to scale rows by a number
-- (Yes, you're right, we did just implement the
--  elementary row operations from linear algebra)
--
-- example:
-- csv = 
-- 1,2,3
-- 4,5,6
-- 7,8,9
--
-- scalRow 1 5 csv =
-- 1,2,3
-- 20,25,30
-- 7,8,9
--
-- useful functions
--  * ++ :: [a] -> [a] -> [a]
--  * map :: (a -> b) -> [a] -> [b]
--  * splitAt :: Int -> [a] -> ([a],[a])
--  * head :: [a] -> a
--  * tail :: [a] -> [a]
scaleRow :: Int -> Int -> [[Int]] -> [[Int]]
scaleRow x n = error "scaleRow not implemented"

scaleCol :: Int -> Int -> [[Int]] -> [[Int]]
scaleCol x n = error "scaleCol not implemented"


----------------------------------------------------------
--
-- Helper functions
--
----------------------------------------------------------

-- The main function
-- We process a file by
--  * reading the command line arguments
--  * reading the file name
--  * recursively performing the command specified by each argument
--  * writing the result to stdout
main :: IO ()
main = do args <- getArgs
          let process = putStrLn . (getCmd (init args)) . readCSV
          readFile (last args) >>= process
            

-- getCmd will recursively process each command line argument.
-- In fact, we are actualy building up a giant function composition
-- for each argument, and when we have processed them all,
-- we can run the function.
-- This works because each transformation is a function
-- t :: [[Int]] -> [[Int]]
-- So, if I have a string of a bunch of transformations
-- t1, t2, t3, t4,
-- then I can compose them
-- t4 . t3 . t2 . t1
-- And that is still a function with type [[Int]] -> [[Int]]
--
getCmd :: [String] -> [[Int]] -> String
getCmd []             = toCSV
getCmd ["-sumRow"]    = sumRows
getCmd ["-sumCol"]    = sumCols
getCmd ["-avgRow"]    = averageRows
getCmd ["-avgCol"]    = averageCols
getCmd ["-count",n]   = count (read n)
getCmd ["-evens"]     = countEvens
getCmd ["-odds"]      = countOdds
getCmd ["-tabs"]      = toTabs
getCmd ["-CSV"]       = toCSV
getCmd ("-h":_)       = showHelp
getCmd ("-t":as)      = getCmd as . transpose
getCmd ("-dr":n:as)   = getCmd as . dropRow (read n)
getCmd ("-dc":n:as)   = getCmd as . dropCol (read n)
getCmd ("-fv":as)     = getCmd as . flipVert
getCmd ("-fh":as)     = getCmd as . flipHori
getCmd ("-r90":as)    = getCmd as . rot90
getCmd ("-r180":as)  = getCmd as . rot180
getCmd ("-r270":as)   = getCmd as . rot270
getCmd ("-sr":x:y:as) = getCmd as . swapRow (read x) (read y)
getCmd ("-sc":x:y:as) = getCmd as . swapCol (read x) (read y)
getCmd ("-ar":x:y:as) = getCmd as . addRow (read x) (read y)
getCmd ("-ac":x:y:as) = getCmd as . addCol (read x) (read y)
getCmd ("-mr":n:r:as) = getCmd as . scaleRow (read n) (read r)
getCmd ("-mc":n:c:as) = getCmd as . scaleCol (read n) (read c)
getCmd x = error $ "invalid command: " ++ unwords x ++ "\n" ++
                   showHelp ()

-- splitOn Takes a list, and splits it based of an element
-- Typically we'd use splitOn for Strings,
-- so we can split a String on a single character.
--
-- For example, if we want to split a string into words,
-- we might call
-- splitOn ' ' "This is the end of the squirrel"
-- ["This","is","the","end","of","the","squirrel"]
splitOn :: (Eq a) => a -> [a] -> [[a]]
splitOn c cs 
 | null cs     = []
 | null before = splitOn c (safeTail after)
 | otherwise   = before : splitOn c (safeTail after)
  where (before,after) = span (/= c) cs
        safeTail []    = []
        safeTail (_:t) = t

-- transpose flips a matrix along it's diagonal
-- m = 
-- 1,2,3
-- 4,5,6
-- 7,8,9
--
-- transpose m =
-- 1,4,7
-- 2,5,8
-- 3,6,9
--
-- This function is also defined in Data.List,
-- but I'm trying to reduce dependancies.
transpose :: [[a]] -> [[a]]
transpose [x] = map (:[]) x
transpose (x:xs) = zipWith (:) x (transpose xs)

----------------------------------------------------------
--
-- Help message is just very long
--
----------------------------------------------------------
showHelp :: a -> String
showHelp _ = "usage:\n" ++
             "./csv flags file.csv\n" ++
             "flags\n" ++
             "  -t: transpose\n" ++
             "  -dr n: drop row n from file\n" ++
             "  -dc n: drop column n from file\n" ++
             "  -fv: flip vertically\n" ++
             "  -fh: flip horizontally\n" ++
             "  -r90: rotate 90 degrees\n" ++
             "  -r180: rotate 180 degrees\n" ++
             "  -r270: rotate 270 degrees\n" ++
             "  -sr x y: swap rows x and y\n" ++
             "  -sc x y: swap columns x and y\n" ++
             "  -ar x y: add row x and y, store in row x\n" ++
             "  -ac x y: add columns x and y, store in column x\n" ++
             "  -mr x n: scale row x by n\n" ++
             "  -mc x n: scale column x by n\n" ++
             "  -sumRow: sum all rows together\n" ++
             "  -sumCol: sum all rows together\n" ++
             "  -avgRow: sum all rows together\n" ++
             "  -avgCol: sum all rows together\n" ++
             "  -count n: count the number of times n appears\n" ++
             "  -evens: count the number of even numbers\n" ++
             "  -odds: count the number of odd numbers\n" ++
             "  -tabs: return a string where cells are seperated by tabs\n" ++
             "  -CSV: return a string in comma seperated value format\n"
