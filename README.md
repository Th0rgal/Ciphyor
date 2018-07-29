![Ciphyor Social Banner](https://zupimages.net/up/18/30/t9ab.jpg)
# Ciphyor

## What's it?
A Java implementation of my own encryption algorithm inspired by Vernam one.

## How does it work ?

### Encryption
* You enter two String: a key and a message
* The message is encoded in base32 (using Guava)
* The key is hashed into a SHA-3 (515 bits) hash that we will call h1 (it's an int)
* A pseudo random number called salt is generated according to a small algorithm which confers a property: once added to h1 the result is necessarily between Integer.MIN_VALUE and Integer.MAX_VALUE (included)
* The result of salt and h1 addition is hashed into another SHA-3 (515 bits) hash that we will call h2 (it's still an int)
* A random generator is initialized with h2. 
* It will generate a sequence of numbers used to shift the characters of the base32 of the initial message into the alphabet.
> Example : The base32 alphabet contains letters + numbers so there are 36 characters. If we want to shift the letter C of 25 (the third letter in the alphabet), we add 25 to 3 and we get 28. The twenty eighth letter of the alphabet is 1 (25 is Y, 26 is Z, 27 is 0, etc). If the result is not into the alphabet (for example 38), we substract 36 to it (so it's 2).
* We cipher the sal's base32 using a random generator initialized with h1.
* A String which contains ciphered sal +  ciphered message is returned 
