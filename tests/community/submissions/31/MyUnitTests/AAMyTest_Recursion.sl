function factorial(n) {
    if (n == 0) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

function checkRecursion() {
    result = factorial(0);       // Factorial of 0 is 1
    println(result);             // Prints 1

    result = factorial(1);       // Factorial of 1 is 1
    println(result);             // Prints 1

    result = factorial(5);       // Factorial of 5 is 5 * 4 * 3 * 2 * 1 = 120
    println(result);             // Prints 120

    result = factorial(7);       // Factorial of 7 is 7 * 6 * 5 * 4 * 3 * 2 * 1 = 5040
    println(result);             // Prints 5040
}

function main() {
    checkRecursion();
}
