# VM Course project

The goal of the VM course project is to implement a fully working language VM for a fictional language called `Toy`. This is an individual project.

To pass the course, it is enough to have a functional implementation. The more features you will implement, the higher your final grade will be.


## Project overview

Your implementation of the `Toy` language must be a Bytecode-style interpreter (BCI) implemented using the Java language. The semantics of the language is provided, as well as a workig reference implementation that can be used to validate your implementation. The reference implementation is based on a open-source project called [Truffle](https://www.graalvm.org/latest/graalvm-as-a-platform/language-implementation-framework/), and therefore is very different (but functionally equivalent) than what you will need to implement. Moreover, it's an AST interpreter, while you are expected to develop a BCI.

You are free to have a look at the source code of the Truffle-based reference implementation (called "simple language"), but you are not allowed to copy and use code and functionalities from the Truffle framework. In other words, all code you submit for the project must be code that was developed by yourself. The only exception is the Java standard library: that is always available to every Java application, including your BCI.

Besides being a BCI, you have _complete freedom_ in the design and implementation of your VM. For example, you are free to design:
* Instructions set
* Stack vs register-based vs hybrid
* Function calls
* Interpreter optimizations
* etc

A skeleton is provided to help you getting started (with a working parser for the language), but strictly speaking you are free to re-implement everything (including the parser) from scratch.

## Deadlines and progress

The project has a final "big" submission deadline, and two "small" intermediate milestones.

The two intermediate milestones are:
* M1: _Unit tests upload_. You are expected to provide 5 simple unit tests for the `Toy` language. All unit tests contributed in M1 will be shared and will be used to validate your VM implementation.
* M2: _Benchmark_. Similarly, you will be required to develop and upload a microbenchmark for the `Toy` language. Like with the unit tests, the benchmarks will be shared and used to measure the performance of your VM.

The final submission deadline will be during the exam week, and you will have to upload your final VM implementation as a standalone `.zip` file.

The actual deadlines for the milestones and the final submission will be announced on Canvas. We will also publish submission guidelines for the final submission.

## VM Features

You are expected to implement a fully working VM, and contribute a few unit tests and benchmarks.

On top of that, you are expected to implement a number of optimizations that we will discuss during the course:

* Object storage (hidden classes)
* Array strategies
* Lazy Strings/ropes
* (Minimal) JIT compilation

Each optimization must be disabled by default, and enabled only with a specific command line flag.


## Repository

The source code for the project (unit tests, skeleton, reference impl etc) is on the TU/e GitLab. Please check Canvas for the actual URL.

## Grading

To pass the course, your implementation of the language VM must be functinal and documented. More precisely, this means:
* you must pass all unit tests collected after M1 in all possible configurations you have implemented
* you must upload unit tests (M1) and benchmarks (M2)
* you must write a short document (max 3 pages) describing your design decisions. Specifically, you must include a description of your BC instructions set, and of the main classes/files where your optimizations can be found in the codebase. Implementing optimizations contribute to a better grade.


### Grading scheme

The project will be graded in the following way:

up to 45pts - Working VM (all tests, all configs)
up to 3pts - Unit tests upload
up to 3pts - Benchmark upload
up to 4pts - Report (max 3 pages)

up to 10pts - Object storage (hidden classes)
up to 10pts - Array strategies
up to 10pts - Lazy Strings/ropes
up to 15pts - JIT compilation

5 “bonus” points for the best performing VM (BCI only)
5 “bonus” points for the best performing VM with optimizations


### Points distribution

A working implementation with unit tests and a written report is 55 points, which is enough to pass the course:

* `  < 55 `  -- Fail
* `[55-64)`  --  6.0
* `[65-74)`  --  7.0
* `[75-84)`  --  8.0
* `[85-94)`  --  9.0
* `[95-100]` -- 10.0

Where possible, points will be awarded in a fully proportional way. This applies for example to the unit tests grading scheme(s): if your VM passes 100% of the unit tests, the points will be 45. If your implementation passes only 20%, you will have 9 points. Of course, rounding (up) will be used.

For the optimizations, I will have a detailed look at your code, and assign points based on the quality of your implementation. I will still assign points in a proportional way, i.e., if the best submission has implemented 3 (working) optimizations, that submission will receive max points. If your submission has implemented only 1 optimization, you will receive 1/3 and so on.

Optimizations must _not_ have a negative impact on your performance (by definition). This means that an optimization is valid (and gives you points) if:
* It does not have a negative performance impact (on all benchmarks!)
* It improves your performance on at least one benchmark (by a factor measurable in a statistically reliable way).

For performance, points will be awarded based on reproducible experiments. The best performing VM(s) will be awarded extra "bonus" points.

## Testing environment

The project must work on Linux. Being Java a portable language, you can implement the project using any platform of your choice, but we do not provide assistance to debug platform-specific issues (e.g., how to install Java on Windows). We will provide a Dockerfile that can be used to verify that your submission works on Linux (before the final deadline). Grading will be done mostly automatically, so it is important to make sure that all submissions are compliant and can be executed on Linux. As the final deadline approaches we will upload the exact submission guidelines on Canvas.
