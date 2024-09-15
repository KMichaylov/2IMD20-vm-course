# VM course BCI


This is the basic skeleton for the BCI you will have to implement in the VM course.

The BCI implementation will be considered complete if it passes 100% of all unit tests that will be collected over the upcoming weeks.

Notable classes that will need some work:
* The package contains a parser for the Toy language. It is largely the same parser of the reference implementation.
* All AST-related classes are in the `nl.tue.vmcourse.toy.ast` package. 
* All BCI-related classes should live in `nl.tue.vmcourse.toy.bci`.
* The `nl.tue.vmcourse.toy.lang` contains basic classes to implement some concepts needed at runtime (e.g., stack frames and objects).
* The `nl.tue.vmcourse.toy.ToyLauncher` is the main entry point to your BCI.
* The `AstToBciAssembler` class should be responsible for creating a BC instruction stream from an input AST.
* The `ToyBciLoop` should implement the main BCI loop. The design of the BC is entirely up to you, as well as the definition of your BC format and ISA. Be creative!
* The `toy` script can be used to start the VM. Building can be done using `mvn clean package` (same as the reference impl).

Important: this is a simple skeleton to help you getting started. You are free to change _anything_, and in fact a few classes are intentionally left empty or underspecified.

