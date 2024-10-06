function main() {
    println(BBP_calc_pi(0));
    println(BBP_calc_pi(2));
    println(BBP_calc_pi(10));
    println(BBP_calc_pi(50));
    println(BBP_calc_pi(100));
    println(BBP_calc_pi(300));
}

// get the front [prec] digits of pi (without decimal point)
function BBP_calc_pi(prec) {
    if (prec < 0) {
        println("prec should be positive");
        return "";
    }

    // [+1] is to add the integer part 3
    // [+2] is to cut off the two inaccurate digits at the end
    prec = prec + 1 + 2;

    result = createFixedInteger(prec);

    // P = 1;
    P = createFixedInteger(prec);
    P[prec - 1] = 1;

    i = 0;
    while(i < prec) {

        factor1 = multipleFixedIntegerAndInt(divideByOne(convertNumberToFixedInteger(8*i+1, prec), prec), 4);
        factor2 = multipleFixedIntegerAndInt(divideByOne(convertNumberToFixedInteger(8*i+4, prec), prec), 2);
        factor3 = divideByOne(convertNumberToFixedInteger(8*i+5, prec), prec);
        factor4 = divideByOne(convertNumberToFixedInteger(8*i+6, prec), prec);

        poly = subFixedInteger(subFixedInteger(subFixedInteger(factor1, factor2), factor3), factor4);

        result = addFixedInteger(
                result,
                multipleFixedIntergerAndFixedInteger_Decimal(
                    poly,
                    divideByOne(P, prec)
                )
            );

        P = multipleFixedIntegerAndInt(P, 16);
        
        i = i + 1;
    }

    return convertFixedIntegerToString_removeTail(result, 2);
}


// class FixedInteger
// FixedInteger implements some functions of BigInteger and BigDecimal

function createFixedInteger(prec) {
    ret = new();
    i = 0;
    while (i < prec) {
        ret[i] = 0;
        i = i + 1;
    }
    return ret;
}

function createFixedIntegerArray(size, prec) {
    ret = new();
    i = 0;
    while (i < size) {
        ret[i] = createFixedInteger(prec);
        i = i + 1;
    }
    return ret;
}

// a + b
function addFixedInteger(a, b) {
    if (getSize(a) != getSize(b)) {
        return a;   // error
    }

    len = getSize(a);
    re = createFixedInteger(len);

    acc = 0;
    i = len - 1;
    while(i >= 0) {
        sum = acc + a[i] + b[i];
        if (sum == 0) {
            i = i - 1;
            continue;
        }
        if (sum > 9) {
            acc = 1;
        } else {
            acc = 0;
        }

        re[i] = modulo(sum, 10);

        i = i - 1;
    }

    return re;
}

// a - b
function subFixedInteger(a, b) {
    if (getSize(a) != getSize(b)) {
        return a;   // error: supposed to be the same size
    }

    len = getSize(a);
    re = createFixedInteger(len);

    i = len - 1;
    while (i >= 0) {
        re[i] = a[i];
        i = i - 1;
    }

    // TODO: implement the operator "<"
    // if (a < b) { 
    //    // error
    // }

    borrow = 0;

    i = len - 1;
    while (i >= 0) {
        sub = b[i] + borrow;
        if (sub == 0) {
            i = i - 1;
            continue;
        }

        diff = a[i] - sub;
        borrow = 0;
        if (diff < 0) {
            borrow = 1;
            diff = diff + 10;
        }
        re[i] = diff;

        i = i - 1;
    }

    return re;
}


// a * b, where a is a FixedInteger, b is a normal int
function multipleFixedIntegerAndInt(a, b) {
    re = createFixedInteger(getSize(a));

    i = 0;
    while (i < b) {
        re = addFixedInteger(re, a);
        i = i + 1;
    }
    return re;
}

// a * b, where a and b represent Integer
function multipleFixedIntergerAndFixedInteger(a, b) {
    return performMultiple(a, b, False);
}

// a * b, where a and b represent Decimal
function multipleFixedIntergerAndFixedInteger_Decimal(a, b) {
    return performMultiple(a, b, True);
}

