/*
 * Testing wether nested functions work.
 */

function multiply(a, b) {
  return a * b;
}

function divide(a, b) {
  return a / b;
}

function complexFunc(m, d) {
  println(m(d(2, 2), m(2, 3)));
}

function main() {
    complexFunc(multiply, divide);
}  