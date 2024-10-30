function isEven(num) {
    return num == num / 2 * 2;
}

function even(num) {
    if (num == 1) {
        return 1;
    }

    num = num / 2;
    if (isEven(num)) {
        return even(num) + 1;
    }
    return odd(num) + 1;
}

function odd(num) {
    if (num == 1) {
        return 1;
    }

    num = 3 * num + 1;
    if (isEven(num)) {
        return even(num) + 1;
    }
    return odd(num) + 1;
}

function collatz(num) {
    if (isEven(num)) {
        return even(num);
    }
    return odd(num);
}

function main() {
    println(collatz(1));
    println(collatz(42));
    println(collatz(420));
    println(collatz(1000));
}