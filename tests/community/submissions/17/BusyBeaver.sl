/*
 * BB(5) = 47,176,870
 * Implemented per https://wiki.bbchallenge.org/wiki/BB(5)
 */

function main() {
    state = 1;
    i = 0;
    array = new();
    H = 0;
    L = 0;
    array[i] = 0;
    step = 0;
    while (state != 0) {
        if (i < L) {
            L = L - 1;
            array[L] = 0;
        }
        if (i > H) {
            H = H + 1;
            array[H] = 0;
        }
        if (state <= 2) {
            if (state == 1) {
                if (array[i] == 0) {
                    array[i] = 1;
                    state = 2;
                    i = i + 1;
                } else {
                    state = 3;
                    i = i - 1;
                }
            } else {
                if (array[i] == 0) {
                    array[i] = 1;
                    state = 3;
                } else {
                    state = 2;
                }
                i = i + 1;
            }
        } else {
            if (state == 3) {
                if (array[i] == 0) {
                    array[i] = 1;
                    state = 4;                    
                    i = i + 1;
                } else {
                    array[i] = 0;
                    state = 5;
                    i = i - 1;
                }
            } else {
                if (state == 4) {
                    if (array[i] == 0) {
                        array[i] = 1;
                        state = 1;
                    } else {
                        state = 4;
                    }
                    i = i - 1;
                } else {
                    if (array[i] == 0) {
                        array[i] = 1;
                        state = 0;
                        i = i + 1;
                    } else {
                        array[i] = 0;
                        state = 1;
                        i = i - 1;
                    }
                }
            }
        }

        step = step + 1;
    }

    println(step + " steps");
    println("Final state (" + L + ".." + H + ")");
    i = L;
    while (i <= H) {
        println(array[i]);
        i = i + 1;
    }
}
