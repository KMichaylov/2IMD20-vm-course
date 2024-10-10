/*
 * Comparing stacktraces doesn't work.
 */

function main() {
    stackComparer = stacktrace();
    a = 5;
    b = 6;
    c = a + b;
    println("Current stacktrace:");
    println(stackComparer);
    println(stacktrace());
}