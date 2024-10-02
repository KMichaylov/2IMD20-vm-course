function two_arg(one, two) {
    return one + two;
}

function three_arg(one, two, three) {
    println(three);
    return one + two;
}

function main() {

    println(two_arg(1, 2, 3));
    println(three_arg(1, 2));
    
}