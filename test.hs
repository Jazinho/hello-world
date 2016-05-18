incAndTriple v = (v+1) *3

specialCases 1 = "Hello"
specialCases 2 = " "
specialCases 3 = "World"
specialCases 4 = "!"
specialCases x = "???"


head1 (x:_) = x

length1(a:x) = do length1(x)+1
length1(_) = 0

--take1(num,x:t) = do take1(num-1,t) : x
take1 (1,x:_) = x

mymap fun [] = []
mymap fun (x:xs) = (fun x) : (mymap fun xs)



myplus [] [] = []
myplus (x:xs) [] = x : myplus xs []
myplus [] (y:ys) = y : myplus ys []
myplus (x:xs) (y:ys) = x : (myplus xs (y:ys))

mytake 0 _ = []
mytake _ [] = []
mytake x (y:ys) = (y : (mytake (x-1) ys))