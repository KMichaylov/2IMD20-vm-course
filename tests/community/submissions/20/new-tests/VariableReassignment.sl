/*
 * Testing whether reassigning variables works.
 */

function main() {
    i = 42;
    i = false;
    if (i == 42) {
        println("i hasn't changed");
    }
    if (i == false) {
        println("i has changed");
    } else {
        println("Something is wrong?");
    }
}