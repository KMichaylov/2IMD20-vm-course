/*
 * Comparing stacktraces doesn't work.
 */

function main() {
    stackComparer = stacktrace();
    println("Current stacktrace:");
    println(stacktrace());
    if (stackComparer == stacktrace()) {
        println("Stacktrace hasn't changed");
    } else {
        println("Something is wrong?");
        println(stacktrace());
    }
}