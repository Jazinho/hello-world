--Made by Jan Pa³ucki. To run: ghci -> graj board

import Data.Maybe (catMaybes)
import Data.Char

data Field = A | U | B | W deriving (Show,Eq)

type Board = [ (Field,Int,Int) ]

board :: Board
board = [if a<4 then (W,a,b) else (B,a,b) | a<-[1..8],b<-[1..8],((a `mod` 2 == 1) && (b `mod` 2 == 0)) || ((a `mod` 2 == 0) && (b `mod` 2 == 1)), a< 4 || a>5 ]

readField :: ([Char],Int,Int) -> Board

readField (el:string,a,b) = do
    if el=='b' && a<9 && b<9 then (B,a,b):readField (string,a,b+1)
    else if el=='w' && a<9 && b<9 then (W,a,b):readField (string,a,b+1)
    else if b<8 then readField (string,a,b+1)
    else readField (string,a+1,1)

readField ("",_,_) = []

readBoard (el:string) = readField (el:string,1,1)

checkField (board,1,1) = do
    putStr("1 ")
    if elem (W,1,1) board then putStr("w ") >> checkField (board,1,2)
    else if elem (B,1,1) board then putStr("b ") >> checkField (board,1,2)
    else if elem (A,1,1) board then putStr("B ") >> checkField (board,1,2)
    else if elem (U,1,1) board then putStr("W ") >> checkField (board,1,2)
    else putStr("_ ") >> checkField (board,1,2)

checkField (board,a,b) = do
    if elem (W,a,b) board then putStr("w ") >> checkField (board,a,b+1)
    else if elem (B,a,b) board then putStr("b ") >> checkField (board,a,b+1)
    else if elem (A,a,b) board then putStr("B ") >> checkField (board,a,b+1)
    else if elem (U,a,b) board then putStr("W ") >> checkField (board,a,b+1)
    else if a<8 && b>8 then putStr("\n"++show(a+1)++" ") >> checkField (board,a+1,1)
    else if b<9 then putStr("_ ") >> checkField (board,a,b+1)
    else putStrLn("\n  1 2 3 4 5 6 7 8")

drawBoard board = putStrLn("Aktualny stan rozgrywki:") >> checkField (board,1,1)

enemy a
    |a==W = B
    |a==B = W

genMoves (W,a,b) board = catMaybes [a1, a2] --change list of Maybe's to normal list
     where a1 = if (elem (W,a,b) board && notElem (W,a+1,b+1) board && notElem (B,a+1,b+1) board && a<8 && b<8) then Just (a+1,b+1) else Nothing
           a2 = if (elem (W,a,b) board && notElem (W,a+1,b-1) board && notElem (B,a+1,b-1) board && a<8 && b>1) then Just (a+1,b-1) else Nothing

genMoves (B,a,b) board = catMaybes [a1, a2]
     where a1 = if (elem (B,a,b) board && notElem (B,a-1,b+1) board && notElem (W,a-1,b+1) board && a>1 && b<8) then Just (a-1,b+1) else Nothing
           a2 = if (elem (B,a,b) board && notElem (B,a-1,b-1) board && notElem (W,a-1,b-1) board && a>1 && b>1) then Just (a-1,b-1) else Nothing


data Branch = Branch {nextHop::(Int,Int) , nextStruct::[Branch]} deriving (Eq,Show)

genKill :: (Field,Int,Int) -> Board -> [Branch]

genKill (f,a,b) board = catMaybes [a1, a2, a3, a4]
     where a1= if (elem (f,a,b) board && notElem (f,a+2,b+2) board && notElem (enemy f,a+2,b+2) board && elem (enemy f,a+1,b+1) board && a<7 && b<7) then Just (Branch{nextHop=(a+2,b+2), nextStruct = genKill (f,a+2,b+2) (dodaj (usun (usun board (f,a,b)) (enemy f,a+1,b+1)) (f,a+2,b+2))}) else Nothing
           a2 = if (elem (f,a,b) board && notElem (f,a+2,b-2) board && notElem (enemy f,a+2,b-2) board && elem (enemy f,a+1,b-1) board && a<7 && b>2) then Just (Branch{nextHop=(a+2,b-2), nextStruct = genKill (f,a+2,b-2) (dodaj (usun (usun board (f,a,b)) (enemy f,a+1,b-1)) (f,a+2,b-2))} ) else Nothing
           a3 = if (elem (f,a,b) board && notElem (enemy f,a-2,b+2) board && notElem (f,a-2,b+2) board && elem (enemy f,a-1,b+1) board && a>2 && b<7) then Just (Branch{nextHop=(a-2,b+2), nextStruct = genKill (f,a-2,b+2) (dodaj (usun (usun board (f,a,b)) (enemy f,a-1,b+1)) (f,a-2,b+2))} ) else Nothing
           a4 = if (elem (f,a,b) board && notElem (enemy f,a-2,b-2) board && notElem (f,a-2,b-2) board && elem (enemy f,a-1,b-1) board && a>2 && b>2) then Just (Branch{nextHop=(a-2,b-2), nextStruct = genKill (f,a-2,b-2) (dodaj (usun (usun board (f,a,b)) (enemy f,a-1,b-1)) (f,a-2,b-2))} ) else Nothing

