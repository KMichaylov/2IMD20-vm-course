function main() {
    val1 = createLargeIntFromNumber(10, 1234567890);
    printLargeInt(val1);

    val2 = createLargeIntFromNumber(10, 12345);
    printLargeInt(val2);

    val3 = createLargeIntFromNumber(20, 0);
    printLargeInt(val3);
}

function createLargeInt(size) {
    ret = new();
    i = 0;
    while(i < size) {
        ret[i] = 0;
        i = i + 1;
    }
    return ret;
}

function createLargeIntFromNumber(size, number) {
    ret = createLargeInt(size);

    i = size - 1;
    while(number > 0 && i >= 0) {
        ret[i] = modulo(number, 10);
        
        i = i - 1;
        number = number / 10;
    }

    return ret;
}


function printLargeInt(input) {
    size = getSize(input);
    printString = "";
    i = 0;
    started = false;
    while(i < size) {
        if (input[i] == 0 && started == false) {
            i = i + 1;
            continue;
        }
        started = true;
        printString = printString + input[i];
        i = i + 1;
    }
    if (printString == "") {
        printString = printString + "0";
    }

    println(printString);
}


function modulo(dividend, divisor) {
    remain = ((dividend - (divisor * ((dividend / divisor) + 1))) + divisor);
    return remain;
}