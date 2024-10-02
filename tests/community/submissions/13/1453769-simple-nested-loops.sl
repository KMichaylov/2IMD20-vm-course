/**
 * Tests some nested loop examples.
 */

function main() {
    index = 0;
    jump = 0;
    while (index < 10) {
        while (index > 3) {
            index = index - 4;
        }
        jump = jump + 1;
        index = index + jump;
        println(index);
    }
}