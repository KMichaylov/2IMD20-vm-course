function min(x, y) {
    if (x < y) {
        return x;
    } else {
        return y;
    }
}

function count(max, left, right) {
    steps = 0;

    while (left < max) {
        steps = steps + min(max + 1, right) - left;
        left = left * 10;
        right = right * 10;
    }

    return steps;
}

function find(max, place) {
    now = 1;
    place = place - 1;
    
    while (place > 0) {
        steps = count(max, now, now + 1);

        if (steps <= place) {
            now = now + 1;
            place = place - steps;
        } else {
            now = now * 10;
            place = place - 1;
        }
    }

    return now;
}

function main() {
    println(find(9223372036854775807, 2016388630118388896));
}
