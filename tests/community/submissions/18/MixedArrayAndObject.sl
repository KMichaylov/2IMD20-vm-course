
function foo() {
    return 3;
}

function bar() {
    return "asdfj";
}

function baz() {
    return ":3";
}

function main() {
    x = new();
    x[0] = 123;
    x[foo()] = 456;
    println(getSize(x));
    x[bar()] = 7;
    x[baz()] = 8;
    println(getSize(x));
    return x[0] + x[3] + x.asdfj + x[":3"];
}
