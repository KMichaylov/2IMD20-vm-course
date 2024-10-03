function add(a, b) {
    return a + b;
}

function main() {
    println(5 * 2 / 3 + 13 * 3);
    println(5 * 2 / (3 + 13) * 3);
    println(100 / 3 - add(7, 2) * (5 - 6));
    println(2 + 5 * (3 / 2) * 8);
    println(15 / 10 * 10);
    println(15 / (10 * 10));
}