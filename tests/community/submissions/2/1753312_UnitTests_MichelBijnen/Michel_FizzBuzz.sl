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

function main() {
    i = 0;
    while (i < 50) {
        i = i + 1;
        if (modulo(i, 3) == 0 && modulo(i, 5) == 0) {
            println("FizzBuzz");
        } else {
            if (modulo (i, 3) == 0) {
                println("Fizz");
            } else {
                if (modulo(i, 5) == 0) {
                    println("Buzz");
                } else {
                    println(i);
                }
            }
        }
    }
}