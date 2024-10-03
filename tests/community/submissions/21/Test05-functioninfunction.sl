/**
    Test for function within a function.
*/

function multi(a, b) {
    return a * b;
}

function add(a, b) {
    return a + b;
}

function main() {
    println(multi(add(1, 3), add(2,3)));
}