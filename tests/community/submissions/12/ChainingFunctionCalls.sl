/*
* Test for chaining function calls
*/

function add(a, b) {
    return a + b;
}

function main() {
    println(add(add(add(1, 2), add(3, 4)), add(5, 6)));
}