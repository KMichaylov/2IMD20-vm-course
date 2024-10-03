
function y() {
    return 1;
}

function x() {
    return 2;
}

// should return 20
function foo() {
    y = 10;
    if (0 == 0) {
        y = 20;
    } else {
        y = 30;
    }
    return y;
}

function main() {
    z = foo(); // z = 20
    if (0 == 0) {
        y = x;
        z = z + 2 * y(); // z = 24
    } else {
        y = x;
    }
    z = z + y(); // z = 25
    return z;
}
