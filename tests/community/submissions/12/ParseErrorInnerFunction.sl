/*
* Test for lexical scoping
*/

function outer() {
    a = 1;
    function inner() {
        a = 2;
    }
    inner();
    println(a);
}

function main() {
    println("ParsingError should be thrown");
}