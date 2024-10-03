function isDivisible(a, b) {
    if (b == 0) {
        return false;
    }

    while (a >= b) {
        a = a - b;
    }

    if (a == 0) {
        return true;
    } else {
        return false;
    }
}

function isPrimeNumber(x) {
    if (x <= 1) {
        return false;
    }
    i = 2;
    while (i <= (x / 2)) {
        res = isDivisible(x, i);
        if (res == true) {
            return false;
        }
        i = i + 1;
    }
    return true;
}

function main() {
    println(isPrimeNumber(1));
    println(isPrimeNumber(2));
    println(isPrimeNumber(13));
    println(isPrimeNumber(42));
    println(isPrimeNumber(69));
    println(isPrimeNumber(1337));
    println(isPrimeNumber(524287));
}