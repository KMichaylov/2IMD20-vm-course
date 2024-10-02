function null() {
}

function modulo(a, b) {
    if (b == 0) {
        return null();
    }

    while (a >= b) {
        a = a - b;
    }
    return a;
}

function binaryToDecimal(bin, n) {
    dec = 0;
    base = 1;
    i = 0;
    while (i < n) {
        digit = modulo(bin, 10);
        if (digit == 1) {
            dec = dec + base;
        }
        base = base * 2;
        bin = (bin - digit) / 10;
        i = i + 1;
    }
    return dec;
}

function main() {
    println(binaryToDecimal(1010, 4));
    println(binaryToDecimal(101010, 6));
    println(binaryToDecimal(1000101, 7));
    println(binaryToDecimal(10100111001, 11));
}