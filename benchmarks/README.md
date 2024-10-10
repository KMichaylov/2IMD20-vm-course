# Benchmarks howto

A benchmark for the `toy` language is a simple application that performs some computations using the langauge.

For a benchmark to be acceptable, it must respect the following properties:
* The benchmark should execute multiple times (`ITERATIONS`) your code.
* Each iteration should be stateless, i.e., should not depend on data produced by the previous iterations.
* The total execution _time_ of the benchmark should be in the range 10~20 seconds (approx.). Benchmarks that take longer than that are not acceptable
* The benchmark should record execution time using the `nanoTime()` builtin. You can use the same implementaiton of the `sl` reference implementation, which simply calls into Java's `nanoTime()` method.
* The first ~80% iterations should be discarded. This is to give the VM enough time to "warm up" and optimize your code
* The last ~20% iterations should be recorded, and you should report the average iteration execution time.
* The benchmark should print _one single line_ to thje stdout using the format `XXX : 424242`, where `424242` is the average execution time (lower is better). Of course, feel free to pick a fancy name for your benchmark!

The `Fibonacci.sl` benchmark in this folder can be used as an example benchmark. You can simply copy&paste the `main()` method of `Fibonacci.sl` and adapt it to your workload (i.e., change name, change iterations, etc.)
