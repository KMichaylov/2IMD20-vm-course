# VM Course (2IMD20, 2024) project submission guidelines

In order to simplify grading, please make sure that your submissin _respects_ the following guidelines.

## General guidelines:

* Please upload your submission as a single `.zip` file.
* Please include your submission in a main folder called `{ID}-{surname}`, using your student ID and your surname (e.g., `4242-Jobs`). The root of your `.zip` file should be that folder.
* The root folder in the uploaded `.zip` file should have the same structure of the project repository. Your implementation should be in the skeleton folder.
* To save space, you can remove the reference implementation folder from your submission. Please do _not_ remove the `.sh` files.
* Please do _not_ include in your submission `.class` and `.jar` files. 
* In general, please do not include other files that are not needed to build and run (e.g., `.zip` files). Likewise, please do not include your git history in the `.zip` file!
* Please test your submission using the Docker container. Make sure you include everything that is needed to build and run.
* Please include a short PDF report (see below). The report should be in your root folder.

Points will be subtracted for project submissions that do not follow these guidelines (e.g., build failures in the Docker container). I will not try to debug your code.

To assess your _baseline BCI implementation_, I will distribute points based on the following criteria:
1. Completeness: full mark if the VM passes _all_ unit tests _and_ can run the benchmarks. 
2. Implementation: I will do a full code review of your code.

## Options and flags

Optimizations should be enabled by passing one of the following config flags:
```
   -jit                Enable JIT compilation (and all other options)
   -inline-caches      Enable Inline Caches
   -string-ropes       Enable String Ropes
   -array-strategies   Enable Array Storage Strategies
```

For each option, I will distribute points based on the following criteria:
1. Completeness: the VM passes _all_ unit tests when the option is enabled. Benchmarks are considered unit tests (pass/fail).
2. Implementation: I will do a full code review of your code.

If you have _not_ implemented an option, your VM should _terminate immediately_, and print the message `Optimization not supported`. E.g.:
```
$ /toy --jit foo.sl
> Optimization not supported
```
You can freely use stdout or stderr for this.

Please avoid "partial optimizations" as much as possible. It is expected that optimizations are implemented on top of a working baseline BCI implementation: if your baseline implementation passes only 20% of the tests, there is no point in implementing a JIT compiler.

If you have implemented an option, you _must_ declare it explicitly in the PDF report (see below). Otherwise, I will not grade them even if they work.

## PDF report

The PDF report is your way to communicate what you have done. I will not grade the quality of the report itself, but the report will be used as the main "driver" for my code review. Therefore, please make sure you write a concise and clear report containing all the informaiton below:

0. Of course, add your name, student ID, and email.
1. Bytecode design: describe the design of your bytecode instructions, and explain why you decided to implement your instructions in a certain way. For example, why a specific instruction set? Stack-based or register based? Did you implementt quickening? superinstructions? etc.
2. Interpreter implementation: list the main classes where your implementation can be found. For each class, add a simple description (1/2 sentences) of what the class does (E.g., "my bytecode loop is in `BCI.java`, and the main loop is in `loop()`. It contains the main BCI loop...")
3. Optimizations: List the optimizations you have implemented.
4. Optimizations implementation: like for the interpreter, list the main classes where your optimizations are. For each optimization, add a simple description (1/2 sentences) of what the class does (E.g., my inline caches are in `IC.java`, and in `Shapes.java`. They are used in instruction `GET` ...)
5. Performance impact. If you think that your optimizations bring a performance boost on a given benchmark, please mention the benchmark(s) and the option(s) explicitly in this section. Provide a list with flags and expected performance impact (e.g., "the `-jit` flag improves performance for `Fibonacci.sl`, the `-string-ropes` improves `XXX.sl`" and so on.) Please avoid claiming performance improvements for all benchmarks unless you _really_ think that your implementation improves all of them (might be possible for the `-jit` flag, but unlikely for other optimizations). If you want, feel free to explicitly claim the expected performance improvement as measured on your local setup (e.g., "on my machine, `Fibonacci.sl` is 3x faster when using the `-jit` flag"). I will take that into account when collecting performance data for your submission.

Please include in your submission a short PDF file. The file should be named `{ID}-{surname}-REPORT.pdf` (e.g., `4242-Jobs-REPORT.pdf`) and contain one separate section for each of the points above.
