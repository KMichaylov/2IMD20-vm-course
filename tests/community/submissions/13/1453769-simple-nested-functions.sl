/**
 * Tests some nested loop examples.
 */

function print(number) {
    println(number);
}

function execute(number, func) {
    return func(number);
}

function double(x) {
    return x * 2;
}

function triple(x) {
    return x * 3;
}

function main() {
    defineFunction("function quadruple(x) { return x * 4; }");
    value = 1;
    print(value);
    value = double(value);
    print(value);
    value = triple(value);
    print(value);
    value = quadruple(value);
    print(value);
    value = execute(value, double);
    print(value);
    defineFunction("function func(x, func) { return func(x); }");
    value = func(value, triple);
    print(value);

    // This last function is intentionally not using the return value
    // to ensure that the functions don't affect the number when unintended.
    func(value, double);
    print(value);
}