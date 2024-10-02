function main() {
    x = 5;
    println("Global scope: " + x);

    foo();
}

function foo() {
    x = 10;
    println("Local scope: " + x);
}