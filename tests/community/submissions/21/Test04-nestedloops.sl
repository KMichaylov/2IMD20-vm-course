/**
    Test for nested loops.
*/
function main() {
    i = 0;
    while (i < 10) {
        j = 0;
        while (j < 5) {
            println("(" + i + ", " + j + "), ");
            j = j + 1;
        }
        i = i + 1;
    }

    return 1;
}