function main() {
    i = 0;
    while (i < 100) {
      i = add_one(i);
    }
    return i;
}

function add_one(i) {
    return i + 1;
}