function checkPEMDAS() {
    result = 2 + 3 * 4;          // Should be 14 (Multiplication before Addition)
    println(result);             // Prints 14

    result = (2 + 3) * 4;        // Should be 20 (Parentheses before Multiplication)
    println(result);             // Prints 20

    result = 2 + 3 * 4 - 5;      // Should be 9 (Multiplication before Addition and Subtraction)
    println(result);             // Prints 9

    result = 4 / 2 + 3 * 2;      // Should be 8 (Division and Multiplication before Addition)
    println(result);             // Prints 8
}

function main() {
    checkPEMDAS();
}
