# VM Course 2024 (2IMD20) Project folder

This repository contains the basic code and tests for the programming language VM that you will have to develop in the project.

The repository contains the following folders:

* `./reference-impl`: the reference implementation (RI) for the language that you will implement.
* `./vm-skeleton`: the starting point for your project.
* `./tests`: the unit tests for the language. You will contribute more tests.
* `./benchmarks`: performance benchmarks for the project. You will contribute more benchmarks.

# Running the unit tests

To run the RI unit tests, you can use the `test_all.sh` bash script with the `sl` executable:
```
$ ./test_all.sh ./reference-impl/sl
```
Note that in this case we are using `./reference-impl`, while you will have to test your code against `./vm-skeleton/sl`.

All tests pass on the RI, and the output should look like:
```
[OK] Output for ./tests/Ackermann matches expected output.
[OK] Output for ./tests/Add matches expected output.
[OK] Output for ./tests/Arithmetic matches expected output.
[OK] Output for ./tests/ArrayLike matches expected output.
[OK] Output for ./tests/Break matches expected output.
...
...
[OK] Error output for ./tests/UndefinedFunction03 matches expected error.
========== ALL DONE ==========
  tests passed: 57
  tests failed: 0
```

The actual number of unit tests will change during the VM course, so `57` is just an example.
