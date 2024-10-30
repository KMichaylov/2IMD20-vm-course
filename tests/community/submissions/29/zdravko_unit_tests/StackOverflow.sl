function main() {
    x = 0;
    recurse(x);
}

function recurse(x) {
    x = x + 1;
    recurse(x);
    
}