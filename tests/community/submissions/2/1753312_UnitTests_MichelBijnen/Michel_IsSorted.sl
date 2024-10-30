function isSorted(a) {
    i = 1;
    while (i < getSize(a)) {
        if (a[i - 1] > a[i]) {
            return false;
        }
        i = i + 1;
    }
    return true;
}

function main() {
    a = new();
    a[0] = 5;
    println(isSorted(a));

    a[1] = 7;
    println(isSorted(a));

    a[2] = 10;
    println(isSorted(a));

    a[3] = 1;
    println(isSorted(a));

    a[4] = 10000;
    println(isSorted(a));

    a[3] = 20;
    println(isSorted(a));
}