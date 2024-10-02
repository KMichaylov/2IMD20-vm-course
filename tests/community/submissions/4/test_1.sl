// This test does some more basic recursion, including the stacktrace primitive

function somerecursion(a){
    if (a == 0) {
        println("Goal!");
        println(stacktrace());
        return;
    }

    if (a < 0) {
        println("Ping!");
        a = ((0 - 1) * a) - 1;
    }
    else {
        println("Pong!");
        a = (0 - 1) * a + 1;
    }

    println(a);
    return somerecursion(a);
}

function main() {
    somerecursion(5);
}