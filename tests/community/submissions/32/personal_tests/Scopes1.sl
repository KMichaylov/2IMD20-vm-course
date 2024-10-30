/* This is strange, out of scope function gets executed, is this the intended functionality of defineFunction? */ 

function main() {
    if (1 == 1) {
        defineFunction("function foo(a, b) { return a / b; }");
        a = 10;
        println(a);
        println(foo(a, 2));
    }

    println(a);
    println(foo(42, 2));
}