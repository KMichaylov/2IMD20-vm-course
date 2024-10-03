/** 
    Test case that tests if continue works properly.
*/

function main() {
    i = 0;
    num = 0;
    while (i < 20) {
       if (i != 10) {
        i = i + 1;
        continue;
       }
       num = 10;
       i = i + 1;
    }

    println(num);
}