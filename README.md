# KaratsubaBinaryMultiplication

_Karatsuba binary multiplication using only strings_

_Assignment Statement_

You have the task of implementing the Karatsuba algorithm to multiply integers of any size under the following conditions:

- Your algorithm does not work with numbers in base 10; it uses binary all the time.

- The numbers are passed as command-line arguments, for example:
  java Karatsuba 11110100011010110000010010010101100001000010011111-01110011011111101101011111 111010001111110101001010100001110101110-1100101000101010100100001110101101 // Two large numbers

1101111001110010110100010001001100110100101000101101111001101101000-0101101100110001111001000011111010100101100101000010110010000100101011110000110011 // One larger number

- The numbers must always be treated as binary strings, and arithmetic operations must be done one digit at a time (bit by bit).

- You need to implement long number addition operations (strings) to complete the task.

- You cannot use long integers or anything similar in your implementation.

- The allowed languages for submission are Java, Python, and C/C++.

---

DEVELOPMENT:

PSEUDO CODE:

FUNCTION: multiply(x,y)
INPUT: x,y
OUTPUT: multiplication x\*y

STEPS:

1. Let X and Y the same length by leading 0s in the smaller string and return length (n).
2. FH = n/2 -> first half of the string, floor(n/2).
3. SH = L-FH -> second half of the string, ceil(n/2).
4. Xl = substr(0,FH), Xr = substr(FH,SH); Yl = substr(0,FH), Xr = substr(FH,SH).
5. R1 = multiply(Xl,Yl).
6. R2 = multiply(Xr,Yr).
7. R3 = ((Xl + Xr) \* (Yl + Yr)).
8. Return R1 * 2ˆ(2*ceil(n/2)) + (R3 - R2 - R1) * 2ˆ(2*ceil(lenght/2)) + R2.
