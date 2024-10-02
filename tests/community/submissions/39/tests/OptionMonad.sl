function main() {
    num = Some(80);

    i = 0;
    while (i < 8) {
        print(num);
        num = bind(num, safeHalve);
        i = i + 1;
    }
}

// Halves the number, but returns None if the number is odd because decimal numbers are not supported
function safeHalve(num) {
    if (even(num)) {
        return Some(num / 2);
    }

    return None;
}

function bind(option, fn) {
    if (option != None) {
        return fn(option.value);
    }

    return None;
}

function even(num) { return num == num / 2 * 2; }

function print(option) {
    if (option != None) {
        println("Some(" + option.value + ")");
    } else {
        println("None");
    }
}

function Some(value) {
    option = new();
    option.value = value;

    return option;
}
