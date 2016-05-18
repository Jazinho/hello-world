import Data.Maybe (catMaybes)
--import Data.List

data Field = A | U | B | W deriving (Show,Eq)

type Board = [ (Field,Integer,Integer) ]

board :: Board
board = [if a<4 then (W,a,b) else (B,a,b) | a<-[1..8],b<-[1..8],((a `mod` 2 == 1) && (b `mod` 2 == 0)) || ((a `mod` 2 == 0) && (b `mod` 2 == 1)), a< 4 || a>5 ]

writeField field = do case field of
                                W -> return('w')
                                B -> return('b')
                                A -> return('W')
                                U -> return('B')

--wczytywanie z funkcji readField odbywa siê za pomoc¹ let board=readField ()

readField :: ([Char],Integer,Integer) -> Board

readField (el:string,a,b) = do
    if el=='b' && a<9 && b<9 then (B,a,b):readField (string,a,b+1)
    else if el=='w' && a<9 && b<9 then (W,a,b):readField (string,a,b+1)
    else if b<8 then readField (string,a,b+1)
    else readField (string,a+1,1)

readField ("",_,_) = []

checkField (board,1,1) = do
    putStr("1 ")
    if elem (W,1,1) board then putStr("w ") >> checkField (board,1,2)
    else if elem (B,1,1) board then putStr("b ") >> checkField (board,1,2)
    else putStr("_ ") >> checkField (board,1,2)

checkField (board,a,b) = do
    if elem (W,a,b) board then putStr("w ") >> checkField (board,a,b+1)
    else if elem (B,a,b) board then putStr("b ") >> checkField (board,a,b+1)
    else if a<8 && b>8 then putStr("\n"++show(a+1)++" ") >> checkField (board,a+1,1)
    else if b<9 then putStr("_ ") >> checkField (board,a,b+1)
    else putStrLn("\n  1 2 3 4 5 6 7 8")

genMoves (f,a,b) board = catMaybes [a1, a2, a3, a4] --change list o Maybe's to normal list
     where a1 = if (elem (f,a,b) board && notElem (W,a+1,b+1) board && notElem (B,a+1,b+1) board && a<8 && b<8) then Just (a+1,b+1) else Nothing
           a2 = if (elem (f,a,b) board && notElem (W,a+1,b-1) board && notElem (B,a+1,b-1) board && a<8 && b>1) then Just (a+1,b-1) else Nothing
           a3 = if (elem (f,a,b) board && notElem (B,a-1,b+1) board && notElem (W,a-1,b+1) board && a>1 && b<8) then Just (a-1,b+1) else Nothing
           a4 = if (elem (f,a,b) board && notElem (B,a-1,b-1) board && notElem (W,a-1,b-1) board && a>1 && b>1) then Just (a-1,b-1) else Nothing

data Branch = Branch {nextHop::(Integer,Integer) , nextStruct::[Branch]} deriving (Eq,Show)

genKill :: (Field,Integer,Integer) -> Board -> [Branch]

genKill (W,a,b) board = catMaybes [a1, a2, a3, a4]
     where a1= if (elem (W,a,b) board && notElem (W,a+2,b+2) board && notElem (B,a+2,b+2) board && elem (B,a+1,b+1) board && a<7 && b<7) then Just (Branch{nextHop=(a+2,b+2), nextStruct = genKill (W,a+2,b+2) (dodaj (usun board (B,a+1,b+1)) (W,a+2,b+2))}) else Nothing
           a2 = if (elem (W,a,b) board && notElem (W,a+2,b-2) board && notElem (B,a+2,b-2) board && elem (B,a+1,b-1) board && a<7 && b>2) then Just (Branch{nextHop=(a+2,b-2), nextStruct = genKill (W,a+2,b-2) (dodaj (usun board (B,a+1,b-1)) (W,a+2,b-2))} ) else Nothing
           a3 = if (elem (W,a,b) board && notElem (B,a-2,b+2) board && notElem (W,a-2,b+2) board && elem (B,a-1,b+1) board && a>2 && b<7) then Just (Branch{nextHop=(a-2,b+2), nextStruct = genKill (W,a-2,b+2) (dodaj (usun board (B,a-1,b+1)) (W,a-2,b+2))} ) else Nothing
           a4 = if (elem (W,a,b) board && notElem (B,a-2,b-2) board && notElem (W,a-2,b-2) board && elem (B,a-1,b-1) board && a>2 && b>2) then Just (Branch{nextHop=(a-2,b-2), nextStruct = genKill (W,a-2,b-2) (dodaj (usun board (B,a-1,b-1)) (W,a-2,b-2))} ) else Nothing

genKill (B,a,b) board = catMaybes [a1, a2, a3, a4]
     where a1= if (elem (B,a,b) board && notElem (W,a+2,b+2) board && notElem (B,a+2,b+2) board && elem (W,a+1,b+1) board && a<7 && b<7) then Just (Branch{nextHop=(a+2,b+2), nextStruct = genKill (B,a+2,b+2) (dodaj (usun board (W,a+1,b+1)) (B,a+2,b+2))}) else Nothing
           a2 = if (elem (B,a,b) board && notElem (W,a+2,b-2) board && notElem (B,a+2,b-2) board && elem (W,a+1,b-1) board && a<7 && b>2) then Just (Branch{nextHop=(a+2,b-2), nextStruct = genKill (B,a+2,b-2) (dodaj (usun board (W,a+1,b-1)) (B,a+2,b-2))} ) else Nothing
           a3 = if (elem (B,a,b) board && notElem (B,a-2,b+2) board && notElem (W,a-2,b+2) board && elem (W,a-1,b+1) board && a>2 && b<7) then Just (Branch{nextHop=(a-2,b+2), nextStruct = genKill (B,a-2,b+2) (dodaj (usun board (W,a-1,b+1)) (B,a-2,b+2))} ) else Nothing
           a4 = if (elem (B,a,b) board && notElem (B,a-2,b-2) board && notElem (W,a-2,b-2) board && elem (W,a-1,b-1) board && a>2 && b>2) then Just (Branch{nextHop=(a-2,b-2), nextStruct = genKill (B,a-2,b-2) (dodaj (usun board (W,a-1,b-1)) (B,a-2,b-2))} ) else Nothing

--przy przypisywaniu wyniku funkcji usun nie nalezy przypisywaæ wyniku do tej samej zmiennej - zawiesza program

usun :: Board -> (Field,Integer,Integer) -> Board
usun ((f,x,y):board) (fi,a,b) = if x==a && y==b && f==fi then board
                                else (f,x,y):(usun board (fi,a,b))
usun [] (f,a,b) = []

dodaj :: Board -> (Field,Integer,Integer) -> Board
dodaj board (f,a,b) = (f,a,b):board

-- moves f(pion) b(board) = (genKill f b, genMoves f b)

-- type - alias np. type String [Char]
-- newtype - silnijeszy np. newtype String = String [Char]
-- data - nowy typ np. data Figure = W|B|E
-- deriving Show - opracowania
-- LOKALNE ARGUMENTY: let...in... albo where...