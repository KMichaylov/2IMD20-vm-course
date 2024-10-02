function soHigh() {
  println("I am above everything.");
}

function main() {
  println("Going to call foo!");
  foo();
}

function foo() {
  println("I am under main!");
  evenMoreUnder();
}

function evenMoreUnder() {
  println("I am under foo!");
  soHigh();
}
