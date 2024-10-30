/*
* Test index out of bounds error
*/

function main() {
    a = new();
    a[0] = 1;
    a[1] = 2;

    println(a[0]);
    println(a[1]);
    println(a[2]);
}