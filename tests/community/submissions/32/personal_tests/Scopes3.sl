/* This is strange, out of scope function gets executed, is this the intended functionality of defineFunction? */ 

function foo() {
    defineFunction("function foo(a, b) { return a / b; }");
    a = 10;
    println(a);
    println(foo(a, 2));
}

function main() {
    foo();
    println(a);
    println(foo(42, 2));
}