function performMultiple(a, b, isDecimal) {
    if (getSize(a) != getSize(b)) {
        return a;   // error: supposed to be the same size
    }
    len_a = getSize(a);
    len_b = getSize(b);

    prod = createFixedIntegerArray(10, len_a);
    prod[1] = a;
    i = 2;
    while (i < 10) {
        prod[i] = multipleFixedIntegerAndInt(a, i);
        i = i + 1;
    }

    len = 0;
    prod_9 = prod[9];
    i = getSize(prod_9) - 1;
    while(i >= 0) {
        if (prod_9[i] != 0) {
            break;
        }
        len = len + 1;

        i = i - 1;
    }
    rev_len = len_a - len;

    re = createFixedInteger(len_a * 2 - 1);
    b_idx = len_b - 1;
    while (b_idx >= 0) {
        digit = b[b_idx];
        if (digit == 0) {
            b_idx = b_idx - 1;
            continue;
        }
        prodx = prod[digit];
        acc = 0;

        prod_idx = rev_len - 1;
        while (prod_idx >= 0) {
            re_digit = re[b_idx + prod_idx];
            sum = acc + prodx[prod_idx] + re_digit;

            acc = sum / 10;
            re[b_idx + prod_idx] = modulo(sum, 10);

            prod_idx = prod_idx - 1;
        }

        b_idx = b_idx - 1;
    }

    ret = createFixedInteger(len_a);

    if (isDecimal == True) {
        i = 0;
        while (i < len_a) {
            ret[i] = re[i];
            i = i + 1;
        }
    } else {
        i = 1;
        while (i <= len_a) {
            ret[len_a - i] = re[len_a * 2 - 1 - i];
            i = i + 1;
        }
    }

    return ret;
}


function divideByOne(dividor, precision) {
    // TODO: implement the operator "<"
    // if(dividor < 0) {
    //     return 0;   // error
    // }

    prod = new();
    prod[0] = 0;
    i = 1;
    while(i < 10) {
        prod[i] = convertFixedIntegerToNumber(
                multipleFixedIntegerAndInt(dividor, i)
            );
        i = i + 1;
    }

    re = createFixedInteger(precision);
    idx = 0;

    residual = 1;
    while (idx < precision) {
        factor = find_quorient(residual, prod);

        re[idx] = factor;
        residual = residual - prod[factor];

        idx = idx + 1;
        residual = residual * 10;
        if (residual < 0) {
            return 0;   // error
        }
    }

    return re;
}

function find_quorient(residual, prod) {
    factor = 1;
    while(factor <= 9) {
        if (prod[factor] > residual) {
            break;
        }
        factor = factor + 1;
    }
    factor = factor - 1;
    return factor;
}


function convertFixedIntegerToNumber(input) {
    re = 0;
    len = getSize(input);

    i = 1;
    j = 1;
    while (i <= len) {
        re = re + input[len - i] * j;
        i = i + 1;
        j = j * 10;
    }

    return re;
}

function convertNumberToFixedInteger(input, prec) {
    re = createFixedInteger(prec);

    i = prec - 1;
    while(input > 0) {
        re[i] = modulo(input, 10);
        
        i = i - 1;
        input = input / 10;
    }

    return re;
}


function convertFixedIntegerToString(input) {
    size = getSize(input);
    printString = "";
    i = 0;
    while(i < size) {
        printString = printString + input[i];
        i = i + 1;
    }

    return printString;
}

function printFixedInteger(input) {
    println(convertFixedIntegerToString(input));
}

// get the FixedInteger, but remove the end [dig] digits
function convertFixedIntegerToString_removeTail(input, dig) {
    size = getSize(input);
    printString = "";
    i = 0;
    while(i < size - dig) {
        printString = printString + input[i];
        i = i + 1;
    }

    return printString;
}

function printFixedInteger_removeTail(input, dig) {
    println(convertFixedIntegerToString_removeTail(input, dig));
}




// other tools 

function modulo(dividend, divisor) {
    remain = ((dividend - (divisor * ((dividend / divisor) + 1))) + divisor);
    return remain;
}

