
function rec(redefine) {
    j = 0;
    if (redefine) {
        defineFunction("function rec(redefine) { return 7; }");
        j = rec(0 == 1);
    } else {
        j = rec(0 == 0);
    }
    return j + 1;
}

function main() {
    return rec(0 == 1);
}
