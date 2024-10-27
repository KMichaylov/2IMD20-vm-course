function main() {
    array = new();
    array[0] = 2;
    array[1] = 3;


    print(array);

    println("Increment:");
    array = map(array, increment);
    print(array);

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

function increment(num) { return num + 1; }

function print(array) {
    str = "";

    i = 0;
    while (i < getSize(array)) {
        str = str + array[i] + " ";
        i = i + 1;
    }

    println(str);
}