-- wywo³uj¹c rekurencyjnie funkcjê nalezy podajac tablice planszy usun¹æ z niej pionek który zbiliœmy, usunac pionek którym byliœmy przed ruchem oraz dodac nasz pionek po ruchu

-- To TEST: let board=readField("_________b________w_w_w___________w_w_w________",1,1)

pathFromGenKill :: [Branch] -> [(Int,Int)]

pathFromGenKill [] = []
pathFromGenKill (branch:rest)
    |nextStruct branch /= [] = nextHop branch : pathFromGenKill (nextStruct branch)
    |otherwise = nextHop branch : []

usun :: Board -> (Field,Int,Int) -> Board
usun ((f,x,y):board) (fi,a,b) = if x==a && y==b && f==fi then board
                                else (f,x,y):(usun board (fi,a,b))
usun [] (f,a,b) = []

dodaj :: Board -> (Field,Int,Int) -> Board
dodaj board (f,a,b) = (f,a,b):board

move (f,a,b) (x,y) board = if elem (x,y) (genMoves (f,a,b) board) then dodaj (usun board (f,a,b)) (f,x,y)
        else if elem (x,y) (pathFromGenKill (genKill (f,a,b) board)) then dodaj (usun (usun board (enemy f,(a+x) `div` 2,(b+y) `div` 2) ) (f,a,b)) (f,x,y)
        else board

zlicz ((f,a,b):board) = policz (0,0) ((f,a,b):board)
policz (x,y) ((f,a,b):board) = if f==W then policz (x+1,y) board
                               else policz (x,y+1) board
policz (x,y) [] = (x,y)

takeFirNum :: String -> Int

takeFirNum (a:rest) = digitToInt a

takeSecNum :: String -> Int

takeSecNum (a:b) = if b /= [] then takeSecNum b else digitToInt a

ruch board = do
    putStrLn("Ruch bialych. Podaj pole pionka ktorym chcesz ruszyc postaci 'wiersz kolumna' :") 
    x<-getLine
    putStrLn("Podaj pole docelowe w postaci 'wiersz kolumna' :") 
    y<-getLine
    let board2=move (W,takeFirNum x,takeSecNum x) (takeFirNum y,takeSecNum y) board
    if board2 == board then drawBoard board >> putStrLn("Niewlasciwy ruch!!! Popraw sie:") >> ruch board
    else do
    if not(czySaCzarne board2) then putStrLn("WYGRALY BIALE!!!")
    else do
    drawBoard board2
    putStrLn("Ruch czarnych. Podaj pole pionka ktorym chcesz ruszyc postaci 'liczba_liczba' :") 
    x<-getLine
    putStrLn("Podaj pole docelowe w postaci 'liczba_liczba' :") 
    y<-getLine
    let board=move (B,takeFirNum x,takeSecNum x) (takeFirNum y,takeSecNum y) board2
    if board == board2 then do
        drawBoard board2
        putStrLn("Niewlasciwy ruch!!! Popraw sie:")
        putStrLn("Ruch czarnych. Podaj pole pionka ktorym chcesz ruszyc postaci 'liczba_liczba' :") 
        x<-getLine
        putStrLn("Podaj pole docelowe w postaci 'liczba_liczba' :") 
        y<-getLine
        let board=move (B,takeFirNum x,takeSecNum x) (takeFirNum y,takeSecNum y) board2
        if czySaBiale board then graj board
        else putStrLn("WYGRALY CZARNE!!!")
    else
        if czySaBiale board then graj board
        else putStrLn("WYGRALY CZARNE!!!")

graj board = do
    drawBoard board
    ruch board

czySaBiale :: Board -> Bool
czySaBiale [] = False
czySaBiale ((W,_,_):board) = True
czySaBiale ((_,_,_):board) = czySaBiale board

czySaCzarne :: Board -> Bool
czySaCzarne [] = False
czySaCzarne ((B,_,_):_) = True
czySaCzarne ((_,_,_):board) = czySaCzarne board