function greet() {
  println("Hello!");
}

function main() {
    greet();

    defineFunction("function greet() { println(42); }");

    greet();
}