function main() {
    println(GCD(56, 98));
    println(GCD(12, 36));
    println(GCD(13, 37));
}

// Greatest Common Divisor
function GCD(a, b) {
    if (b == 0) {
        return a;
    } else {
        return GCD(b, modulo(a, b));
    }
}

function modulo(dividend, divisor) {
    remain = ((dividend - (divisor * ((dividend / divisor) + 1))) + divisor);
    return remain;
}
