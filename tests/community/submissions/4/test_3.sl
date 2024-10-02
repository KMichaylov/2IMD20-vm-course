// This test double definitions for functions

function main() {
    println("hello world!");
    eval("sl", "function main(){return (0 - 1);}");
    println(main());
}