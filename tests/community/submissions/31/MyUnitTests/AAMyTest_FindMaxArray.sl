function findMax(arr) {
    max = arr[0];
    i = 1;
    while (i < getSize(arr)) {
        if (arr[i] > max) {
            max = arr[i];
        }
        i = i + 1;
    }
    return max;
}

function checkFindMax() {
    a = new();
    
    a[0] = 3;
    a[1] = 1;
    a[2] = 4;
    a[3] = 1;
    a[4] = 5;
    a[5] = 9;                    // Max should be 9
    println(findMax(a));          // Prints 9

    c = new();
    
    c[0] = 10;
    c[1] = 20;
    c[2] = 30;
    c[3] = 40;
    c[4] = 50;                    // Max should be 50
    println(findMax(c));          // Prints 50

    d = new();
    
    d[0] = 7;                     // Max should be 7 (only one element)
    println(findMax(d));          // Prints 7
}

function main() {
    checkFindMax();
}
