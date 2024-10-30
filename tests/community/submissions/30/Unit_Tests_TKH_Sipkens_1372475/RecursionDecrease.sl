function decrease(i, k) {
    if (i == 0) {
        return k;
    }

    return decrease(i - 1, k + 1);
}

function main() {
    println(decrease(10, 0));
}