function isEven(num) {
    while (num >= 2) {
        num = num - 2; // Subtract 2 until less than 2
    }
    
    return num == 0; // If num is 0, it's even; otherwise, it's odd
}

function checkEvenOdd() {
    println(isEven(1));   // Should print false (1 is odd)
    println(isEven(2));   // Should print true (2 is even)
    println(isEven(3));   // Should print false (3 is odd)
    println(isEven(0));   // Should print true (0 is even)
    println(isEven(2000));   // Should print true (2000 is even)
}

function main() {
    checkEvenOdd();
}
