function main() {
    array = new();
    array[0] = 2;
    array[1] = 3;
    array[2] = 5;
    array[3] = 7;
    array[4] = 11;

    print(array);

    println("Increment:");
    array = map(array, increment);
    print(array);

    println("Filter out odd numbers:");
    array = filter(array, even);
    print(array);

    println("Sum:");
    sum = foldl(array, add);
    println(sum);
}

// Apply fn to every element of array
function map(array, fn) {
    out = new();

    i = 0;
    while (i < getSize(array)) {
        out[i] = fn(array[i]);
        i = i + 1;
    }

    return out;
}

// Only keep elements where fn returns true
function filter(array, fn) {
    out = new();

    i = 0;
    j = 0;
    while (i < getSize(array)) {
        if (fn(array[i])) {
            out[j] = array[i];
            j = j + 1;
        }
        i = i + 1;
    }

    return out;
}

// Apply fn to the first two elements of the array recursively until a single value remains
function foldl(array, fn) {
    len = getSize(array);

    if (len == 1) {
        return array[0];
    } else {
        next = new();
        next[0] = fn(array[0], array[1]);

        i = 1;
        while (i < len - 1) {
            next[i] = array[i + 1];
            i = i + 1;
        }

        return foldl(next, fn);
    }
}

function even(num) { return num == num / 2 * 2; }
function increment(num) { return num + 1; }
function add(num1, num2) { return num1 + num2; }

function print(array) {
    str = "";

    i = 0;
    while (i < getSize(array)) {
        str = str + array[i] + " ";
        i = i + 1;
    }

    println(str);
}
