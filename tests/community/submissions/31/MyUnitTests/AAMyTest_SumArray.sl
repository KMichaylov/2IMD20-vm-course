function sumArray(arr) {
    total = 0;
    i = 0;
    
    while (i < getSize(arr)) {
        total = total + arr[i];
        i = i + 1;
    }
    
    return total;
}

function checkSumArray() {
    a = new();
    
    a[0] = 1;
    a[1] = 2;
    a[2] = 3;
    a[3] = 4;            // Sum should be 10
    println(sumArray(a)); // Prints 10

    b = new();
    
    b[0] = 5;
    b[1] = 5;            // Sum should be 10
    println(sumArray(b)); // Prints 10

    c = new();
    
    c[0] = 0;            // Sum should be 0
    println(sumArray(c)); // Prints 0

    d = new();
    
    d[0] = 10;
    d[1] = 20;
    d[2] = 30;           // Sum should be 60
    println(sumArray(d)); // Prints 60

    e = new();
    
    e[0] = 100;
    e[1] = 200;          // Sum should be 300
    println(sumArray(e)); // Prints 300
}

function main() {
    checkSumArray();
}
