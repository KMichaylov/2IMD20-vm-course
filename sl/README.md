# VM Course 2024 (2IMD20) Reference Implementation

This repository contains the _reference implementation_ (RI) for the programming language that you will have to develop in the project.

The RI is based on GraalVM's [Simple Language](https://github.com/graalvm/simplelanguage). The implementation in this repository is almost entirely identical to the GraalVM's original code, with only minor modifications.

# Setup

We assume that you can install and configure a JVM and Maven in your system.

The RI should work on any recent Java VM. It was tested on the following version of GraalVM:
```
openjdk 21 2023-09-19
OpenJDK Runtime Environment GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15)
OpenJDK 64-Bit Server VM GraalVM CE 21+35.1 (build 21+35-jvmci-23.1-b15, mixed mode, sharing)
```
which can be downloaded from [GitHub](https://github.com/graalvm/graalvm-ce-builds/releases/tag/jdk-21.0.0).

Any recent version of Maven should work out of the box. We tested with the following version:
```
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Java version: 21, vendor: GraalVM Community
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "14.6.1", arch: "aarch64", family: "mac"
```

The RI has been tested on Linux and Mac OS (apple silicon). We expect Windows to work out of the box, but do not provide testing and support for Windows.

# Building

Build the project with `mvn package`.

# Running a single application

To run the RI, you must define the JAVA_HOME environment variable pointing to your JDK. Then, you can use the `./sl` command:
```
$ echo $JAVA_HOME
/path/to/your/JDK/bin

$ ./sl helloWorld.sl
Hello SL!
```

# Running the unit tests

To run the RI unit tests, you can use the `test_all.sh` bash script:
```
$ ./test_all.sh
```
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


# Building a Native Image

Native image will be discussed during the VM course. If you want to try that out, you can build the project with `mvn package -Pnative`.
To run simple language natively you should then run `./standalone/target/slnative`.
