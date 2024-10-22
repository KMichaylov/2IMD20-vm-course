function main() {
    println(primeCheck(1));
    println(primeCheck(2));
    println(primeCheck(3));
    println(primeCheck(8));
    println(primeCheck(12));
    println(primeCheck(17));
    println(primeCheck(1024));
    println(primeCheck(2003));
}


function primeCheck(input) {
    if (input <= 1) {
        return false;
    }
    i = 2;
    while (i * i < input) {
        if (modulo(input, i) == 0) {
            return false;
        }

        i = i + 1;
    }
    return true;
}


function modulo(dividend, divisor) {
    remain = ((dividend - (divisor * ((dividend / divisor) + 1))) + divisor);
    return remain;
}