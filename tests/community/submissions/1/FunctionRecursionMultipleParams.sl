function sumRange(start, end) {
    if (start > end) {
        return 0;
    }
    return start + end + sumRange(start + 1, end - 1);
}

function main() {
    println(sumRange(1, 10));
}
