/*
* Test for continue
*/

function main() {
    i = 0;
    while (i < 5) {
        i = i + 1;
        if (i == 2) {
            continue;
        }
        println(i);

    }
}